/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserProfilesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserProfiles}.
 */
public interface UserProfilesService {

    /**
     * Save a userProfiles.
     *
     * @param userProfilesDTO the entity to save.
     * @return the persisted entity.
     */
    UserProfilesDTO save(UserProfilesDTO userProfilesDTO);

    /**
     * Get all the userProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserProfilesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userProfiles.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserProfilesDTO> findOne(Long id);

    /**
     * Delete the "id" userProfiles.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
