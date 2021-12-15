/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SiteSettingsService;
import com.infy.domain.SiteSettings;
import com.infy.repository.SiteSettingsRepository;
import com.infy.service.dto.SiteSettingsDTO;
import com.infy.service.mapper.SiteSettingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SiteSettings}.
 */
@Service
@Transactional
public class SiteSettingsServiceImpl implements SiteSettingsService {

    private final Logger log = LoggerFactory.getLogger(SiteSettingsServiceImpl.class);

    private final SiteSettingsRepository siteSettingsRepository;

    private final SiteSettingsMapper siteSettingsMapper;

    public SiteSettingsServiceImpl(SiteSettingsRepository siteSettingsRepository, SiteSettingsMapper siteSettingsMapper) {
        this.siteSettingsRepository = siteSettingsRepository;
        this.siteSettingsMapper = siteSettingsMapper;
    }

    @Override
    public SiteSettingsDTO save(SiteSettingsDTO siteSettingsDTO) {
        log.debug("Request to save SiteSettings : {}", siteSettingsDTO);
        SiteSettings siteSettings = siteSettingsMapper.toEntity(siteSettingsDTO);
        siteSettings = siteSettingsRepository.save(siteSettings);
        return siteSettingsMapper.toDto(siteSettings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteSettingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SiteSettings");
        return siteSettingsRepository.findAll(pageable)
            .map(siteSettingsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SiteSettingsDTO> findOne(Long id) {
        log.debug("Request to get SiteSettings : {}", id);
        return siteSettingsRepository.findById(id)
            .map(siteSettingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SiteSettings : {}", id);
        siteSettingsRepository.deleteById(id);
    }
}
