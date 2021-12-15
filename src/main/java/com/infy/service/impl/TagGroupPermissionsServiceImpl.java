/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagGroupPermissionsService;
import com.infy.domain.TagGroupPermissions;
import com.infy.repository.TagGroupPermissionsRepository;
import com.infy.service.dto.TagGroupPermissionsDTO;
import com.infy.service.mapper.TagGroupPermissionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagGroupPermissions}.
 */
@Service
@Transactional
public class TagGroupPermissionsServiceImpl implements TagGroupPermissionsService {

    private final Logger log = LoggerFactory.getLogger(TagGroupPermissionsServiceImpl.class);

    private final TagGroupPermissionsRepository tagGroupPermissionsRepository;

    private final TagGroupPermissionsMapper tagGroupPermissionsMapper;

    public TagGroupPermissionsServiceImpl(TagGroupPermissionsRepository tagGroupPermissionsRepository, TagGroupPermissionsMapper tagGroupPermissionsMapper) {
        this.tagGroupPermissionsRepository = tagGroupPermissionsRepository;
        this.tagGroupPermissionsMapper = tagGroupPermissionsMapper;
    }

    @Override
    public TagGroupPermissionsDTO save(TagGroupPermissionsDTO tagGroupPermissionsDTO) {
        log.debug("Request to save TagGroupPermissions : {}", tagGroupPermissionsDTO);
        TagGroupPermissions tagGroupPermissions = tagGroupPermissionsMapper.toEntity(tagGroupPermissionsDTO);
        tagGroupPermissions = tagGroupPermissionsRepository.save(tagGroupPermissions);
        return tagGroupPermissionsMapper.toDto(tagGroupPermissions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagGroupPermissionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagGroupPermissions");
        return tagGroupPermissionsRepository.findAll(pageable)
            .map(tagGroupPermissionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagGroupPermissionsDTO> findOne(Long id) {
        log.debug("Request to get TagGroupPermissions : {}", id);
        return tagGroupPermissionsRepository.findById(id)
            .map(tagGroupPermissionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagGroupPermissions : {}", id);
        tagGroupPermissionsRepository.deleteById(id);
    }
}
