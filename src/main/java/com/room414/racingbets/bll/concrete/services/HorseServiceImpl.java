package com.room414.racingbets.bll.concrete.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.abstraction.services.HorseService;
import com.room414.racingbets.bll.dto.entities.HorseDto;
import com.room414.racingbets.dal.abstraction.factories.UnitOfWorkFactory;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 19 Mar 2017
 */
public class HorseServiceImpl implements HorseService {
    private UnitOfWorkFactory factory;

    public HorseServiceImpl(UnitOfWorkFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(HorseDto jockey) {

    }

    @Override
    public void update(HorseDto jockey) {

    }

    @Override
    public HorseDto find(long id) {
        return null;
    }

    @Override
    public List<HorseDto> search(String searchString, Pager pager) {
        return null;
    }

    @Override
    public List<HorseDto> findAll(Pager pager) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
