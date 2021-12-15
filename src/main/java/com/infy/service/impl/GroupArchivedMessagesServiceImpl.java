/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupArchivedMessagesService;
import com.infy.domain.GroupArchivedMessages;
import com.infy.repository.GroupArchivedMessagesRepository;
import com.infy.service.dto.GroupArchivedMessagesDTO;
import com.infy.service.mapper.GroupArchivedMessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupArchivedMessages}.
 */
@Service
@Transactional
public class GroupArchivedMessagesServiceImpl implements GroupArchivedMessagesService {

    private final Logger log = LoggerFactory.getLogger(GroupArchivedMessagesServiceImpl.class);

    private final GroupArchivedMessagesRepository groupArchivedMessagesRepository;

    private final GroupArchivedMessagesMapper groupArchivedMessagesMapper;

    public GroupArchivedMessagesServiceImpl(GroupArchivedMessagesRepository groupArchivedMessagesRepository, GroupArchivedMessagesMapper groupArchivedMessagesMapper) {
        this.groupArchivedMessagesRepository = groupArchivedMessagesRepository;
        this.groupArchivedMessagesMapper = groupArchivedMessagesMapper;
    }

    @Override
    public GroupArchivedMessagesDTO save(GroupArchivedMessagesDTO groupArchivedMessagesDTO) {
        log.debug("Request to save GroupArchivedMessages : {}", groupArchivedMessagesDTO);
        GroupArchivedMessages groupArchivedMessages = groupArchivedMessagesMapper.toEntity(groupArchivedMessagesDTO);
        groupArchivedMessages = groupArchivedMessagesRepository.save(groupArchivedMessages);
        return groupArchivedMessagesMapper.toDto(groupArchivedMessages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupArchivedMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupArchivedMessages");
        return groupArchivedMessagesRepository.findAll(pageable)
            .map(groupArchivedMessagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupArchivedMessagesDTO> findOne(Long id) {
        log.debug("Request to get GroupArchivedMessages : {}", id);
        return groupArchivedMessagesRepository.findById(id)
            .map(groupArchivedMessagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupArchivedMessages : {}", id);
        groupArchivedMessagesRepository.deleteById(id);
    }
}
