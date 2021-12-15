/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoryUsersService;
import com.infy.domain.CategoryUsers;
import com.infy.repository.CategoryUsersRepository;
import com.infy.service.dto.CategoryUsersDTO;
import com.infy.service.mapper.CategoryUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoryUsers}.
 */
@Service
@Transactional
public class CategoryUsersServiceImpl implements CategoryUsersService {

    private final Logger log = LoggerFactory.getLogger(CategoryUsersServiceImpl.class);

    private final CategoryUsersRepository categoryUsersRepository;

    private final CategoryUsersMapper categoryUsersMapper;

    public CategoryUsersServiceImpl(CategoryUsersRepository categoryUsersRepository, CategoryUsersMapper categoryUsersMapper) {
        this.categoryUsersRepository = categoryUsersRepository;
        this.categoryUsersMapper = categoryUsersMapper;
    }

    @Override
    public CategoryUsersDTO save(CategoryUsersDTO categoryUsersDTO) {
        log.debug("Request to save CategoryUsers : {}", categoryUsersDTO);
        CategoryUsers categoryUsers = categoryUsersMapper.toEntity(categoryUsersDTO);
        categoryUsers = categoryUsersRepository.save(categoryUsers);
        return categoryUsersMapper.toDto(categoryUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoryUsers");
        return categoryUsersRepository.findAll(pageable)
            .map(categoryUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoryUsersDTO> findOne(Long id) {
        log.debug("Request to get CategoryUsers : {}", id);
        return categoryUsersRepository.findById(id)
            .map(categoryUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoryUsers : {}", id);
        categoryUsersRepository.deleteById(id);
    }
}
