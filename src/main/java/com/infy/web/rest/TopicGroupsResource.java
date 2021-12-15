/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicGroups}.
 */
@RestController
@RequestMapping("/api")
public class TopicGroupsResource {

    private final Logger log = LoggerFactory.getLogger(TopicGroupsResource.class);

    private static final String ENTITY_NAME = "topicGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicGroupsService topicGroupsService;

    public TopicGroupsResource(TopicGroupsService topicGroupsService) {
        this.topicGroupsService = topicGroupsService;
    }

    /**
     * {@code POST  /topic-groups} : Create a new topicGroups.
     *
     * @param topicGroupsDTO the topicGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicGroupsDTO, or with status {@code 400 (Bad Request)} if the topicGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-groups")
    public ResponseEntity<TopicGroupsDTO> createTopicGroups(@Valid @RequestBody TopicGroupsDTO topicGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicGroups : {}", topicGroupsDTO);
        if (topicGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicGroupsDTO result = topicGroupsService.save(topicGroupsDTO);
        return ResponseEntity.created(new URI("/api/topic-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-groups} : Updates an existing topicGroups.
     *
     * @param topicGroupsDTO the topicGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the topicGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-groups")
    public ResponseEntity<TopicGroupsDTO> updateTopicGroups(@Valid @RequestBody TopicGroupsDTO topicGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicGroups : {}", topicGroupsDTO);
        if (topicGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicGroupsDTO result = topicGroupsService.save(topicGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-groups} : get all the topicGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicGroups in body.
     */
    @GetMapping("/topic-groups")
    public ResponseEntity<List<TopicGroupsDTO>> getAllTopicGroups(Pageable pageable) {
        log.debug("REST request to get a page of TopicGroups");
        Page<TopicGroupsDTO> page = topicGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-groups/:id} : get the "id" topicGroups.
     *
     * @param id the id of the topicGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-groups/{id}")
    public ResponseEntity<TopicGroupsDTO> getTopicGroups(@PathVariable Long id) {
        log.debug("REST request to get TopicGroups : {}", id);
        Optional<TopicGroupsDTO> topicGroupsDTO = topicGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicGroupsDTO);
    }

    /**
     * {@code DELETE  /topic-groups/:id} : delete the "id" topicGroups.
     *
     * @param id the id of the topicGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-groups/{id}")
    public ResponseEntity<Void> deleteTopicGroups(@PathVariable Long id) {
        log.debug("REST request to delete TopicGroups : {}", id);
        topicGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
