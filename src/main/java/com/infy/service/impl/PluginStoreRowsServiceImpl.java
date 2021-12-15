/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PluginStoreRowsService;
import com.infy.domain.PluginStoreRows;
import com.infy.repository.PluginStoreRowsRepository;
import com.infy.service.dto.PluginStoreRowsDTO;
import com.infy.service.mapper.PluginStoreRowsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PluginStoreRows}.
 */
@Service
@Transactional
public class PluginStoreRowsServiceImpl implements PluginStoreRowsService {

    private final Logger log = LoggerFactory.getLogger(PluginStoreRowsServiceImpl.class);

    private final PluginStoreRowsRepository pluginStoreRowsRepository;

    private final PluginStoreRowsMapper pluginStoreRowsMapper;

    public PluginStoreRowsServiceImpl(PluginStoreRowsRepository pluginStoreRowsRepository, PluginStoreRowsMapper pluginStoreRowsMapper) {
        this.pluginStoreRowsRepository = pluginStoreRowsRepository;
        this.pluginStoreRowsMapper = pluginStoreRowsMapper;
    }

    @Override
    public PluginStoreRowsDTO save(PluginStoreRowsDTO pluginStoreRowsDTO) {
        log.debug("Request to save PluginStoreRows : {}", pluginStoreRowsDTO);
        PluginStoreRows pluginStoreRows = pluginStoreRowsMapper.toEntity(pluginStoreRowsDTO);
        pluginStoreRows = pluginStoreRowsRepository.save(pluginStoreRows);
        return pluginStoreRowsMapper.toDto(pluginStoreRows);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PluginStoreRowsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PluginStoreRows");
        return pluginStoreRowsRepository.findAll(pageable)
            .map(pluginStoreRowsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PluginStoreRowsDTO> findOne(Long id) {
        log.debug("Request to get PluginStoreRows : {}", id);
        return pluginStoreRowsRepository.findById(id)
            .map(pluginStoreRowsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PluginStoreRows : {}", id);
        pluginStoreRowsRepository.deleteById(id);
    }
}
