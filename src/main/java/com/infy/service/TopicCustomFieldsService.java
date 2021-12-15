/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TopicCustomFieldsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TopicCustomFields}.
 */
public interface TopicCustomFieldsService {

    /**
     * Save a topicCustomFields.
     *
     * @param topicCustomFieldsDTO the entity to save.
     * @return the persisted entity.
     */
    TopicCustomFieldsDTO save(TopicCustomFieldsDTO topicCustomFieldsDTO);

    /**
     * Get all the topicCustomFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TopicCustomFieldsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" topicCustomFields.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TopicCustomFieldsDTO> findOne(Long id);

    /**
     * Delete the "id" topicCustomFields.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
