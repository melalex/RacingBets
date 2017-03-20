package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.UserService;
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

    public UserServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
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
    public void update(UserDto horse) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUser entity = mapper.map(horse, ApplicationUser.class);
            unitOfWork.getApplicationUserDao().update(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = updateErrorMessage(horse);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = updateErrorMessage(horse);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    // TODO: add token logic
    // TODO: add error message
    public boolean confirmEmail(long id, String token) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().confirmEmail(id);
            unitOfWork.commit();
            return false;
        } catch (DalException e) {
            String message = "";
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "";
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
            String message = "";
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "";
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
            String message = "";
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "";
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getApplicationUserDao().delete(id);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = deleteErrorMessage(id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = deleteErrorMessage(id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public UserDto find(long id) {
        return null;
    }

    @Override
    public UserDto findByLoginPassword(String login, String password) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUser entity = unitOfWork
                    .getApplicationUserDao()
                    .findByLoginAndPassword(login, password);

            return mapper.map(entity, UserDto.class);
        } catch (DalException e) {
            String message = "";
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "";
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
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

            List<ApplicationUser> entities = ownerDao.findAll(offset, limit);
            int count = ownerDao.count();

            pager.setCount(count);

            return mapList(entities);
        } catch (DalException e) {
            String message = findAllErrorMessage(limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findAllErrorMessage(limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<UserDto> search(String login, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            ApplicationUserDao ownerDao = unitOfWork.getApplicationUserDao();

            List<ApplicationUser> entities = ownerDao.search(login, offset, limit);
            int count = ownerDao.searchCount(login);

            pager.setCount(count);

            return mapList(entities);
        } catch (DalException e) {
            String message = searchErrorMessage(login, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = searchErrorMessage(login, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }
}
