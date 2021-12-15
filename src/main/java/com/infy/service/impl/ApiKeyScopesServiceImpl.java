/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ApiKeyScopesService;
import com.infy.domain.ApiKeyScopes;
import com.infy.repository.ApiKeyScopesRepository;
import com.infy.service.dto.ApiKeyScopesDTO;
import com.infy.service.mapper.ApiKeyScopesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ApiKeyScopes}.
 */
@Service
@Transactional
public class ApiKeyScopesServiceImpl implements ApiKeyScopesService {

    private final Logger log = LoggerFactory.getLogger(ApiKeyScopesServiceImpl.class);

    private final ApiKeyScopesRepository apiKeyScopesRepository;

    private final ApiKeyScopesMapper apiKeyScopesMapper;

    public ApiKeyScopesServiceImpl(ApiKeyScopesRepository apiKeyScopesRepository, ApiKeyScopesMapper apiKeyScopesMapper) {
        this.apiKeyScopesRepository = apiKeyScopesRepository;
        this.apiKeyScopesMapper = apiKeyScopesMapper;
    }

    @Override
    public ApiKeyScopesDTO save(ApiKeyScopesDTO apiKeyScopesDTO) {
        log.debug("Request to save ApiKeyScopes : {}", apiKeyScopesDTO);
        ApiKeyScopes apiKeyScopes = apiKeyScopesMapper.toEntity(apiKeyScopesDTO);
        apiKeyScopes = apiKeyScopesRepository.save(apiKeyScopes);
        return apiKeyScopesMapper.toDto(apiKeyScopes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ApiKeyScopesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApiKeyScopes");
        return apiKeyScopesRepository.findAll(pageable)
            .map(apiKeyScopesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApiKeyScopesDTO> findOne(Long id) {
        log.debug("Request to get ApiKeyScopes : {}", id);
        return apiKeyScopesRepository.findById(id)
            .map(apiKeyScopesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApiKeyScopes : {}", id);
        apiKeyScopesRepository.deleteById(id);
    }
}
