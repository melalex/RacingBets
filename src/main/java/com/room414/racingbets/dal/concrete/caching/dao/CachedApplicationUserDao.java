package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.ApplicationUserCache;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 12 Mar 2017
 */
public class CachedApplicationUserDao extends CacheCrudDao<ApplicationUser> implements ApplicationUserDao {
    protected ApplicationUserDao dao;
    protected ApplicationUserCache cache;

    // TODO: ivar inheritance
    CachedApplicationUserDao(ApplicationUserDao dao, ApplicationUserCache cache) {
        super(dao, cache);
        this.dao = dao;
        this.cache = cache;
    }

    @Override
    public void create(ApplicationUser entity) {
        super.create(entity);

        String key = String.format("find:login:password:%s:%s", entity.getLogin(), entity.getPassword());

        cache.deleteOneCached(key);
    }

    @Override
    public List<ApplicationUser> search(String loginPart, int offset, int limit) {
        String key = String.format("find:login:part:%s:%d:%d", loginPart, limit, offset);

        return cache.getManyCached(key, () -> dao.search(loginPart, offset, limit));
    }

    @Override
    public int searchCount(String loginPart) {
        String key = "find:login:part:count:" + loginPart;

        return cache.getCachedCount(key, () -> dao.searchCount(loginPart));
    }

    @Override
    public ApplicationUser findByLogin(String login) {
        String key = getFindByLoginAndPasswordKey(login, password);

        return cache.getOneCached(key, () -> dao.findByLogin(login));
    }

    @Override
    public List<ApplicationUser> findByLoginAndEmail(String login, String email) {
        String key = String.format("find:by:login:email:%s:%s", login, email);

        return cache.getManyCached(key, () -> dao.findByLoginAndEmail(login, email));
    }

    @Override
    public void confirmEmail(long id) {
        removeFromCacheById(id);
        cache.deleteManyCached();
        dao.confirmEmail(id);
    }

    @Override
    public void addRole(long userId, Role role) {
        dao.addRole(userId, role);
        cache.deleteManyCached();
        removeFromCacheById(userId);
    }

    @Override
    public void removeRole(long userId, Role role) {
        dao.removeRole(userId, role);
        cache.deleteManyCached();
        removeFromCacheById(userId);
    }

    @Override
    public boolean tryGetMoney(long id, BigDecimal amount) {
        removeFromCacheById(id);
        cache.deleteManyCached();
        return dao.tryGetMoney(id, amount);
    }

    @Override
    public void putMoney(long id, BigDecimal amount) {
        dao.putMoney(id, amount);
        removeFromCacheById(id);
        cache.deleteManyCached();
    }

    private String  getFindByLoginAndPasswordKey(String login, String password) {
        return String.format("find:login:password:%s:%s", login, password);
    }

    private void removeFromCacheById(long id) {
        ApplicationUser user = find(id);

        if (user != null) {
            String idLoginPassword = getFindByLoginAndPasswordKey(user.getLogin(), user.getPassword());
            String idKey = getFindByIdKey(id);

            cache.deleteOneCached(idLoginPassword);
            cache.deleteOneCached(idKey);
        }
    }

}
