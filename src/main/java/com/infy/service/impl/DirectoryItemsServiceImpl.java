/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DirectoryItemsService;
import com.infy.domain.DirectoryItems;
import com.infy.repository.DirectoryItemsRepository;
import com.infy.service.dto.DirectoryItemsDTO;
import com.infy.service.mapper.DirectoryItemsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DirectoryItems}.
 */
@Service
@Transactional
public class DirectoryItemsServiceImpl implements DirectoryItemsService {

    private final Logger log = LoggerFactory.getLogger(DirectoryItemsServiceImpl.class);

    private final DirectoryItemsRepository directoryItemsRepository;

    private final DirectoryItemsMapper directoryItemsMapper;

    public DirectoryItemsServiceImpl(DirectoryItemsRepository directoryItemsRepository, DirectoryItemsMapper directoryItemsMapper) {
        this.directoryItemsRepository = directoryItemsRepository;
        this.directoryItemsMapper = directoryItemsMapper;
    }

    @Override
    public DirectoryItemsDTO save(DirectoryItemsDTO directoryItemsDTO) {
        log.debug("Request to save DirectoryItems : {}", directoryItemsDTO);
        DirectoryItems directoryItems = directoryItemsMapper.toEntity(directoryItemsDTO);
        directoryItems = directoryItemsRepository.save(directoryItems);
        return directoryItemsMapper.toDto(directoryItems);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DirectoryItemsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DirectoryItems");
        return directoryItemsRepository.findAll(pageable)
            .map(directoryItemsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DirectoryItemsDTO> findOne(Long id) {
        log.debug("Request to get DirectoryItems : {}", id);
        return directoryItemsRepository.findById(id)
            .map(directoryItemsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DirectoryItems : {}", id);
        directoryItemsRepository.deleteById(id);
    }
}
