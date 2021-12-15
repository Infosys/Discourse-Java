/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupRequestsService;
import com.infy.domain.GroupRequests;
import com.infy.repository.GroupRequestsRepository;
import com.infy.service.dto.GroupRequestsDTO;
import com.infy.service.mapper.GroupRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupRequests}.
 */
@Service
@Transactional
public class GroupRequestsServiceImpl implements GroupRequestsService {

    private final Logger log = LoggerFactory.getLogger(GroupRequestsServiceImpl.class);

    private final GroupRequestsRepository groupRequestsRepository;

    private final GroupRequestsMapper groupRequestsMapper;

    public GroupRequestsServiceImpl(GroupRequestsRepository groupRequestsRepository, GroupRequestsMapper groupRequestsMapper) {
        this.groupRequestsRepository = groupRequestsRepository;
        this.groupRequestsMapper = groupRequestsMapper;
    }

    @Override
    public GroupRequestsDTO save(GroupRequestsDTO groupRequestsDTO) {
        log.debug("Request to save GroupRequests : {}", groupRequestsDTO);
        GroupRequests groupRequests = groupRequestsMapper.toEntity(groupRequestsDTO);
        groupRequests = groupRequestsRepository.save(groupRequests);
        return groupRequestsMapper.toDto(groupRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupRequests");
        return groupRequestsRepository.findAll(pageable)
            .map(groupRequestsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupRequestsDTO> findOne(Long id) {
        log.debug("Request to get GroupRequests : {}", id);
        return groupRequestsRepository.findById(id)
            .map(groupRequestsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupRequests : {}", id);
        groupRequestsRepository.deleteById(id);
    }
}
