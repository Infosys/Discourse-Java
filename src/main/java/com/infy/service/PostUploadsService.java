/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.PostUploadsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.PostUploads}.
 */
public interface PostUploadsService {

    /**
     * Save a postUploads.
     *
     * @param postUploadsDTO the entity to save.
     * @return the persisted entity.
     */
    PostUploadsDTO save(PostUploadsDTO postUploadsDTO);

    /**
     * Get all the postUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostUploadsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" postUploads.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostUploadsDTO> findOne(Long id);

    /**
     * Delete the "id" postUploads.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
