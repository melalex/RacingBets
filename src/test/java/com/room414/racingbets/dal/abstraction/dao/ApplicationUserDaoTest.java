package com.room414.racingbets.dal.abstraction.dao;

import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.exception.UserAlreadyExistsException;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;
import com.room414.racingbets.dal.infrastructure.EntityStorage;
import com.room414.racingbets.dal.resolvers.UnitOfWorkParameterResolver;
import com.sun.corba.se.impl.oa.poa.AOMEntry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import static com.room414.racingbets.dal.infrastructure.TestHelper.defaultAssertionFailMessage;

import static org.junit.jupiter.api.Assertions.*;


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
    @Tag("read")
    void find_existent_returnedEntity() {
        ApplicationUserDao dao = getDao();

        ApplicationUser expectedResult = storage.getApplicationUser(1);

        ApplicationUser result = dao.find(1L);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void find_nonexistent_returnedNull() {
        ApplicationUserDao dao = getDao();

        ApplicationUser result = dao.find(300L);

        assert result == null : defaultAssertionFailMessage(result, null);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset() throws ParseException {
        ApplicationUserDao dao = getDao();
        List<ApplicationUser> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getApplicationUser(1));
        expectedResult.add(storage.getApplicationUser(2));

        List<ApplicationUser> result = dao.findAll(0, 2);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findAllLimitOffset_nonexistent_returnedEmptyList() {
        ApplicationUserDao dao = getDao();

        List<ApplicationUser> result = dao.findAll(300L, 400L);

        assert result.isEmpty() : "result is not empty";
    }

    @Test
    @Tag("read")
    void findAll() throws ParseException {
        List<ApplicationUser> expectedResult = storage.getAllApplicationUsers();

        List<ApplicationUser> result = getDao().findAll();

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void count() {
        ApplicationUserDao dao = getDao();
        long expectedResult = 5;

        long result = dao.count();

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByLoginPart() {
        ApplicationUserDao dao = getDao();

        List<ApplicationUser> expectedResult = new LinkedList<>();

        expectedResult.add(storage.getApplicationUser(1));

        List<ApplicationUser> result = dao.findByLoginPart("pgor", 0, 1);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByLoginPartCount() {
        ApplicationUserDao dao = getDao();
        long expectedResult = 1;

        long result = dao.findByLoginPartCount("pgor");

        assert expectedResult == result : defaultAssertionFailMessage(result, expectedResult);

    }

    @Test
    @Tag("read")
    void findByLoginAndPassword() {
        ApplicationUserDao dao = getDao();

        ApplicationUser expectedResult = storage.getApplicationUser(1);

        ApplicationUser result = dao.findByLoginAndPassword("pgordon0", "kJ182n");

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("read")
    void findByLoginAndEmail() {
        ApplicationUserDao dao = getDao();
        String login = "pgordon0";
        String email = "hbanks2@adobe.com";
        List<ApplicationUser> expectedResult = new LinkedList<>();
        expectedResult.add(storage.getApplicationUser(1));
        expectedResult.add(storage.getApplicationUser(3));

        List<ApplicationUser> result = dao.findByLoginAndEmail(login, email);

        assert result.equals(expectedResult) : defaultAssertionFailMessage(result, expectedResult);
    }

    @Test
    @Tag("write")
    void createDelete() throws ParseException {
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

        assert newEntity.equals(entity1) : "Dao did not create entity";

        dao.delete(newEntity.getId());

        ApplicationUser entity2 = dao.find(newEntity.getId());

        assert entity2 == null : "Dao did not delete entity";
    }

    @Test
    @Tag("write")
    void create_existentLogin_notCreated() {
        ApplicationUserDao dao = getDao();

        ApplicationUser newEntity = ApplicationUser.builder()
                .setLogin("pgordon0")
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

        assertThrows(UserAlreadyExistsException.class, () -> dao.create(newEntity));
    }

    @Test
    @Tag("write")
    void create_existentEmail_notCreated() {
        ApplicationUserDao dao = getDao();

        ApplicationUser newEntity = ApplicationUser.builder()
                .setLogin("melalex")
                .setPassword("fortuna322")
                .setFirstName("Alex")
                .setLastName("Allen")
                .setEmail("pgordon0@google.ru")
                .setEmailConfirmed(true)
                .setBalance(BigDecimal.valueOf(385.59))
                .addRole(Role.ADMIN)
                .addRole(Role.BOOKMAKER)
                .addRole(Role.HANDICAPPER)
                .build();

        assertThrows(UserAlreadyExistsException.class, () -> dao.create(newEntity));
    }

    @Test
    @Tag("write")
    void create_existentEmailLogin_notCreated() {
        ApplicationUserDao dao = getDao();

        ApplicationUser newEntity = ApplicationUser.builder()
                .setLogin("pgordon0")
                .setPassword("fortuna322")
                .setFirstName("Alex")
                .setLastName("Allen")
                .setEmail("pgordon0@google.ru")
                .setEmailConfirmed(true)
                .setBalance(BigDecimal.valueOf(385.59))
                .addRole(Role.ADMIN)
                .addRole(Role.BOOKMAKER)
                .addRole(Role.HANDICAPPER)
                .build();

        assertThrows(UserAlreadyExistsException.class, () -> dao.create(newEntity));
    }


    @Test
    @Tag("write")
    void update() throws ParseException {
        final long targetId = 1L;

        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);
        ApplicationUser updated = ApplicationUser.builder()
                .setId(entity.getId())
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

        assert !updated.equals(entity) : "entity and updated are already same";

        dao.update(updated);

        ApplicationUser afterSave = dao.find(entity.getId());

        assert updated.equals(afterSave) : defaultAssertionFailMessage(afterSave, updated);

        // rollback

        dao.update(entity);
    }

    @Test
    @Tag("write")
    void confirmEmail() {
        ApplicationUserDao dao = getDao();

        ApplicationUser newEntity = ApplicationUser.builder()
                .setLogin("melalex")
                .setPassword("fortuna322")
                .setFirstName("Alex")
                .setLastName("Allen")
                .setEmail("melalex490@virginia.edu")
                .setEmailConfirmed(false)
                .setBalance(BigDecimal.valueOf(385.59))
                .addRole(Role.ADMIN)
                .addRole(Role.BOOKMAKER)
                .addRole(Role.HANDICAPPER)
                .build();

        dao.create(newEntity);

        ApplicationUser entity1 = dao.find(newEntity.getId());

        assert newEntity.equals(entity1) : "Dao did not create entity";
        assert !entity1.isEmailConfirmed() : "Email already confirmed";

        dao.confirmEmail(newEntity.getId());

        ApplicationUser entity2 = dao.find(newEntity.getId());

        assert entity2.isEmailConfirmed() : "Dao did not confirm email";

        dao.delete(newEntity.getId());
    }

    @Test
    @Tag("write")
    void addRoleAndRemoveRole() {
        final long targetId = 3L;
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
    @Tag("write")
    void tryGetMoneyAndPutMoney_enoughMoney_gotMoneyReturnTrue() {
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
    @Tag("write")
    void tryGetMoney_notEnoughMoney_returnFalse() {
        final long targetId = 1L;
        BigDecimal moneyToGet = BigDecimal.valueOf(10000);
        ApplicationUserDao dao = getDao();

        ApplicationUser entity = dao.find(targetId);

        assert !dao.tryGetMoney(entity.getId(), moneyToGet) : "expected false";

        ApplicationUser updated = dao.find(entity.getId());

        assert updated.getBalance().compareTo(entity.getBalance()) == 0 : "entity and updated should be same";
    }
}