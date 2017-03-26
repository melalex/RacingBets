package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.pagination.Pager;

import java.util.List;

/**
 * @author Alexander Melashchenko
 * @version 1.0 25 Mar 2017
 */
public interface CrudService<T> {
    void create(T jockey);
    void update(T jockey);
    T find(long id);
    List<T> search(String searchString, Pager pager);
    List<T> findAll(Pager pager);
    void delete(long id);
}