/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.InvitedGroupsService;
import com.infy.domain.InvitedGroups;
import com.infy.repository.InvitedGroupsRepository;
import com.infy.service.dto.InvitedGroupsDTO;
import com.infy.service.mapper.InvitedGroupsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InvitedGroups}.
 */
@Service
@Transactional
public class InvitedGroupsServiceImpl implements InvitedGroupsService {

    private final Logger log = LoggerFactory.getLogger(InvitedGroupsServiceImpl.class);

    private final InvitedGroupsRepository invitedGroupsRepository;

    private final InvitedGroupsMapper invitedGroupsMapper;

    public InvitedGroupsServiceImpl(InvitedGroupsRepository invitedGroupsRepository, InvitedGroupsMapper invitedGroupsMapper) {
        this.invitedGroupsRepository = invitedGroupsRepository;
        this.invitedGroupsMapper = invitedGroupsMapper;
    }

    @Override
    public InvitedGroupsDTO save(InvitedGroupsDTO invitedGroupsDTO) {
        log.debug("Request to save InvitedGroups : {}", invitedGroupsDTO);
        InvitedGroups invitedGroups = invitedGroupsMapper.toEntity(invitedGroupsDTO);
        invitedGroups = invitedGroupsRepository.save(invitedGroups);
        return invitedGroupsMapper.toDto(invitedGroups);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InvitedGroupsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InvitedGroups");
        return invitedGroupsRepository.findAll(pageable)
            .map(invitedGroupsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InvitedGroupsDTO> findOne(Long id) {
        log.debug("Request to get InvitedGroups : {}", id);
        return invitedGroupsRepository.findById(id)
            .map(invitedGroupsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvitedGroups : {}", id);
        invitedGroupsRepository.deleteById(id);
    }
}
