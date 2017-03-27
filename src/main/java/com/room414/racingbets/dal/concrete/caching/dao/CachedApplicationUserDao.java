package com.room414.racingbets.dal.concrete.caching.dao;

import com.room414.racingbets.dal.abstraction.cache.ApplicationUserCache;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.concrete.caching.caffeine.base.CacheCrudDao;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

        String key = String.format("search:login:password:%s:%s", entity.getLogin(), entity.getPassword());

        cache.deleteOneCached(key);
    }

    @Override
    public List<ApplicationUser> search(String loginPart, int offset, int limit) {
        String key = String.format("search:login:part:%s:%d:%d", loginPart, limit, offset);

        return cache.getManyCached(key, () -> dao.search(loginPart, offset, limit));
    }

    @Override
    public int searchCount(String loginPart) {
        String key = "search:login:part:count:" + loginPart;

        return cache.getCachedCount(key, () -> dao.searchCount(loginPart));
    }

    @Override
    public ApplicationUser findByLogin(String login) {
        String key = getFindByLogin(login);

        return cache.getOneCached(key, () -> dao.findByLogin(login));
    }

    @Override
    public List<ApplicationUser> findByLoginAndEmail(String login, String email) {
        String key = String.format("search:by:login:email:%s:%s", login, email);

        return cache.getManyCached(key, () -> dao.findByLoginAndEmail(login, email));
    }

    @Override
    public void confirmEmail(long id) {
        removeFromCacheById(id);
        cache.deleteManyCached();
        dao.confirmEmail(id);
    }

    @Override
    public void setRoles(long userId, Set<Role> roles) {
        dao.setRoles(userId, roles);
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

    private String getFindByLogin(String login) {
        return String.format("search:login:%s", login);
    }

    private void removeFromCacheById(long id) {
        ApplicationUser user = find(id);

        if (user != null) {
            String idLoginPassword = getFindByLogin(user.getLogin());
            String idKey = getFindByIdKey(id);

            cache.deleteOneCached(idLoginPassword);
            cache.deleteOneCached(idKey);
        }
    }

}
