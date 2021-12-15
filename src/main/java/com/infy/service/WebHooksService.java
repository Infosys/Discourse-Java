/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.WebHooksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.WebHooks}.
 */
public interface WebHooksService {

    /**
     * Save a webHooks.
     *
     * @param webHooksDTO the entity to save.
     * @return the persisted entity.
     */
    WebHooksDTO save(WebHooksDTO webHooksDTO);

    /**
     * Get all the webHooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebHooksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" webHooks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebHooksDTO> findOne(Long id);

    /**
     * Delete the "id" webHooks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
