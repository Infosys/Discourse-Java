/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserIpAddressHistoriesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserIpAddressHistories}.
 */
public interface UserIpAddressHistoriesService {

    /**
     * Save a userIpAddressHistories.
     *
     * @param userIpAddressHistoriesDTO the entity to save.
     * @return the persisted entity.
     */
    UserIpAddressHistoriesDTO save(UserIpAddressHistoriesDTO userIpAddressHistoriesDTO);

    /**
     * Get all the userIpAddressHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserIpAddressHistoriesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userIpAddressHistories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserIpAddressHistoriesDTO> findOne(Long id);

    /**
     * Delete the "id" userIpAddressHistories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
