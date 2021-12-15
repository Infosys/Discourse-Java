/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagGroupMembershipsService;
import com.infy.domain.TagGroupMemberships;
import com.infy.repository.TagGroupMembershipsRepository;
import com.infy.service.dto.TagGroupMembershipsDTO;
import com.infy.service.mapper.TagGroupMembershipsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagGroupMemberships}.
 */
@Service
@Transactional
public class TagGroupMembershipsServiceImpl implements TagGroupMembershipsService {

    private final Logger log = LoggerFactory.getLogger(TagGroupMembershipsServiceImpl.class);

    private final TagGroupMembershipsRepository tagGroupMembershipsRepository;

    private final TagGroupMembershipsMapper tagGroupMembershipsMapper;

    public TagGroupMembershipsServiceImpl(TagGroupMembershipsRepository tagGroupMembershipsRepository, TagGroupMembershipsMapper tagGroupMembershipsMapper) {
        this.tagGroupMembershipsRepository = tagGroupMembershipsRepository;
        this.tagGroupMembershipsMapper = tagGroupMembershipsMapper;
    }

    @Override
    public TagGroupMembershipsDTO save(TagGroupMembershipsDTO tagGroupMembershipsDTO) {
        log.debug("Request to save TagGroupMemberships : {}", tagGroupMembershipsDTO);
        TagGroupMemberships tagGroupMemberships = tagGroupMembershipsMapper.toEntity(tagGroupMembershipsDTO);
        tagGroupMemberships = tagGroupMembershipsRepository.save(tagGroupMemberships);
        return tagGroupMembershipsMapper.toDto(tagGroupMemberships);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagGroupMembershipsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagGroupMemberships");
        return tagGroupMembershipsRepository.findAll(pageable)
            .map(tagGroupMembershipsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagGroupMembershipsDTO> findOne(Long id) {
        log.debug("Request to get TagGroupMemberships : {}", id);
        return tagGroupMembershipsRepository.findById(id)
            .map(tagGroupMembershipsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagGroupMemberships : {}", id);
        tagGroupMembershipsRepository.deleteById(id);
    }
}
