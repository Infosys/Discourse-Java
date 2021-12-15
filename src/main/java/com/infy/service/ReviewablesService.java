/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ReviewablesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Reviewables}.
 */
public interface ReviewablesService {

    /**
     * Save a reviewables.
     *
     * @param reviewablesDTO the entity to save.
     * @return the persisted entity.
     */
    ReviewablesDTO save(ReviewablesDTO reviewablesDTO);

    /**
     * Get all the reviewables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReviewablesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" reviewables.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReviewablesDTO> findOne(Long id);

    /**
     * Delete the "id" reviewables.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
