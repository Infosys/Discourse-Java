/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GivenDailyLikesService;
import com.infy.domain.GivenDailyLikes;
import com.infy.repository.GivenDailyLikesRepository;
import com.infy.service.dto.GivenDailyLikesDTO;
import com.infy.service.mapper.GivenDailyLikesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GivenDailyLikes}.
 */
@Service
@Transactional
public class GivenDailyLikesServiceImpl implements GivenDailyLikesService {

    private final Logger log = LoggerFactory.getLogger(GivenDailyLikesServiceImpl.class);

    private final GivenDailyLikesRepository givenDailyLikesRepository;

    private final GivenDailyLikesMapper givenDailyLikesMapper;

    public GivenDailyLikesServiceImpl(GivenDailyLikesRepository givenDailyLikesRepository, GivenDailyLikesMapper givenDailyLikesMapper) {
        this.givenDailyLikesRepository = givenDailyLikesRepository;
        this.givenDailyLikesMapper = givenDailyLikesMapper;
    }

    @Override
    public GivenDailyLikesDTO save(GivenDailyLikesDTO givenDailyLikesDTO) {
        log.debug("Request to save GivenDailyLikes : {}", givenDailyLikesDTO);
        GivenDailyLikes givenDailyLikes = givenDailyLikesMapper.toEntity(givenDailyLikesDTO);
        givenDailyLikes = givenDailyLikesRepository.save(givenDailyLikes);
        return givenDailyLikesMapper.toDto(givenDailyLikes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GivenDailyLikesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GivenDailyLikes");
        return givenDailyLikesRepository.findAll(pageable)
            .map(givenDailyLikesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GivenDailyLikesDTO> findOne(Long id) {
        log.debug("Request to get GivenDailyLikes : {}", id);
        return givenDailyLikesRepository.findById(id)
            .map(givenDailyLikesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GivenDailyLikes : {}", id);
        givenDailyLikesRepository.deleteById(id);
    }
}
