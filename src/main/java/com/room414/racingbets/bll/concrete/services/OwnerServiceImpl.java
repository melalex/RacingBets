package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.dto.entities.OwnerDto;
import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Owner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class OwnerServiceImpl implements OwnerService {
    private Log log  = LogFactory.getLog(OwnerServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    private UnitOfWorkFactory factory;

    public OwnerServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<OwnerDto> mapList(List<Owner> source) {
        return source.stream()
                .map(e -> mapper.map(e, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(OwnerDto owner) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = mapper.map(owner, Owner.class);
            unitOfWork.getOwnerDao().create(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = createErrorMessage(owner);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = createErrorMessage(owner);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(OwnerDto owner) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = mapper.map(owner, Owner.class);
            unitOfWork.getOwnerDao().update(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = updateErrorMessage(owner);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = updateErrorMessage(owner);
            log.error(message, t);
            throw new BllException(message, t);
        }

    }

    @Override
    public OwnerDto find(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = unitOfWork.getOwnerDao().find(id);
            return mapper.map(entity, OwnerDto.class);
        } catch (DalException e) {
            String message = findErrorMessage(id);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = findErrorMessage(id);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<OwnerDto> search(String searchString, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            OwnerDao ownerDao = unitOfWork.getOwnerDao();

            List<Owner> entities = ownerDao.search(searchString, offset, limit);
            int count = ownerDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        } catch (DalException e) {
            String message = searchErrorMessage(searchString, limit, offset);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = searchErrorMessage(searchString, limit, offset);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<OwnerDto> findAll(Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            OwnerDao ownerDao = unitOfWork.getOwnerDao();

            List<Owner> entities = ownerDao.findAll(offset, limit);
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
    public void delete(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getOwnerDao().delete(id);
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
}
