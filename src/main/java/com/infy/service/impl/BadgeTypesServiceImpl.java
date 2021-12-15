/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BadgeTypesService;
import com.infy.domain.BadgeTypes;
import com.infy.repository.BadgeTypesRepository;
import com.infy.service.dto.BadgeTypesDTO;
import com.infy.service.mapper.BadgeTypesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgeTypes}.
 */
@Service
@Transactional
public class BadgeTypesServiceImpl implements BadgeTypesService {

    private final Logger log = LoggerFactory.getLogger(BadgeTypesServiceImpl.class);

    private final BadgeTypesRepository badgeTypesRepository;

    private final BadgeTypesMapper badgeTypesMapper;

    public BadgeTypesServiceImpl(BadgeTypesRepository badgeTypesRepository, BadgeTypesMapper badgeTypesMapper) {
        this.badgeTypesRepository = badgeTypesRepository;
        this.badgeTypesMapper = badgeTypesMapper;
    }

    @Override
    public BadgeTypesDTO save(BadgeTypesDTO badgeTypesDTO) {
        log.debug("Request to save BadgeTypes : {}", badgeTypesDTO);
        BadgeTypes badgeTypes = badgeTypesMapper.toEntity(badgeTypesDTO);
        badgeTypes = badgeTypesRepository.save(badgeTypes);
        return badgeTypesMapper.toDto(badgeTypes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeTypesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BadgeTypes");
        return badgeTypesRepository.findAll(pageable)
            .map(badgeTypesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeTypesDTO> findOne(Long id) {
        log.debug("Request to get BadgeTypes : {}", id);
        return badgeTypesRepository.findById(id)
            .map(badgeTypesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeTypes : {}", id);
        badgeTypesRepository.deleteById(id);
    }
}
