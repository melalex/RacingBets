package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.dal.domain.entities.Racecourse;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface RacecourseService {
    void create(Racecourse jockey);
    void update(Racecourse jockey);
    Racecourse find(long id);
    List<Racecourse> search(String searchString, Pager pager);
    List<Racecourse> findAll(Pager pager);
    void delete(long id);
}
