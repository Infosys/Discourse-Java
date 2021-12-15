/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.InvitesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Invites}.
 */
public interface InvitesService {

    /**
     * Save a invites.
     *
     * @param invitesDTO the entity to save.
     * @return the persisted entity.
     */
    InvitesDTO save(InvitesDTO invitesDTO);

    /**
     * Get all the invites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InvitesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" invites.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvitesDTO> findOne(Long id);

    /**
     * Delete the "id" invites.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
