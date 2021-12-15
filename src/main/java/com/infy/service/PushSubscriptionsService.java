/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PushSubscriptionsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PushSubscriptions}.
 */
public interface PushSubscriptionsService {

    /**
     * Save a pushSubscriptions.
     *
     * @param pushSubscriptionsDTO the entity to save.
     * @return the persisted entity.
     */
    PushSubscriptionsDTO save(PushSubscriptionsDTO pushSubscriptionsDTO);

    /**
     * Get all the pushSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PushSubscriptionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pushSubscriptions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PushSubscriptionsDTO> findOne(Long id);

    /**
     * Delete the "id" pushSubscriptions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
