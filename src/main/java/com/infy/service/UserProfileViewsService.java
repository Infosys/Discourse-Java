/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserProfileViewsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserProfileViews}.
 */
public interface UserProfileViewsService {

    /**
     * Save a userProfileViews.
     *
     * @param userProfileViewsDTO the entity to save.
     * @return the persisted entity.
     */
    UserProfileViewsDTO save(UserProfileViewsDTO userProfileViewsDTO);

    /**
     * Get all the userProfileViews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserProfileViewsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userProfileViews.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserProfileViewsDTO> findOne(Long id);

    /**
     * Delete the "id" userProfileViews.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
