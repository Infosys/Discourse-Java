/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.QuotedPostsService;
import com.infy.domain.QuotedPosts;
import com.infy.repository.QuotedPostsRepository;
import com.infy.service.dto.QuotedPostsDTO;
import com.infy.service.mapper.QuotedPostsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link QuotedPosts}.
 */
@Service
@Transactional
public class QuotedPostsServiceImpl implements QuotedPostsService {

    private final Logger log = LoggerFactory.getLogger(QuotedPostsServiceImpl.class);

    private final QuotedPostsRepository quotedPostsRepository;

    private final QuotedPostsMapper quotedPostsMapper;

    public QuotedPostsServiceImpl(QuotedPostsRepository quotedPostsRepository, QuotedPostsMapper quotedPostsMapper) {
        this.quotedPostsRepository = quotedPostsRepository;
        this.quotedPostsMapper = quotedPostsMapper;
    }

    @Override
    public QuotedPostsDTO save(QuotedPostsDTO quotedPostsDTO) {
        log.debug("Request to save QuotedPosts : {}", quotedPostsDTO);
        QuotedPosts quotedPosts = quotedPostsMapper.toEntity(quotedPostsDTO);
        quotedPosts = quotedPostsRepository.save(quotedPosts);
        return quotedPostsMapper.toDto(quotedPosts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<QuotedPostsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all QuotedPosts");
        return quotedPostsRepository.findAll(pageable)
            .map(quotedPostsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuotedPostsDTO> findOne(Long id) {
        log.debug("Request to get QuotedPosts : {}", id);
        return quotedPostsRepository.findById(id)
            .map(quotedPostsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuotedPosts : {}", id);
        quotedPostsRepository.deleteById(id);
    }
}
