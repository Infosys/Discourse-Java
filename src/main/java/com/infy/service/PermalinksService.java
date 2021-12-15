/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PermalinksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Permalinks}.
 */
public interface PermalinksService {

    /**
     * Save a permalinks.
     *
     * @param permalinksDTO the entity to save.
     * @return the persisted entity.
     */
    PermalinksDTO save(PermalinksDTO permalinksDTO);

    /**
     * Get all the permalinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PermalinksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" permalinks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PermalinksDTO> findOne(Long id);

    /**
     * Delete the "id" permalinks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
