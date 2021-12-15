/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.IncomingReferersService;
import com.infy.domain.IncomingReferers;
import com.infy.repository.IncomingReferersRepository;
import com.infy.service.dto.IncomingReferersDTO;
import com.infy.service.mapper.IncomingReferersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link IncomingReferers}.
 */
@Service
@Transactional
public class IncomingReferersServiceImpl implements IncomingReferersService {

    private final Logger log = LoggerFactory.getLogger(IncomingReferersServiceImpl.class);

    private final IncomingReferersRepository incomingReferersRepository;

    private final IncomingReferersMapper incomingReferersMapper;

    public IncomingReferersServiceImpl(IncomingReferersRepository incomingReferersRepository, IncomingReferersMapper incomingReferersMapper) {
        this.incomingReferersRepository = incomingReferersRepository;
        this.incomingReferersMapper = incomingReferersMapper;
    }

    @Override
    public IncomingReferersDTO save(IncomingReferersDTO incomingReferersDTO) {
        log.debug("Request to save IncomingReferers : {}", incomingReferersDTO);
        IncomingReferers incomingReferers = incomingReferersMapper.toEntity(incomingReferersDTO);
        incomingReferers = incomingReferersRepository.save(incomingReferers);
        return incomingReferersMapper.toDto(incomingReferers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomingReferersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomingReferers");
        return incomingReferersRepository.findAll(pageable)
            .map(incomingReferersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<IncomingReferersDTO> findOne(Long id) {
        log.debug("Request to get IncomingReferers : {}", id);
        return incomingReferersRepository.findById(id)
            .map(incomingReferersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncomingReferers : {}", id);
        incomingReferersRepository.deleteById(id);
    }
}
