/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostTimingsService;
import com.infy.domain.PostTimings;
import com.infy.repository.PostTimingsRepository;
import com.infy.service.dto.PostTimingsDTO;
import com.infy.service.mapper.PostTimingsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostTimings}.
 */
@Service
@Transactional
public class PostTimingsServiceImpl implements PostTimingsService {

    private final Logger log = LoggerFactory.getLogger(PostTimingsServiceImpl.class);

    private final PostTimingsRepository postTimingsRepository;

    private final PostTimingsMapper postTimingsMapper;

    public PostTimingsServiceImpl(PostTimingsRepository postTimingsRepository, PostTimingsMapper postTimingsMapper) {
        this.postTimingsRepository = postTimingsRepository;
        this.postTimingsMapper = postTimingsMapper;
    }

    @Override
    public PostTimingsDTO save(PostTimingsDTO postTimingsDTO) {
        log.debug("Request to save PostTimings : {}", postTimingsDTO);
        PostTimings postTimings = postTimingsMapper.toEntity(postTimingsDTO);
        postTimings = postTimingsRepository.save(postTimings);
        return postTimingsMapper.toDto(postTimings);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostTimingsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostTimings");
        return postTimingsRepository.findAll(pageable)
            .map(postTimingsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostTimingsDTO> findOne(Long id) {
        log.debug("Request to get PostTimings : {}", id);
        return postTimingsRepository.findById(id)
            .map(postTimingsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostTimings : {}", id);
        postTimingsRepository.deleteById(id);
    }
}
