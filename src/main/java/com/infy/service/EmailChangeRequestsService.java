/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.EmailChangeRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.EmailChangeRequests}.
 */
public interface EmailChangeRequestsService {

    /**
     * Save a emailChangeRequests.
     *
     * @param emailChangeRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    EmailChangeRequestsDTO save(EmailChangeRequestsDTO emailChangeRequestsDTO);

    /**
     * Get all the emailChangeRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailChangeRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emailChangeRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailChangeRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" emailChangeRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
