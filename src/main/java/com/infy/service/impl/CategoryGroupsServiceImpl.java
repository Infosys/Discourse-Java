/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryGroupsService;
import com.infy.domain.CategoryGroups;
import com.infy.repository.CategoryGroupsRepository;
import com.infy.service.dto.CategoryGroupsDTO;
import com.infy.service.mapper.CategoryGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryGroups}.
 */
@Service
@Transactional
public class CategoryGroupsServiceImpl implements CategoryGroupsService {

    private final Logger log = LoggerFactory.getLogger(CategoryGroupsServiceImpl.class);

    private final CategoryGroupsRepository categoryGroupsRepository;

    private final CategoryGroupsMapper categoryGroupsMapper;

    public CategoryGroupsServiceImpl(CategoryGroupsRepository categoryGroupsRepository, CategoryGroupsMapper categoryGroupsMapper) {
        this.categoryGroupsRepository = categoryGroupsRepository;
        this.categoryGroupsMapper = categoryGroupsMapper;
    }

    @Override
    public CategoryGroupsDTO save(CategoryGroupsDTO categoryGroupsDTO) {
        log.debug("Request to save CategoryGroups : {}", categoryGroupsDTO);
        CategoryGroups categoryGroups = categoryGroupsMapper.toEntity(categoryGroupsDTO);
        categoryGroups = categoryGroupsRepository.save(categoryGroups);
        return categoryGroupsMapper.toDto(categoryGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryGroups");
        return categoryGroupsRepository.findAll(pageable)
            .map(categoryGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryGroupsDTO> findOne(Long id) {
        log.debug("Request to get CategoryGroups : {}", id);
        return categoryGroupsRepository.findById(id)
            .map(categoryGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryGroups : {}", id);
        categoryGroupsRepository.deleteById(id);
    }
}
