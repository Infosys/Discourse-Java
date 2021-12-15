/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ThemeSettingsService;
import com.infy.domain.ThemeSettings;
import com.infy.repository.ThemeSettingsRepository;
import com.infy.service.dto.ThemeSettingsDTO;
import com.infy.service.mapper.ThemeSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThemeSettings}.
 */
@Service
@Transactional
public class ThemeSettingsServiceImpl implements ThemeSettingsService {

    private final Logger log = LoggerFactory.getLogger(ThemeSettingsServiceImpl.class);

    private final ThemeSettingsRepository themeSettingsRepository;

    private final ThemeSettingsMapper themeSettingsMapper;

    public ThemeSettingsServiceImpl(ThemeSettingsRepository themeSettingsRepository, ThemeSettingsMapper themeSettingsMapper) {
        this.themeSettingsRepository = themeSettingsRepository;
        this.themeSettingsMapper = themeSettingsMapper;
    }

    @Override
    public ThemeSettingsDTO save(ThemeSettingsDTO themeSettingsDTO) {
        log.debug("Request to save ThemeSettings : {}", themeSettingsDTO);
        ThemeSettings themeSettings = themeSettingsMapper.toEntity(themeSettingsDTO);
        themeSettings = themeSettingsRepository.save(themeSettings);
        return themeSettingsMapper.toDto(themeSettings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeSettings");
        return themeSettingsRepository.findAll(pageable)
            .map(themeSettingsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeSettingsDTO> findOne(Long id) {
        log.debug("Request to get ThemeSettings : {}", id);
        return themeSettingsRepository.findById(id)
            .map(themeSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThemeSettings : {}", id);
        themeSettingsRepository.deleteById(id);
    }
}
