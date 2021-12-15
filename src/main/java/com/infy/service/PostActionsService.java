/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostActionsDTO;
import com.infy.service.model.CreatePostActionRequest;
import com.infy.service.model.TopicOrPostResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostActions}.
 */
public interface PostActionsService {

    /**
     * Save a postActions.
     *
     * @param postActionsDTO the entity to save.
     * @return the persisted entity.
     */
    PostActionsDTO save(PostActionsDTO postActionsDTO);

    /**
     * Get all the postActions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostActionsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postActions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostActionsDTO> findOne(Long id);

    /**
     * Delete the "id" postActions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    TopicOrPostResponse createPostAction(CreatePostActionRequest createPostActionRequest);

    TopicOrPostResponse removePostAction(CreatePostActionRequest createPostActionRequest);
}
