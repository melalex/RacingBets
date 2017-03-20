package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.OwnerDto;
import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Owner;
import org.apache.commons.logging.LogFactory;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class OwnerServiceImpl implements OwnerService {
    private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
    private ErrorHandleDecorator<OwnerDto> decorator;

    public OwnerServiceImpl(UnitOfWorkFactory factory) {
        this.decorator = new ErrorHandleDecorator<>(factory, LogFactory.getLog(OwnerServiceImpl.class));
    }

    private List<OwnerDto> mapList(List<Owner> source) {
        return source.stream()
                .map(e -> mapper.map(e, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(OwnerDto horse) {
        decorator.create(horse, this::create);
    }

    @Override
    public void update(OwnerDto horse) {
        decorator.create(horse, this::update);
    }

    @Override
    public OwnerDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<OwnerDto> search(String searchString, Pager pager) {
        return decorator.search(searchString, pager, this::search);
    }

    @Override
    public List<OwnerDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    private void create(UnitOfWork unitOfWork, OwnerDto horse) {
        Owner entity = mapper.map(horse, Owner.class);
        unitOfWork.getOwnerDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, OwnerDto horse) {
        Owner entity = mapper.map(horse, Owner.class);
        unitOfWork.getOwnerDao().update(entity);
        unitOfWork.commit();
    }

    private OwnerDto find(UnitOfWork unitOfWork, long id) {
        Owner entity = unitOfWork.getOwnerDao().find(id);
        return mapper.map(entity, OwnerDto.class);
    }

    private List<OwnerDto> search(UnitOfWork unitOfWork, String searchString, Pager pager) {
        OwnerDao horseDao = unitOfWork.getOwnerDao();

        List<Owner> entities = horseDao.search(searchString, pager.getOffset(), pager.getLimit());
        int count = horseDao.searchCount(searchString);

        pager.setCount(count);

        return mapList(entities);
    }

    private List<OwnerDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        OwnerDao horseDao = unitOfWork.getOwnerDao();

        List<Owner> entities = horseDao.findAll(pager.getOffset(), pager.getLimit());
        int count = horseDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getOwnerDao().delete(id);
        unitOfWork.commit();
    }
}
