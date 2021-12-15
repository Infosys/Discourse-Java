/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BadgesService;
import com.infy.domain.Badges;
import com.infy.repository.BadgesRepository;
import com.infy.service.dto.BadgesDTO;
import com.infy.service.mapper.BadgesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Badges}.
 */
@Service
@Transactional
public class BadgesServiceImpl implements BadgesService {

    private final Logger log = LoggerFactory.getLogger(BadgesServiceImpl.class);

    private final BadgesRepository badgesRepository;

    private final BadgesMapper badgesMapper;

    public BadgesServiceImpl(BadgesRepository badgesRepository, BadgesMapper badgesMapper) {
        this.badgesRepository = badgesRepository;
        this.badgesMapper = badgesMapper;
    }

    @Override
    public BadgesDTO save(BadgesDTO badgesDTO) {
        log.debug("Request to save Badges : {}", badgesDTO);
        Badges badges = badgesMapper.toEntity(badgesDTO);
        badges = badgesRepository.save(badges);
        return badgesMapper.toDto(badges);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Badges");
        return badgesRepository.findAll(pageable)
            .map(badgesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BadgesDTO> findOne(Long id) {
        log.debug("Request to get Badges : {}", id);
        return badgesRepository.findById(id)
            .map(badgesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badges : {}", id);
        badgesRepository.deleteById(id);
    }
}
