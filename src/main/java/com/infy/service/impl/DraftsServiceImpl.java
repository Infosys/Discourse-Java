/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DraftsService;
import com.infy.domain.Drafts;
import com.infy.repository.DraftsRepository;
import com.infy.service.dto.DraftsDTO;
import com.infy.service.mapper.DraftsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Drafts}.
 */
@Service
@Transactional
public class DraftsServiceImpl implements DraftsService {

    private final Logger log = LoggerFactory.getLogger(DraftsServiceImpl.class);

    private final DraftsRepository draftsRepository;

    private final DraftsMapper draftsMapper;

    public DraftsServiceImpl(DraftsRepository draftsRepository, DraftsMapper draftsMapper) {
        this.draftsRepository = draftsRepository;
        this.draftsMapper = draftsMapper;
    }

    @Override
    public DraftsDTO save(DraftsDTO draftsDTO) {
        log.debug("Request to save Drafts : {}", draftsDTO);
        Drafts drafts = draftsMapper.toEntity(draftsDTO);
        drafts = draftsRepository.save(drafts);
        return draftsMapper.toDto(drafts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DraftsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drafts");
        return draftsRepository.findAll(pageable)
            .map(draftsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DraftsDTO> findOne(Long id) {
        log.debug("Request to get Drafts : {}", id);
        return draftsRepository.findById(id)
            .map(draftsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drafts : {}", id);
        draftsRepository.deleteById(id);
    }
}
