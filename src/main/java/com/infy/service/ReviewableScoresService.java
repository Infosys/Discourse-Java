/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ReviewableScoresDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ReviewableScores}.
 */
public interface ReviewableScoresService {

    /**
     * Save a reviewableScores.
     *
     * @param reviewableScoresDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewableScoresDTO save(ReviewableScoresDTO reviewableScoresDTO);

    /**
     * Get all the reviewableScores.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReviewableScoresDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reviewableScores.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewableScoresDTO> findOne(Long id);

    /**
     * Delete the "id" reviewableScores.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
