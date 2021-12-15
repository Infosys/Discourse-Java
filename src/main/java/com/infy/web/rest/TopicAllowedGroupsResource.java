/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicAllowedGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicAllowedGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicAllowedGroups}.
 */
@RestController
@RequestMapping("/api")
public class TopicAllowedGroupsResource {

    private final Logger log = LoggerFactory.getLogger(TopicAllowedGroupsResource.class);

    private static final String ENTITY_NAME = "topicAllowedGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicAllowedGroupsService topicAllowedGroupsService;

    public TopicAllowedGroupsResource(TopicAllowedGroupsService topicAllowedGroupsService) {
        this.topicAllowedGroupsService = topicAllowedGroupsService;
    }

    /**
     * {@code POST  /topic-allowed-groups} : Create a new topicAllowedGroups.
     *
     * @param topicAllowedGroupsDTO the topicAllowedGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicAllowedGroupsDTO, or with status {@code 400 (Bad Request)} if the topicAllowedGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-allowed-groups")
    public ResponseEntity<TopicAllowedGroupsDTO> createTopicAllowedGroups(@Valid @RequestBody TopicAllowedGroupsDTO topicAllowedGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicAllowedGroups : {}", topicAllowedGroupsDTO);
        if (topicAllowedGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicAllowedGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicAllowedGroupsDTO result = topicAllowedGroupsService.save(topicAllowedGroupsDTO);
        return ResponseEntity.created(new URI("/api/topic-allowed-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-allowed-groups} : Updates an existing topicAllowedGroups.
     *
     * @param topicAllowedGroupsDTO the topicAllowedGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicAllowedGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the topicAllowedGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicAllowedGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-allowed-groups")
    public ResponseEntity<TopicAllowedGroupsDTO> updateTopicAllowedGroups(@Valid @RequestBody TopicAllowedGroupsDTO topicAllowedGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicAllowedGroups : {}", topicAllowedGroupsDTO);
        if (topicAllowedGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicAllowedGroupsDTO result = topicAllowedGroupsService.save(topicAllowedGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicAllowedGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-allowed-groups} : get all the topicAllowedGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicAllowedGroups in body.
     */
    @GetMapping("/topic-allowed-groups")
    public ResponseEntity<List<TopicAllowedGroupsDTO>> getAllTopicAllowedGroups(Pageable pageable) {
        log.debug("REST request to get a page of TopicAllowedGroups");
        Page<TopicAllowedGroupsDTO> page = topicAllowedGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-allowed-groups/:id} : get the "id" topicAllowedGroups.
     *
     * @param id the id of the topicAllowedGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicAllowedGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-allowed-groups/{id}")
    public ResponseEntity<TopicAllowedGroupsDTO> getTopicAllowedGroups(@PathVariable Long id) {
        log.debug("REST request to get TopicAllowedGroups : {}", id);
        Optional<TopicAllowedGroupsDTO> topicAllowedGroupsDTO = topicAllowedGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicAllowedGroupsDTO);
    }

    /**
     * {@code DELETE  /topic-allowed-groups/:id} : delete the "id" topicAllowedGroups.
     *
     * @param id the id of the topicAllowedGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-allowed-groups/{id}")
    public ResponseEntity<Void> deleteTopicAllowedGroups(@PathVariable Long id) {
        log.debug("REST request to delete TopicAllowedGroups : {}", id);
        topicAllowedGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
