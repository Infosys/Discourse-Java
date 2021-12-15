/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ChildThemesService;
import com.infy.domain.ChildThemes;
import com.infy.repository.ChildThemesRepository;
import com.infy.service.dto.ChildThemesDTO;
import com.infy.service.mapper.ChildThemesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ChildThemes}.
 */
@Service
@Transactional
public class ChildThemesServiceImpl implements ChildThemesService {

    private final Logger log = LoggerFactory.getLogger(ChildThemesServiceImpl.class);

    private final ChildThemesRepository childThemesRepository;

    private final ChildThemesMapper childThemesMapper;

    public ChildThemesServiceImpl(ChildThemesRepository childThemesRepository, ChildThemesMapper childThemesMapper) {
        this.childThemesRepository = childThemesRepository;
        this.childThemesMapper = childThemesMapper;
    }

    @Override
    public ChildThemesDTO save(ChildThemesDTO childThemesDTO) {
        log.debug("Request to save ChildThemes : {}", childThemesDTO);
        ChildThemes childThemes = childThemesMapper.toEntity(childThemesDTO);
        childThemes = childThemesRepository.save(childThemes);
        return childThemesMapper.toDto(childThemes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChildThemesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChildThemes");
        return childThemesRepository.findAll(pageable)
            .map(childThemesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ChildThemesDTO> findOne(Long id) {
        log.debug("Request to get ChildThemes : {}", id);
        return childThemesRepository.findById(id)
            .map(childThemesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ChildThemes : {}", id);
        childThemesRepository.deleteById(id);
    }
}
