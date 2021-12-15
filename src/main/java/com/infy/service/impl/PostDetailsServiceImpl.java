/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostDetailsService;
import com.infy.domain.PostDetails;
import com.infy.repository.PostDetailsRepository;
import com.infy.service.dto.PostDetailsDTO;
import com.infy.service.mapper.PostDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostDetails}.
 */
@Service
@Transactional
public class PostDetailsServiceImpl implements PostDetailsService {

    private final Logger log = LoggerFactory.getLogger(PostDetailsServiceImpl.class);

    private final PostDetailsRepository postDetailsRepository;

    private final PostDetailsMapper postDetailsMapper;

    public PostDetailsServiceImpl(PostDetailsRepository postDetailsRepository, PostDetailsMapper postDetailsMapper) {
        this.postDetailsRepository = postDetailsRepository;
        this.postDetailsMapper = postDetailsMapper;
    }

    @Override
    public PostDetailsDTO save(PostDetailsDTO postDetailsDTO) {
        log.debug("Request to save PostDetails : {}", postDetailsDTO);
        PostDetails postDetails = postDetailsMapper.toEntity(postDetailsDTO);
        postDetails = postDetailsRepository.save(postDetails);
        return postDetailsMapper.toDto(postDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostDetails");
        return postDetailsRepository.findAll(pageable)
            .map(postDetailsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostDetailsDTO> findOne(Long id) {
        log.debug("Request to get PostDetails : {}", id);
        return postDetailsRepository.findById(id)
            .map(postDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostDetails : {}", id);
        postDetailsRepository.deleteById(id);
    }
}
