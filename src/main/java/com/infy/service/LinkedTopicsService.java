/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.LinkedTopicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.LinkedTopics}.
 */
public interface LinkedTopicsService {

    /**
     * Save a linkedTopics.
     *
     * @param linkedTopicsDTO the entity to save.
     * @return the persisted entity.
     */
    LinkedTopicsDTO save(LinkedTopicsDTO linkedTopicsDTO);

    /**
     * Get all the linkedTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LinkedTopicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" linkedTopics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LinkedTopicsDTO> findOne(Long id);

    /**
     * Delete the "id" linkedTopics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
