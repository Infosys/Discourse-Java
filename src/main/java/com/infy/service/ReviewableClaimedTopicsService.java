/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ReviewableClaimedTopicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ReviewableClaimedTopics}.
 */
public interface ReviewableClaimedTopicsService {

    /**
     * Save a reviewableClaimedTopics.
     *
     * @param reviewableClaimedTopicsDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewableClaimedTopicsDTO save(ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO);

    /**
     * Get all the reviewableClaimedTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReviewableClaimedTopicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reviewableClaimedTopics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewableClaimedTopicsDTO> findOne(Long id);

    /**
     * Delete the "id" reviewableClaimedTopics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
