/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PushSubscriptionsService;
import com.infy.domain.PushSubscriptions;
import com.infy.repository.PushSubscriptionsRepository;
import com.infy.service.dto.PushSubscriptionsDTO;
import com.infy.service.mapper.PushSubscriptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PushSubscriptions}.
 */
@Service
@Transactional
public class PushSubscriptionsServiceImpl implements PushSubscriptionsService {

    private final Logger log = LoggerFactory.getLogger(PushSubscriptionsServiceImpl.class);

    private final PushSubscriptionsRepository pushSubscriptionsRepository;

    private final PushSubscriptionsMapper pushSubscriptionsMapper;

    public PushSubscriptionsServiceImpl(PushSubscriptionsRepository pushSubscriptionsRepository, PushSubscriptionsMapper pushSubscriptionsMapper) {
        this.pushSubscriptionsRepository = pushSubscriptionsRepository;
        this.pushSubscriptionsMapper = pushSubscriptionsMapper;
    }

    @Override
    public PushSubscriptionsDTO save(PushSubscriptionsDTO pushSubscriptionsDTO) {
        log.debug("Request to save PushSubscriptions : {}", pushSubscriptionsDTO);
        PushSubscriptions pushSubscriptions = pushSubscriptionsMapper.toEntity(pushSubscriptionsDTO);
        pushSubscriptions = pushSubscriptionsRepository.save(pushSubscriptions);
        return pushSubscriptionsMapper.toDto(pushSubscriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PushSubscriptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PushSubscriptions");
        return pushSubscriptionsRepository.findAll(pageable)
            .map(pushSubscriptionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PushSubscriptionsDTO> findOne(Long id) {
        log.debug("Request to get PushSubscriptions : {}", id);
        return pushSubscriptionsRepository.findById(id)
            .map(pushSubscriptionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PushSubscriptions : {}", id);
        pushSubscriptionsRepository.deleteById(id);
    }
}
