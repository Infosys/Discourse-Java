/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SchemaMigrationDetailsService;
import com.infy.domain.SchemaMigrationDetails;
import com.infy.repository.SchemaMigrationDetailsRepository;
import com.infy.service.dto.SchemaMigrationDetailsDTO;
import com.infy.service.mapper.SchemaMigrationDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchemaMigrationDetails}.
 */
@Service
@Transactional
public class SchemaMigrationDetailsServiceImpl implements SchemaMigrationDetailsService {

    private final Logger log = LoggerFactory.getLogger(SchemaMigrationDetailsServiceImpl.class);

    private final SchemaMigrationDetailsRepository schemaMigrationDetailsRepository;

    private final SchemaMigrationDetailsMapper schemaMigrationDetailsMapper;

    public SchemaMigrationDetailsServiceImpl(SchemaMigrationDetailsRepository schemaMigrationDetailsRepository, SchemaMigrationDetailsMapper schemaMigrationDetailsMapper) {
        this.schemaMigrationDetailsRepository = schemaMigrationDetailsRepository;
        this.schemaMigrationDetailsMapper = schemaMigrationDetailsMapper;
    }

    @Override
    public SchemaMigrationDetailsDTO save(SchemaMigrationDetailsDTO schemaMigrationDetailsDTO) {
        log.debug("Request to save SchemaMigrationDetails : {}", schemaMigrationDetailsDTO);
        SchemaMigrationDetails schemaMigrationDetails = schemaMigrationDetailsMapper.toEntity(schemaMigrationDetailsDTO);
        schemaMigrationDetails = schemaMigrationDetailsRepository.save(schemaMigrationDetails);
        return schemaMigrationDetailsMapper.toDto(schemaMigrationDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchemaMigrationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaMigrationDetails");
        return schemaMigrationDetailsRepository.findAll(pageable)
            .map(schemaMigrationDetailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SchemaMigrationDetailsDTO> findOne(Long id) {
        log.debug("Request to get SchemaMigrationDetails : {}", id);
        return schemaMigrationDetailsRepository.findById(id)
            .map(schemaMigrationDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchemaMigrationDetails : {}", id);
        schemaMigrationDetailsRepository.deleteById(id);
    }
}
