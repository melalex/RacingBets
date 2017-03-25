package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Horse;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class HorseServiceImpl implements HorseService {
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
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = mapper.map(horse, Horse.class);
            unitOfWork.getHorseDao().create(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public void update(HorseDto horse) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = mapper.map(horse, Horse.class);
            unitOfWork.getHorseDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public HorseDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Horse entity = unitOfWork.getHorseDao().find(id);
            return entity != null ? mapper.map(entity, HorseDto.class) : null;
        }
    }

    @Override
    public List<HorseDto> search(String searchString, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            HorseDao horseDao = unitOfWork.getHorseDao();

            List<Horse> entities = horseDao.search(searchString, pager.getOffset(), pager.getLimit());
            int count = horseDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<HorseDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            HorseDao horseDao = unitOfWork.getHorseDao();

            List<Horse> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
            int count = horseDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getHorseDao().delete(id);
            unitOfWork.commit();
        }
    }
}
