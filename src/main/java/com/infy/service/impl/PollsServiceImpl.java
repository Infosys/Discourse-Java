/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PollsService;
import com.infy.domain.Polls;
import com.infy.repository.PollsRepository;
import com.infy.service.dto.PollsDTO;
import com.infy.service.mapper.PollsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Polls}.
 */
@Service
@Transactional
public class PollsServiceImpl implements PollsService {

    private final Logger log = LoggerFactory.getLogger(PollsServiceImpl.class);

    private final PollsRepository pollsRepository;

    private final PollsMapper pollsMapper;

    public PollsServiceImpl(PollsRepository pollsRepository, PollsMapper pollsMapper) {
        this.pollsRepository = pollsRepository;
        this.pollsMapper = pollsMapper;
    }

    @Override
    public PollsDTO save(PollsDTO pollsDTO) {
        log.debug("Request to save Polls : {}", pollsDTO);
        Polls polls = pollsMapper.toEntity(pollsDTO);
        polls = pollsRepository.save(polls);
        return pollsMapper.toDto(polls);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PollsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Polls");
        return pollsRepository.findAll(pageable)
            .map(pollsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PollsDTO> findOne(Long id) {
        log.debug("Request to get Polls : {}", id);
        return pollsRepository.findById(id)
            .map(pollsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Polls : {}", id);
        pollsRepository.deleteById(id);
    }
}
