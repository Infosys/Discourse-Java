/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopTopicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopTopics}.
 */
public interface TopTopicsService {

    /**
     * Save a topTopics.
     *
     * @param topTopicsDTO the entity to save.
     * @return the persisted entity.
     */
    TopTopicsDTO save(TopTopicsDTO topTopicsDTO);

    /**
     * Get all the topTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopTopicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topTopics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopTopicsDTO> findOne(Long id);

    /**
     * Delete the "id" topTopics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
