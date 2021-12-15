/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostReplyKeysDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostReplyKeys}.
 */
public interface PostReplyKeysService {

    /**
     * Save a postReplyKeys.
     *
     * @param postReplyKeysDTO the entity to save.
     * @return the persisted entity.
     */
    PostReplyKeysDTO save(PostReplyKeysDTO postReplyKeysDTO);

    /**
     * Get all the postReplyKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostReplyKeysDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postReplyKeys.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostReplyKeysDTO> findOne(Long id);

    /**
     * Delete the "id" postReplyKeys.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
