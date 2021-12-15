/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagsService;
import com.infy.domain.Tags;
import com.infy.repository.TagsRepository;
import com.infy.service.dto.TagsDTO;
import com.infy.service.mapper.TagsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tags}.
 */
@Service
@Transactional
public class TagsServiceImpl implements TagsService {

    private final Logger log = LoggerFactory.getLogger(TagsServiceImpl.class);

    private final TagsRepository tagsRepository;

    private final TagsMapper tagsMapper;

    public TagsServiceImpl(TagsRepository tagsRepository, TagsMapper tagsMapper) {
        this.tagsRepository = tagsRepository;
        this.tagsMapper = tagsMapper;
    }

    @Override
    public TagsDTO save(TagsDTO tagsDTO) {
        log.debug("Request to save Tags : {}", tagsDTO);
        Tags tags = tagsMapper.toEntity(tagsDTO);
        tags = tagsRepository.save(tags);
        return tagsMapper.toDto(tags);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tags");
        return tagsRepository.findAll(pageable)
            .map(tagsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagsDTO> findOne(Long id) {
        log.debug("Request to get Tags : {}", id);
        return tagsRepository.findById(id)
            .map(tagsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tags : {}", id);
        tagsRepository.deleteById(id);
    }
}
