/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.CategoriesWebHooksService;
import com.infy.domain.CategoriesWebHooks;
import com.infy.repository.CategoriesWebHooksRepository;
import com.infy.service.dto.CategoriesWebHooksDTO;
import com.infy.service.mapper.CategoriesWebHooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CategoriesWebHooks}.
 */
@Service
@Transactional
public class CategoriesWebHooksServiceImpl implements CategoriesWebHooksService {

    private final Logger log = LoggerFactory.getLogger(CategoriesWebHooksServiceImpl.class);

    private final CategoriesWebHooksRepository categoriesWebHooksRepository;

    private final CategoriesWebHooksMapper categoriesWebHooksMapper;

    public CategoriesWebHooksServiceImpl(CategoriesWebHooksRepository categoriesWebHooksRepository, CategoriesWebHooksMapper categoriesWebHooksMapper) {
        this.categoriesWebHooksRepository = categoriesWebHooksRepository;
        this.categoriesWebHooksMapper = categoriesWebHooksMapper;
    }

    @Override
    public CategoriesWebHooksDTO save(CategoriesWebHooksDTO categoriesWebHooksDTO) {
        log.debug("Request to save CategoriesWebHooks : {}", categoriesWebHooksDTO);
        CategoriesWebHooks categoriesWebHooks = categoriesWebHooksMapper.toEntity(categoriesWebHooksDTO);
        categoriesWebHooks = categoriesWebHooksRepository.save(categoriesWebHooks);
        return categoriesWebHooksMapper.toDto(categoriesWebHooks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriesWebHooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CategoriesWebHooks");
        return categoriesWebHooksRepository.findAll(pageable)
            .map(categoriesWebHooksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriesWebHooksDTO> findOne(Long id) {
        log.debug("Request to get CategoriesWebHooks : {}", id);
        return categoriesWebHooksRepository.findById(id)
            .map(categoriesWebHooksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CategoriesWebHooks : {}", id);
        categoriesWebHooksRepository.deleteById(id);
    }
}
