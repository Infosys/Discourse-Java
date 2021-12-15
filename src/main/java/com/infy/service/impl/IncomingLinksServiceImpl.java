/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.IncomingLinksService;
import com.infy.domain.IncomingLinks;
import com.infy.repository.IncomingLinksRepository;
import com.infy.service.dto.IncomingLinksDTO;
import com.infy.service.mapper.IncomingLinksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncomingLinks}.
 */
@Service
@Transactional
public class IncomingLinksServiceImpl implements IncomingLinksService {

    private final Logger log = LoggerFactory.getLogger(IncomingLinksServiceImpl.class);

    private final IncomingLinksRepository incomingLinksRepository;

    private final IncomingLinksMapper incomingLinksMapper;

    public IncomingLinksServiceImpl(IncomingLinksRepository incomingLinksRepository, IncomingLinksMapper incomingLinksMapper) {
        this.incomingLinksRepository = incomingLinksRepository;
        this.incomingLinksMapper = incomingLinksMapper;
    }

    @Override
    public IncomingLinksDTO save(IncomingLinksDTO incomingLinksDTO) {
        log.debug("Request to save IncomingLinks : {}", incomingLinksDTO);
        IncomingLinks incomingLinks = incomingLinksMapper.toEntity(incomingLinksDTO);
        incomingLinks = incomingLinksRepository.save(incomingLinks);
        return incomingLinksMapper.toDto(incomingLinks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomingLinksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomingLinks");
        return incomingLinksRepository.findAll(pageable)
            .map(incomingLinksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IncomingLinksDTO> findOne(Long id) {
        log.debug("Request to get IncomingLinks : {}", id);
        return incomingLinksRepository.findById(id)
            .map(incomingLinksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncomingLinks : {}", id);
        incomingLinksRepository.deleteById(id);
    }
}
