/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ThemeModifierSetsService;
import com.infy.domain.ThemeModifierSets;
import com.infy.repository.ThemeModifierSetsRepository;
import com.infy.service.dto.ThemeModifierSetsDTO;
import com.infy.service.mapper.ThemeModifierSetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThemeModifierSets}.
 */
@Service
@Transactional
public class ThemeModifierSetsServiceImpl implements ThemeModifierSetsService {

    private final Logger log = LoggerFactory.getLogger(ThemeModifierSetsServiceImpl.class);

    private final ThemeModifierSetsRepository themeModifierSetsRepository;

    private final ThemeModifierSetsMapper themeModifierSetsMapper;

    public ThemeModifierSetsServiceImpl(ThemeModifierSetsRepository themeModifierSetsRepository, ThemeModifierSetsMapper themeModifierSetsMapper) {
        this.themeModifierSetsRepository = themeModifierSetsRepository;
        this.themeModifierSetsMapper = themeModifierSetsMapper;
    }

    @Override
    public ThemeModifierSetsDTO save(ThemeModifierSetsDTO themeModifierSetsDTO) {
        log.debug("Request to save ThemeModifierSets : {}", themeModifierSetsDTO);
        ThemeModifierSets themeModifierSets = themeModifierSetsMapper.toEntity(themeModifierSetsDTO);
        themeModifierSets = themeModifierSetsRepository.save(themeModifierSets);
        return themeModifierSetsMapper.toDto(themeModifierSets);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeModifierSetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeModifierSets");
        return themeModifierSetsRepository.findAll(pageable)
            .map(themeModifierSetsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeModifierSetsDTO> findOne(Long id) {
        log.debug("Request to get ThemeModifierSets : {}", id);
        return themeModifierSetsRepository.findById(id)
            .map(themeModifierSetsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThemeModifierSets : {}", id);
        themeModifierSetsRepository.deleteById(id);
    }
}
