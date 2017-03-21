package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.TrainerService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.TrainerDto;
import com.room414.racingbets.dal.abstraction.dao.TrainerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Trainer;
import org.apache.commons.logging.LogFactory;
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
    private ErrorHandleDecorator<TrainerDto> decorator;

    public TrainerServiceImpl(UnitOfWorkFactory factory) {
        this.decorator = new ErrorHandleDecorator<>(factory, LogFactory.getLog(TrainerServiceImpl.class));
    }

    private List<TrainerDto> mapList(List<Trainer> source) {
        return source.stream()
                .map(e -> mapper.map(e, TrainerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(TrainerDto horse) {
        decorator.create(horse, this::create);
    }

    @Override
    public void update(TrainerDto horse) {
        decorator.create(horse, this::update);
    }

    @Override
    public TrainerDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<TrainerDto> search(String searchString, Pager pager) {
        return decorator.search(searchString, pager, this::search);
    }

    @Override
    public List<TrainerDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    private void create(UnitOfWork unitOfWork, TrainerDto horse) {
        Trainer entity = mapper.map(horse, Trainer.class);
        unitOfWork.getTrainerDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, TrainerDto horse) {
        Trainer entity = mapper.map(horse, Trainer.class);
        unitOfWork.getTrainerDao().update(entity);
        unitOfWork.commit();
    }

    private TrainerDto find(UnitOfWork unitOfWork, long id) {
        Trainer entity = unitOfWork.getTrainerDao().find(id);
        return mapper.map(entity, TrainerDto.class);
    }

    private List<TrainerDto> search(UnitOfWork unitOfWork, String searchString, Pager pager) {
        TrainerDao horseDao = unitOfWork.getTrainerDao();

        List<Trainer> entities = horseDao.search(searchString, pager.getOffset(), pager.getLimit());
        int count = horseDao.searchCount(searchString);

        pager.setCount(count);

        return mapList(entities);
    }

    private List<TrainerDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        TrainerDao horseDao = unitOfWork.getTrainerDao();

        List<Trainer> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
        int count = horseDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getTrainerDao().delete(id);
        unitOfWork.commit();
    }
}
