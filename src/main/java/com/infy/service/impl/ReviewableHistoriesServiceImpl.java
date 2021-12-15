/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ReviewableHistoriesService;
import com.infy.domain.ReviewableHistories;
import com.infy.repository.ReviewableHistoriesRepository;
import com.infy.service.dto.ReviewableHistoriesDTO;
import com.infy.service.mapper.ReviewableHistoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ReviewableHistories}.
 */
@Service
@Transactional
public class ReviewableHistoriesServiceImpl implements ReviewableHistoriesService {

    private final Logger log = LoggerFactory.getLogger(ReviewableHistoriesServiceImpl.class);

    private final ReviewableHistoriesRepository reviewableHistoriesRepository;

    private final ReviewableHistoriesMapper reviewableHistoriesMapper;

    public ReviewableHistoriesServiceImpl(ReviewableHistoriesRepository reviewableHistoriesRepository, ReviewableHistoriesMapper reviewableHistoriesMapper) {
        this.reviewableHistoriesRepository = reviewableHistoriesRepository;
        this.reviewableHistoriesMapper = reviewableHistoriesMapper;
    }

    @Override
    public ReviewableHistoriesDTO save(ReviewableHistoriesDTO reviewableHistoriesDTO) {
        log.debug("Request to save ReviewableHistories : {}", reviewableHistoriesDTO);
        ReviewableHistories reviewableHistories = reviewableHistoriesMapper.toEntity(reviewableHistoriesDTO);
        reviewableHistories = reviewableHistoriesRepository.save(reviewableHistories);
        return reviewableHistoriesMapper.toDto(reviewableHistories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewableHistoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReviewableHistories");
        return reviewableHistoriesRepository.findAll(pageable)
            .map(reviewableHistoriesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewableHistoriesDTO> findOne(Long id) {
        log.debug("Request to get ReviewableHistories : {}", id);
        return reviewableHistoriesRepository.findById(id)
            .map(reviewableHistoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReviewableHistories : {}", id);
        reviewableHistoriesRepository.deleteById(id);
    }
}
