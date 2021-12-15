/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.AnonymousUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.AnonymousUsers}.
 */
public interface AnonymousUsersService {

    /**
     * Save a anonymousUsers.
     *
     * @param anonymousUsersDTO the entity to save.
     * @return the persisted entity.
     */
    AnonymousUsersDTO save(AnonymousUsersDTO anonymousUsersDTO);

    /**
     * Get all the anonymousUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnonymousUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" anonymousUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnonymousUsersDTO> findOne(Long id);

    /**
     * Delete the "id" anonymousUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
