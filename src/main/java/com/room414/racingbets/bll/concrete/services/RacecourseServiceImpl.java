package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.RacecourseService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;
import com.room414.racingbets.dal.abstraction.dao.RacecourseDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Racecourse;
import org.apache.commons.logging.LogFactory;
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
    private ErrorHandleDecorator<RacecourseDto> decorator;

    public RacecourseServiceImpl(UnitOfWorkFactory factory) {
        this.decorator = new ErrorHandleDecorator<>(factory, LogFactory.getLog(RacecourseServiceImpl.class));
    }

    private List<RacecourseDto> mapList(List<Racecourse> source) {
        return source.stream()
                .map(e -> mapper.map(e, RacecourseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(RacecourseDto horse) {
        decorator.create(horse, this::create);
    }

    @Override
    public void update(RacecourseDto horse) {
        decorator.create(horse, this::update);
    }

    @Override
    public RacecourseDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<RacecourseDto> search(String searchString, Pager pager) {
        return decorator.search(searchString, pager, this::search);
    }

    @Override
    public List<RacecourseDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    private void create(UnitOfWork unitOfWork, RacecourseDto horse) {
        Racecourse entity = mapper.map(horse, Racecourse.class);
        unitOfWork.getRacecourseDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, RacecourseDto horse) {
        Racecourse entity = mapper.map(horse, Racecourse.class);
        unitOfWork.getRacecourseDao().update(entity);
        unitOfWork.commit();
    }

    private RacecourseDto find(UnitOfWork unitOfWork, long id) {
        Racecourse entity = unitOfWork.getRacecourseDao().find(id);
        return mapper.map(entity, RacecourseDto.class);
    }

    private List<RacecourseDto> search(UnitOfWork unitOfWork, String searchString, Pager pager) {
        RacecourseDao horseDao = unitOfWork.getRacecourseDao();

        List<Racecourse> entities = horseDao.search(searchString, pager.getOffset(), pager.getLimit());
        int count = horseDao.searchCount(searchString);

        pager.setCount(count);

        return mapList(entities);
    }

    private List<RacecourseDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        RacecourseDao horseDao = unitOfWork.getRacecourseDao();

        List<Racecourse> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
        int count = horseDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getRacecourseDao().delete(id);
        unitOfWork.commit();
    }
}
