/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicLinkClicksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicLinkClicksDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicLinkClicks}.
 */
@RestController
@RequestMapping("/api")
public class TopicLinkClicksResource {

    private final Logger log = LoggerFactory.getLogger(TopicLinkClicksResource.class);

    private static final String ENTITY_NAME = "topicLinkClicks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicLinkClicksService topicLinkClicksService;

    public TopicLinkClicksResource(TopicLinkClicksService topicLinkClicksService) {
        this.topicLinkClicksService = topicLinkClicksService;
    }

    /**
     * {@code POST  /topic-link-clicks} : Create a new topicLinkClicks.
     *
     * @param topicLinkClicksDTO the topicLinkClicksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicLinkClicksDTO, or with status {@code 400 (Bad Request)} if the topicLinkClicks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-link-clicks")
    public ResponseEntity<TopicLinkClicksDTO> createTopicLinkClicks(@Valid @RequestBody TopicLinkClicksDTO topicLinkClicksDTO) throws URISyntaxException {
        log.debug("REST request to save TopicLinkClicks : {}", topicLinkClicksDTO);
        if (topicLinkClicksDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicLinkClicks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicLinkClicksDTO result = topicLinkClicksService.save(topicLinkClicksDTO);
        return ResponseEntity.created(new URI("/api/topic-link-clicks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-link-clicks} : Updates an existing topicLinkClicks.
     *
     * @param topicLinkClicksDTO the topicLinkClicksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicLinkClicksDTO,
     * or with status {@code 400 (Bad Request)} if the topicLinkClicksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicLinkClicksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-link-clicks")
    public ResponseEntity<TopicLinkClicksDTO> updateTopicLinkClicks(@Valid @RequestBody TopicLinkClicksDTO topicLinkClicksDTO) throws URISyntaxException {
        log.debug("REST request to update TopicLinkClicks : {}", topicLinkClicksDTO);
        if (topicLinkClicksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicLinkClicksDTO result = topicLinkClicksService.save(topicLinkClicksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicLinkClicksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-link-clicks} : get all the topicLinkClicks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicLinkClicks in body.
     */
    @GetMapping("/topic-link-clicks")
    public ResponseEntity<List<TopicLinkClicksDTO>> getAllTopicLinkClicks(Pageable pageable) {
        log.debug("REST request to get a page of TopicLinkClicks");
        Page<TopicLinkClicksDTO> page = topicLinkClicksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-link-clicks/:id} : get the "id" topicLinkClicks.
     *
     * @param id the id of the topicLinkClicksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicLinkClicksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-link-clicks/{id}")
    public ResponseEntity<TopicLinkClicksDTO> getTopicLinkClicks(@PathVariable Long id) {
        log.debug("REST request to get TopicLinkClicks : {}", id);
        Optional<TopicLinkClicksDTO> topicLinkClicksDTO = topicLinkClicksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicLinkClicksDTO);
    }

    /**
     * {@code DELETE  /topic-link-clicks/:id} : delete the "id" topicLinkClicks.
     *
     * @param id the id of the topicLinkClicksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-link-clicks/{id}")
    public ResponseEntity<Void> deleteTopicLinkClicks(@PathVariable Long id) {
        log.debug("REST request to delete TopicLinkClicks : {}", id);
        topicLinkClicksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
