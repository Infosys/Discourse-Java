/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicUsers}.
 */
@RestController
@RequestMapping("/api")
public class TopicUsersResource {

    private final Logger log = LoggerFactory.getLogger(TopicUsersResource.class);

    private static final String ENTITY_NAME = "topicUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicUsersService topicUsersService;

    public TopicUsersResource(TopicUsersService topicUsersService) {
        this.topicUsersService = topicUsersService;
    }

    /**
     * {@code POST  /topic-users} : Create a new topicUsers.
     *
     * @param topicUsersDTO the topicUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicUsersDTO, or with status {@code 400 (Bad Request)} if the topicUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-users")
    public ResponseEntity<TopicUsersDTO> createTopicUsers(@Valid @RequestBody TopicUsersDTO topicUsersDTO) throws URISyntaxException {
        log.debug("REST request to save TopicUsers : {}", topicUsersDTO);
        if (topicUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicUsersDTO result = topicUsersService.save(topicUsersDTO);
        return ResponseEntity.created(new URI("/api/topic-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-users} : Updates an existing topicUsers.
     *
     * @param topicUsersDTO the topicUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicUsersDTO,
     * or with status {@code 400 (Bad Request)} if the topicUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-users")
    public ResponseEntity<TopicUsersDTO> updateTopicUsers(@Valid @RequestBody TopicUsersDTO topicUsersDTO) throws URISyntaxException {
        log.debug("REST request to update TopicUsers : {}", topicUsersDTO);
        if (topicUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicUsersDTO result = topicUsersService.save(topicUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-users} : get all the topicUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicUsers in body.
     */
    @GetMapping("/topic-users")
    public ResponseEntity<List<TopicUsersDTO>> getAllTopicUsers(Pageable pageable) {
        log.debug("REST request to get a page of TopicUsers");
        Page<TopicUsersDTO> page = topicUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-users/:id} : get the "id" topicUsers.
     *
     * @param id the id of the topicUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-users/{id}")
    public ResponseEntity<TopicUsersDTO> getTopicUsers(@PathVariable Long id) {
        log.debug("REST request to get TopicUsers : {}", id);
        Optional<TopicUsersDTO> topicUsersDTO = topicUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicUsersDTO);
    }

    /**
     * {@code DELETE  /topic-users/:id} : delete the "id" topicUsers.
     *
     * @param id the id of the topicUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-users/{id}")
    public ResponseEntity<Void> deleteTopicUsers(@PathVariable Long id) {
        log.debug("REST request to delete TopicUsers : {}", id);
        topicUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
