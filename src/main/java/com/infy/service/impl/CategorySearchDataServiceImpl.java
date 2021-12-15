/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategorySearchDataService;
import com.infy.domain.CategorySearchData;
import com.infy.repository.CategorySearchDataRepository;
import com.infy.service.dto.CategorySearchDataDTO;
import com.infy.service.mapper.CategorySearchDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategorySearchData}.
 */
@Service
@Transactional
public class CategorySearchDataServiceImpl implements CategorySearchDataService {

    private final Logger log = LoggerFactory.getLogger(CategorySearchDataServiceImpl.class);

    private final CategorySearchDataRepository categorySearchDataRepository;

    private final CategorySearchDataMapper categorySearchDataMapper;

    public CategorySearchDataServiceImpl(CategorySearchDataRepository categorySearchDataRepository, CategorySearchDataMapper categorySearchDataMapper) {
        this.categorySearchDataRepository = categorySearchDataRepository;
        this.categorySearchDataMapper = categorySearchDataMapper;
    }

    @Override
    public CategorySearchDataDTO save(CategorySearchDataDTO categorySearchDataDTO) {
        log.debug("Request to save CategorySearchData : {}", categorySearchDataDTO);
        CategorySearchData categorySearchData = categorySearchDataMapper.toEntity(categorySearchDataDTO);
        categorySearchData = categorySearchDataRepository.save(categorySearchData);
        return categorySearchDataMapper.toDto(categorySearchData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategorySearchDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategorySearchData");
        return categorySearchDataRepository.findAll(pageable)
            .map(categorySearchDataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategorySearchDataDTO> findOne(Long id) {
        log.debug("Request to get CategorySearchData : {}", id);
        return categorySearchDataRepository.findById(id)
            .map(categorySearchDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategorySearchData : {}", id);
        categorySearchDataRepository.deleteById(id);
    }
}
