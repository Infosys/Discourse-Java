/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.SingleSignOnRecordsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.SingleSignOnRecords}.
 */
public interface SingleSignOnRecordsService {

    /**
     * Save a singleSignOnRecords.
     *
     * @param singleSignOnRecordsDTO the entity to save.
     * @return the persisted entity.
     */
    SingleSignOnRecordsDTO save(SingleSignOnRecordsDTO singleSignOnRecordsDTO);

    /**
     * Get all the singleSignOnRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SingleSignOnRecordsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" singleSignOnRecords.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SingleSignOnRecordsDTO> findOne(Long id);

    /**
     * Delete the "id" singleSignOnRecords.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
