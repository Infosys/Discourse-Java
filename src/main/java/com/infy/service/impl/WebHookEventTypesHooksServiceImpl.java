/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.WebHookEventTypesHooksService;
import com.infy.domain.WebHookEventTypesHooks;
import com.infy.repository.WebHookEventTypesHooksRepository;
import com.infy.service.dto.WebHookEventTypesHooksDTO;
import com.infy.service.mapper.WebHookEventTypesHooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebHookEventTypesHooks}.
 */
@Service
@Transactional
public class WebHookEventTypesHooksServiceImpl implements WebHookEventTypesHooksService {

    private final Logger log = LoggerFactory.getLogger(WebHookEventTypesHooksServiceImpl.class);

    private final WebHookEventTypesHooksRepository webHookEventTypesHooksRepository;

    private final WebHookEventTypesHooksMapper webHookEventTypesHooksMapper;

    public WebHookEventTypesHooksServiceImpl(WebHookEventTypesHooksRepository webHookEventTypesHooksRepository, WebHookEventTypesHooksMapper webHookEventTypesHooksMapper) {
        this.webHookEventTypesHooksRepository = webHookEventTypesHooksRepository;
        this.webHookEventTypesHooksMapper = webHookEventTypesHooksMapper;
    }

    @Override
    public WebHookEventTypesHooksDTO save(WebHookEventTypesHooksDTO webHookEventTypesHooksDTO) {
        log.debug("Request to save WebHookEventTypesHooks : {}", webHookEventTypesHooksDTO);
        WebHookEventTypesHooks webHookEventTypesHooks = webHookEventTypesHooksMapper.toEntity(webHookEventTypesHooksDTO);
        webHookEventTypesHooks = webHookEventTypesHooksRepository.save(webHookEventTypesHooks);
        return webHookEventTypesHooksMapper.toDto(webHookEventTypesHooks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WebHookEventTypesHooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebHookEventTypesHooks");
        return webHookEventTypesHooksRepository.findAll(pageable)
            .map(webHookEventTypesHooksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WebHookEventTypesHooksDTO> findOne(Long id) {
        log.debug("Request to get WebHookEventTypesHooks : {}", id);
        return webHookEventTypesHooksRepository.findById(id)
            .map(webHookEventTypesHooksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebHookEventTypesHooks : {}", id);
        webHookEventTypesHooksRepository.deleteById(id);
    }
}
