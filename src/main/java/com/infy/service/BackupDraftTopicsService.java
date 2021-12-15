/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BackupDraftTopicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BackupDraftTopics}.
 */
public interface BackupDraftTopicsService {

    /**
     * Save a backupDraftTopics.
     *
     * @param backupDraftTopicsDTO the entity to save.
     * @return the persisted entity.
     */
    BackupDraftTopicsDTO save(BackupDraftTopicsDTO backupDraftTopicsDTO);

    /**
     * Get all the backupDraftTopics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BackupDraftTopicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" backupDraftTopics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BackupDraftTopicsDTO> findOne(Long id);

    /**
     * Delete the "id" backupDraftTopics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
