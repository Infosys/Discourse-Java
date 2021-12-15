/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostDetailsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostDetails}.
 */
public interface PostDetailsService {

    /**
     * Save a postDetails.
     *
     * @param postDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PostDetailsDTO save(PostDetailsDTO postDetailsDTO);

    /**
     * Get all the postDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostDetailsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" postDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
