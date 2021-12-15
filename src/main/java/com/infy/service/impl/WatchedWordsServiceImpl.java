/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.WatchedWordsService;
import com.infy.domain.WatchedWords;
import com.infy.repository.WatchedWordsRepository;
import com.infy.service.dto.WatchedWordsDTO;
import com.infy.service.mapper.WatchedWordsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WatchedWords}.
 */
@Service
@Transactional
public class WatchedWordsServiceImpl implements WatchedWordsService {

    private final Logger log = LoggerFactory.getLogger(WatchedWordsServiceImpl.class);

    private final WatchedWordsRepository watchedWordsRepository;

    private final WatchedWordsMapper watchedWordsMapper;

    public WatchedWordsServiceImpl(WatchedWordsRepository watchedWordsRepository, WatchedWordsMapper watchedWordsMapper) {
        this.watchedWordsRepository = watchedWordsRepository;
        this.watchedWordsMapper = watchedWordsMapper;
    }

    @Override
    public WatchedWordsDTO save(WatchedWordsDTO watchedWordsDTO) {
        log.debug("Request to save WatchedWords : {}", watchedWordsDTO);
        WatchedWords watchedWords = watchedWordsMapper.toEntity(watchedWordsDTO);
        watchedWords = watchedWordsRepository.save(watchedWords);
        return watchedWordsMapper.toDto(watchedWords);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WatchedWordsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WatchedWords");
        return watchedWordsRepository.findAll(pageable)
            .map(watchedWordsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<WatchedWordsDTO> findOne(Long id) {
        log.debug("Request to get WatchedWords : {}", id);
        return watchedWordsRepository.findById(id)
            .map(watchedWordsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WatchedWords : {}", id);
        watchedWordsRepository.deleteById(id);
    }
}
