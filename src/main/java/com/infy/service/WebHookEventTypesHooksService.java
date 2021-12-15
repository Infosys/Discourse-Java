/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.WebHookEventTypesHooksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.WebHookEventTypesHooks}.
 */
public interface WebHookEventTypesHooksService {

    /**
     * Save a webHookEventTypesHooks.
     *
     * @param webHookEventTypesHooksDTO the entity to save.
     * @return the persisted entity.
     */
    WebHookEventTypesHooksDTO save(WebHookEventTypesHooksDTO webHookEventTypesHooksDTO);

    /**
     * Get all the webHookEventTypesHooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebHookEventTypesHooksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" webHookEventTypesHooks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebHookEventTypesHooksDTO> findOne(Long id);

    /**
     * Delete the "id" webHookEventTypesHooks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
