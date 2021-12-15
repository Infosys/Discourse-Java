/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PublishedPagesService;
import com.infy.domain.PublishedPages;
import com.infy.repository.PublishedPagesRepository;
import com.infy.service.dto.PublishedPagesDTO;
import com.infy.service.mapper.PublishedPagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PublishedPages}.
 */
@Service
@Transactional
public class PublishedPagesServiceImpl implements PublishedPagesService {

    private final Logger log = LoggerFactory.getLogger(PublishedPagesServiceImpl.class);

    private final PublishedPagesRepository publishedPagesRepository;

    private final PublishedPagesMapper publishedPagesMapper;

    public PublishedPagesServiceImpl(PublishedPagesRepository publishedPagesRepository, PublishedPagesMapper publishedPagesMapper) {
        this.publishedPagesRepository = publishedPagesRepository;
        this.publishedPagesMapper = publishedPagesMapper;
    }

    @Override
    public PublishedPagesDTO save(PublishedPagesDTO publishedPagesDTO) {
        log.debug("Request to save PublishedPages : {}", publishedPagesDTO);
        PublishedPages publishedPages = publishedPagesMapper.toEntity(publishedPagesDTO);
        publishedPages = publishedPagesRepository.save(publishedPages);
        return publishedPagesMapper.toDto(publishedPages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublishedPagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PublishedPages");
        return publishedPagesRepository.findAll(pageable)
            .map(publishedPagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PublishedPagesDTO> findOne(Long id) {
        log.debug("Request to get PublishedPages : {}", id);
        return publishedPagesRepository.findById(id)
            .map(publishedPagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublishedPages : {}", id);
        publishedPagesRepository.deleteById(id);
    }
}
