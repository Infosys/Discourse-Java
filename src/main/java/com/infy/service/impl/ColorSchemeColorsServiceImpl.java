/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ColorSchemeColorsService;
import com.infy.domain.ColorSchemeColors;
import com.infy.repository.ColorSchemeColorsRepository;
import com.infy.service.dto.ColorSchemeColorsDTO;
import com.infy.service.mapper.ColorSchemeColorsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ColorSchemeColors}.
 */
@Service
@Transactional
public class ColorSchemeColorsServiceImpl implements ColorSchemeColorsService {

    private final Logger log = LoggerFactory.getLogger(ColorSchemeColorsServiceImpl.class);

    private final ColorSchemeColorsRepository colorSchemeColorsRepository;

    private final ColorSchemeColorsMapper colorSchemeColorsMapper;

    public ColorSchemeColorsServiceImpl(ColorSchemeColorsRepository colorSchemeColorsRepository, ColorSchemeColorsMapper colorSchemeColorsMapper) {
        this.colorSchemeColorsRepository = colorSchemeColorsRepository;
        this.colorSchemeColorsMapper = colorSchemeColorsMapper;
    }

    @Override
    public ColorSchemeColorsDTO save(ColorSchemeColorsDTO colorSchemeColorsDTO) {
        log.debug("Request to save ColorSchemeColors : {}", colorSchemeColorsDTO);
        ColorSchemeColors colorSchemeColors = colorSchemeColorsMapper.toEntity(colorSchemeColorsDTO);
        colorSchemeColors = colorSchemeColorsRepository.save(colorSchemeColors);
        return colorSchemeColorsMapper.toDto(colorSchemeColors);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColorSchemeColorsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ColorSchemeColors");
        return colorSchemeColorsRepository.findAll(pageable)
            .map(colorSchemeColorsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ColorSchemeColorsDTO> findOne(Long id) {
        log.debug("Request to get ColorSchemeColors : {}", id);
        return colorSchemeColorsRepository.findById(id)
            .map(colorSchemeColorsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ColorSchemeColors : {}", id);
        colorSchemeColorsRepository.deleteById(id);
    }
}
