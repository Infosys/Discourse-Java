/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DraftsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Drafts}.
 */
public interface DraftsService {

    /**
     * Save a drafts.
     *
     * @param draftsDTO the entity to save.
     * @return the persisted entity.
     */
    DraftsDTO save(DraftsDTO draftsDTO);

    /**
     * Get all the drafts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DraftsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" drafts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DraftsDTO> findOne(Long id);

    /**
     * Delete the "id" drafts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
