/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.EmbeddableHostsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.EmbeddableHosts}.
 */
public interface EmbeddableHostsService {

    /**
     * Save a embeddableHosts.
     *
     * @param embeddableHostsDTO the entity to save.
     * @return the persisted entity.
     */
    EmbeddableHostsDTO save(EmbeddableHostsDTO embeddableHostsDTO);

    /**
     * Get all the embeddableHosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmbeddableHostsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" embeddableHosts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmbeddableHostsDTO> findOne(Long id);

    /**
     * Delete the "id" embeddableHosts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
