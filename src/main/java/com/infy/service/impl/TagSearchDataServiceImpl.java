/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagSearchDataService;
import com.infy.domain.TagSearchData;
import com.infy.repository.TagSearchDataRepository;
import com.infy.service.dto.TagSearchDataDTO;
import com.infy.service.mapper.TagSearchDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagSearchData}.
 */
@Service
@Transactional
public class TagSearchDataServiceImpl implements TagSearchDataService {

    private final Logger log = LoggerFactory.getLogger(TagSearchDataServiceImpl.class);

    private final TagSearchDataRepository tagSearchDataRepository;

    private final TagSearchDataMapper tagSearchDataMapper;

    public TagSearchDataServiceImpl(TagSearchDataRepository tagSearchDataRepository, TagSearchDataMapper tagSearchDataMapper) {
        this.tagSearchDataRepository = tagSearchDataRepository;
        this.tagSearchDataMapper = tagSearchDataMapper;
    }

    @Override
    public TagSearchDataDTO save(TagSearchDataDTO tagSearchDataDTO) {
        log.debug("Request to save TagSearchData : {}", tagSearchDataDTO);
        TagSearchData tagSearchData = tagSearchDataMapper.toEntity(tagSearchDataDTO);
        tagSearchData = tagSearchDataRepository.save(tagSearchData);
        return tagSearchDataMapper.toDto(tagSearchData);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagSearchDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagSearchData");
        return tagSearchDataRepository.findAll(pageable)
            .map(tagSearchDataMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagSearchDataDTO> findOne(Long id) {
        log.debug("Request to get TagSearchData : {}", id);
        return tagSearchDataRepository.findById(id)
            .map(tagSearchDataMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagSearchData : {}", id);
        tagSearchDataRepository.deleteById(id);
    }
}
