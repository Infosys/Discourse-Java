/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PollVotesService;
import com.infy.domain.PollVotes;
import com.infy.repository.PollVotesRepository;
import com.infy.service.dto.PollVotesDTO;
import com.infy.service.mapper.PollVotesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PollVotes}.
 */
@Service
@Transactional
public class PollVotesServiceImpl implements PollVotesService {

    private final Logger log = LoggerFactory.getLogger(PollVotesServiceImpl.class);

    private final PollVotesRepository pollVotesRepository;

    private final PollVotesMapper pollVotesMapper;

    public PollVotesServiceImpl(PollVotesRepository pollVotesRepository, PollVotesMapper pollVotesMapper) {
        this.pollVotesRepository = pollVotesRepository;
        this.pollVotesMapper = pollVotesMapper;
    }

    @Override
    public PollVotesDTO save(PollVotesDTO pollVotesDTO) {
        log.debug("Request to save PollVotes : {}", pollVotesDTO);
        PollVotes pollVotes = pollVotesMapper.toEntity(pollVotesDTO);
        pollVotes = pollVotesRepository.save(pollVotes);
        return pollVotesMapper.toDto(pollVotes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PollVotesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PollVotes");
        return pollVotesRepository.findAll(pageable)
            .map(pollVotesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PollVotesDTO> findOne(Long id) {
        log.debug("Request to get PollVotes : {}", id);
        return pollVotesRepository.findById(id)
            .map(pollVotesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PollVotes : {}", id);
        pollVotesRepository.deleteById(id);
    }
}
