package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.CheckResult;
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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceImpl implements UserService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;
    private String randomAlgorithm;
    private String hashAlgorithm;
    private String encoding;

    public UserServiceImpl(
            UnitOfWorkFactory factory,
            String randomAlgorithm,
            String hashAlgorithm,
            String encoding
    ) {
        this.factory = factory;
        this.randomAlgorithm = randomAlgorithm;
        this.hashAlgorithm = hashAlgorithm;
        this.encoding = encoding;
    }

    class CheckResultImpl implements CheckResult {
        private List<ApplicationUser> applicationUsers;
        private String login;
        private String email;

        private CheckResultImpl(List<ApplicationUser> applicationUsers, String login, String email) {
            this.applicationUsers = applicationUsers;
            this.login = login;
            this.email = email;
        }

        public boolean isEmailExists() {
            return applicationUsers
                    .stream()
                    .anyMatch(u -> u.getEmail().equals(email));
        }

        public boolean isLoginExists() {
            return applicationUsers
                    .stream()
                    .anyMatch(u -> u.getLogin().equals(login));
        }
    }

    private byte[] getSalt() {
        try {
            SecureRandom sr = SecureRandom.getInstance(randomAlgorithm);
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            String message = "No algorithm named " + randomAlgorithm;
            throw new BllException(message, e);
        }
    }

    private String getSecurePassword(String passwordToHash, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes(encoding));
            StringBuilder sb = new StringBuilder();

            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            String message = "No algorithm named " + hashAlgorithm;
            throw new BllException(message, e);
        } catch (UnsupportedEncodingException e) {
            String message = "No encoding named " + encoding;
            throw new BllException(message, e);
        }
    }

    private void hashPassword(ApplicationUser user) {
        byte[] salt = getSalt();
        String password = getSecurePassword(user.getPassword(), salt);
        String saltString = Base64.encodeBase64String(salt);
        user.setSalt(saltString);
        user.setPassword(password);
    }

    private boolean isValid(String password, ApplicationUser user) {
        // salt: VLHQoyp60NWpJ/OMuN9hwQ==
        // password: secure
        // hash: eea460d6c550e67f7e7797ead57823327ec7fa2afda6f72ba2a9582d15aa85a7adfe97c326a1a5260413e3217515d2b815179c52dea6bbf8a4dbce5ec48ca94f
        // [84, -79, -48, -93, 42, 122, -48, -43, -87, 39, -13, -116, -72, -33, 97, -63]

        byte[] salt = Base64.decodeBase64(user.getSalt());
        String passwordHash = getSecurePassword(password, salt);
        return passwordHash.equals(user.getPassword());
    }

    @Override
    public CheckResult checkLoginAndEmail(String login, String email) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao dao = unitOfWork.getApplicationUserDao();
            List<ApplicationUser> users = dao.findByLoginAndEmail(login, email);

            return new CheckResultImpl(users, login, email);
        }
    }

    @Override
    public void create(UserDto user) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            user.addRole(Role.HANDICAPPER);
            user.setBalance(new BigDecimal(0));
            // TODO: change to false
            user.setEmailConfirmed(true);
            ApplicationUser entity = mapper.map(user, ApplicationUser.class);
            hashPassword(entity);
            unitOfWork.getApplicationUserDao().create(entity);
            unitOfWork.commit();
            user.setId(entity.getId());
        }
    }

    @Override
    public void setRoles(long id, Set<Role> roles) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().setRoles(id, roles);
            unitOfWork.commit();
        }
    }

    @Override
    public void update(UserDto user) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao dao = unitOfWork.getApplicationUserDao();

            ApplicationUser entityToUpdate = mapper.map(user, ApplicationUser.class);
            ApplicationUser entityFromStorage = dao.find(user.getId());

            if (entityToUpdate.equals(entityFromStorage)) {
                return;
            }

            if (!entityToUpdate.getEmail().equals(entityFromStorage.getEmail())) {
                entityToUpdate.setEmailConfirmed(false);
            }

            byte[] salt = Base64.decodeBase64(entityFromStorage.getSalt());

            entityToUpdate.setPassword(getSecurePassword(user.getPassword(), salt));

            dao.update(entityToUpdate);

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
    public void putMoney(long id, BigDecimal amount) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().putMoney(id, amount);
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

    @Override
    public boolean tryGetMoney(long id, BigDecimal betSize) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            boolean result = unitOfWork
                    .getApplicationUserDao()
                    .tryGetMoney(id, betSize);

            unitOfWork.commit();

            return result;
        }
    }
}
