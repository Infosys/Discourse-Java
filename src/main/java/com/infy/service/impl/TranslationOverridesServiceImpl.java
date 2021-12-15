/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TranslationOverridesService;
import com.infy.domain.TranslationOverrides;
import com.infy.repository.TranslationOverridesRepository;
import com.infy.service.dto.TranslationOverridesDTO;
import com.infy.service.mapper.TranslationOverridesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TranslationOverrides}.
 */
@Service
@Transactional
public class TranslationOverridesServiceImpl implements TranslationOverridesService {

    private final Logger log = LoggerFactory.getLogger(TranslationOverridesServiceImpl.class);

    private final TranslationOverridesRepository translationOverridesRepository;

    private final TranslationOverridesMapper translationOverridesMapper;

    public TranslationOverridesServiceImpl(TranslationOverridesRepository translationOverridesRepository, TranslationOverridesMapper translationOverridesMapper) {
        this.translationOverridesRepository = translationOverridesRepository;
        this.translationOverridesMapper = translationOverridesMapper;
    }

    @Override
    public TranslationOverridesDTO save(TranslationOverridesDTO translationOverridesDTO) {
        log.debug("Request to save TranslationOverrides : {}", translationOverridesDTO);
        TranslationOverrides translationOverrides = translationOverridesMapper.toEntity(translationOverridesDTO);
        translationOverrides = translationOverridesRepository.save(translationOverrides);
        return translationOverridesMapper.toDto(translationOverrides);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TranslationOverridesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TranslationOverrides");
        return translationOverridesRepository.findAll(pageable)
            .map(translationOverridesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TranslationOverridesDTO> findOne(Long id) {
        log.debug("Request to get TranslationOverrides : {}", id);
        return translationOverridesRepository.findById(id)
            .map(translationOverridesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TranslationOverrides : {}", id);
        translationOverridesRepository.deleteById(id);
    }
}
