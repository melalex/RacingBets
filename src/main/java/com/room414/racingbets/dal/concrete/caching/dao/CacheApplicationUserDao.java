package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.caches.ApplicationUserCache;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CacheApplicationUserDao extends CacheCrudDao<ApplicationUser> implements ApplicationUserDao {
    protected ApplicationUserDao dao;
    protected ApplicationUserCache cache;

    // TODO: ivar inheritance
    CacheApplicationUserDao(ApplicationUserDao dao, ApplicationUserCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public void create(ApplicationUser entity) throws DalException {
        super.create(entity);

        String key = String.format("find:login:password:%s:%s", entity.getLogin(), entity.getPassword());

        cache.deleteOneCached(key);
    }

    @Override
    public List<ApplicationUser> findByLoginPart(String loginPart, long offset, long limit) throws DalException {
        String key = String.format("find:login:part:%s:%d:%d", loginPart, limit, offset);

        return cache.getManyCached(key, () -> dao.findByLoginPart(loginPart, offset, limit));
    }

    @Override
    public long findByLoginPartCount(String loginPart) throws DalException {
        String key = "find:login:part:count:" + loginPart;

        return cache.getCachedCount(key, () -> dao.findByLoginPartCount(loginPart));
    }

    @Override
    public ApplicationUser findByLoginAndPassword(String login, String password) throws DalException {
        String key = getFindByLoginAndPasswordKey(login, password);

        return cache.getOneCached(key, () -> dao.findByLoginAndPassword(login, password));
    }

    @Override
    public boolean confirmEmail(long id) throws DalException {
        removeFromCacheById(id);
        cache.deleteManyCached();
        return dao.confirmEmail(id);
    }

    @Override
    public void addRole(long userId, Role role) throws DalException {
        dao.addRole(userId, role);
        cache.deleteManyCached();
        removeFromCacheById(userId);
    }

    @Override
    public void removeRole(long userId, Role role) throws DalException {
        dao.removeRole(userId, role);
        cache.deleteManyCached();
        removeFromCacheById(userId);
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) throws DalException {
        removeFromCacheById(id);
        cache.deleteManyCached();
        return dao.tryGetMoney(id, amount);
    }

    @Override
    public void putMoney(long id, BigDecimal amount) throws DalException {
        dao.putMoney(id, amount);
        removeFromCacheById(id);
        cache.deleteManyCached();
    }

    private String  getFindByLoginAndPasswordKey(String login, String password) {
        return String.format("find:login:password:%s:%s", login, password);
    }

    private void removeFromCacheById(long id) throws DalException {
        ApplicationUser user = find(id);

        if (user != null) {
            String idLoginPassword = getFindByLoginAndPasswordKey(user.getLogin(), user.getPassword());
            String idKey = getFindByIdKey(id);

            cache.deleteOneCached(idLoginPassword);
            cache.deleteOneCached(idKey);
        }
    }

}
