package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.TrainerService;
import com.room414.racingbets.bll.dto.entities.TrainerDto;
import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Trainer;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class TrainerServiceImpl implements TrainerService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private UnitOfWorkFactory factory;

    public TrainerServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<TrainerDto> mapList(List<Trainer> source) {
        return source.stream()
                .map(e -> mapper.map(e, TrainerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(TrainerDto trainer) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Trainer entity = mapper.map(trainer, Trainer.class);
            unitOfWork.getTrainerDao().create(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public void update(TrainerDto trainer) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Trainer entity = mapper.map(trainer, Trainer.class);
            unitOfWork.getTrainerDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public TrainerDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Trainer entity = unitOfWork.getTrainerDao().find(id);
            return entity != null ? mapper.map(entity, TrainerDto.class) : null;
        }
    }

    @Override
    public List<TrainerDto> search(String searchString, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            TrainerDao trainerDao = unitOfWork.getTrainerDao();

            List<Trainer> entities = trainerDao.search(searchString, pager.getOffset(), pager.getLimit());
            int count = trainerDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<TrainerDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            TrainerDao trainerDao = unitOfWork.getTrainerDao();

            List<Trainer> entities = trainerDao.findAll(pager.getOffset(), pager.getLimit());
            int count = trainerDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getTrainerDao().delete(id);
            unitOfWork.commit();
        }
    }
}
