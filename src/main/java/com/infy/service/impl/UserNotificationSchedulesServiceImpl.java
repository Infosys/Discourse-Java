/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserNotificationSchedulesService;
import com.infy.domain.UserNotificationSchedules;
import com.infy.repository.UserNotificationSchedulesRepository;
import com.infy.service.dto.UserNotificationSchedulesDTO;
import com.infy.service.mapper.UserNotificationSchedulesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserNotificationSchedules}.
 */
@Service
@Transactional
public class UserNotificationSchedulesServiceImpl implements UserNotificationSchedulesService {

    private final Logger log = LoggerFactory.getLogger(UserNotificationSchedulesServiceImpl.class);

    private final UserNotificationSchedulesRepository userNotificationSchedulesRepository;

    private final UserNotificationSchedulesMapper userNotificationSchedulesMapper;

    public UserNotificationSchedulesServiceImpl(UserNotificationSchedulesRepository userNotificationSchedulesRepository, UserNotificationSchedulesMapper userNotificationSchedulesMapper) {
        this.userNotificationSchedulesRepository = userNotificationSchedulesRepository;
        this.userNotificationSchedulesMapper = userNotificationSchedulesMapper;
    }

    @Override
    public UserNotificationSchedulesDTO save(UserNotificationSchedulesDTO userNotificationSchedulesDTO) {
        log.debug("Request to save UserNotificationSchedules : {}", userNotificationSchedulesDTO);
        UserNotificationSchedules userNotificationSchedules = userNotificationSchedulesMapper.toEntity(userNotificationSchedulesDTO);
        userNotificationSchedules = userNotificationSchedulesRepository.save(userNotificationSchedules);
        return userNotificationSchedulesMapper.toDto(userNotificationSchedules);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserNotificationSchedulesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserNotificationSchedules");
        return userNotificationSchedulesRepository.findAll(pageable)
            .map(userNotificationSchedulesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserNotificationSchedulesDTO> findOne(Long id) {
        log.debug("Request to get UserNotificationSchedules : {}", id);
        return userNotificationSchedulesRepository.findById(id)
            .map(userNotificationSchedulesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserNotificationSchedules : {}", id);
        userNotificationSchedulesRepository.deleteById(id);
    }
}
