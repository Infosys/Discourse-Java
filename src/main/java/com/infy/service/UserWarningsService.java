/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserWarningsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserWarnings}.
 */
public interface UserWarningsService {

    /**
     * Save a userWarnings.
     *
     * @param userWarningsDTO the entity to save.
     * @return the persisted entity.
     */
    UserWarningsDTO save(UserWarningsDTO userWarningsDTO);

    /**
     * Get all the userWarnings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserWarningsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userWarnings.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserWarningsDTO> findOne(Long id);

    /**
     * Delete the "id" userWarnings.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
