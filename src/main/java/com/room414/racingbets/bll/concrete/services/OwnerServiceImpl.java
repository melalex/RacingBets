package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.OwnerService;
import com.room414.racingbets.bll.dto.entities.OwnerDto;
import com.room414.racingbets.dal.abstraction.dao.OwnerDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Owner;
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
    private UnitOfWorkFactory factory;

    public OwnerServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    private List<OwnerDto> mapList(List<Owner> source) {
        return source.stream()
                .map(e -> mapper.map(e, OwnerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(OwnerDto owner) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = mapper.map(owner, Owner.class);
            unitOfWork.getOwnerDao().create(entity);
            unitOfWork.commit();
            owner.setId(entity.getId());
        }
    }

    @Override
    public void update(long id, OwnerDto entity) {
        entity.setId(id);
        update(entity);
    }

    @Override
    public void update(OwnerDto owner) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = mapper.map(owner, Owner.class);
            unitOfWork.getOwnerDao().update(entity);
            unitOfWork.commit();
        }
    }

    @Override
    public OwnerDto find(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            Owner entity = unitOfWork.getOwnerDao().find(id);
            return entity != null ? mapper.map(entity, OwnerDto.class) : null;
        }
    }

    @Override
    public List<OwnerDto> search(String searchString, Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            OwnerDao ownerDao = unitOfWork.getOwnerDao();

            List<Owner> entities = ownerDao.search(searchString, pager.getOffset(), pager.getLimit());
            int count = ownerDao.searchCount(searchString);

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public List<OwnerDto> findAll(Pager pager) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            OwnerDao ownerDao = unitOfWork.getOwnerDao();

            List<Owner> entities = ownerDao.findAll(pager.getOffset(), pager.getLimit());
            int count = ownerDao.count();

            pager.setCount(count);

            return mapList(entities);
        }
    }

    @Override
    public void delete(long id) {
        try (UnitOfWork unitOfWork = factory.createUnitOfWork()) {
            unitOfWork.getOwnerDao().delete(id);
            unitOfWork.commit();
        }
    }
}
