/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.WebHooksService;
import com.infy.domain.WebHooks;
import com.infy.repository.WebHooksRepository;
import com.infy.service.dto.WebHooksDTO;
import com.infy.service.mapper.WebHooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebHooks}.
 */
@Service
@Transactional
public class WebHooksServiceImpl implements WebHooksService {

    private final Logger log = LoggerFactory.getLogger(WebHooksServiceImpl.class);

    private final WebHooksRepository webHooksRepository;

    private final WebHooksMapper webHooksMapper;

    public WebHooksServiceImpl(WebHooksRepository webHooksRepository, WebHooksMapper webHooksMapper) {
        this.webHooksRepository = webHooksRepository;
        this.webHooksMapper = webHooksMapper;
    }

    @Override
    public WebHooksDTO save(WebHooksDTO webHooksDTO) {
        log.debug("Request to save WebHooks : {}", webHooksDTO);
        WebHooks webHooks = webHooksMapper.toEntity(webHooksDTO);
        webHooks = webHooksRepository.save(webHooks);
        return webHooksMapper.toDto(webHooks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WebHooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebHooks");
        return webHooksRepository.findAll(pageable)
            .map(webHooksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WebHooksDTO> findOne(Long id) {
        log.debug("Request to get WebHooks : {}", id);
        return webHooksRepository.findById(id)
            .map(webHooksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebHooks : {}", id);
        webHooksRepository.deleteById(id);
    }
}
