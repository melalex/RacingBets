package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Horse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class HorseServiceImpl implements HorseService {
    // TODO: is right place?
    // TODO: is should be static?
    private Log logger = LogFactory.getLog(HorseServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public HorseServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<HorseDto> mapList(List<Horse> source) {
        return source.stream()
                .map(e -> mapper.map(e, HorseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(HorseDto horse) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = mapper.map(horse, Horse.class);
            unitOfWork.getHorseDao().create(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = "Exception during creating horse entity " + horse;
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "Exception during creating horse entity " + horse;
            logger.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(HorseDto horse) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = mapper.map(horse, Horse.class);
            unitOfWork.getHorseDao().update(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = "Exception during updating horse entity " + horse;
            logger.error(message, e);
            throw new BllException(message, e);
        }
    }

    @Override
    public HorseDto find(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = unitOfWork.getHorseDao().find(id);
            return mapper.map(entity, HorseDto.class);
        } catch (DalException e) {
            String message = "Exception during finding horse entity with id " + id;
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "Exception during finding horse entity with id " + id;
            logger.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<HorseDto> search(String searchString, Pager pager) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            List<Horse> entities = unitOfWork
                    .getHorseDao()
                    .findByNamePart(searchString, pager.getOffset(), pager.getLimit());

            return mapList(entities);
        } catch (DalException e) {
            int limit = pager.getLimit();
            int offset = pager.getOffset();
            String message = String.format("Exception during searching horses with search string '%s' in [%d; %d]",
                    searchString,
                    offset,
                    offset + limit
            );
            throw new BllException(message, e);
        } catch (Throwable t) {
            int limit = pager.getLimit();
            int offset = pager.getOffset();
            String message = String.format("Exception during searching horses with search string '%s' in [%d; %d]",
                    searchString,
                    offset,
                    offset + limit
            );
            logger.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public List<HorseDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            List<Horse> entities = unitOfWork
                    .getHorseDao()
                    .findAll(pager.getOffset(), pager.getLimit());

            return mapList(entities);
        } catch (DalException e) {
            int limit = pager.getLimit();
            int offset = pager.getOffset();
            String message = String.format("Exception during getting all horses in [%d; %d]",
                    offset,
                    offset + limit
            );
            throw new BllException(message, e);
        } catch (Throwable t) {
            int limit = pager.getLimit();
            int offset = pager.getOffset();
            String message = String.format("Exception during getting all horses in [%d; %d]",
                    offset,
                    offset + limit
            );
            logger.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void delete(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getHorseDao().delete(id);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = "Exception during deleting horse with id " + id;
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = "Exception during deleting horse with id " + id;
            logger.error(message, t);
            throw new BllException(message, t);
        }
    }
}
