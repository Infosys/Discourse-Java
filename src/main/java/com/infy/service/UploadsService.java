/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UploadsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Uploads}.
 */
public interface UploadsService {

    /**
     * Save a uploads.
     *
     * @param uploadsDTO the entity to save.
     * @return the persisted entity.
     */
    UploadsDTO save(UploadsDTO uploadsDTO);

    /**
     * Get all the uploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UploadsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" uploads.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UploadsDTO> findOne(Long id);

    /**
     * Delete the "id" uploads.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
