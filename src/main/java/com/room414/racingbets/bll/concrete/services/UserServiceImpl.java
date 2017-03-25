package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;
import org.apache.commons.codec.binary.Base64;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceImpl implements UserService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;
    private String randomAlgorithm;

    public UserServiceImpl(UnitOfWorkFactory factory, String randomAlgorithm) {
        this.factory = factory;
        this.randomAlgorithm = randomAlgorithm;
    }

    private String getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance(randomAlgorithm);
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return new String(salt);
        } catch (NoSuchAlgorithmException e) {
            String message = "No algorithm named " + randomAlgorithm;
            throw new BllException(message, e);
        }
    }

    private String getSecurePassword(String passwordToHash, String salt) {
        String passwordHash = Base64.encodeBase64URLSafeString(passwordToHash.getBytes());
        return Base64.encodeBase64URLSafeString((passwordHash + salt).getBytes());
    }

    private void hashPassword(ApplicationUser user) {
        String salt = getSalt();
        String password = getSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password);
    }

    private boolean isValid(String password, ApplicationUser user) {
        String passwordHash = getSecurePassword(password, user.getSalt());
        return passwordHash.equals(user.getPassword());
    }

    @Override
    public int create(UserDto user) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao dao = unitOfWork.getApplicationUserDao();
            List<ApplicationUser> users = dao.findByLoginAndEmail(user.getLogin(), user.getEmail());

            int result = 0;
            for (ApplicationUser u: users){
                if (u.getEmail().equals(user.getEmail())) {
                    result |= EMAIL_EXISTS;
                }
                if (u.getLogin().equals(user.getLogin())) {
                    result |= LOGIN_EXISTS;
                }
            }

            if (result == 0) {
                user.addRole(Role.HANDICAPPER);
                user.setBalance(new BigDecimal(0));
                ApplicationUser entity = mapper.map(user, ApplicationUser.class);
                hashPassword(entity);
                unitOfWork.getApplicationUserDao().create(entity);
                unitOfWork.commit();
                return SUCCESS;
            } else {
                return result;
            }
        }
    }

    @Override
    public void addRole(long id, Role role) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().addRole(id, role);
            unitOfWork.commit();
        }
    }

    @Override
    public void removeRole(long id, Role role) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().removeRole(id, role);
            unitOfWork.commit();
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().delete(id);
            unitOfWork.commit();
        }
    }

    @Override
    public void confirmEmail(long userId) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().confirmEmail(userId);
            unitOfWork.commit();
        }
    }

    @Override
    public UserDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUser user = unitOfWork.getApplicationUserDao().find(id);
            return mapper.map(user, UserDto.class);
        }
    }

    @Override
    public UserDto findByLoginPassword(String login, String password) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUser entity = unitOfWork
                    .getApplicationUserDao()
                    .findByLogin(login);

            if (entity == null) {
                return null;
            }

            return isValid(password, entity) ? mapper.map(entity, UserDto.class) : null;
        }
    }

    private List<UserDto> mapList(List<ApplicationUser> source) {
        return source.stream()
                .map(e -> mapper.map(e, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

            List<ApplicationUser> entities = ownerDao.findAll(pager.getOffset(), pager.getLimit());
            int count = ownerDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<UserDto> search(String login, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

            List<ApplicationUser> entities = ownerDao.search(login, pager.getOffset(), pager.getLimit());
            int count = ownerDao.searchCount(login);

            pager.setCount(count);

            return mapList(entities);
        }
    }
}
