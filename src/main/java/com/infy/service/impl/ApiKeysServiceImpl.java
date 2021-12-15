/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ApiKeysService;
import com.infy.domain.ApiKeys;
import com.infy.repository.ApiKeysRepository;
import com.infy.service.dto.ApiKeysDTO;
import com.infy.service.mapper.ApiKeysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiKeys}.
 */
@Service
@Transactional
public class ApiKeysServiceImpl implements ApiKeysService {

    private final Logger log = LoggerFactory.getLogger(ApiKeysServiceImpl.class);

    private final ApiKeysRepository apiKeysRepository;

    private final ApiKeysMapper apiKeysMapper;

    public ApiKeysServiceImpl(ApiKeysRepository apiKeysRepository, ApiKeysMapper apiKeysMapper) {
        this.apiKeysRepository = apiKeysRepository;
        this.apiKeysMapper = apiKeysMapper;
    }

    @Override
    public ApiKeysDTO save(ApiKeysDTO apiKeysDTO) {
        log.debug("Request to save ApiKeys : {}", apiKeysDTO);
        ApiKeys apiKeys = apiKeysMapper.toEntity(apiKeysDTO);
        apiKeys = apiKeysRepository.save(apiKeys);
        return apiKeysMapper.toDto(apiKeys);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiKeys");
        return apiKeysRepository.findAll(pageable)
            .map(apiKeysMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApiKeysDTO> findOne(Long id) {
        log.debug("Request to get ApiKeys : {}", id);
        return apiKeysRepository.findById(id)
            .map(apiKeysMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiKeys : {}", id);
        apiKeysRepository.deleteById(id);
    }
}
