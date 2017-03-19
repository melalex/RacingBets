package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.RacecourseDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface RacecourseService {
    void create(RacecourseDto racecourse);
    void update(RacecourseDto racecourse);
    RacecourseDto find(long id);
    List<RacecourseDto> search(String searchString, Pager pager);
    List<RacecourseDto> findAll(Pager pager);
    void delete(long id);
}
