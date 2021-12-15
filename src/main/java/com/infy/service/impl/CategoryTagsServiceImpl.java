/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryTagsService;
import com.infy.domain.CategoryTags;
import com.infy.repository.CategoryTagsRepository;
import com.infy.service.dto.CategoryTagsDTO;
import com.infy.service.mapper.CategoryTagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryTags}.
 */
@Service
@Transactional
public class CategoryTagsServiceImpl implements CategoryTagsService {

    private final Logger log = LoggerFactory.getLogger(CategoryTagsServiceImpl.class);

    private final CategoryTagsRepository categoryTagsRepository;

    private final CategoryTagsMapper categoryTagsMapper;

    public CategoryTagsServiceImpl(CategoryTagsRepository categoryTagsRepository, CategoryTagsMapper categoryTagsMapper) {
        this.categoryTagsRepository = categoryTagsRepository;
        this.categoryTagsMapper = categoryTagsMapper;
    }

    @Override
    public CategoryTagsDTO save(CategoryTagsDTO categoryTagsDTO) {
        log.debug("Request to save CategoryTags : {}", categoryTagsDTO);
        CategoryTags categoryTags = categoryTagsMapper.toEntity(categoryTagsDTO);
        categoryTags = categoryTagsRepository.save(categoryTags);
        return categoryTagsMapper.toDto(categoryTags);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryTagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryTags");
        return categoryTagsRepository.findAll(pageable)
            .map(categoryTagsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryTagsDTO> findOne(Long id) {
        log.debug("Request to get CategoryTags : {}", id);
        return categoryTagsRepository.findById(id)
            .map(categoryTagsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryTags : {}", id);
        categoryTagsRepository.deleteById(id);
    }
}
