/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BadgeGroupingsService;
import com.infy.domain.BadgeGroupings;
import com.infy.repository.BadgeGroupingsRepository;
import com.infy.service.dto.BadgeGroupingsDTO;
import com.infy.service.mapper.BadgeGroupingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgeGroupings}.
 */
@Service
@Transactional
public class BadgeGroupingsServiceImpl implements BadgeGroupingsService {

    private final Logger log = LoggerFactory.getLogger(BadgeGroupingsServiceImpl.class);

    private final BadgeGroupingsRepository badgeGroupingsRepository;

    private final BadgeGroupingsMapper badgeGroupingsMapper;

    public BadgeGroupingsServiceImpl(BadgeGroupingsRepository badgeGroupingsRepository, BadgeGroupingsMapper badgeGroupingsMapper) {
        this.badgeGroupingsRepository = badgeGroupingsRepository;
        this.badgeGroupingsMapper = badgeGroupingsMapper;
    }

    @Override
    public BadgeGroupingsDTO save(BadgeGroupingsDTO badgeGroupingsDTO) {
        log.debug("Request to save BadgeGroupings : {}", badgeGroupingsDTO);
        BadgeGroupings badgeGroupings = badgeGroupingsMapper.toEntity(badgeGroupingsDTO);
        badgeGroupings = badgeGroupingsRepository.save(badgeGroupings);
        return badgeGroupingsMapper.toDto(badgeGroupings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgeGroupingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BadgeGroupings");
        return badgeGroupingsRepository.findAll(pageable)
            .map(badgeGroupingsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BadgeGroupingsDTO> findOne(Long id) {
        log.debug("Request to get BadgeGroupings : {}", id);
        return badgeGroupingsRepository.findById(id)
            .map(badgeGroupingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgeGroupings : {}", id);
        badgeGroupingsRepository.deleteById(id);
    }
}
