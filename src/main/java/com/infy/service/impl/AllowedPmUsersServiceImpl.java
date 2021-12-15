/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.AllowedPmUsersService;
import com.infy.domain.AllowedPmUsers;
import com.infy.repository.AllowedPmUsersRepository;
import com.infy.service.dto.AllowedPmUsersDTO;
import com.infy.service.mapper.AllowedPmUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AllowedPmUsers}.
 */
@Service
@Transactional
public class AllowedPmUsersServiceImpl implements AllowedPmUsersService {

    private final Logger log = LoggerFactory.getLogger(AllowedPmUsersServiceImpl.class);

    private final AllowedPmUsersRepository allowedPmUsersRepository;

    private final AllowedPmUsersMapper allowedPmUsersMapper;

    public AllowedPmUsersServiceImpl(AllowedPmUsersRepository allowedPmUsersRepository, AllowedPmUsersMapper allowedPmUsersMapper) {
        this.allowedPmUsersRepository = allowedPmUsersRepository;
        this.allowedPmUsersMapper = allowedPmUsersMapper;
    }

    @Override
    public AllowedPmUsersDTO save(AllowedPmUsersDTO allowedPmUsersDTO) {
        log.debug("Request to save AllowedPmUsers : {}", allowedPmUsersDTO);
        AllowedPmUsers allowedPmUsers = allowedPmUsersMapper.toEntity(allowedPmUsersDTO);
        allowedPmUsers = allowedPmUsersRepository.save(allowedPmUsers);
        return allowedPmUsersMapper.toDto(allowedPmUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AllowedPmUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AllowedPmUsers");
        return allowedPmUsersRepository.findAll(pageable)
            .map(allowedPmUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AllowedPmUsersDTO> findOne(Long id) {
        log.debug("Request to get AllowedPmUsers : {}", id);
        return allowedPmUsersRepository.findById(id)
            .map(allowedPmUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AllowedPmUsers : {}", id);
        allowedPmUsersRepository.deleteById(id);
    }
}
