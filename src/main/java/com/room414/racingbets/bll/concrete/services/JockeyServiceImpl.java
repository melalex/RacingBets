package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.dto.entities.JockeyDto;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.*;
import static com.room414.racingbets.bll.concrete.infrastrucure.ErrorMessageUtil.searchErrorMessage;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class JockeyServiceImpl implements JockeyService {
    private Log log = LogFactory.getLog(JockeyServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    private UnitOfWorkFactory factory;

    public JockeyServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<JockeyDto> mapList(List<Jockey> source) {
        return source.stream()
                .map(e -> mapper.map(e, JockeyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(JockeyDto jockey) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Jockey entity = mapper.map(jockey, Jockey.class);
            unitOfWork.getJockeyDao().create(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = createErrorMessage(jockey);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = createErrorMessage(jockey);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(JockeyDto jockey) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Jockey entity = mapper.map(jockey, Jockey.class);
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
    public JockeyDto find(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Jockey entity = unitOfWork.getJockeyDao().find(id);
            return mapper.map(entity, JockeyDto.class);
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
    public List<JockeyDto> search(String searchString, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            List<Jockey> entities = unitOfWork
                    .getJockeyDao()
                    .findByNamePart(searchString, offset, limit);

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
    public List<JockeyDto> findAll(Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            List<Jockey> entities = unitOfWork
                    .getJockeyDao()
                    .findAll(offset, limit);

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
            unitOfWork.getJockeyDao().delete(id);
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
