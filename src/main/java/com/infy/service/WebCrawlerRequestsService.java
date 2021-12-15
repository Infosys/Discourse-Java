/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.WebCrawlerRequestsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.WebCrawlerRequests}.
 */
public interface WebCrawlerRequestsService {

    /**
     * Save a webCrawlerRequests.
     *
     * @param webCrawlerRequestsDTO the entity to save.
     * @return the persisted entity.
     */
    WebCrawlerRequestsDTO save(WebCrawlerRequestsDTO webCrawlerRequestsDTO);

    /**
     * Get all the webCrawlerRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebCrawlerRequestsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" webCrawlerRequests.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebCrawlerRequestsDTO> findOne(Long id);

    /**
     * Delete the "id" webCrawlerRequests.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
