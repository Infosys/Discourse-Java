/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.OptimizedImagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.OptimizedImages}.
 */
public interface OptimizedImagesService {

    /**
     * Save a optimizedImages.
     *
     * @param optimizedImagesDTO the entity to save.
     * @return the persisted entity.
     */
    OptimizedImagesDTO save(OptimizedImagesDTO optimizedImagesDTO);

    /**
     * Get all the optimizedImages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OptimizedImagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" optimizedImages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OptimizedImagesDTO> findOne(Long id);

    /**
     * Delete the "id" optimizedImages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
