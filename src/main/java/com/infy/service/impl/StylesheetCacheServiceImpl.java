/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.StylesheetCacheService;
import com.infy.domain.StylesheetCache;
import com.infy.repository.StylesheetCacheRepository;
import com.infy.service.dto.StylesheetCacheDTO;
import com.infy.service.mapper.StylesheetCacheMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StylesheetCache}.
 */
@Service
@Transactional
public class StylesheetCacheServiceImpl implements StylesheetCacheService {

    private final Logger log = LoggerFactory.getLogger(StylesheetCacheServiceImpl.class);

    private final StylesheetCacheRepository stylesheetCacheRepository;

    private final StylesheetCacheMapper stylesheetCacheMapper;

    public StylesheetCacheServiceImpl(StylesheetCacheRepository stylesheetCacheRepository, StylesheetCacheMapper stylesheetCacheMapper) {
        this.stylesheetCacheRepository = stylesheetCacheRepository;
        this.stylesheetCacheMapper = stylesheetCacheMapper;
    }

    @Override
    public StylesheetCacheDTO save(StylesheetCacheDTO stylesheetCacheDTO) {
        log.debug("Request to save StylesheetCache : {}", stylesheetCacheDTO);
        StylesheetCache stylesheetCache = stylesheetCacheMapper.toEntity(stylesheetCacheDTO);
        stylesheetCache = stylesheetCacheRepository.save(stylesheetCache);
        return stylesheetCacheMapper.toDto(stylesheetCache);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StylesheetCacheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StylesheetCaches");
        return stylesheetCacheRepository.findAll(pageable)
            .map(stylesheetCacheMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StylesheetCacheDTO> findOne(Long id) {
        log.debug("Request to get StylesheetCache : {}", id);
        return stylesheetCacheRepository.findById(id)
            .map(stylesheetCacheMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StylesheetCache : {}", id);
        stylesheetCacheRepository.deleteById(id);
    }
}
