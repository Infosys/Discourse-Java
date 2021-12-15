/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagGroupsService;
import com.infy.domain.TagGroups;
import com.infy.repository.TagGroupsRepository;
import com.infy.service.dto.TagGroupsDTO;
import com.infy.service.mapper.TagGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagGroups}.
 */
@Service
@Transactional
public class TagGroupsServiceImpl implements TagGroupsService {

    private final Logger log = LoggerFactory.getLogger(TagGroupsServiceImpl.class);

    private final TagGroupsRepository tagGroupsRepository;

    private final TagGroupsMapper tagGroupsMapper;

    public TagGroupsServiceImpl(TagGroupsRepository tagGroupsRepository, TagGroupsMapper tagGroupsMapper) {
        this.tagGroupsRepository = tagGroupsRepository;
        this.tagGroupsMapper = tagGroupsMapper;
    }

    @Override
    public TagGroupsDTO save(TagGroupsDTO tagGroupsDTO) {
        log.debug("Request to save TagGroups : {}", tagGroupsDTO);
        TagGroups tagGroups = tagGroupsMapper.toEntity(tagGroupsDTO);
        tagGroups = tagGroupsRepository.save(tagGroups);
        return tagGroupsMapper.toDto(tagGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagGroups");
        return tagGroupsRepository.findAll(pageable)
            .map(tagGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagGroupsDTO> findOne(Long id) {
        log.debug("Request to get TagGroups : {}", id);
        return tagGroupsRepository.findById(id)
            .map(tagGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagGroups : {}", id);
        tagGroupsRepository.deleteById(id);
    }
}
