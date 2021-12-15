/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.NotificationsService;
import com.infy.domain.Notifications;
import com.infy.repository.NotificationsRepository;
import com.infy.service.dto.NotificationsDTO;
import com.infy.service.mapper.NotificationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Notifications}.
 */
@Service
@Transactional
public class NotificationsServiceImpl implements NotificationsService {

    private final Logger log = LoggerFactory.getLogger(NotificationsServiceImpl.class);

    private final NotificationsRepository notificationsRepository;

    private final NotificationsMapper notificationsMapper;

    public NotificationsServiceImpl(NotificationsRepository notificationsRepository, NotificationsMapper notificationsMapper) {
        this.notificationsRepository = notificationsRepository;
        this.notificationsMapper = notificationsMapper;
    }

    @Override
    public NotificationsDTO save(NotificationsDTO notificationsDTO) {
        log.debug("Request to save Notifications : {}", notificationsDTO);
        Notifications notifications = notificationsMapper.toEntity(notificationsDTO);
        notifications = notificationsRepository.save(notifications);
        return notificationsMapper.toDto(notifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notifications");
        return notificationsRepository.findAll(pageable)
            .map(notificationsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationsDTO> findOne(Long id) {
        log.debug("Request to get Notifications : {}", id);
        return notificationsRepository.findById(id)
            .map(notificationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notifications : {}", id);
        notificationsRepository.deleteById(id);
    }
}
