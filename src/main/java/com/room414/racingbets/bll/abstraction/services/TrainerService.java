package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.TrainerDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface TrainerService {
    void create(TrainerDto trainer);
    void update(TrainerDto trainer);
    TrainerDto find(long id);
    List<TrainerDto> search(String searchString, Pager pager);
    List<TrainerDto> findAll(Pager pager);
    void delete(long id);
}
