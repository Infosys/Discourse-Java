/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.DraftSequencesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.DraftSequences}.
 */
public interface DraftSequencesService {

    /**
     * Save a draftSequences.
     *
     * @param draftSequencesDTO the entity to save.
     * @return the persisted entity.
     */
    DraftSequencesDTO save(DraftSequencesDTO draftSequencesDTO);

    /**
     * Get all the draftSequences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DraftSequencesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" draftSequences.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DraftSequencesDTO> findOne(Long id);

    /**
     * Delete the "id" draftSequences.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
