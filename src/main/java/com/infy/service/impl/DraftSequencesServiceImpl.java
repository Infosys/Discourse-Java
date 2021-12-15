/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DraftSequencesService;
import com.infy.domain.DraftSequences;
import com.infy.repository.DraftSequencesRepository;
import com.infy.service.dto.DraftSequencesDTO;
import com.infy.service.mapper.DraftSequencesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DraftSequences}.
 */
@Service
@Transactional
public class DraftSequencesServiceImpl implements DraftSequencesService {

    private final Logger log = LoggerFactory.getLogger(DraftSequencesServiceImpl.class);

    private final DraftSequencesRepository draftSequencesRepository;

    private final DraftSequencesMapper draftSequencesMapper;

    public DraftSequencesServiceImpl(DraftSequencesRepository draftSequencesRepository, DraftSequencesMapper draftSequencesMapper) {
        this.draftSequencesRepository = draftSequencesRepository;
        this.draftSequencesMapper = draftSequencesMapper;
    }

    @Override
    public DraftSequencesDTO save(DraftSequencesDTO draftSequencesDTO) {
        log.debug("Request to save DraftSequences : {}", draftSequencesDTO);
        DraftSequences draftSequences = draftSequencesMapper.toEntity(draftSequencesDTO);
        draftSequences = draftSequencesRepository.save(draftSequences);
        return draftSequencesMapper.toDto(draftSequences);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DraftSequencesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DraftSequences");
        return draftSequencesRepository.findAll(pageable)
            .map(draftSequencesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DraftSequencesDTO> findOne(Long id) {
        log.debug("Request to get DraftSequences : {}", id);
        return draftSequencesRepository.findById(id)
            .map(draftSequencesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DraftSequences : {}", id);
        draftSequencesRepository.deleteById(id);
    }
}
