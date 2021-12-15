/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.AnonymousUsersService;
import com.infy.domain.AnonymousUsers;
import com.infy.repository.AnonymousUsersRepository;
import com.infy.service.dto.AnonymousUsersDTO;
import com.infy.service.mapper.AnonymousUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnonymousUsers}.
 */
@Service
@Transactional
public class AnonymousUsersServiceImpl implements AnonymousUsersService {

    private final Logger log = LoggerFactory.getLogger(AnonymousUsersServiceImpl.class);

    private final AnonymousUsersRepository anonymousUsersRepository;

    private final AnonymousUsersMapper anonymousUsersMapper;

    public AnonymousUsersServiceImpl(AnonymousUsersRepository anonymousUsersRepository, AnonymousUsersMapper anonymousUsersMapper) {
        this.anonymousUsersRepository = anonymousUsersRepository;
        this.anonymousUsersMapper = anonymousUsersMapper;
    }

    @Override
    public AnonymousUsersDTO save(AnonymousUsersDTO anonymousUsersDTO) {
        log.debug("Request to save AnonymousUsers : {}", anonymousUsersDTO);
        AnonymousUsers anonymousUsers = anonymousUsersMapper.toEntity(anonymousUsersDTO);
        anonymousUsers = anonymousUsersRepository.save(anonymousUsers);
        return anonymousUsersMapper.toDto(anonymousUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnonymousUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnonymousUsers");
        return anonymousUsersRepository.findAll(pageable)
            .map(anonymousUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AnonymousUsersDTO> findOne(Long id) {
        log.debug("Request to get AnonymousUsers : {}", id);
        return anonymousUsersRepository.findById(id)
            .map(anonymousUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnonymousUsers : {}", id);
        anonymousUsersRepository.deleteById(id);
    }
}
