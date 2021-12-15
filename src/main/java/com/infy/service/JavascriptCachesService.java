/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.JavascriptCachesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.JavascriptCaches}.
 */
public interface JavascriptCachesService {

    /**
     * Save a javascriptCaches.
     *
     * @param javascriptCachesDTO the entity to save.
     * @return the persisted entity.
     */
    JavascriptCachesDTO save(JavascriptCachesDTO javascriptCachesDTO);

    /**
     * Get all the javascriptCaches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JavascriptCachesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" javascriptCaches.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JavascriptCachesDTO> findOne(Long id);

    /**
     * Delete the "id" javascriptCaches.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
