/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ThemeFieldsService;
import com.infy.domain.ThemeFields;
import com.infy.repository.ThemeFieldsRepository;
import com.infy.service.dto.ThemeFieldsDTO;
import com.infy.service.mapper.ThemeFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ThemeFields}.
 */
@Service
@Transactional
public class ThemeFieldsServiceImpl implements ThemeFieldsService {

    private final Logger log = LoggerFactory.getLogger(ThemeFieldsServiceImpl.class);

    private final ThemeFieldsRepository themeFieldsRepository;

    private final ThemeFieldsMapper themeFieldsMapper;

    public ThemeFieldsServiceImpl(ThemeFieldsRepository themeFieldsRepository, ThemeFieldsMapper themeFieldsMapper) {
        this.themeFieldsRepository = themeFieldsRepository;
        this.themeFieldsMapper = themeFieldsMapper;
    }

    @Override
    public ThemeFieldsDTO save(ThemeFieldsDTO themeFieldsDTO) {
        log.debug("Request to save ThemeFields : {}", themeFieldsDTO);
        ThemeFields themeFields = themeFieldsMapper.toEntity(themeFieldsDTO);
        themeFields = themeFieldsRepository.save(themeFields);
        return themeFieldsMapper.toDto(themeFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ThemeFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ThemeFields");
        return themeFieldsRepository.findAll(pageable)
            .map(themeFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeFieldsDTO> findOne(Long id) {
        log.debug("Request to get ThemeFields : {}", id);
        return themeFieldsRepository.findById(id)
            .map(themeFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ThemeFields : {}", id);
        themeFieldsRepository.deleteById(id);
    }
}
