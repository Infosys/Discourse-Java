/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ReviewableClaimedTopicsService;
import com.infy.domain.ReviewableClaimedTopics;
import com.infy.repository.ReviewableClaimedTopicsRepository;
import com.infy.service.dto.ReviewableClaimedTopicsDTO;
import com.infy.service.mapper.ReviewableClaimedTopicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReviewableClaimedTopics}.
 */
@Service
@Transactional
public class ReviewableClaimedTopicsServiceImpl implements ReviewableClaimedTopicsService {

    private final Logger log = LoggerFactory.getLogger(ReviewableClaimedTopicsServiceImpl.class);

    private final ReviewableClaimedTopicsRepository reviewableClaimedTopicsRepository;

    private final ReviewableClaimedTopicsMapper reviewableClaimedTopicsMapper;

    public ReviewableClaimedTopicsServiceImpl(ReviewableClaimedTopicsRepository reviewableClaimedTopicsRepository, ReviewableClaimedTopicsMapper reviewableClaimedTopicsMapper) {
        this.reviewableClaimedTopicsRepository = reviewableClaimedTopicsRepository;
        this.reviewableClaimedTopicsMapper = reviewableClaimedTopicsMapper;
    }

    @Override
    public ReviewableClaimedTopicsDTO save(ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO) {
        log.debug("Request to save ReviewableClaimedTopics : {}", reviewableClaimedTopicsDTO);
        ReviewableClaimedTopics reviewableClaimedTopics = reviewableClaimedTopicsMapper.toEntity(reviewableClaimedTopicsDTO);
        reviewableClaimedTopics = reviewableClaimedTopicsRepository.save(reviewableClaimedTopics);
        return reviewableClaimedTopicsMapper.toDto(reviewableClaimedTopics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewableClaimedTopicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReviewableClaimedTopics");
        return reviewableClaimedTopicsRepository.findAll(pageable)
            .map(reviewableClaimedTopicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewableClaimedTopicsDTO> findOne(Long id) {
        log.debug("Request to get ReviewableClaimedTopics : {}", id);
        return reviewableClaimedTopicsRepository.findById(id)
            .map(reviewableClaimedTopicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReviewableClaimedTopics : {}", id);
        reviewableClaimedTopicsRepository.deleteById(id);
    }
}
