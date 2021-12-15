/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SchemaMigrationDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SchemaMigrationDetails}.
 */
public interface SchemaMigrationDetailsService {

    /**
     * Save a schemaMigrationDetails.
     *
     * @param schemaMigrationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    SchemaMigrationDetailsDTO save(SchemaMigrationDetailsDTO schemaMigrationDetailsDTO);

    /**
     * Get all the schemaMigrationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchemaMigrationDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schemaMigrationDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchemaMigrationDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" schemaMigrationDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
