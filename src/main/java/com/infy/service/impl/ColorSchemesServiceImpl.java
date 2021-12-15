/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ColorSchemesService;
import com.infy.domain.ColorSchemes;
import com.infy.repository.ColorSchemesRepository;
import com.infy.service.dto.ColorSchemesDTO;
import com.infy.service.mapper.ColorSchemesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ColorSchemes}.
 */
@Service
@Transactional
public class ColorSchemesServiceImpl implements ColorSchemesService {

    private final Logger log = LoggerFactory.getLogger(ColorSchemesServiceImpl.class);

    private final ColorSchemesRepository colorSchemesRepository;

    private final ColorSchemesMapper colorSchemesMapper;

    public ColorSchemesServiceImpl(ColorSchemesRepository colorSchemesRepository, ColorSchemesMapper colorSchemesMapper) {
        this.colorSchemesRepository = colorSchemesRepository;
        this.colorSchemesMapper = colorSchemesMapper;
    }

    @Override
    public ColorSchemesDTO save(ColorSchemesDTO colorSchemesDTO) {
        log.debug("Request to save ColorSchemes : {}", colorSchemesDTO);
        ColorSchemes colorSchemes = colorSchemesMapper.toEntity(colorSchemesDTO);
        colorSchemes = colorSchemesRepository.save(colorSchemes);
        return colorSchemesMapper.toDto(colorSchemes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColorSchemesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ColorSchemes");
        return colorSchemesRepository.findAll(pageable)
            .map(colorSchemesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ColorSchemesDTO> findOne(Long id) {
        log.debug("Request to get ColorSchemes : {}", id);
        return colorSchemesRepository.findById(id)
            .map(colorSchemesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColorSchemes : {}", id);
        colorSchemesRepository.deleteById(id);
    }
}
