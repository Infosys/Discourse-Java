/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SharedDraftsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SharedDrafts}.
 */
public interface SharedDraftsService {

    /**
     * Save a sharedDrafts.
     *
     * @param sharedDraftsDTO the entity to save.
     * @return the persisted entity.
     */
    SharedDraftsDTO save(SharedDraftsDTO sharedDraftsDTO);

    /**
     * Get all the sharedDrafts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SharedDraftsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" sharedDrafts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SharedDraftsDTO> findOne(Long id);

    /**
     * Delete the "id" sharedDrafts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
