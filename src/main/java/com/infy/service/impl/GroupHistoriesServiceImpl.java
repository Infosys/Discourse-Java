/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupHistoriesService;
import com.infy.domain.GroupHistories;
import com.infy.repository.GroupHistoriesRepository;
import com.infy.service.dto.GroupHistoriesDTO;
import com.infy.service.mapper.GroupHistoriesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupHistories}.
 */
@Service
@Transactional
public class GroupHistoriesServiceImpl implements GroupHistoriesService {

    private final Logger log = LoggerFactory.getLogger(GroupHistoriesServiceImpl.class);

    private final GroupHistoriesRepository groupHistoriesRepository;

    private final GroupHistoriesMapper groupHistoriesMapper;

    public GroupHistoriesServiceImpl(GroupHistoriesRepository groupHistoriesRepository, GroupHistoriesMapper groupHistoriesMapper) {
        this.groupHistoriesRepository = groupHistoriesRepository;
        this.groupHistoriesMapper = groupHistoriesMapper;
    }

    @Override
    public GroupHistoriesDTO save(GroupHistoriesDTO groupHistoriesDTO) {
        log.debug("Request to save GroupHistories : {}", groupHistoriesDTO);
        GroupHistories groupHistories = groupHistoriesMapper.toEntity(groupHistoriesDTO);
        groupHistories = groupHistoriesRepository.save(groupHistories);
        return groupHistoriesMapper.toDto(groupHistories);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupHistoriesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupHistories");
        return groupHistoriesRepository.findAll(pageable)
            .map(groupHistoriesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupHistoriesDTO> findOne(Long id) {
        log.debug("Request to get GroupHistories : {}", id);
        return groupHistoriesRepository.findById(id)
            .map(groupHistoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupHistories : {}", id);
        groupHistoriesRepository.deleteById(id);
    }
}
