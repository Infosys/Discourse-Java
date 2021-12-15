/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.JavascriptCachesService;
import com.infy.domain.JavascriptCaches;
import com.infy.repository.JavascriptCachesRepository;
import com.infy.service.dto.JavascriptCachesDTO;
import com.infy.service.mapper.JavascriptCachesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link JavascriptCaches}.
 */
@Service
@Transactional
public class JavascriptCachesServiceImpl implements JavascriptCachesService {

    private final Logger log = LoggerFactory.getLogger(JavascriptCachesServiceImpl.class);

    private final JavascriptCachesRepository javascriptCachesRepository;

    private final JavascriptCachesMapper javascriptCachesMapper;

    public JavascriptCachesServiceImpl(JavascriptCachesRepository javascriptCachesRepository, JavascriptCachesMapper javascriptCachesMapper) {
        this.javascriptCachesRepository = javascriptCachesRepository;
        this.javascriptCachesMapper = javascriptCachesMapper;
    }

    @Override
    public JavascriptCachesDTO save(JavascriptCachesDTO javascriptCachesDTO) {
        log.debug("Request to save JavascriptCaches : {}", javascriptCachesDTO);
        JavascriptCaches javascriptCaches = javascriptCachesMapper.toEntity(javascriptCachesDTO);
        javascriptCaches = javascriptCachesRepository.save(javascriptCaches);
        return javascriptCachesMapper.toDto(javascriptCaches);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JavascriptCachesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JavascriptCaches");
        return javascriptCachesRepository.findAll(pageable)
            .map(javascriptCachesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<JavascriptCachesDTO> findOne(Long id) {
        log.debug("Request to get JavascriptCaches : {}", id);
        return javascriptCachesRepository.findById(id)
            .map(javascriptCachesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JavascriptCaches : {}", id);
        javascriptCachesRepository.deleteById(id);
    }
}
