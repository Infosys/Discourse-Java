/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserSearchDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserSearchData}.
 */
public interface UserSearchDataService {

    /**
     * Save a userSearchData.
     *
     * @param userSearchDataDTO the entity to save.
     * @return the persisted entity.
     */
    UserSearchDataDTO save(UserSearchDataDTO userSearchDataDTO);

    /**
     * Get all the userSearchData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSearchDataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userSearchData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSearchDataDTO> findOne(Long id);

    /**
     * Delete the "id" userSearchData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
