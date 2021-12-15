/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TagUsersService;
import com.infy.domain.TagUsers;
import com.infy.repository.TagUsersRepository;
import com.infy.service.dto.TagUsersDTO;
import com.infy.service.mapper.TagUsersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TagUsers}.
 */
@Service
@Transactional
public class TagUsersServiceImpl implements TagUsersService {

    private final Logger log = LoggerFactory.getLogger(TagUsersServiceImpl.class);

    private final TagUsersRepository tagUsersRepository;

    private final TagUsersMapper tagUsersMapper;

    public TagUsersServiceImpl(TagUsersRepository tagUsersRepository, TagUsersMapper tagUsersMapper) {
        this.tagUsersRepository = tagUsersRepository;
        this.tagUsersMapper = tagUsersMapper;
    }

    @Override
    public TagUsersDTO save(TagUsersDTO tagUsersDTO) {
        log.debug("Request to save TagUsers : {}", tagUsersDTO);
        TagUsers tagUsers = tagUsersMapper.toEntity(tagUsersDTO);
        tagUsers = tagUsersRepository.save(tagUsers);
        return tagUsersMapper.toDto(tagUsers);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagUsersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TagUsers");
        return tagUsersRepository.findAll(pageable)
            .map(tagUsersMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TagUsersDTO> findOne(Long id) {
        log.debug("Request to get TagUsers : {}", id);
        return tagUsersRepository.findById(id)
            .map(tagUsersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TagUsers : {}", id);
        tagUsersRepository.deleteById(id);
    }
}
