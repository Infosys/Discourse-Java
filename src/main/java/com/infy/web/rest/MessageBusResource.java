/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.MessageBusService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.MessageBusDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.MessageBus}.
 */
@RestController
@RequestMapping("/api")
public class MessageBusResource {

    private final Logger log = LoggerFactory.getLogger(MessageBusResource.class);

    private static final String ENTITY_NAME = "messageBus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageBusService messageBusService;

    public MessageBusResource(MessageBusService messageBusService) {
        this.messageBusService = messageBusService;
    }

    /**
     * {@code POST  /message-buses} : Create a new messageBus.
     *
     * @param messageBusDTO the messageBusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageBusDTO, or with status {@code 400 (Bad Request)} if the messageBus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-buses")
    public ResponseEntity<MessageBusDTO> createMessageBus(@RequestBody MessageBusDTO messageBusDTO) throws URISyntaxException {
        log.debug("REST request to save MessageBus : {}", messageBusDTO);
        if (messageBusDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageBus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageBusDTO result = messageBusService.save(messageBusDTO);
        return ResponseEntity.created(new URI("/api/message-buses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-buses} : Updates an existing messageBus.
     *
     * @param messageBusDTO the messageBusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageBusDTO,
     * or with status {@code 400 (Bad Request)} if the messageBusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageBusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-buses")
    public ResponseEntity<MessageBusDTO> updateMessageBus(@RequestBody MessageBusDTO messageBusDTO) throws URISyntaxException {
        log.debug("REST request to update MessageBus : {}", messageBusDTO);
        if (messageBusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageBusDTO result = messageBusService.save(messageBusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageBusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-buses} : get all the messageBuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageBuses in body.
     */
    @GetMapping("/message-buses")
    public ResponseEntity<List<MessageBusDTO>> getAllMessageBuses(Pageable pageable) {
        log.debug("REST request to get a page of MessageBuses");
        Page<MessageBusDTO> page = messageBusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-buses/:id} : get the "id" messageBus.
     *
     * @param id the id of the messageBusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageBusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-buses/{id}")
    public ResponseEntity<MessageBusDTO> getMessageBus(@PathVariable Long id) {
        log.debug("REST request to get MessageBus : {}", id);
        Optional<MessageBusDTO> messageBusDTO = messageBusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageBusDTO);
    }

    /**
     * {@code DELETE  /message-buses/:id} : delete the "id" messageBus.
     *
     * @param id the id of the messageBusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-buses/{id}")
    public ResponseEntity<Void> deleteMessageBus(@PathVariable Long id) {
        log.debug("REST request to delete MessageBus : {}", id);
        messageBusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
