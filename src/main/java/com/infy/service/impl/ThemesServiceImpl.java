/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ThemesService;
import com.infy.domain.Themes;
import com.infy.repository.ThemesRepository;
import com.infy.service.dto.ThemesDTO;
import com.infy.service.mapper.ThemesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Themes}.
 */
@Service
@Transactional
public class ThemesServiceImpl implements ThemesService {

    private final Logger log = LoggerFactory.getLogger(ThemesServiceImpl.class);

    private final ThemesRepository themesRepository;

    private final ThemesMapper themesMapper;

    public ThemesServiceImpl(ThemesRepository themesRepository, ThemesMapper themesMapper) {
        this.themesRepository = themesRepository;
        this.themesMapper = themesMapper;
    }

    @Override
    public ThemesDTO save(ThemesDTO themesDTO) {
        log.debug("Request to save Themes : {}", themesDTO);
        Themes themes = themesMapper.toEntity(themesDTO);
        themes = themesRepository.save(themes);
        return themesMapper.toDto(themes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Themes");
        return themesRepository.findAll(pageable)
            .map(themesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThemesDTO> findOne(Long id) {
        log.debug("Request to get Themes : {}", id);
        return themesRepository.findById(id)
            .map(themesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Themes : {}", id);
        themesRepository.deleteById(id);
    }
}
