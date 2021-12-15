/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.IncomingDomainsService;
import com.infy.domain.IncomingDomains;
import com.infy.repository.IncomingDomainsRepository;
import com.infy.service.dto.IncomingDomainsDTO;
import com.infy.service.mapper.IncomingDomainsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncomingDomains}.
 */
@Service
@Transactional
public class IncomingDomainsServiceImpl implements IncomingDomainsService {

    private final Logger log = LoggerFactory.getLogger(IncomingDomainsServiceImpl.class);

    private final IncomingDomainsRepository incomingDomainsRepository;

    private final IncomingDomainsMapper incomingDomainsMapper;

    public IncomingDomainsServiceImpl(IncomingDomainsRepository incomingDomainsRepository, IncomingDomainsMapper incomingDomainsMapper) {
        this.incomingDomainsRepository = incomingDomainsRepository;
        this.incomingDomainsMapper = incomingDomainsMapper;
    }

    @Override
    public IncomingDomainsDTO save(IncomingDomainsDTO incomingDomainsDTO) {
        log.debug("Request to save IncomingDomains : {}", incomingDomainsDTO);
        IncomingDomains incomingDomains = incomingDomainsMapper.toEntity(incomingDomainsDTO);
        incomingDomains = incomingDomainsRepository.save(incomingDomains);
        return incomingDomainsMapper.toDto(incomingDomains);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomingDomainsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomingDomains");
        return incomingDomainsRepository.findAll(pageable)
            .map(incomingDomainsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IncomingDomainsDTO> findOne(Long id) {
        log.debug("Request to get IncomingDomains : {}", id);
        return incomingDomainsRepository.findById(id)
            .map(incomingDomainsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncomingDomains : {}", id);
        incomingDomainsRepository.deleteById(id);
    }
}
