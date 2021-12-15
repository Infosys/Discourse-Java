/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ReviewablesService;
import com.infy.domain.Reviewables;
import com.infy.repository.ReviewablesRepository;
import com.infy.service.dto.ReviewablesDTO;
import com.infy.service.mapper.ReviewablesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Reviewables}.
 */
@Service
@Transactional
public class ReviewablesServiceImpl implements ReviewablesService {

    private final Logger log = LoggerFactory.getLogger(ReviewablesServiceImpl.class);

    private final ReviewablesRepository reviewablesRepository;

    private final ReviewablesMapper reviewablesMapper;

    public ReviewablesServiceImpl(ReviewablesRepository reviewablesRepository, ReviewablesMapper reviewablesMapper) {
        this.reviewablesRepository = reviewablesRepository;
        this.reviewablesMapper = reviewablesMapper;
    }

    @Override
    public ReviewablesDTO save(ReviewablesDTO reviewablesDTO) {
        log.debug("Request to save Reviewables : {}", reviewablesDTO);
        Reviewables reviewables = reviewablesMapper.toEntity(reviewablesDTO);
        reviewables = reviewablesRepository.save(reviewables);
        return reviewablesMapper.toDto(reviewables);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewablesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reviewables");
        return reviewablesRepository.findAll(pageable)
            .map(reviewablesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewablesDTO> findOne(Long id) {
        log.debug("Request to get Reviewables : {}", id);
        return reviewablesRepository.findById(id)
            .map(reviewablesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Reviewables : {}", id);
        reviewablesRepository.deleteById(id);
    }
}
