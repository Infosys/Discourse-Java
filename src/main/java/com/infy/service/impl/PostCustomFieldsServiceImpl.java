/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostCustomFieldsService;
import com.infy.domain.PostCustomFields;
import com.infy.repository.PostCustomFieldsRepository;
import com.infy.service.dto.PostCustomFieldsDTO;
import com.infy.service.mapper.PostCustomFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostCustomFields}.
 */
@Service
@Transactional
public class PostCustomFieldsServiceImpl implements PostCustomFieldsService {

    private final Logger log = LoggerFactory.getLogger(PostCustomFieldsServiceImpl.class);

    private final PostCustomFieldsRepository postCustomFieldsRepository;

    private final PostCustomFieldsMapper postCustomFieldsMapper;

    public PostCustomFieldsServiceImpl(PostCustomFieldsRepository postCustomFieldsRepository, PostCustomFieldsMapper postCustomFieldsMapper) {
        this.postCustomFieldsRepository = postCustomFieldsRepository;
        this.postCustomFieldsMapper = postCustomFieldsMapper;
    }

    @Override
    public PostCustomFieldsDTO save(PostCustomFieldsDTO postCustomFieldsDTO) {
        log.debug("Request to save PostCustomFields : {}", postCustomFieldsDTO);
        PostCustomFields postCustomFields = postCustomFieldsMapper.toEntity(postCustomFieldsDTO);
        postCustomFields = postCustomFieldsRepository.save(postCustomFields);
        return postCustomFieldsMapper.toDto(postCustomFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostCustomFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostCustomFields");
        return postCustomFieldsRepository.findAll(pageable)
            .map(postCustomFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostCustomFieldsDTO> findOne(Long id) {
        log.debug("Request to get PostCustomFields : {}", id);
        return postCustomFieldsRepository.findById(id)
            .map(postCustomFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostCustomFields : {}", id);
        postCustomFieldsRepository.deleteById(id);
    }
}
