/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.IncomingReferersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.IncomingReferers}.
 */
public interface IncomingReferersService {

    /**
     * Save a incomingReferers.
     *
     * @param incomingReferersDTO the entity to save.
     * @return the persisted entity.
     */
    IncomingReferersDTO save(IncomingReferersDTO incomingReferersDTO);

    /**
     * Get all the incomingReferers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncomingReferersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" incomingReferers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomingReferersDTO> findOne(Long id);

    /**
     * Delete the "id" incomingReferers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
