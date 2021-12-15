/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserAssociatedAccountsService;
import com.infy.domain.UserAssociatedAccounts;
import com.infy.repository.UserAssociatedAccountsRepository;
import com.infy.service.dto.UserAssociatedAccountsDTO;
import com.infy.service.mapper.UserAssociatedAccountsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserAssociatedAccounts}.
 */
@Service
@Transactional
public class UserAssociatedAccountsServiceImpl implements UserAssociatedAccountsService {

    private final Logger log = LoggerFactory.getLogger(UserAssociatedAccountsServiceImpl.class);

    private final UserAssociatedAccountsRepository userAssociatedAccountsRepository;

    private final UserAssociatedAccountsMapper userAssociatedAccountsMapper;

    public UserAssociatedAccountsServiceImpl(UserAssociatedAccountsRepository userAssociatedAccountsRepository, UserAssociatedAccountsMapper userAssociatedAccountsMapper) {
        this.userAssociatedAccountsRepository = userAssociatedAccountsRepository;
        this.userAssociatedAccountsMapper = userAssociatedAccountsMapper;
    }

    @Override
    public UserAssociatedAccountsDTO save(UserAssociatedAccountsDTO userAssociatedAccountsDTO) {
        log.debug("Request to save UserAssociatedAccounts : {}", userAssociatedAccountsDTO);
        UserAssociatedAccounts userAssociatedAccounts = userAssociatedAccountsMapper.toEntity(userAssociatedAccountsDTO);
        userAssociatedAccounts = userAssociatedAccountsRepository.save(userAssociatedAccounts);
        return userAssociatedAccountsMapper.toDto(userAssociatedAccounts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserAssociatedAccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAssociatedAccounts");
        return userAssociatedAccountsRepository.findAll(pageable)
            .map(userAssociatedAccountsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserAssociatedAccountsDTO> findOne(Long id) {
        log.debug("Request to get UserAssociatedAccounts : {}", id);
        return userAssociatedAccountsRepository.findById(id)
            .map(userAssociatedAccountsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAssociatedAccounts : {}", id);
        userAssociatedAccountsRepository.deleteById(id);
    }
}
