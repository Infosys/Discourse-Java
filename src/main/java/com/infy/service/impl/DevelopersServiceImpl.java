/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.DevelopersService;
import com.infy.domain.Developers;
import com.infy.repository.DevelopersRepository;
import com.infy.service.dto.DevelopersDTO;
import com.infy.service.mapper.DevelopersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Developers}.
 */
@Service
@Transactional
public class DevelopersServiceImpl implements DevelopersService {

    private final Logger log = LoggerFactory.getLogger(DevelopersServiceImpl.class);

    private final DevelopersRepository developersRepository;

    private final DevelopersMapper developersMapper;

    public DevelopersServiceImpl(DevelopersRepository developersRepository, DevelopersMapper developersMapper) {
        this.developersRepository = developersRepository;
        this.developersMapper = developersMapper;
    }

    @Override
    public DevelopersDTO save(DevelopersDTO developersDTO) {
        log.debug("Request to save Developers : {}", developersDTO);
        Developers developers = developersMapper.toEntity(developersDTO);
        developers = developersRepository.save(developers);
        return developersMapper.toDto(developers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DevelopersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Developers");
        return developersRepository.findAll(pageable)
            .map(developersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DevelopersDTO> findOne(Long id) {
        log.debug("Request to get Developers : {}", id);
        return developersRepository.findById(id)
            .map(developersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Developers : {}", id);
        developersRepository.deleteById(id);
    }
}
