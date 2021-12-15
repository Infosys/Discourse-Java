/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PollOptionsService;
import com.infy.domain.PollOptions;
import com.infy.repository.PollOptionsRepository;
import com.infy.service.dto.PollOptionsDTO;
import com.infy.service.mapper.PollOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PollOptions}.
 */
@Service
@Transactional
public class PollOptionsServiceImpl implements PollOptionsService {

    private final Logger log = LoggerFactory.getLogger(PollOptionsServiceImpl.class);

    private final PollOptionsRepository pollOptionsRepository;

    private final PollOptionsMapper pollOptionsMapper;

    public PollOptionsServiceImpl(PollOptionsRepository pollOptionsRepository, PollOptionsMapper pollOptionsMapper) {
        this.pollOptionsRepository = pollOptionsRepository;
        this.pollOptionsMapper = pollOptionsMapper;
    }

    @Override
    public PollOptionsDTO save(PollOptionsDTO pollOptionsDTO) {
        log.debug("Request to save PollOptions : {}", pollOptionsDTO);
        PollOptions pollOptions = pollOptionsMapper.toEntity(pollOptionsDTO);
        pollOptions = pollOptionsRepository.save(pollOptions);
        return pollOptionsMapper.toDto(pollOptions);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PollOptionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PollOptions");
        return pollOptionsRepository.findAll(pageable)
            .map(pollOptionsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PollOptionsDTO> findOne(Long id) {
        log.debug("Request to get PollOptions : {}", id);
        return pollOptionsRepository.findById(id)
            .map(pollOptionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PollOptions : {}", id);
        pollOptionsRepository.deleteById(id);
    }
}
