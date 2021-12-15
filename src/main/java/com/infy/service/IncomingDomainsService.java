/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.IncomingDomainsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.IncomingDomains}.
 */
public interface IncomingDomainsService {

    /**
     * Save a incomingDomains.
     *
     * @param incomingDomainsDTO the entity to save.
     * @return the persisted entity.
     */
    IncomingDomainsDTO save(IncomingDomainsDTO incomingDomainsDTO);

    /**
     * Get all the incomingDomains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncomingDomainsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" incomingDomains.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncomingDomainsDTO> findOne(Long id);

    /**
     * Delete the "id" incomingDomains.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
