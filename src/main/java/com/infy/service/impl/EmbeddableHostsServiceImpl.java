/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.EmbeddableHostsService;
import com.infy.domain.EmbeddableHosts;
import com.infy.repository.EmbeddableHostsRepository;
import com.infy.service.dto.EmbeddableHostsDTO;
import com.infy.service.mapper.EmbeddableHostsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmbeddableHosts}.
 */
@Service
@Transactional
public class EmbeddableHostsServiceImpl implements EmbeddableHostsService {

    private final Logger log = LoggerFactory.getLogger(EmbeddableHostsServiceImpl.class);

    private final EmbeddableHostsRepository embeddableHostsRepository;

    private final EmbeddableHostsMapper embeddableHostsMapper;

    public EmbeddableHostsServiceImpl(EmbeddableHostsRepository embeddableHostsRepository, EmbeddableHostsMapper embeddableHostsMapper) {
        this.embeddableHostsRepository = embeddableHostsRepository;
        this.embeddableHostsMapper = embeddableHostsMapper;
    }

    @Override
    public EmbeddableHostsDTO save(EmbeddableHostsDTO embeddableHostsDTO) {
        log.debug("Request to save EmbeddableHosts : {}", embeddableHostsDTO);
        EmbeddableHosts embeddableHosts = embeddableHostsMapper.toEntity(embeddableHostsDTO);
        embeddableHosts = embeddableHostsRepository.save(embeddableHosts);
        return embeddableHostsMapper.toDto(embeddableHosts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmbeddableHostsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmbeddableHosts");
        return embeddableHostsRepository.findAll(pageable)
            .map(embeddableHostsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EmbeddableHostsDTO> findOne(Long id) {
        log.debug("Request to get EmbeddableHosts : {}", id);
        return embeddableHostsRepository.findById(id)
            .map(embeddableHostsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmbeddableHosts : {}", id);
        embeddableHostsRepository.deleteById(id);
    }
}
