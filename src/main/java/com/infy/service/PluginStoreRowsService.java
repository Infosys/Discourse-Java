/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PluginStoreRowsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PluginStoreRows}.
 */
public interface PluginStoreRowsService {

    /**
     * Save a pluginStoreRows.
     *
     * @param pluginStoreRowsDTO the entity to save.
     * @return the persisted entity.
     */
    PluginStoreRowsDTO save(PluginStoreRowsDTO pluginStoreRowsDTO);

    /**
     * Get all the pluginStoreRows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PluginStoreRowsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pluginStoreRows.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PluginStoreRowsDTO> findOne(Long id);

    /**
     * Delete the "id" pluginStoreRows.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
