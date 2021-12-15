/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SchemaMigrationsService;
import com.infy.domain.SchemaMigrations;
import com.infy.repository.SchemaMigrationsRepository;
import com.infy.service.dto.SchemaMigrationsDTO;
import com.infy.service.mapper.SchemaMigrationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchemaMigrations}.
 */
@Service
@Transactional
public class SchemaMigrationsServiceImpl implements SchemaMigrationsService {

    private final Logger log = LoggerFactory.getLogger(SchemaMigrationsServiceImpl.class);

    private final SchemaMigrationsRepository schemaMigrationsRepository;

    private final SchemaMigrationsMapper schemaMigrationsMapper;

    public SchemaMigrationsServiceImpl(SchemaMigrationsRepository schemaMigrationsRepository, SchemaMigrationsMapper schemaMigrationsMapper) {
        this.schemaMigrationsRepository = schemaMigrationsRepository;
        this.schemaMigrationsMapper = schemaMigrationsMapper;
    }

    @Override
    public SchemaMigrationsDTO save(SchemaMigrationsDTO schemaMigrationsDTO) {
        log.debug("Request to save SchemaMigrations : {}", schemaMigrationsDTO);
        SchemaMigrations schemaMigrations = schemaMigrationsMapper.toEntity(schemaMigrationsDTO);
        schemaMigrations = schemaMigrationsRepository.save(schemaMigrations);
        return schemaMigrationsMapper.toDto(schemaMigrations);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SchemaMigrationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchemaMigrations");
        return schemaMigrationsRepository.findAll(pageable)
            .map(schemaMigrationsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SchemaMigrationsDTO> findOne(Long id) {
        log.debug("Request to get SchemaMigrations : {}", id);
        return schemaMigrationsRepository.findById(id)
            .map(schemaMigrationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchemaMigrations : {}", id);
        schemaMigrationsRepository.deleteById(id);
    }
}
