/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserExportsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserExports}.
 */
public interface UserExportsService {

    /**
     * Save a userExports.
     *
     * @param userExportsDTO the entity to save.
     * @return the persisted entity.
     */
    UserExportsDTO save(UserExportsDTO userExportsDTO);

    /**
     * Get all the userExports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserExportsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userExports.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserExportsDTO> findOne(Long id);

    /**
     * Delete the "id" userExports.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
