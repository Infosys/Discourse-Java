/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SingleSignOnRecordsService;
import com.infy.domain.SingleSignOnRecords;
import com.infy.repository.SingleSignOnRecordsRepository;
import com.infy.service.dto.SingleSignOnRecordsDTO;
import com.infy.service.mapper.SingleSignOnRecordsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SingleSignOnRecords}.
 */
@Service
@Transactional
public class SingleSignOnRecordsServiceImpl implements SingleSignOnRecordsService {

    private final Logger log = LoggerFactory.getLogger(SingleSignOnRecordsServiceImpl.class);

    private final SingleSignOnRecordsRepository singleSignOnRecordsRepository;

    private final SingleSignOnRecordsMapper singleSignOnRecordsMapper;

    public SingleSignOnRecordsServiceImpl(SingleSignOnRecordsRepository singleSignOnRecordsRepository, SingleSignOnRecordsMapper singleSignOnRecordsMapper) {
        this.singleSignOnRecordsRepository = singleSignOnRecordsRepository;
        this.singleSignOnRecordsMapper = singleSignOnRecordsMapper;
    }

    @Override
    public SingleSignOnRecordsDTO save(SingleSignOnRecordsDTO singleSignOnRecordsDTO) {
        log.debug("Request to save SingleSignOnRecords : {}", singleSignOnRecordsDTO);
        SingleSignOnRecords singleSignOnRecords = singleSignOnRecordsMapper.toEntity(singleSignOnRecordsDTO);
        singleSignOnRecords = singleSignOnRecordsRepository.save(singleSignOnRecords);
        return singleSignOnRecordsMapper.toDto(singleSignOnRecords);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SingleSignOnRecordsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SingleSignOnRecords");
        return singleSignOnRecordsRepository.findAll(pageable)
            .map(singleSignOnRecordsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SingleSignOnRecordsDTO> findOne(Long id) {
        log.debug("Request to get SingleSignOnRecords : {}", id);
        return singleSignOnRecordsRepository.findById(id)
            .map(singleSignOnRecordsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SingleSignOnRecords : {}", id);
        singleSignOnRecordsRepository.deleteById(id);
    }
}
