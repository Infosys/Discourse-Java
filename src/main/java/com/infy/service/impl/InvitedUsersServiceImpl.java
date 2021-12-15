/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.InvitedUsersService;
import com.infy.domain.InvitedUsers;
import com.infy.repository.InvitedUsersRepository;
import com.infy.service.dto.InvitedUsersDTO;
import com.infy.service.mapper.InvitedUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InvitedUsers}.
 */
@Service
@Transactional
public class InvitedUsersServiceImpl implements InvitedUsersService {

    private final Logger log = LoggerFactory.getLogger(InvitedUsersServiceImpl.class);

    private final InvitedUsersRepository invitedUsersRepository;

    private final InvitedUsersMapper invitedUsersMapper;

    public InvitedUsersServiceImpl(InvitedUsersRepository invitedUsersRepository, InvitedUsersMapper invitedUsersMapper) {
        this.invitedUsersRepository = invitedUsersRepository;
        this.invitedUsersMapper = invitedUsersMapper;
    }

    @Override
    public InvitedUsersDTO save(InvitedUsersDTO invitedUsersDTO) {
        log.debug("Request to save InvitedUsers : {}", invitedUsersDTO);
        InvitedUsers invitedUsers = invitedUsersMapper.toEntity(invitedUsersDTO);
        invitedUsers = invitedUsersRepository.save(invitedUsers);
        return invitedUsersMapper.toDto(invitedUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitedUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvitedUsers");
        return invitedUsersRepository.findAll(pageable)
            .map(invitedUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InvitedUsersDTO> findOne(Long id) {
        log.debug("Request to get InvitedUsers : {}", id);
        return invitedUsersRepository.findById(id)
            .map(invitedUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvitedUsers : {}", id);
        invitedUsersRepository.deleteById(id);
    }
}
