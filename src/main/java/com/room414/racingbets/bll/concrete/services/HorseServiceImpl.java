package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.abstraction.dao.HorseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Horse;
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
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private ErrorHandleDecorator<HorseDto> decorator;

    public HorseServiceImpl(UnitOfWorkFactory factory) {
        this.decorator = new ErrorHandleDecorator<>(factory, LogFactory.getLog(HorseServiceImpl.class));
    }

    private List<HorseDto> mapList(List<Horse> source) {
        return source.stream()
                .map(e -> mapper.map(e, HorseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(HorseDto horse) {
        decorator.create(horse, this::create);
    }

    @Override
    public void update(HorseDto horse) {
        decorator.create(horse, this::update);
    }

    @Override
    public HorseDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<HorseDto> search(String searchString, Pager pager) {
        return decorator.search(searchString, pager, this::search);
    }

    @Override
    public List<HorseDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    private void create(UnitOfWork unitOfWork, HorseDto horse) {
        Horse entity = mapper.map(horse, Horse.class);
        unitOfWork.getHorseDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, HorseDto horse) {
        Horse entity = mapper.map(horse, Horse.class);
        unitOfWork.getHorseDao().update(entity);
        unitOfWork.commit();
    }

    private HorseDto find(UnitOfWork unitOfWork, long id) {
        Horse entity = unitOfWork.getHorseDao().find(id);
        return entity != null ? mapper.map(entity, HorseDto.class) : null;
    }

    private List<HorseDto> search(UnitOfWork unitOfWork, String searchString, Pager pager) {
        HorseDao horseDao = unitOfWork.getHorseDao();

        List<Horse> entities = horseDao.search(searchString, pager.getOffset(), pager.getLimit());
        int count = horseDao.searchCount(searchString);

        pager.setCount(count);

        return mapList(entities);
    }

    private List<HorseDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        HorseDao horseDao = unitOfWork.getHorseDao();

        List<Horse> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
        int count = horseDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getHorseDao().delete(id);
        unitOfWork.commit();
    }
}
