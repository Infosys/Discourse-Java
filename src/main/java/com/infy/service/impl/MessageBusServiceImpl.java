/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.MessageBusService;
import com.infy.domain.MessageBus;
import com.infy.repository.MessageBusRepository;
import com.infy.service.dto.MessageBusDTO;
import com.infy.service.mapper.MessageBusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MessageBus}.
 */
@Service
@Transactional
public class MessageBusServiceImpl implements MessageBusService {

    private final Logger log = LoggerFactory.getLogger(MessageBusServiceImpl.class);

    private final MessageBusRepository messageBusRepository;

    private final MessageBusMapper messageBusMapper;

    public MessageBusServiceImpl(MessageBusRepository messageBusRepository, MessageBusMapper messageBusMapper) {
        this.messageBusRepository = messageBusRepository;
        this.messageBusMapper = messageBusMapper;
    }

    @Override
    public MessageBusDTO save(MessageBusDTO messageBusDTO) {
        log.debug("Request to save MessageBus : {}", messageBusDTO);
        MessageBus messageBus = messageBusMapper.toEntity(messageBusDTO);
        messageBus = messageBusRepository.save(messageBus);
        return messageBusMapper.toDto(messageBus);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageBusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageBuses");
        return messageBusRepository.findAll(pageable)
            .map(messageBusMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<MessageBusDTO> findOne(Long id) {
        log.debug("Request to get MessageBus : {}", id);
        return messageBusRepository.findById(id)
            .map(messageBusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageBus : {}", id);
        messageBusRepository.deleteById(id);
    }
}
