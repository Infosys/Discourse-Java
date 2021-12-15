/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostStatsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostStats}.
 */
public interface PostStatsService {

    /**
     * Save a postStats.
     *
     * @param postStatsDTO the entity to save.
     * @return the persisted entity.
     */
    PostStatsDTO save(PostStatsDTO postStatsDTO);

    /**
     * Get all the postStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostStatsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostStatsDTO> findOne(Long id);

    /**
     * Delete the "id" postStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
