/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.MessageBusDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.MessageBus}.
 */
public interface MessageBusService {

    /**
     * Save a messageBus.
     *
     * @param messageBusDTO the entity to save.
     * @return the persisted entity.
     */
    MessageBusDTO save(MessageBusDTO messageBusDTO);

    /**
     * Get all the messageBuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageBusDTO> findAll(Pageable pageable);


    /**
     * Get the "id" messageBus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageBusDTO> findOne(Long id);

    /**
     * Delete the "id" messageBus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
