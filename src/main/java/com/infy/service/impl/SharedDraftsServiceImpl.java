/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SharedDraftsService;
import com.infy.domain.SharedDrafts;
import com.infy.repository.SharedDraftsRepository;
import com.infy.service.dto.SharedDraftsDTO;
import com.infy.service.mapper.SharedDraftsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SharedDrafts}.
 */
@Service
@Transactional
public class SharedDraftsServiceImpl implements SharedDraftsService {

    private final Logger log = LoggerFactory.getLogger(SharedDraftsServiceImpl.class);

    private final SharedDraftsRepository sharedDraftsRepository;

    private final SharedDraftsMapper sharedDraftsMapper;

    public SharedDraftsServiceImpl(SharedDraftsRepository sharedDraftsRepository, SharedDraftsMapper sharedDraftsMapper) {
        this.sharedDraftsRepository = sharedDraftsRepository;
        this.sharedDraftsMapper = sharedDraftsMapper;
    }

    @Override
    public SharedDraftsDTO save(SharedDraftsDTO sharedDraftsDTO) {
        log.debug("Request to save SharedDrafts : {}", sharedDraftsDTO);
        SharedDrafts sharedDrafts = sharedDraftsMapper.toEntity(sharedDraftsDTO);
        sharedDrafts = sharedDraftsRepository.save(sharedDrafts);
        return sharedDraftsMapper.toDto(sharedDrafts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SharedDraftsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SharedDrafts");
        return sharedDraftsRepository.findAll(pageable)
            .map(sharedDraftsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SharedDraftsDTO> findOne(Long id) {
        log.debug("Request to get SharedDrafts : {}", id);
        return sharedDraftsRepository.findById(id)
            .map(sharedDraftsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SharedDrafts : {}", id);
        sharedDraftsRepository.deleteById(id);
    }
}
