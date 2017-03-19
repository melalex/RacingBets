package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.HorseDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface HorseService {
    void create(HorseDto horse);
    void update(HorseDto horse);
    HorseDto find(long id);
    List<HorseDto> search(String searchString, Pager pager);
    List<HorseDto> findAll(Pager pager);
    void delete(long id);
}
