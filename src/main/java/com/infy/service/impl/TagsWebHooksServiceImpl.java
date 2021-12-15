/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagsWebHooksService;
import com.infy.domain.TagsWebHooks;
import com.infy.repository.TagsWebHooksRepository;
import com.infy.service.dto.TagsWebHooksDTO;
import com.infy.service.mapper.TagsWebHooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagsWebHooks}.
 */
@Service
@Transactional
public class TagsWebHooksServiceImpl implements TagsWebHooksService {

    private final Logger log = LoggerFactory.getLogger(TagsWebHooksServiceImpl.class);

    private final TagsWebHooksRepository tagsWebHooksRepository;

    private final TagsWebHooksMapper tagsWebHooksMapper;

    public TagsWebHooksServiceImpl(TagsWebHooksRepository tagsWebHooksRepository, TagsWebHooksMapper tagsWebHooksMapper) {
        this.tagsWebHooksRepository = tagsWebHooksRepository;
        this.tagsWebHooksMapper = tagsWebHooksMapper;
    }

    @Override
    public TagsWebHooksDTO save(TagsWebHooksDTO tagsWebHooksDTO) {
        log.debug("Request to save TagsWebHooks : {}", tagsWebHooksDTO);
        TagsWebHooks tagsWebHooks = tagsWebHooksMapper.toEntity(tagsWebHooksDTO);
        tagsWebHooks = tagsWebHooksRepository.save(tagsWebHooks);
        return tagsWebHooksMapper.toDto(tagsWebHooks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagsWebHooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagsWebHooks");
        return tagsWebHooksRepository.findAll(pageable)
            .map(tagsWebHooksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagsWebHooksDTO> findOne(Long id) {
        log.debug("Request to get TagsWebHooks : {}", id);
        return tagsWebHooksRepository.findById(id)
            .map(tagsWebHooksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagsWebHooks : {}", id);
        tagsWebHooksRepository.deleteById(id);
    }
}
