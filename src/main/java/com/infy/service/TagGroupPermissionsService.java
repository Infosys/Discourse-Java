/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TagGroupPermissionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TagGroupPermissions}.
 */
public interface TagGroupPermissionsService {

    /**
     * Save a tagGroupPermissions.
     *
     * @param tagGroupPermissionsDTO the entity to save.
     * @return the persisted entity.
     */
    TagGroupPermissionsDTO save(TagGroupPermissionsDTO tagGroupPermissionsDTO);

    /**
     * Get all the tagGroupPermissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagGroupPermissionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tagGroupPermissions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagGroupPermissionsDTO> findOne(Long id);

    /**
     * Delete the "id" tagGroupPermissions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
