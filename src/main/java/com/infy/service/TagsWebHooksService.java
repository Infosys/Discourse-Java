/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.TagsWebHooksDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.TagsWebHooks}.
 */
public interface TagsWebHooksService {

    /**
     * Save a tagsWebHooks.
     *
     * @param tagsWebHooksDTO the entity to save.
     * @return the persisted entity.
     */
    TagsWebHooksDTO save(TagsWebHooksDTO tagsWebHooksDTO);

    /**
     * Get all the tagsWebHooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TagsWebHooksDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tagsWebHooks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TagsWebHooksDTO> findOne(Long id);

    /**
     * Delete the "id" tagsWebHooks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
