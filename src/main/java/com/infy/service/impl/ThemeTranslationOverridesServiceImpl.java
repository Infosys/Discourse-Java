/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ThemeTranslationOverridesService;
import com.infy.domain.ThemeTranslationOverrides;
import com.infy.repository.ThemeTranslationOverridesRepository;
import com.infy.service.dto.ThemeTranslationOverridesDTO;
import com.infy.service.mapper.ThemeTranslationOverridesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThemeTranslationOverrides}.
 */
@Service
@Transactional
public class ThemeTranslationOverridesServiceImpl implements ThemeTranslationOverridesService {

    private final Logger log = LoggerFactory.getLogger(ThemeTranslationOverridesServiceImpl.class);

    private final ThemeTranslationOverridesRepository themeTranslationOverridesRepository;

    private final ThemeTranslationOverridesMapper themeTranslationOverridesMapper;

    public ThemeTranslationOverridesServiceImpl(ThemeTranslationOverridesRepository themeTranslationOverridesRepository, ThemeTranslationOverridesMapper themeTranslationOverridesMapper) {
        this.themeTranslationOverridesRepository = themeTranslationOverridesRepository;
        this.themeTranslationOverridesMapper = themeTranslationOverridesMapper;
    }

    @Override
    public ThemeTranslationOverridesDTO save(ThemeTranslationOverridesDTO themeTranslationOverridesDTO) {
        log.debug("Request to save ThemeTranslationOverrides : {}", themeTranslationOverridesDTO);
        ThemeTranslationOverrides themeTranslationOverrides = themeTranslationOverridesMapper.toEntity(themeTranslationOverridesDTO);
        themeTranslationOverrides = themeTranslationOverridesRepository.save(themeTranslationOverrides);
        return themeTranslationOverridesMapper.toDto(themeTranslationOverrides);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeTranslationOverridesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeTranslationOverrides");
        return themeTranslationOverridesRepository.findAll(pageable)
            .map(themeTranslationOverridesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeTranslationOverridesDTO> findOne(Long id) {
        log.debug("Request to get ThemeTranslationOverrides : {}", id);
        return themeTranslationOverridesRepository.findById(id)
            .map(themeTranslationOverridesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThemeTranslationOverrides : {}", id);
        themeTranslationOverridesRepository.deleteById(id);
    }
}
