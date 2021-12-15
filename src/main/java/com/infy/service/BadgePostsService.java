/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BadgePostsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BadgePosts}.
 */
public interface BadgePostsService {

    /**
     * Save a badgePosts.
     *
     * @param badgePostsDTO the entity to save.
     * @return the persisted entity.
     */
    BadgePostsDTO save(BadgePostsDTO badgePostsDTO);

    /**
     * Get all the badgePosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgePostsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" badgePosts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgePostsDTO> findOne(Long id);

    /**
     * Delete the "id" badgePosts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
