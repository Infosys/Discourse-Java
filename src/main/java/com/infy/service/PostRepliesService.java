/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostRepliesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostReplies}.
 */
public interface PostRepliesService {

    /**
     * Save a postReplies.
     *
     * @param postRepliesDTO the entity to save.
     * @return the persisted entity.
     */
    PostRepliesDTO save(PostRepliesDTO postRepliesDTO);

    /**
     * Get all the postReplies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostRepliesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postReplies.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostRepliesDTO> findOne(Long id);

    /**
     * Delete the "id" postReplies.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
