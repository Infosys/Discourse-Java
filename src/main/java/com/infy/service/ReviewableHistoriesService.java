/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ReviewableHistoriesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ReviewableHistories}.
 */
public interface ReviewableHistoriesService {

    /**
     * Save a reviewableHistories.
     *
     * @param reviewableHistoriesDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewableHistoriesDTO save(ReviewableHistoriesDTO reviewableHistoriesDTO);

    /**
     * Get all the reviewableHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReviewableHistoriesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reviewableHistories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewableHistoriesDTO> findOne(Long id);

    /**
     * Delete the "id" reviewableHistories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
