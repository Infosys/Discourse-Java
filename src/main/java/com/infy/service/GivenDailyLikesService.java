/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GivenDailyLikesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GivenDailyLikes}.
 */
public interface GivenDailyLikesService {

    /**
     * Save a givenDailyLikes.
     *
     * @param givenDailyLikesDTO the entity to save.
     * @return the persisted entity.
     */
    GivenDailyLikesDTO save(GivenDailyLikesDTO givenDailyLikesDTO);

    /**
     * Get all the givenDailyLikes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GivenDailyLikesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" givenDailyLikes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GivenDailyLikesDTO> findOne(Long id);

    /**
     * Delete the "id" givenDailyLikes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
