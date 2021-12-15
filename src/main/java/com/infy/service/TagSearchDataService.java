/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TagSearchDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TagSearchData}.
 */
public interface TagSearchDataService {

    /**
     * Save a tagSearchData.
     *
     * @param tagSearchDataDTO the entity to save.
     * @return the persisted entity.
     */
    TagSearchDataDTO save(TagSearchDataDTO tagSearchDataDTO);

    /**
     * Get all the tagSearchData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagSearchDataDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tagSearchData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagSearchDataDTO> findOne(Long id);

    /**
     * Delete the "id" tagSearchData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
