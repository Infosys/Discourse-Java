/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TagUsersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TagUsers}.
 */
public interface TagUsersService {

    /**
     * Save a tagUsers.
     *
     * @param tagUsersDTO the entity to save.
     * @return the persisted entity.
     */
    TagUsersDTO save(TagUsersDTO tagUsersDTO);

    /**
     * Get all the tagUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagUsersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tagUsers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagUsersDTO> findOne(Long id);

    /**
     * Delete the "id" tagUsers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
