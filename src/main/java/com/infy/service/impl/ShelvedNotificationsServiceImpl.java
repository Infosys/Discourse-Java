/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ShelvedNotificationsService;
import com.infy.domain.ShelvedNotifications;
import com.infy.repository.ShelvedNotificationsRepository;
import com.infy.service.dto.ShelvedNotificationsDTO;
import com.infy.service.mapper.ShelvedNotificationsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ShelvedNotifications}.
 */
@Service
@Transactional
public class ShelvedNotificationsServiceImpl implements ShelvedNotificationsService {

    private final Logger log = LoggerFactory.getLogger(ShelvedNotificationsServiceImpl.class);

    private final ShelvedNotificationsRepository shelvedNotificationsRepository;

    private final ShelvedNotificationsMapper shelvedNotificationsMapper;

    public ShelvedNotificationsServiceImpl(ShelvedNotificationsRepository shelvedNotificationsRepository, ShelvedNotificationsMapper shelvedNotificationsMapper) {
        this.shelvedNotificationsRepository = shelvedNotificationsRepository;
        this.shelvedNotificationsMapper = shelvedNotificationsMapper;
    }

    @Override
    public ShelvedNotificationsDTO save(ShelvedNotificationsDTO shelvedNotificationsDTO) {
        log.debug("Request to save ShelvedNotifications : {}", shelvedNotificationsDTO);
        ShelvedNotifications shelvedNotifications = shelvedNotificationsMapper.toEntity(shelvedNotificationsDTO);
        shelvedNotifications = shelvedNotificationsRepository.save(shelvedNotifications);
        return shelvedNotificationsMapper.toDto(shelvedNotifications);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShelvedNotificationsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShelvedNotifications");
        return shelvedNotificationsRepository.findAll(pageable)
            .map(shelvedNotificationsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ShelvedNotificationsDTO> findOne(Long id) {
        log.debug("Request to get ShelvedNotifications : {}", id);
        return shelvedNotificationsRepository.findById(id)
            .map(shelvedNotificationsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShelvedNotifications : {}", id);
        shelvedNotificationsRepository.deleteById(id);
    }
}
