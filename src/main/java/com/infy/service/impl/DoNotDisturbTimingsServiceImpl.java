/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DoNotDisturbTimingsService;
import com.infy.domain.DoNotDisturbTimings;
import com.infy.repository.DoNotDisturbTimingsRepository;
import com.infy.service.dto.DoNotDisturbTimingsDTO;
import com.infy.service.mapper.DoNotDisturbTimingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DoNotDisturbTimings}.
 */
@Service
@Transactional
public class DoNotDisturbTimingsServiceImpl implements DoNotDisturbTimingsService {

    private final Logger log = LoggerFactory.getLogger(DoNotDisturbTimingsServiceImpl.class);

    private final DoNotDisturbTimingsRepository doNotDisturbTimingsRepository;

    private final DoNotDisturbTimingsMapper doNotDisturbTimingsMapper;

    public DoNotDisturbTimingsServiceImpl(DoNotDisturbTimingsRepository doNotDisturbTimingsRepository, DoNotDisturbTimingsMapper doNotDisturbTimingsMapper) {
        this.doNotDisturbTimingsRepository = doNotDisturbTimingsRepository;
        this.doNotDisturbTimingsMapper = doNotDisturbTimingsMapper;
    }

    @Override
    public DoNotDisturbTimingsDTO save(DoNotDisturbTimingsDTO doNotDisturbTimingsDTO) {
        log.debug("Request to save DoNotDisturbTimings : {}", doNotDisturbTimingsDTO);
        DoNotDisturbTimings doNotDisturbTimings = doNotDisturbTimingsMapper.toEntity(doNotDisturbTimingsDTO);
        doNotDisturbTimings = doNotDisturbTimingsRepository.save(doNotDisturbTimings);
        return doNotDisturbTimingsMapper.toDto(doNotDisturbTimings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DoNotDisturbTimingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DoNotDisturbTimings");
        return doNotDisturbTimingsRepository.findAll(pageable)
            .map(doNotDisturbTimingsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DoNotDisturbTimingsDTO> findOne(Long id) {
        log.debug("Request to get DoNotDisturbTimings : {}", id);
        return doNotDisturbTimingsRepository.findById(id)
            .map(doNotDisturbTimingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DoNotDisturbTimings : {}", id);
        doNotDisturbTimingsRepository.deleteById(id);
    }
}
