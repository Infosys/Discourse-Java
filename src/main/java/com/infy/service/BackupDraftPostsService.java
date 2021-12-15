/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BackupDraftPostsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.BackupDraftPosts}.
 */
public interface BackupDraftPostsService {

    /**
     * Save a backupDraftPosts.
     *
     * @param backupDraftPostsDTO the entity to save.
     * @return the persisted entity.
     */
    BackupDraftPostsDTO save(BackupDraftPostsDTO backupDraftPostsDTO);

    /**
     * Get all the backupDraftPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BackupDraftPostsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" backupDraftPosts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BackupDraftPostsDTO> findOne(Long id);

    /**
     * Delete the "id" backupDraftPosts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
