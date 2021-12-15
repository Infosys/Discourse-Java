/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.BookmarksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Bookmarks}.
 */
public interface BookmarksService {

    /**
     * Save a bookmarks.
     *
     * @param bookmarksDTO the entity to save.
     * @return the persisted entity.
     */
    BookmarksDTO save(BookmarksDTO bookmarksDTO);

    /**
     * Get all the bookmarks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookmarksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bookmarks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookmarksDTO> findOne(Long id);

    /**
     * Delete the "id" bookmarks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
