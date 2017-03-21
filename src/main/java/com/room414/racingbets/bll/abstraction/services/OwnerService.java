package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;
import com.room414.racingbets.bll.dto.entities.OwnerDto;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface OwnerService {
    void create(OwnerDto owner);
    void update(OwnerDto owner);
    OwnerDto find(long id);
    List<OwnerDto> search(String searchString, Pager pager);
    List<OwnerDto> findAll(Pager pager);
    void delete(long id);
}
