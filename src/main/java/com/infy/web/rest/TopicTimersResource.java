/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicTimersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicTimersDTO;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.TopicTimers}.
 */
@RestController
@RequestMapping("/api")
public class TopicTimersResource {

    private final Logger log = LoggerFactory.getLogger(TopicTimersResource.class);

    private static final String ENTITY_NAME = "topicTimers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicTimersService topicTimersService;

    public TopicTimersResource(TopicTimersService topicTimersService) {
        this.topicTimersService = topicTimersService;
    }

    /**
     * {@code POST  /topic-timers} : Create a new topicTimers.
     *
     * @param topicTimersDTO the topicTimersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicTimersDTO, or with status {@code 400 (Bad Request)} if the topicTimers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-timers")
    public ResponseEntity<TopicTimersDTO> createTopicTimers(@Valid @RequestBody TopicTimersDTO topicTimersDTO) throws URISyntaxException {
        log.debug("REST request to save TopicTimers : {}", topicTimersDTO);
        if (topicTimersDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicTimers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicTimersDTO result = topicTimersService.save(topicTimersDTO);
        return ResponseEntity.created(new URI("/api/topic-timers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-timers} : Updates an existing topicTimers.
     *
     * @param topicTimersDTO the topicTimersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicTimersDTO,
     * or with status {@code 400 (Bad Request)} if the topicTimersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicTimersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-timers")
    public ResponseEntity<TopicTimersDTO> updateTopicTimers(@Valid @RequestBody TopicTimersDTO topicTimersDTO) throws URISyntaxException {
        log.debug("REST request to update TopicTimers : {}", topicTimersDTO);
        if (topicTimersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicTimersDTO result = topicTimersService.save(topicTimersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicTimersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-timers} : get all the topicTimers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicTimers in body.
     */
    @GetMapping("/topic-timers")
    public ResponseEntity<List<TopicTimersDTO>> getAllTopicTimers(Pageable pageable) {
        log.debug("REST request to get a page of TopicTimers");
        Page<TopicTimersDTO> page = topicTimersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-timers/:id} : get the "id" topicTimers.
     *
     * @param id the id of the topicTimersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicTimersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-timers/{id}")
    public ResponseEntity<TopicTimersDTO> getTopicTimers(@PathVariable Long id) {
        log.debug("REST request to get TopicTimers : {}", id);
        Optional<TopicTimersDTO> topicTimersDTO = topicTimersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicTimersDTO);
    }

    /**
     * {@code DELETE  /topic-timers/:id} : delete the "id" topicTimers.
     *
     * @param id the id of the topicTimersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-timers/{id}")
    public ResponseEntity<Void> deleteTopicTimers(@PathVariable Long id) {
        log.debug("REST request to delete TopicTimers : {}", id);
        topicTimersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
