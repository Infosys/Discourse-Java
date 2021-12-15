/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicViewsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicViewsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicViews}.
 */
@RestController
@RequestMapping("/api")
public class TopicViewsResource {

    private final Logger log = LoggerFactory.getLogger(TopicViewsResource.class);

    private static final String ENTITY_NAME = "topicViews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicViewsService topicViewsService;

    public TopicViewsResource(TopicViewsService topicViewsService) {
        this.topicViewsService = topicViewsService;
    }

    /**
     * {@code POST  /topic-views} : Create a new topicViews.
     *
     * @param topicViewsDTO the topicViewsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicViewsDTO, or with status {@code 400 (Bad Request)} if the topicViews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-views")
    public ResponseEntity<TopicViewsDTO> createTopicViews(@Valid @RequestBody TopicViewsDTO topicViewsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicViews : {}", topicViewsDTO);
        if (topicViewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicViews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicViewsDTO result = topicViewsService.save(topicViewsDTO);
        return ResponseEntity.created(new URI("/api/topic-views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-views} : Updates an existing topicViews.
     *
     * @param topicViewsDTO the topicViewsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicViewsDTO,
     * or with status {@code 400 (Bad Request)} if the topicViewsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicViewsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-views")
    public ResponseEntity<TopicViewsDTO> updateTopicViews(@Valid @RequestBody TopicViewsDTO topicViewsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicViews : {}", topicViewsDTO);
        if (topicViewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicViewsDTO result = topicViewsService.save(topicViewsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicViewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-views} : get all the topicViews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicViews in body.
     */
    @GetMapping("/topic-views")
    public ResponseEntity<List<TopicViewsDTO>> getAllTopicViews(Pageable pageable) {
        log.debug("REST request to get a page of TopicViews");
        Page<TopicViewsDTO> page = topicViewsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-views/:id} : get the "id" topicViews.
     *
     * @param id the id of the topicViewsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicViewsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-views/{id}")
    public ResponseEntity<TopicViewsDTO> getTopicViews(@PathVariable Long id) {
        log.debug("REST request to get TopicViews : {}", id);
        Optional<TopicViewsDTO> topicViewsDTO = topicViewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicViewsDTO);
    }

    /**
     * {@code DELETE  /topic-views/:id} : delete the "id" topicViews.
     *
     * @param id the id of the topicViewsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-views/{id}")
    public ResponseEntity<Void> deleteTopicViews(@PathVariable Long id) {
        log.debug("REST request to delete TopicViews : {}", id);
        topicViewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
