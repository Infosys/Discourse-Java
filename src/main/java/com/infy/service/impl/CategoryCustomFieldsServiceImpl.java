/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryCustomFieldsService;
import com.infy.domain.CategoryCustomFields;
import com.infy.repository.CategoryCustomFieldsRepository;
import com.infy.service.dto.CategoryCustomFieldsDTO;
import com.infy.service.mapper.CategoryCustomFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryCustomFields}.
 */
@Service
@Transactional
public class CategoryCustomFieldsServiceImpl implements CategoryCustomFieldsService {

    private final Logger log = LoggerFactory.getLogger(CategoryCustomFieldsServiceImpl.class);

    private final CategoryCustomFieldsRepository categoryCustomFieldsRepository;

    private final CategoryCustomFieldsMapper categoryCustomFieldsMapper;

    public CategoryCustomFieldsServiceImpl(CategoryCustomFieldsRepository categoryCustomFieldsRepository, CategoryCustomFieldsMapper categoryCustomFieldsMapper) {
        this.categoryCustomFieldsRepository = categoryCustomFieldsRepository;
        this.categoryCustomFieldsMapper = categoryCustomFieldsMapper;
    }

    @Override
    public CategoryCustomFieldsDTO save(CategoryCustomFieldsDTO categoryCustomFieldsDTO) {
        log.debug("Request to save CategoryCustomFields : {}", categoryCustomFieldsDTO);
        CategoryCustomFields categoryCustomFields = categoryCustomFieldsMapper.toEntity(categoryCustomFieldsDTO);
        categoryCustomFields = categoryCustomFieldsRepository.save(categoryCustomFields);
        return categoryCustomFieldsMapper.toDto(categoryCustomFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryCustomFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryCustomFields");
        return categoryCustomFieldsRepository.findAll(pageable)
            .map(categoryCustomFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryCustomFieldsDTO> findOne(Long id) {
        log.debug("Request to get CategoryCustomFields : {}", id);
        return categoryCustomFieldsRepository.findById(id)
            .map(categoryCustomFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryCustomFields : {}", id);
        categoryCustomFieldsRepository.deleteById(id);
    }
}
