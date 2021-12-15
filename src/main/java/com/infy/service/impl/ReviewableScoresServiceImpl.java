/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ReviewableScoresService;
import com.infy.domain.ReviewableScores;
import com.infy.repository.ReviewableScoresRepository;
import com.infy.service.dto.ReviewableScoresDTO;
import com.infy.service.mapper.ReviewableScoresMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReviewableScores}.
 */
@Service
@Transactional
public class ReviewableScoresServiceImpl implements ReviewableScoresService {

    private final Logger log = LoggerFactory.getLogger(ReviewableScoresServiceImpl.class);

    private final ReviewableScoresRepository reviewableScoresRepository;

    private final ReviewableScoresMapper reviewableScoresMapper;

    public ReviewableScoresServiceImpl(ReviewableScoresRepository reviewableScoresRepository, ReviewableScoresMapper reviewableScoresMapper) {
        this.reviewableScoresRepository = reviewableScoresRepository;
        this.reviewableScoresMapper = reviewableScoresMapper;
    }

    @Override
    public ReviewableScoresDTO save(ReviewableScoresDTO reviewableScoresDTO) {
        log.debug("Request to save ReviewableScores : {}", reviewableScoresDTO);
        ReviewableScores reviewableScores = reviewableScoresMapper.toEntity(reviewableScoresDTO);
        reviewableScores = reviewableScoresRepository.save(reviewableScores);
        return reviewableScoresMapper.toDto(reviewableScores);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewableScoresDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReviewableScores");
        return reviewableScoresRepository.findAll(pageable)
            .map(reviewableScoresMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewableScoresDTO> findOne(Long id) {
        log.debug("Request to get ReviewableScores : {}", id);
        return reviewableScoresRepository.findById(id)
            .map(reviewableScoresMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReviewableScores : {}", id);
        reviewableScoresRepository.deleteById(id);
    }
}
