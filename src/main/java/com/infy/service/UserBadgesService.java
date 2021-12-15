/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserBadgesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserBadges}.
 */
public interface UserBadgesService {

    /**
     * Save a userBadges.
     *
     * @param userBadgesDTO the entity to save.
     * @return the persisted entity.
     */
    UserBadgesDTO save(UserBadgesDTO userBadgesDTO);

    /**
     * Get all the userBadges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserBadgesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userBadges.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserBadgesDTO> findOne(Long id);

    /**
     * Delete the "id" userBadges.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
