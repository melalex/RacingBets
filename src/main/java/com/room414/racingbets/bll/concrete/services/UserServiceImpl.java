package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.infrastructure.mail.EmailConfirmMessenger;
import com.room414.racingbets.bll.abstraction.services.UserService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.UserDto;
import com.room414.racingbets.dal.abstraction.dao.ApplicationUserDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.ApplicationUser;
import com.room414.racingbets.dal.domain.enums.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 20 Mar 2017
 */
public class UserServiceImpl implements UserService {
    private Log log = LogFactory.getLog(UserServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;
    private ErrorHandleDecorator<UserDto> decorator;
    private EmailConfirmMessenger messenger;

    public UserServiceImpl(UnitOfWorkFactory factory, EmailConfirmMessenger messenger) {
        this.factory = factory;
        this.decorator = new ErrorHandleDecorator<>(factory, log);
        this.messenger = messenger;
    }

    @Override
    // TODO: Password hashcode
    // TODO: Confirm tokens
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
                ApplicationUser entity = mapper.map(user, ApplicationUser.class);
                unitOfWork.getApplicationUserDao().create(entity);
                unitOfWork.commit();
                return SUCCESS;
            } else {
                return result;
            }
        } catch (DalException e) {
            String message = createErrorMessage(user);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = createErrorMessage(user);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(UserDto user) {
        decorator.update(user, this::update);
    }

    @Override
    public boolean confirmEmail(long id, String token) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().confirmEmail(id);
            unitOfWork.commit();
            return false;
        } catch (DalException e) {
            String message = defaultErrorMessage("confirmEmail", id, token);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("confirmEmail", id, token);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void addRole(long id, Role role) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().addRole(id, role);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = defaultErrorMessage("addRole", id, role);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("addRole", id, role);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void removeRole(long id, Role role) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().removeRole(id, role);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = defaultErrorMessage("removeRole", id, role);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("removeRole", id, role);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    @Override
    public UserDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public UserDto findByLoginPassword(String login, String password) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUser entity = unitOfWork
                    .getApplicationUserDao()
                    .findByLoginAndPassword(login, password);

            return mapper.map(entity, UserDto.class);
        } catch (DalException e) {
            String message = defaultErrorMessage("findByLoginPassword", login, password);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = defaultErrorMessage("findByLoginPassword", login, password);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    private List<UserDto> mapList(List<ApplicationUser> source) {
        return source.stream()
                .map(e -> mapper.map(e, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public List<UserDto> search(String login, Pager pager) {
        return decorator.search(login, pager, this::search);
    }

    private void update(UnitOfWork unitOfWork, UserDto user) {
        ApplicationUser entity = mapper.map(user, ApplicationUser.class);
        unitOfWork.getApplicationUserDao().update(entity);
        unitOfWork.commit();
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getApplicationUserDao().delete(id);
        unitOfWork.commit();
    }

    public UserDto find(UnitOfWork unitOfWork, long id) {
        ApplicationUser user = unitOfWork.getApplicationUserDao().find(id);
        return mapper.map(user, UserDto.class);
    }

    public List<UserDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

        List<ApplicationUser> entities = ownerDao.findAll(pager.getOffset(), pager.getLimit());
        int count = ownerDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    public List<UserDto> search(UnitOfWork unitOfWork, String login, Pager pager) {
        ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

        List<ApplicationUser> entities = ownerDao.search(login, pager.getOffset(), pager.getLimit());
        int count = ownerDao.searchCount(login);

        pager.setCount(count);

        return mapList(entities);
    }
}
