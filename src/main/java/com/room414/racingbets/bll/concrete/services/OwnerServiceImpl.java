package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.dto.entities.OwnerDto;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import com.room414.racingbets.dal.domain.entities.Owner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.createErrorMessage;
import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.updateErrorMessage;

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
            unitOfWork.getJockeyDao().update(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = updateErrorMessage(jockey);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = updateErrorMessage(jockey);
            log.error(message, t);
            throw new BllException(message, t);
        }

    }

    @Override
    public OwnerDto find(long id) {
        return null;
    }

    @Override
    public List<OwnerDto> search(String searchString, Pager pager) {
        return null;
    }

    @Override
    public List<OwnerDto> findAll(Pager pager) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
