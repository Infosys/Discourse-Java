/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicAllowedUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicAllowedUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicAllowedUsers}.
 */
@RestController
@RequestMapping("/api")
public class TopicAllowedUsersResource {

    private final Logger log = LoggerFactory.getLogger(TopicAllowedUsersResource.class);

    private static final String ENTITY_NAME = "topicAllowedUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicAllowedUsersService topicAllowedUsersService;

    public TopicAllowedUsersResource(TopicAllowedUsersService topicAllowedUsersService) {
        this.topicAllowedUsersService = topicAllowedUsersService;
    }

    /**
     * {@code POST  /topic-allowed-users} : Create a new topicAllowedUsers.
     *
     * @param topicAllowedUsersDTO the topicAllowedUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicAllowedUsersDTO, or with status {@code 400 (Bad Request)} if the topicAllowedUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-allowed-users")
    public ResponseEntity<TopicAllowedUsersDTO> createTopicAllowedUsers(@Valid @RequestBody TopicAllowedUsersDTO topicAllowedUsersDTO) throws URISyntaxException {
        log.debug("REST request to save TopicAllowedUsers : {}", topicAllowedUsersDTO);
        if (topicAllowedUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicAllowedUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicAllowedUsersDTO result = topicAllowedUsersService.save(topicAllowedUsersDTO);
        return ResponseEntity.created(new URI("/api/topic-allowed-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-allowed-users} : Updates an existing topicAllowedUsers.
     *
     * @param topicAllowedUsersDTO the topicAllowedUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicAllowedUsersDTO,
     * or with status {@code 400 (Bad Request)} if the topicAllowedUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicAllowedUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-allowed-users")
    public ResponseEntity<TopicAllowedUsersDTO> updateTopicAllowedUsers(@Valid @RequestBody TopicAllowedUsersDTO topicAllowedUsersDTO) throws URISyntaxException {
        log.debug("REST request to update TopicAllowedUsers : {}", topicAllowedUsersDTO);
        if (topicAllowedUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicAllowedUsersDTO result = topicAllowedUsersService.save(topicAllowedUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicAllowedUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-allowed-users} : get all the topicAllowedUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicAllowedUsers in body.
     */
    @GetMapping("/topic-allowed-users")
    public ResponseEntity<List<TopicAllowedUsersDTO>> getAllTopicAllowedUsers(Pageable pageable) {
        log.debug("REST request to get a page of TopicAllowedUsers");
        Page<TopicAllowedUsersDTO> page = topicAllowedUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-allowed-users/:id} : get the "id" topicAllowedUsers.
     *
     * @param id the id of the topicAllowedUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicAllowedUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-allowed-users/{id}")
    public ResponseEntity<TopicAllowedUsersDTO> getTopicAllowedUsers(@PathVariable Long id) {
        log.debug("REST request to get TopicAllowedUsers : {}", id);
        Optional<TopicAllowedUsersDTO> topicAllowedUsersDTO = topicAllowedUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicAllowedUsersDTO);
    }

    /**
     * {@code DELETE  /topic-allowed-users/:id} : delete the "id" topicAllowedUsers.
     *
     * @param id the id of the topicAllowedUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-allowed-users/{id}")
    public ResponseEntity<Void> deleteTopicAllowedUsers(@PathVariable Long id) {
        log.debug("REST request to delete TopicAllowedUsers : {}", id);
        topicAllowedUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
