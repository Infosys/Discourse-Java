/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.IgnoredUsersService;
import com.infy.domain.IgnoredUsers;
import com.infy.repository.IgnoredUsersRepository;
import com.infy.service.dto.IgnoredUsersDTO;
import com.infy.service.mapper.IgnoredUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IgnoredUsers}.
 */
@Service
@Transactional
public class IgnoredUsersServiceImpl implements IgnoredUsersService {

    private final Logger log = LoggerFactory.getLogger(IgnoredUsersServiceImpl.class);

    private final IgnoredUsersRepository ignoredUsersRepository;

    private final IgnoredUsersMapper ignoredUsersMapper;

    public IgnoredUsersServiceImpl(IgnoredUsersRepository ignoredUsersRepository, IgnoredUsersMapper ignoredUsersMapper) {
        this.ignoredUsersRepository = ignoredUsersRepository;
        this.ignoredUsersMapper = ignoredUsersMapper;
    }

    @Override
    public IgnoredUsersDTO save(IgnoredUsersDTO ignoredUsersDTO) {
        log.debug("Request to save IgnoredUsers : {}", ignoredUsersDTO);
        IgnoredUsers ignoredUsers = ignoredUsersMapper.toEntity(ignoredUsersDTO);
        ignoredUsers = ignoredUsersRepository.save(ignoredUsers);
        return ignoredUsersMapper.toDto(ignoredUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IgnoredUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IgnoredUsers");
        return ignoredUsersRepository.findAll(pageable)
            .map(ignoredUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IgnoredUsersDTO> findOne(Long id) {
        log.debug("Request to get IgnoredUsers : {}", id);
        return ignoredUsersRepository.findById(id)
            .map(ignoredUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IgnoredUsers : {}", id);
        ignoredUsersRepository.deleteById(id);
    }
}
