/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostReplyKeysService;
import com.infy.domain.PostReplyKeys;
import com.infy.repository.PostReplyKeysRepository;
import com.infy.service.dto.PostReplyKeysDTO;
import com.infy.service.mapper.PostReplyKeysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostReplyKeys}.
 */
@Service
@Transactional
public class PostReplyKeysServiceImpl implements PostReplyKeysService {

    private final Logger log = LoggerFactory.getLogger(PostReplyKeysServiceImpl.class);

    private final PostReplyKeysRepository postReplyKeysRepository;

    private final PostReplyKeysMapper postReplyKeysMapper;

    public PostReplyKeysServiceImpl(PostReplyKeysRepository postReplyKeysRepository, PostReplyKeysMapper postReplyKeysMapper) {
        this.postReplyKeysRepository = postReplyKeysRepository;
        this.postReplyKeysMapper = postReplyKeysMapper;
    }

    @Override
    public PostReplyKeysDTO save(PostReplyKeysDTO postReplyKeysDTO) {
        log.debug("Request to save PostReplyKeys : {}", postReplyKeysDTO);
        PostReplyKeys postReplyKeys = postReplyKeysMapper.toEntity(postReplyKeysDTO);
        postReplyKeys = postReplyKeysRepository.save(postReplyKeys);
        return postReplyKeysMapper.toDto(postReplyKeys);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostReplyKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostReplyKeys");
        return postReplyKeysRepository.findAll(pageable)
            .map(postReplyKeysMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostReplyKeysDTO> findOne(Long id) {
        log.debug("Request to get PostReplyKeys : {}", id);
        return postReplyKeysRepository.findById(id)
            .map(postReplyKeysMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostReplyKeys : {}", id);
        postReplyKeysRepository.deleteById(id);
    }
}
