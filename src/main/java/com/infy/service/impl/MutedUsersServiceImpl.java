/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.MutedUsersService;
import com.infy.domain.MutedUsers;
import com.infy.repository.MutedUsersRepository;
import com.infy.service.dto.MutedUsersDTO;
import com.infy.service.mapper.MutedUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MutedUsers}.
 */
@Service
@Transactional
public class MutedUsersServiceImpl implements MutedUsersService {

    private final Logger log = LoggerFactory.getLogger(MutedUsersServiceImpl.class);

    private final MutedUsersRepository mutedUsersRepository;

    private final MutedUsersMapper mutedUsersMapper;

    public MutedUsersServiceImpl(MutedUsersRepository mutedUsersRepository, MutedUsersMapper mutedUsersMapper) {
        this.mutedUsersRepository = mutedUsersRepository;
        this.mutedUsersMapper = mutedUsersMapper;
    }

    @Override
    public MutedUsersDTO save(MutedUsersDTO mutedUsersDTO) {
        log.debug("Request to save MutedUsers : {}", mutedUsersDTO);
        MutedUsers mutedUsers = mutedUsersMapper.toEntity(mutedUsersDTO);
        mutedUsers = mutedUsersRepository.save(mutedUsers);
        return mutedUsersMapper.toDto(mutedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MutedUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MutedUsers");
        return mutedUsersRepository.findAll(pageable)
            .map(mutedUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MutedUsersDTO> findOne(Long id) {
        log.debug("Request to get MutedUsers : {}", id);
        return mutedUsersRepository.findById(id)
            .map(mutedUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MutedUsers : {}", id);
        mutedUsersRepository.deleteById(id);
    }
}
