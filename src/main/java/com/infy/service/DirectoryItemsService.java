/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DirectoryItemsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.DirectoryItems}.
 */
public interface DirectoryItemsService {

    /**
     * Save a directoryItems.
     *
     * @param directoryItemsDTO the entity to save.
     * @return the persisted entity.
     */
    DirectoryItemsDTO save(DirectoryItemsDTO directoryItemsDTO);

    /**
     * Get all the directoryItems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DirectoryItemsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" directoryItems.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DirectoryItemsDTO> findOne(Long id);

    /**
     * Delete the "id" directoryItems.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
