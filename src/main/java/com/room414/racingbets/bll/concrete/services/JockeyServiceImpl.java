package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.dto.entities.JockeyDto;
import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class JockeyServiceImpl implements JockeyService {
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
            jockey.setId(entity.getId());
        }
    }

    @Override
    public void update(long id, JockeyDto entity) {
        entity.setId(id);
        update(entity);
    }

    @Override
    public void update(JockeyDto jockey) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Jockey entity = mapper.map(jockey, Jockey.class);
            unitOfWork.getJockeyDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public JockeyDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Jockey entity = unitOfWork.getJockeyDao().find(id);
            return entity != null ? mapper.map(entity, JockeyDto.class) : null;
        }
    }

    @Override
    public List<JockeyDto> search(String searchString, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();

            List<Jockey> entities = jockeyDao.search(searchString, pager.getOffset(), pager.getLimit());
            int count = jockeyDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<JockeyDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            JockeyDao jockeyDao = unitOfWork.getJockeyDao();

            List<Jockey> entities = jockeyDao.findAll(pager.getOffset(), pager.getLimit());
            int count = jockeyDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getJockeyDao().delete(id);
            unitOfWork.commit();
        }
    }
}
