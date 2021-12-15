/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DevelopersDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Developers}.
 */
public interface DevelopersService {

    /**
     * Save a developers.
     *
     * @param developersDTO the entity to save.
     * @return the persisted entity.
     */
    DevelopersDTO save(DevelopersDTO developersDTO);

    /**
     * Get all the developers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DevelopersDTO> findAll(Pageable pageable);


    /**
     * Get the "id" developers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DevelopersDTO> findOne(Long id);

    /**
     * Delete the "id" developers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
