/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.GroupsWebHooksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.GroupsWebHooks}.
 */
public interface GroupsWebHooksService {

    /**
     * Save a groupsWebHooks.
     *
     * @param groupsWebHooksDTO the entity to save.
     * @return the persisted entity.
     */
    GroupsWebHooksDTO save(GroupsWebHooksDTO groupsWebHooksDTO);

    /**
     * Get all the groupsWebHooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupsWebHooksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupsWebHooks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupsWebHooksDTO> findOne(Long id);

    /**
     * Delete the "id" groupsWebHooks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
