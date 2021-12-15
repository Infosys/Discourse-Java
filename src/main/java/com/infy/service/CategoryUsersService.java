/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.CategoryUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.CategoryUsers}.
 */
public interface CategoryUsersService {

    /**
     * Save a categoryUsers.
     *
     * @param categoryUsersDTO the entity to save.
     * @return the persisted entity.
     */
    CategoryUsersDTO save(CategoryUsersDTO categoryUsersDTO);

    /**
     * Get all the categoryUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategoryUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categoryUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategoryUsersDTO> findOne(Long id);

    /**
     * Delete the "id" categoryUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
