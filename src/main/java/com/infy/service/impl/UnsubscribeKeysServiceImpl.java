/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UnsubscribeKeysService;
import com.infy.domain.UnsubscribeKeys;
import com.infy.repository.UnsubscribeKeysRepository;
import com.infy.service.dto.UnsubscribeKeysDTO;
import com.infy.service.mapper.UnsubscribeKeysMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UnsubscribeKeys}.
 */
@Service
@Transactional
public class UnsubscribeKeysServiceImpl implements UnsubscribeKeysService {

    private final Logger log = LoggerFactory.getLogger(UnsubscribeKeysServiceImpl.class);

    private final UnsubscribeKeysRepository unsubscribeKeysRepository;

    private final UnsubscribeKeysMapper unsubscribeKeysMapper;

    public UnsubscribeKeysServiceImpl(UnsubscribeKeysRepository unsubscribeKeysRepository, UnsubscribeKeysMapper unsubscribeKeysMapper) {
        this.unsubscribeKeysRepository = unsubscribeKeysRepository;
        this.unsubscribeKeysMapper = unsubscribeKeysMapper;
    }

    @Override
    public UnsubscribeKeysDTO save(UnsubscribeKeysDTO unsubscribeKeysDTO) {
        log.debug("Request to save UnsubscribeKeys : {}", unsubscribeKeysDTO);
        UnsubscribeKeys unsubscribeKeys = unsubscribeKeysMapper.toEntity(unsubscribeKeysDTO);
        unsubscribeKeys = unsubscribeKeysRepository.save(unsubscribeKeys);
        return unsubscribeKeysMapper.toDto(unsubscribeKeys);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnsubscribeKeysDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UnsubscribeKeys");
        return unsubscribeKeysRepository.findAll(pageable)
            .map(unsubscribeKeysMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UnsubscribeKeysDTO> findOne(Long id) {
        log.debug("Request to get UnsubscribeKeys : {}", id);
        return unsubscribeKeysRepository.findById(id)
            .map(unsubscribeKeysMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnsubscribeKeys : {}", id);
        unsubscribeKeysRepository.deleteById(id);
    }
}
