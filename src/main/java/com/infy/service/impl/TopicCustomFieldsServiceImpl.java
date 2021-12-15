/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TopicCustomFieldsService;
import com.infy.domain.TopicCustomFields;
import com.infy.repository.TopicCustomFieldsRepository;
import com.infy.service.dto.TopicCustomFieldsDTO;
import com.infy.service.mapper.TopicCustomFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TopicCustomFields}.
 */
@Service
@Transactional
public class TopicCustomFieldsServiceImpl implements TopicCustomFieldsService {

    private final Logger log = LoggerFactory.getLogger(TopicCustomFieldsServiceImpl.class);

    private final TopicCustomFieldsRepository topicCustomFieldsRepository;

    private final TopicCustomFieldsMapper topicCustomFieldsMapper;

    public TopicCustomFieldsServiceImpl(TopicCustomFieldsRepository topicCustomFieldsRepository, TopicCustomFieldsMapper topicCustomFieldsMapper) {
        this.topicCustomFieldsRepository = topicCustomFieldsRepository;
        this.topicCustomFieldsMapper = topicCustomFieldsMapper;
    }

    @Override
    public TopicCustomFieldsDTO save(TopicCustomFieldsDTO topicCustomFieldsDTO) {
        log.debug("Request to save TopicCustomFields : {}", topicCustomFieldsDTO);
        TopicCustomFields topicCustomFields = topicCustomFieldsMapper.toEntity(topicCustomFieldsDTO);
        topicCustomFields = topicCustomFieldsRepository.save(topicCustomFields);
        return topicCustomFieldsMapper.toDto(topicCustomFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TopicCustomFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TopicCustomFields");
        return topicCustomFieldsRepository.findAll(pageable)
            .map(topicCustomFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TopicCustomFieldsDTO> findOne(Long id) {
        log.debug("Request to get TopicCustomFields : {}", id);
        return topicCustomFieldsRepository.findById(id)
            .map(topicCustomFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TopicCustomFields : {}", id);
        topicCustomFieldsRepository.deleteById(id);
    }
}
