/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.ScreenedIpAddressesService;
import com.infy.domain.ScreenedIpAddresses;
import com.infy.repository.ScreenedIpAddressesRepository;
import com.infy.service.dto.ScreenedIpAddressesDTO;
import com.infy.service.mapper.ScreenedIpAddressesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScreenedIpAddresses}.
 */
@Service
@Transactional
public class ScreenedIpAddressesServiceImpl implements ScreenedIpAddressesService {

    private final Logger log = LoggerFactory.getLogger(ScreenedIpAddressesServiceImpl.class);

    private final ScreenedIpAddressesRepository screenedIpAddressesRepository;

    private final ScreenedIpAddressesMapper screenedIpAddressesMapper;

    public ScreenedIpAddressesServiceImpl(ScreenedIpAddressesRepository screenedIpAddressesRepository, ScreenedIpAddressesMapper screenedIpAddressesMapper) {
        this.screenedIpAddressesRepository = screenedIpAddressesRepository;
        this.screenedIpAddressesMapper = screenedIpAddressesMapper;
    }

    @Override
    public ScreenedIpAddressesDTO save(ScreenedIpAddressesDTO screenedIpAddressesDTO) {
        log.debug("Request to save ScreenedIpAddresses : {}", screenedIpAddressesDTO);
        ScreenedIpAddresses screenedIpAddresses = screenedIpAddressesMapper.toEntity(screenedIpAddressesDTO);
        screenedIpAddresses = screenedIpAddressesRepository.save(screenedIpAddresses);
        return screenedIpAddressesMapper.toDto(screenedIpAddresses);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ScreenedIpAddressesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScreenedIpAddresses");
        return screenedIpAddressesRepository.findAll(pageable)
            .map(screenedIpAddressesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ScreenedIpAddressesDTO> findOne(Long id) {
        log.debug("Request to get ScreenedIpAddresses : {}", id);
        return screenedIpAddressesRepository.findById(id)
            .map(screenedIpAddressesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScreenedIpAddresses : {}", id);
        screenedIpAddressesRepository.deleteById(id);
    }
}
