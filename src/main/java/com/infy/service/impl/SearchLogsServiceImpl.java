/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.SearchLogsService;
import com.infy.domain.SearchLogs;
import com.infy.repository.SearchLogsRepository;
import com.infy.service.dto.SearchLogsDTO;
import com.infy.service.mapper.SearchLogsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SearchLogs}.
 */
@Service
@Transactional
public class SearchLogsServiceImpl implements SearchLogsService {

    private final Logger log = LoggerFactory.getLogger(SearchLogsServiceImpl.class);

    private final SearchLogsRepository searchLogsRepository;

    private final SearchLogsMapper searchLogsMapper;

    public SearchLogsServiceImpl(SearchLogsRepository searchLogsRepository, SearchLogsMapper searchLogsMapper) {
        this.searchLogsRepository = searchLogsRepository;
        this.searchLogsMapper = searchLogsMapper;
    }

    @Override
    public SearchLogsDTO save(SearchLogsDTO searchLogsDTO) {
        log.debug("Request to save SearchLogs : {}", searchLogsDTO);
        SearchLogs searchLogs = searchLogsMapper.toEntity(searchLogsDTO);
        searchLogs = searchLogsRepository.save(searchLogs);
        return searchLogsMapper.toDto(searchLogs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SearchLogsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SearchLogs");
        return searchLogsRepository.findAll(pageable)
            .map(searchLogsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SearchLogsDTO> findOne(Long id) {
        log.debug("Request to get SearchLogs : {}", id);
        return searchLogsRepository.findById(id)
            .map(searchLogsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SearchLogs : {}", id);
        searchLogsRepository.deleteById(id);
    }
}
