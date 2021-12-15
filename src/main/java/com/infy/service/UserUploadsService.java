/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserUploadsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserUploads}.
 */
public interface UserUploadsService {

    /**
     * Save a userUploads.
     *
     * @param userUploadsDTO the entity to save.
     * @return the persisted entity.
     */
    UserUploadsDTO save(UserUploadsDTO userUploadsDTO);

    /**
     * Get all the userUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserUploadsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userUploads.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserUploadsDTO> findOne(Long id);

    /**
     * Delete the "id" userUploads.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
