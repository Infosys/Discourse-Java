/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.WebHookEventsService;
import com.infy.domain.WebHookEvents;
import com.infy.repository.WebHookEventsRepository;
import com.infy.service.dto.WebHookEventsDTO;
import com.infy.service.mapper.WebHookEventsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebHookEvents}.
 */
@Service
@Transactional
public class WebHookEventsServiceImpl implements WebHookEventsService {

    private final Logger log = LoggerFactory.getLogger(WebHookEventsServiceImpl.class);

    private final WebHookEventsRepository webHookEventsRepository;

    private final WebHookEventsMapper webHookEventsMapper;

    public WebHookEventsServiceImpl(WebHookEventsRepository webHookEventsRepository, WebHookEventsMapper webHookEventsMapper) {
        this.webHookEventsRepository = webHookEventsRepository;
        this.webHookEventsMapper = webHookEventsMapper;
    }

    @Override
    public WebHookEventsDTO save(WebHookEventsDTO webHookEventsDTO) {
        log.debug("Request to save WebHookEvents : {}", webHookEventsDTO);
        WebHookEvents webHookEvents = webHookEventsMapper.toEntity(webHookEventsDTO);
        webHookEvents = webHookEventsRepository.save(webHookEvents);
        return webHookEventsMapper.toDto(webHookEvents);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WebHookEventsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebHookEvents");
        return webHookEventsRepository.findAll(pageable)
            .map(webHookEventsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WebHookEventsDTO> findOne(Long id) {
        log.debug("Request to get WebHookEvents : {}", id);
        return webHookEventsRepository.findById(id)
            .map(webHookEventsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebHookEvents : {}", id);
        webHookEventsRepository.deleteById(id);
    }
}
