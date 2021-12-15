/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.GroupUsersService;
import com.infy.domain.GroupUsers;
import com.infy.repository.GroupUsersRepository;
import com.infy.service.dto.GroupUsersDTO;
import com.infy.service.mapper.GroupUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupUsers}.
 */
@Service
@Transactional
public class GroupUsersServiceImpl implements GroupUsersService {

    private final Logger log = LoggerFactory.getLogger(GroupUsersServiceImpl.class);

    private final GroupUsersRepository groupUsersRepository;

    private final GroupUsersMapper groupUsersMapper;

    public GroupUsersServiceImpl(GroupUsersRepository groupUsersRepository, GroupUsersMapper groupUsersMapper) {
        this.groupUsersRepository = groupUsersRepository;
        this.groupUsersMapper = groupUsersMapper;
    }

    @Override
    public GroupUsersDTO save(GroupUsersDTO groupUsersDTO) {
        log.debug("Request to save GroupUsers : {}", groupUsersDTO);
        GroupUsers groupUsers = groupUsersMapper.toEntity(groupUsersDTO);
        groupUsers = groupUsersRepository.save(groupUsers);
        return groupUsersMapper.toDto(groupUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupUsers");
        return groupUsersRepository.findAll(pageable)
            .map(groupUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GroupUsersDTO> findOne(Long id) {
        log.debug("Request to get GroupUsers : {}", id);
        return groupUsersRepository.findById(id)
            .map(groupUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupUsers : {}", id);
        groupUsersRepository.deleteById(id);
    }
}
