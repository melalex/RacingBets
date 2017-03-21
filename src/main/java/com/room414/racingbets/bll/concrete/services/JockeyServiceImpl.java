package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.abstraction.services.JockeyService;
import com.room414.racingbets.bll.concrete.infrastrucure.ErrorHandleDecorator;
import com.room414.racingbets.bll.dto.entities.JockeyDto;
import com.room414.racingbets.dal.abstraction.dao.JockeyDao;
import com.room414.racingbets.dal.abstraction.dao.UnitOfWork;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;
import com.room414.racingbets.dal.domain.entities.Jockey;
import org.apache.commons.logging.LogFactory;
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
    private ErrorHandleDecorator<JockeyDto> decorator;

    public JockeyServiceImpl(UnitOfWorkFactory factory) {
        this.decorator = new ErrorHandleDecorator<>(factory, LogFactory.getLog(JockeyServiceImpl.class));
    }

    private List<JockeyDto> mapList(List<Jockey> source) {
        return source.stream()
                .map(e -> mapper.map(e, JockeyDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(JockeyDto jockey) {
        decorator.create(jockey, this::create);
    }

    @Override
    public void update(JockeyDto jockey) {
        decorator.create(jockey, this::update);
    }

    @Override
    public JockeyDto find(long id) {
        return decorator.find(id, this::find);
    }

    @Override
    public List<JockeyDto> search(String searchString, Pager pager) {
        return decorator.search(searchString, pager, this::search);
    }

    @Override
    public List<JockeyDto> findAll(Pager pager) {
        return decorator.findAll(pager, this::findAll);
    }

    @Override
    public void delete(long id) {
        decorator.delete(id, this::delete);
    }

    private void create(UnitOfWork unitOfWork, JockeyDto jockey) {
        Jockey entity = mapper.map(jockey, Jockey.class);
        unitOfWork.getJockeyDao().create(entity);
        unitOfWork.commit();
    }

    private void update(UnitOfWork unitOfWork, JockeyDto jockey) {
        Jockey entity = mapper.map(jockey, Jockey.class);
        unitOfWork.getJockeyDao().update(entity);
        unitOfWork.commit();
    }

    private JockeyDto find(UnitOfWork unitOfWork, long id) {
        Jockey entity = unitOfWork.getJockeyDao().find(id);
        return entity != null ? mapper.map(entity, JockeyDto.class) : null;
    }

    private List<JockeyDto> search(UnitOfWork unitOfWork, String searchString, Pager pager) {
        JockeyDao jockeyDao = unitOfWork.getJockeyDao();

        List<Jockey> entities = jockeyDao.search(searchString, pager.getOffset(), pager.getLimit());
        int count = jockeyDao.searchCount(searchString);

        pager.setCount(count);

        return mapList(entities);
    }

    private List<JockeyDto> findAll(UnitOfWork unitOfWork, Pager pager) {
        JockeyDao jockeyDao = unitOfWork.getJockeyDao();

        List<Jockey> entities = jockeyDao.findAll(pager.getOffset(), pager.getLimit());
        int count = jockeyDao.count();

        pager.setCount(count);

        return mapList(entities);
    }

    private void delete(UnitOfWork unitOfWork, long id) {
        unitOfWork.getJockeyDao().delete(id);
        unitOfWork.commit();
    }
}
