/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BackupMetadataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BackupMetadata}.
 */
public interface BackupMetadataService {

    /**
     * Save a backupMetadata.
     *
     * @param backupMetadataDTO the entity to save.
     * @return the persisted entity.
     */
    BackupMetadataDTO save(BackupMetadataDTO backupMetadataDTO);

    /**
     * Get all the backupMetadata.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BackupMetadataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" backupMetadata.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BackupMetadataDTO> findOne(Long id);

    /**
     * Delete the "id" backupMetadata.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
