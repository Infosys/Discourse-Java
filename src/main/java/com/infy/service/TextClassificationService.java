/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TextClassificationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TextClassification}.
 */
public interface TextClassificationService {

    /**
     * Save a textClassification.
     *
     * @param textClassificationDTO the entity to save.
     * @return the persisted entity.
     */
    TextClassificationDTO save(TextClassificationDTO textClassificationDTO);

    /**
     * Get all the textClassifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TextClassificationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" textClassification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TextClassificationDTO> findOne(Long id);

    /**
     * Delete the "id" textClassification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<TextClassificationDTO> findByPostId(Long id);

    Optional<TextClassificationDTO> findByTopicId(Long id);
}
