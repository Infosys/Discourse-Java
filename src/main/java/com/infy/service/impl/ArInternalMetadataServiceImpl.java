/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ArInternalMetadataService;
import com.infy.domain.ArInternalMetadata;
import com.infy.repository.ArInternalMetadataRepository;
import com.infy.service.dto.ArInternalMetadataDTO;
import com.infy.service.mapper.ArInternalMetadataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ArInternalMetadata}.
 */
@Service
@Transactional
public class ArInternalMetadataServiceImpl implements ArInternalMetadataService {

    private final Logger log = LoggerFactory.getLogger(ArInternalMetadataServiceImpl.class);

    private final ArInternalMetadataRepository arInternalMetadataRepository;

    private final ArInternalMetadataMapper arInternalMetadataMapper;

    public ArInternalMetadataServiceImpl(ArInternalMetadataRepository arInternalMetadataRepository, ArInternalMetadataMapper arInternalMetadataMapper) {
        this.arInternalMetadataRepository = arInternalMetadataRepository;
        this.arInternalMetadataMapper = arInternalMetadataMapper;
    }

    @Override
    public ArInternalMetadataDTO save(ArInternalMetadataDTO arInternalMetadataDTO) {
        log.debug("Request to save ArInternalMetadata : {}", arInternalMetadataDTO);
        ArInternalMetadata arInternalMetadata = arInternalMetadataMapper.toEntity(arInternalMetadataDTO);
        arInternalMetadata = arInternalMetadataRepository.save(arInternalMetadata);
        return arInternalMetadataMapper.toDto(arInternalMetadata);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArInternalMetadataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArInternalMetadata");
        return arInternalMetadataRepository.findAll(pageable)
            .map(arInternalMetadataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ArInternalMetadataDTO> findOne(Long id) {
        log.debug("Request to get ArInternalMetadata : {}", id);
        return arInternalMetadataRepository.findById(id)
            .map(arInternalMetadataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArInternalMetadata : {}", id);
        arInternalMetadataRepository.deleteById(id);
    }
}
