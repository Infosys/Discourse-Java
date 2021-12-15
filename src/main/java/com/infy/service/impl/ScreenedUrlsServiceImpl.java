/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ScreenedUrlsService;
import com.infy.domain.ScreenedUrls;
import com.infy.repository.ScreenedUrlsRepository;
import com.infy.service.dto.ScreenedUrlsDTO;
import com.infy.service.mapper.ScreenedUrlsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScreenedUrls}.
 */
@Service
@Transactional
public class ScreenedUrlsServiceImpl implements ScreenedUrlsService {

    private final Logger log = LoggerFactory.getLogger(ScreenedUrlsServiceImpl.class);

    private final ScreenedUrlsRepository screenedUrlsRepository;

    private final ScreenedUrlsMapper screenedUrlsMapper;

    public ScreenedUrlsServiceImpl(ScreenedUrlsRepository screenedUrlsRepository, ScreenedUrlsMapper screenedUrlsMapper) {
        this.screenedUrlsRepository = screenedUrlsRepository;
        this.screenedUrlsMapper = screenedUrlsMapper;
    }

    @Override
    public ScreenedUrlsDTO save(ScreenedUrlsDTO screenedUrlsDTO) {
        log.debug("Request to save ScreenedUrls : {}", screenedUrlsDTO);
        ScreenedUrls screenedUrls = screenedUrlsMapper.toEntity(screenedUrlsDTO);
        screenedUrls = screenedUrlsRepository.save(screenedUrls);
        return screenedUrlsMapper.toDto(screenedUrls);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScreenedUrlsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScreenedUrls");
        return screenedUrlsRepository.findAll(pageable)
            .map(screenedUrlsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ScreenedUrlsDTO> findOne(Long id) {
        log.debug("Request to get ScreenedUrls : {}", id);
        return screenedUrlsRepository.findById(id)
            .map(screenedUrlsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScreenedUrls : {}", id);
        screenedUrlsRepository.deleteById(id);
    }
}
