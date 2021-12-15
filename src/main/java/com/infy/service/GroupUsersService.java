/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupUsers}.
 */
public interface GroupUsersService {

    /**
     * Save a groupUsers.
     *
     * @param groupUsersDTO the entity to save.
     * @return the persisted entity.
     */
    GroupUsersDTO save(GroupUsersDTO groupUsersDTO);

    /**
     * Get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupUsersDTO> findOne(Long id);

    /**
     * Delete the "id" groupUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
