/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UserSecondFactorsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.UserSecondFactors}.
 */
public interface UserSecondFactorsService {

    /**
     * Save a userSecondFactors.
     *
     * @param userSecondFactorsDTO the entity to save.
     * @return the persisted entity.
     */
    UserSecondFactorsDTO save(UserSecondFactorsDTO userSecondFactorsDTO);

    /**
     * Get all the userSecondFactors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserSecondFactorsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userSecondFactors.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSecondFactorsDTO> findOne(Long id);

    /**
     * Delete the "id" userSecondFactors.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
