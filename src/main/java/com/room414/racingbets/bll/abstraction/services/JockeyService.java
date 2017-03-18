package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.JockeyDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface JockeyService {
    void create(JockeyDto jockey);
    void update(JockeyDto jockey);
    JockeyDto find(long id);
    List<JockeyDto> search(String searchString, Pager pager);
    List<JockeyDto> findAll(Pager pager);
    void delete(long id);
}
