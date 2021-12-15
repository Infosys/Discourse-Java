/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TagGroupMembershipsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TagGroupMemberships}.
 */
public interface TagGroupMembershipsService {

    /**
     * Save a tagGroupMemberships.
     *
     * @param tagGroupMembershipsDTO the entity to save.
     * @return the persisted entity.
     */
    TagGroupMembershipsDTO save(TagGroupMembershipsDTO tagGroupMembershipsDTO);

    /**
     * Get all the tagGroupMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagGroupMembershipsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tagGroupMemberships.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagGroupMembershipsDTO> findOne(Long id);

    /**
     * Delete the "id" tagGroupMemberships.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
