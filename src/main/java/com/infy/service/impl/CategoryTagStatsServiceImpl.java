/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryTagStatsService;
import com.infy.domain.CategoryTagStats;
import com.infy.repository.CategoryTagStatsRepository;
import com.infy.service.dto.CategoryTagStatsDTO;
import com.infy.service.mapper.CategoryTagStatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryTagStats}.
 */
@Service
@Transactional
public class CategoryTagStatsServiceImpl implements CategoryTagStatsService {

    private final Logger log = LoggerFactory.getLogger(CategoryTagStatsServiceImpl.class);

    private final CategoryTagStatsRepository categoryTagStatsRepository;

    private final CategoryTagStatsMapper categoryTagStatsMapper;

    public CategoryTagStatsServiceImpl(CategoryTagStatsRepository categoryTagStatsRepository, CategoryTagStatsMapper categoryTagStatsMapper) {
        this.categoryTagStatsRepository = categoryTagStatsRepository;
        this.categoryTagStatsMapper = categoryTagStatsMapper;
    }

    @Override
    public CategoryTagStatsDTO save(CategoryTagStatsDTO categoryTagStatsDTO) {
        log.debug("Request to save CategoryTagStats : {}", categoryTagStatsDTO);
        CategoryTagStats categoryTagStats = categoryTagStatsMapper.toEntity(categoryTagStatsDTO);
        categoryTagStats = categoryTagStatsRepository.save(categoryTagStats);
        return categoryTagStatsMapper.toDto(categoryTagStats);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryTagStatsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryTagStats");
        return categoryTagStatsRepository.findAll(pageable)
            .map(categoryTagStatsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryTagStatsDTO> findOne(Long id) {
        log.debug("Request to get CategoryTagStats : {}", id);
        return categoryTagStatsRepository.findById(id)
            .map(categoryTagStatsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryTagStats : {}", id);
        categoryTagStatsRepository.deleteById(id);
    }
}
