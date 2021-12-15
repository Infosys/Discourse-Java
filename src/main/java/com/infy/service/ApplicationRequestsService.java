/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.ApplicationRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.ApplicationRequests}.
 */
public interface ApplicationRequestsService {

    /**
     * Save a applicationRequests.
     *
     * @param applicationRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationRequestsDTO save(ApplicationRequestsDTO applicationRequestsDTO);

    /**
     * Get all the applicationRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApplicationRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" applicationRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" applicationRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
