/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryFeaturedTopicsService;
import com.infy.domain.CategoryFeaturedTopics;
import com.infy.repository.CategoryFeaturedTopicsRepository;
import com.infy.service.dto.CategoryFeaturedTopicsDTO;
import com.infy.service.mapper.CategoryFeaturedTopicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryFeaturedTopics}.
 */
@Service
@Transactional
public class CategoryFeaturedTopicsServiceImpl implements CategoryFeaturedTopicsService {

    private final Logger log = LoggerFactory.getLogger(CategoryFeaturedTopicsServiceImpl.class);

    private final CategoryFeaturedTopicsRepository categoryFeaturedTopicsRepository;

    private final CategoryFeaturedTopicsMapper categoryFeaturedTopicsMapper;

    public CategoryFeaturedTopicsServiceImpl(CategoryFeaturedTopicsRepository categoryFeaturedTopicsRepository, CategoryFeaturedTopicsMapper categoryFeaturedTopicsMapper) {
        this.categoryFeaturedTopicsRepository = categoryFeaturedTopicsRepository;
        this.categoryFeaturedTopicsMapper = categoryFeaturedTopicsMapper;
    }

    @Override
    public CategoryFeaturedTopicsDTO save(CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO) {
        log.debug("Request to save CategoryFeaturedTopics : {}", categoryFeaturedTopicsDTO);
        CategoryFeaturedTopics categoryFeaturedTopics = categoryFeaturedTopicsMapper.toEntity(categoryFeaturedTopicsDTO);
        categoryFeaturedTopics = categoryFeaturedTopicsRepository.save(categoryFeaturedTopics);
        return categoryFeaturedTopicsMapper.toDto(categoryFeaturedTopics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryFeaturedTopicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryFeaturedTopics");
        return categoryFeaturedTopicsRepository.findAll(pageable)
            .map(categoryFeaturedTopicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryFeaturedTopicsDTO> findOne(Long id) {
        log.debug("Request to get CategoryFeaturedTopics : {}", id);
        return categoryFeaturedTopicsRepository.findById(id)
            .map(categoryFeaturedTopicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryFeaturedTopics : {}", id);
        categoryFeaturedTopicsRepository.deleteById(id);
    }
}
