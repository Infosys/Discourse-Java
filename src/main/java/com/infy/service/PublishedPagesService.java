/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PublishedPagesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PublishedPages}.
 */
public interface PublishedPagesService {

    /**
     * Save a publishedPages.
     *
     * @param publishedPagesDTO the entity to save.
     * @return the persisted entity.
     */
    PublishedPagesDTO save(PublishedPagesDTO publishedPagesDTO);

    /**
     * Get all the publishedPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublishedPagesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" publishedPages.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublishedPagesDTO> findOne(Long id);

    /**
     * Delete the "id" publishedPages.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
