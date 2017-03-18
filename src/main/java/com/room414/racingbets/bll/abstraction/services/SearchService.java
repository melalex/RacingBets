package com.room414.racingbets.bll.abstraction.services;

import com.room414.racingbets.bll.abstraction.infrastructure.Pager;
import com.room414.racingbets.bll.dto.entities.SearchResult;

/**
 * @author Alexander Melashchenko
 * @version 1.0 18 Mar 2017
 */
public interface SearchService {
    SearchResult search(String searchString, Pager pager);
}
