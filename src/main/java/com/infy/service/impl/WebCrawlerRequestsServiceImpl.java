/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.WebCrawlerRequestsService;
import com.infy.domain.WebCrawlerRequests;
import com.infy.repository.WebCrawlerRequestsRepository;
import com.infy.service.dto.WebCrawlerRequestsDTO;
import com.infy.service.mapper.WebCrawlerRequestsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WebCrawlerRequests}.
 */
@Service
@Transactional
public class WebCrawlerRequestsServiceImpl implements WebCrawlerRequestsService {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerRequestsServiceImpl.class);

    private final WebCrawlerRequestsRepository webCrawlerRequestsRepository;

    private final WebCrawlerRequestsMapper webCrawlerRequestsMapper;

    public WebCrawlerRequestsServiceImpl(WebCrawlerRequestsRepository webCrawlerRequestsRepository, WebCrawlerRequestsMapper webCrawlerRequestsMapper) {
        this.webCrawlerRequestsRepository = webCrawlerRequestsRepository;
        this.webCrawlerRequestsMapper = webCrawlerRequestsMapper;
    }

    @Override
    public WebCrawlerRequestsDTO save(WebCrawlerRequestsDTO webCrawlerRequestsDTO) {
        log.debug("Request to save WebCrawlerRequests : {}", webCrawlerRequestsDTO);
        WebCrawlerRequests webCrawlerRequests = webCrawlerRequestsMapper.toEntity(webCrawlerRequestsDTO);
        webCrawlerRequests = webCrawlerRequestsRepository.save(webCrawlerRequests);
        return webCrawlerRequestsMapper.toDto(webCrawlerRequests);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WebCrawlerRequestsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebCrawlerRequests");
        return webCrawlerRequestsRepository.findAll(pageable)
            .map(webCrawlerRequestsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WebCrawlerRequestsDTO> findOne(Long id) {
        log.debug("Request to get WebCrawlerRequests : {}", id);
        return webCrawlerRequestsRepository.findById(id)
            .map(webCrawlerRequestsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebCrawlerRequests : {}", id);
        webCrawlerRequestsRepository.deleteById(id);
    }
}
