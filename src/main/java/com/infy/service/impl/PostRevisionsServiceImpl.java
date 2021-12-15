/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostRevisionsService;
import com.infy.domain.PostRevisions;
import com.infy.repository.PostRevisionsRepository;
import com.infy.service.dto.PostRevisionsDTO;
import com.infy.service.mapper.PostRevisionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostRevisions}.
 */
@Service
@Transactional
public class PostRevisionsServiceImpl implements PostRevisionsService {

    private final Logger log = LoggerFactory.getLogger(PostRevisionsServiceImpl.class);

    private final PostRevisionsRepository postRevisionsRepository;

    private final PostRevisionsMapper postRevisionsMapper;

    public PostRevisionsServiceImpl(PostRevisionsRepository postRevisionsRepository, PostRevisionsMapper postRevisionsMapper) {
        this.postRevisionsRepository = postRevisionsRepository;
        this.postRevisionsMapper = postRevisionsMapper;
    }

    @Override
    public PostRevisionsDTO save(PostRevisionsDTO postRevisionsDTO) {
        log.debug("Request to save PostRevisions : {}", postRevisionsDTO);
        PostRevisions postRevisions = postRevisionsMapper.toEntity(postRevisionsDTO);
        postRevisions = postRevisionsRepository.save(postRevisions);
        return postRevisionsMapper.toDto(postRevisions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostRevisionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostRevisions");
        return postRevisionsRepository.findAll(pageable)
            .map(postRevisionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostRevisionsDTO> findOne(Long id) {
        log.debug("Request to get PostRevisions : {}", id);
        return postRevisionsRepository.findById(id)
            .map(postRevisionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostRevisions : {}", id);
        postRevisionsRepository.deleteById(id);
    }
}
