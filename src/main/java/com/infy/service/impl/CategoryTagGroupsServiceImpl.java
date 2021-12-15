/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryTagGroupsService;
import com.infy.domain.CategoryTagGroups;
import com.infy.repository.CategoryTagGroupsRepository;
import com.infy.service.dto.CategoryTagGroupsDTO;
import com.infy.service.mapper.CategoryTagGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryTagGroups}.
 */
@Service
@Transactional
public class CategoryTagGroupsServiceImpl implements CategoryTagGroupsService {

    private final Logger log = LoggerFactory.getLogger(CategoryTagGroupsServiceImpl.class);

    private final CategoryTagGroupsRepository categoryTagGroupsRepository;

    private final CategoryTagGroupsMapper categoryTagGroupsMapper;

    public CategoryTagGroupsServiceImpl(CategoryTagGroupsRepository categoryTagGroupsRepository, CategoryTagGroupsMapper categoryTagGroupsMapper) {
        this.categoryTagGroupsRepository = categoryTagGroupsRepository;
        this.categoryTagGroupsMapper = categoryTagGroupsMapper;
    }

    @Override
    public CategoryTagGroupsDTO save(CategoryTagGroupsDTO categoryTagGroupsDTO) {
        log.debug("Request to save CategoryTagGroups : {}", categoryTagGroupsDTO);
        CategoryTagGroups categoryTagGroups = categoryTagGroupsMapper.toEntity(categoryTagGroupsDTO);
        categoryTagGroups = categoryTagGroupsRepository.save(categoryTagGroups);
        return categoryTagGroupsMapper.toDto(categoryTagGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryTagGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryTagGroups");
        return categoryTagGroupsRepository.findAll(pageable)
            .map(categoryTagGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryTagGroupsDTO> findOne(Long id) {
        log.debug("Request to get CategoryTagGroups : {}", id);
        return categoryTagGroupsRepository.findById(id)
            .map(categoryTagGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryTagGroups : {}", id);
        categoryTagGroupsRepository.deleteById(id);
    }
}
