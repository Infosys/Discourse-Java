/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostSearchDataService;
import com.infy.domain.PostSearchData;
import com.infy.repository.PostSearchDataRepository;
import com.infy.service.dto.PostSearchDataDTO;
import com.infy.service.mapper.PostSearchDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostSearchData}.
 */
@Service
@Transactional
public class PostSearchDataServiceImpl implements PostSearchDataService {

    private final Logger log = LoggerFactory.getLogger(PostSearchDataServiceImpl.class);

    private final PostSearchDataRepository postSearchDataRepository;

    private final PostSearchDataMapper postSearchDataMapper;

    public PostSearchDataServiceImpl(PostSearchDataRepository postSearchDataRepository, PostSearchDataMapper postSearchDataMapper) {
        this.postSearchDataRepository = postSearchDataRepository;
        this.postSearchDataMapper = postSearchDataMapper;
    }

    @Override
    public PostSearchDataDTO save(PostSearchDataDTO postSearchDataDTO) {
        log.debug("Request to save PostSearchData : {}", postSearchDataDTO);
        PostSearchData postSearchData = postSearchDataMapper.toEntity(postSearchDataDTO);
        postSearchData = postSearchDataRepository.save(postSearchData);
        return postSearchDataMapper.toDto(postSearchData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostSearchDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostSearchData");
        return postSearchDataRepository.findAll(pageable)
            .map(postSearchDataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostSearchDataDTO> findOne(Long id) {
        log.debug("Request to get PostSearchData : {}", id);
        return postSearchDataRepository.findById(id)
            .map(postSearchDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostSearchData : {}", id);
        postSearchDataRepository.deleteById(id);
    }
}
