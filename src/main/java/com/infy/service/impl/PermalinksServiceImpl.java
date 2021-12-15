/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PermalinksService;
import com.infy.domain.Permalinks;
import com.infy.repository.PermalinksRepository;
import com.infy.service.dto.PermalinksDTO;
import com.infy.service.mapper.PermalinksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Permalinks}.
 */
@Service
@Transactional
public class PermalinksServiceImpl implements PermalinksService {

    private final Logger log = LoggerFactory.getLogger(PermalinksServiceImpl.class);

    private final PermalinksRepository permalinksRepository;

    private final PermalinksMapper permalinksMapper;

    public PermalinksServiceImpl(PermalinksRepository permalinksRepository, PermalinksMapper permalinksMapper) {
        this.permalinksRepository = permalinksRepository;
        this.permalinksMapper = permalinksMapper;
    }

    @Override
    public PermalinksDTO save(PermalinksDTO permalinksDTO) {
        log.debug("Request to save Permalinks : {}", permalinksDTO);
        Permalinks permalinks = permalinksMapper.toEntity(permalinksDTO);
        permalinks = permalinksRepository.save(permalinks);
        return permalinksMapper.toDto(permalinks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermalinksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Permalinks");
        return permalinksRepository.findAll(pageable)
            .map(permalinksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PermalinksDTO> findOne(Long id) {
        log.debug("Request to get Permalinks : {}", id);
        return permalinksRepository.findById(id)
            .map(permalinksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permalinks : {}", id);
        permalinksRepository.deleteById(id);
    }
}
