/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.WatchedWordsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.WatchedWords}.
 */
public interface WatchedWordsService {

    /**
     * Save a watchedWords.
     *
     * @param watchedWordsDTO the entity to save.
     * @return the persisted entity.
     */
    WatchedWordsDTO save(WatchedWordsDTO watchedWordsDTO);

    /**
     * Get all the watchedWords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WatchedWordsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" watchedWords.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WatchedWordsDTO> findOne(Long id);

    /**
     * Delete the "id" watchedWords.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
