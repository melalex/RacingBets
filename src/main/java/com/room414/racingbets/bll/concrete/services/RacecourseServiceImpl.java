package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.exceptions.BllException;
import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;
import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.exception.DalException;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Racecourse;
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
public class RacecourseServiceImpl implements RacecourseService {
    private Log log = LogFactory.getLog(RacecourseServiceImpl.class);
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public RacecourseServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<RacecourseDto> mapList(List<Racecourse> source) {
        return source.stream()
                .map(e -> mapper.map(e, RacecourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(RacecourseDto horse) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = mapper.map(horse, Racecourse.class);
            unitOfWork.getRacecourseDao().create(entity);
            unitOfWork.commit();
        } catch (DalException e) {
            String message = createErrorMessage(horse);
            throw new BllException(message, e);
        } catch (Throwable t) {
            String message = createErrorMessage(horse);
            log.error(message, t);
            throw new BllException(message, t);
        }
    }

    @Override
    public void update(RacecourseDto horse) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = mapper.map(horse, Racecourse.class);
            unitOfWork.getRacecourseDao().update(entity);
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
    public RacecourseDto find(long id) {
        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = unitOfWork.getRacecourseDao().find(id);
            return mapper.map(entity, RacecourseDto.class);
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
    public List<RacecourseDto> search(String searchString, Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try(UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RacecourseDao horseDao = unitOfWork.getRacecourseDao();

            List<Racecourse> entities = horseDao.search(searchString, offset, limit);
            int count = horseDao.searchCount(searchString);

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
    public List<RacecourseDto> findAll(Pager pager) {
        int limit = pager.getLimit();
        int offset = pager.getOffset();

        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RacecourseDao horseDao = unitOfWork.getRacecourseDao();

            List<Racecourse> entities = horseDao.findAll(offset, limit);
            int count = horseDao.count();

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
            unitOfWork.getRacecourseDao().delete(id);
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
