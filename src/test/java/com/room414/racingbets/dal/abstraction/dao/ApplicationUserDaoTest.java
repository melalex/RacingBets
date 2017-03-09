package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;


/**
 * @author melalex
 * @version 1.0 09 Mar 2017
 */
@ExtendWith(UnitOfWorkParameterResolver.class)
class ApplicationUserDaoTest {
    private static UnitOfWork unitOfWork;

    private EntityStorage storage = EntityStorage.getInstance();

    @BeforeAll
    static void setUp(UnitOfWork unitOfWork) {
        ApplicationUserDaoTest.unitOfWork = unitOfWork;
    }

    @AfterAll
    static void tearDown() throws Exception {
        unitOfWork.close();
    }

    private static ApplicationUserDao getDao() {
        return unitOfWork.getApplicationUserDao();
    }

    @Test
    void find_existent_returnedEntity() throws DalException {
        ApplicationUserDao dao = getDao();

        ApplicationUser expectedResult = storage.getApplicationUser(1);

        ApplicationUser result = dao.find(1L);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void find_nonexistent_returnedNull() throws DalException {
        ApplicationUserDao dao = getDao();

        ApplicationUser result = dao.find(300L);

        assert result == null : "result != null";
    }

    @Test
    void findAllLimitOffset() throws ParseException, DalException {
        ApplicationUserDao dao = getDao();
        List<ApplicationUser> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getApplicationUser(1));
        expectedResult.add(storage.getApplicationUser(2));

        List<ApplicationUser> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void findAllLimitOffset_nonexistent_returnedEmptyList() throws DalException {
        ApplicationUserDao dao = getDao();

        List<ApplicationUser> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    void findAll() throws ParseException, DalException {
        List<ApplicationUser> expectedResult = storage.getAllApplicationUsers();

        List<ApplicationUser> result = getDao().findAll();

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void count() throws DalException {
        ApplicationUserDao dao = getDao();
        long expectedResult = 5;

        long result = dao.count();

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByLoginPart() throws DalException {
        ApplicationUserDao dao = getDao();

        List<ApplicationUser> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getApplicationUser(1));

        List<ApplicationUser> result = dao.findByLoginPart("pgor", 0, 1);

        assert result.equals(expectedResult) : "result != expectedResult";

    }

    @Test
    void findByLoginPartCount() throws DalException {
        ApplicationUserDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByLoginPartCount("pgor");

        assert expectedResult == result : "result != expectedResult";

    }

    @Test
    void findByLoginAndPassword() throws DalException {
        ApplicationUserDao dao = getDao();

        ApplicationUser expectedResult = storage.getApplicationUser(1);

        ApplicationUser result = dao.findByLoginAndPassword("pgordon0", "kJ182n");

        assert result.equals(expectedResult) : "result != expectedResult";
    }

    @Test
    void createDelete() throws DalException, ParseException {
        ApplicationUserDao dao = getDao();

        ApplicationUser newEntity = ApplicationUser.builder()
                .setLogin("melalex")
                .setPassword("fortuna322")
                .setFirstName("Alex")
                .setLastName("Allen")
                .setEmail("melalex490@virginia.edu")
                .setEmailConfirmed(true)
                .setBalance(BigDecimal.valueOf(385.59))
                .addRole(Role.ADMIN)
                .addRole(Role.BOOKMAKER)
                .addRole(Role.HANDICAPPER)
                .build();

        dao.create(newEntity);

        ApplicationUser entity1 = dao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create Horse";

        dao.delete(newEntity.getId());

        ApplicationUser entity2 = dao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete Person";
    }

    @Test
    void create_existentLogin_notCreated() {
        // TODO: test this
    }

    @Test
    void update() throws DalException, ParseException {
        final long targetId = 1L;

        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);
        ApplicationUser updated = ApplicationUser.builder()
                .setLogin("melalex")
                .setPassword("fortuna322")
                .setFirstName("Alex")
                .setLastName("Allen")
                .setEmail("melalex490@virginia.edu")
                .setEmailConfirmed(true)
                .setBalance(BigDecimal.valueOf(385.59))
                .build();

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        ApplicationUser afterSave = dao.find(entity.getId());

        assert updated.equals(afterSave) : "updated != afterSave";

        // rollback

        dao.update(entity);
    }

    @Test
    void confirmEmail() {
        // TODO: test this
    }

    @Test
    void addRoleAndRemoveRole() throws DalException {
        final long targetId = 1L;
        Role newRole = Role.ADMIN;
        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);
        assert !entity.isInRole(newRole) : "entity already in role";

        dao.addRole(entity.getId(), newRole);
        entity = dao.find(targetId);
        assert entity.isInRole(newRole) : "Dao didn't add role to user";

        dao.removeRole(entity.getId(), newRole);
        entity = dao.find(targetId);
        assert !entity.isInRole(newRole) : "Dao didn't remove role from user";
    }

    @Test
    void tryGetMoneyAndPutMoney_enoughMoney_gotMoneyReturnTrue() throws DalException {
        final long targetId = 1L;
        BigDecimal moneyToGet = BigDecimal.valueOf(10);
        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);

        assert dao.tryGetMoney(entity.getId(), moneyToGet) : "expected true";

        ApplicationUser updated = dao.find(entity.getId());

        assert updated.getBalance().compareTo(entity.getBalance().subtract(moneyToGet)) == 0 : "Dao didn't reduce the balance";

        dao.putMoney(entity.getId(), moneyToGet);

        updated = dao.find(entity.getId());

        assert updated.getBalance().compareTo(entity.getBalance()) == 0 : "Dao didn't replenish the balance";
    }

    @Test
    void tryGetMoney_notEnoughMoney_returnFalse() throws DalException {
        final long targetId = 1L;
        BigDecimal moneyToGet = BigDecimal.valueOf(10000);
        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);

        assert !dao.tryGetMoney(entity.getId(), moneyToGet) : "expected false";

        ApplicationUser updated = dao.find(entity.getId());

        assert updated.getBalance().compareTo(entity.getBalance()) == 0 : "entity and updated should be same";
    }
}