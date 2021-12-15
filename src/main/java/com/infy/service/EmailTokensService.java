/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.EmailTokensDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.EmailTokens}.
 */
public interface EmailTokensService {

    /**
     * Save a emailTokens.
     *
     * @param emailTokensDTO the entity to save.
     * @return the persisted entity.
     */
    EmailTokensDTO save(EmailTokensDTO emailTokensDTO);

    /**
     * Get all the emailTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailTokensDTO> findAll(Pageable pageable);


    /**
     * Get the "id" emailTokens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailTokensDTO> findOne(Long id);

    /**
     * Delete the "id" emailTokens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
