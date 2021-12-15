/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CustomEmojisService;
import com.infy.domain.CustomEmojis;
import com.infy.repository.CustomEmojisRepository;
import com.infy.service.dto.CustomEmojisDTO;
import com.infy.service.mapper.CustomEmojisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CustomEmojis}.
 */
@Service
@Transactional
public class CustomEmojisServiceImpl implements CustomEmojisService {

    private final Logger log = LoggerFactory.getLogger(CustomEmojisServiceImpl.class);

    private final CustomEmojisRepository customEmojisRepository;

    private final CustomEmojisMapper customEmojisMapper;

    public CustomEmojisServiceImpl(CustomEmojisRepository customEmojisRepository, CustomEmojisMapper customEmojisMapper) {
        this.customEmojisRepository = customEmojisRepository;
        this.customEmojisMapper = customEmojisMapper;
    }

    @Override
    public CustomEmojisDTO save(CustomEmojisDTO customEmojisDTO) {
        log.debug("Request to save CustomEmojis : {}", customEmojisDTO);
        CustomEmojis customEmojis = customEmojisMapper.toEntity(customEmojisDTO);
        customEmojis = customEmojisRepository.save(customEmojis);
        return customEmojisMapper.toDto(customEmojis);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomEmojisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomEmojis");
        return customEmojisRepository.findAll(pageable)
            .map(customEmojisMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CustomEmojisDTO> findOne(Long id) {
        log.debug("Request to get CustomEmojis : {}", id);
        return customEmojisRepository.findById(id)
            .map(customEmojisMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomEmojis : {}", id);
        customEmojisRepository.deleteById(id);
    }
}
