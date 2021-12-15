/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupCustomFieldsService;
import com.infy.domain.GroupCustomFields;
import com.infy.repository.GroupCustomFieldsRepository;
import com.infy.service.dto.GroupCustomFieldsDTO;
import com.infy.service.mapper.GroupCustomFieldsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupCustomFields}.
 */
@Service
@Transactional
public class GroupCustomFieldsServiceImpl implements GroupCustomFieldsService {

    private final Logger log = LoggerFactory.getLogger(GroupCustomFieldsServiceImpl.class);

    private final GroupCustomFieldsRepository groupCustomFieldsRepository;

    private final GroupCustomFieldsMapper groupCustomFieldsMapper;

    public GroupCustomFieldsServiceImpl(GroupCustomFieldsRepository groupCustomFieldsRepository, GroupCustomFieldsMapper groupCustomFieldsMapper) {
        this.groupCustomFieldsRepository = groupCustomFieldsRepository;
        this.groupCustomFieldsMapper = groupCustomFieldsMapper;
    }

    @Override
    public GroupCustomFieldsDTO save(GroupCustomFieldsDTO groupCustomFieldsDTO) {
        log.debug("Request to save GroupCustomFields : {}", groupCustomFieldsDTO);
        GroupCustomFields groupCustomFields = groupCustomFieldsMapper.toEntity(groupCustomFieldsDTO);
        groupCustomFields = groupCustomFieldsRepository.save(groupCustomFields);
        return groupCustomFieldsMapper.toDto(groupCustomFields);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupCustomFieldsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupCustomFields");
        return groupCustomFieldsRepository.findAll(pageable)
            .map(groupCustomFieldsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupCustomFieldsDTO> findOne(Long id) {
        log.debug("Request to get GroupCustomFields : {}", id);
        return groupCustomFieldsRepository.findById(id)
            .map(groupCustomFieldsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupCustomFields : {}", id);
        groupCustomFieldsRepository.deleteById(id);
    }
}
