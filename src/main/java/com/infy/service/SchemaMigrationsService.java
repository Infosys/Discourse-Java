/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SchemaMigrationsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SchemaMigrations}.
 */
public interface SchemaMigrationsService {

    /**
     * Save a schemaMigrations.
     *
     * @param schemaMigrationsDTO the entity to save.
     * @return the persisted entity.
     */
    SchemaMigrationsDTO save(SchemaMigrationsDTO schemaMigrationsDTO);

    /**
     * Get all the schemaMigrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchemaMigrationsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schemaMigrations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchemaMigrationsDTO> findOne(Long id);

    /**
     * Delete the "id" schemaMigrations.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
