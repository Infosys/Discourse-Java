/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.InvitesService;
import com.infy.domain.Invites;
import com.infy.repository.InvitesRepository;
import com.infy.service.dto.InvitesDTO;
import com.infy.service.mapper.InvitesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Invites}.
 */
@Service
@Transactional
public class InvitesServiceImpl implements InvitesService {

    private final Logger log = LoggerFactory.getLogger(InvitesServiceImpl.class);

    private final InvitesRepository invitesRepository;

    private final InvitesMapper invitesMapper;

    public InvitesServiceImpl(InvitesRepository invitesRepository, InvitesMapper invitesMapper) {
        this.invitesRepository = invitesRepository;
        this.invitesMapper = invitesMapper;
    }

    @Override
    public InvitesDTO save(InvitesDTO invitesDTO) {
        log.debug("Request to save Invites : {}", invitesDTO);
        Invites invites = invitesMapper.toEntity(invitesDTO);
        invites = invitesRepository.save(invites);
        return invitesMapper.toDto(invites);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Invites");
        return invitesRepository.findAll(pageable)
            .map(invitesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InvitesDTO> findOne(Long id) {
        log.debug("Request to get Invites : {}", id);
        return invitesRepository.findById(id)
            .map(invitesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Invites : {}", id);
        invitesRepository.deleteById(id);
    }
}
