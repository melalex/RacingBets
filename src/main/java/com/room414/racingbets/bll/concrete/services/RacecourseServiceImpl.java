package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;
import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class RacecourseServiceImpl implements RacecourseService {
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
    public void create(RacecourseDto racecourse) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = mapper.map(racecourse, Racecourse.class);
            unitOfWork.getRacecourseDao().create(entity);
            unitOfWork.commit();
            racecourse.setId(entity.getId());
        }
    }

    @Override
    public void update(long id, RacecourseDto entity) {
        entity.setId(id);
        update(entity);
    }

    @Override
    public void update(RacecourseDto racecourse) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = mapper.map(racecourse, Racecourse.class);
            unitOfWork.getRacecourseDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public RacecourseDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Racecourse entity = unitOfWork.getRacecourseDao().find(id);
            return entity != null ? mapper.map(entity, RacecourseDto.class) : null;
        }
    }

    @Override
    public List<RacecourseDto> search(String searchString, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RacecourseDao racecourseDao = unitOfWork.getRacecourseDao();

            List<Racecourse> entities = racecourseDao.search(searchString, pager.getOffset(), pager.getLimit());
            int count = racecourseDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<RacecourseDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            RacecourseDao racecourseDao = unitOfWork.getRacecourseDao();

            List<Racecourse> entities = racecourseDao.findAll(pager.getOffset(), pager.getLimit());
            int count = racecourseDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getRacecourseDao().delete(id);
            unitOfWork.commit();
        }
    }
}
