/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicLinksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicLinksDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicLinks}.
 */
@RestController
@RequestMapping("/api")
public class TopicLinksResource {

    private final Logger log = LoggerFactory.getLogger(TopicLinksResource.class);

    private static final String ENTITY_NAME = "topicLinks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicLinksService topicLinksService;

    public TopicLinksResource(TopicLinksService topicLinksService) {
        this.topicLinksService = topicLinksService;
    }

    /**
     * {@code POST  /topic-links} : Create a new topicLinks.
     *
     * @param topicLinksDTO the topicLinksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicLinksDTO, or with status {@code 400 (Bad Request)} if the topicLinks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-links")
    public ResponseEntity<TopicLinksDTO> createTopicLinks(@Valid @RequestBody TopicLinksDTO topicLinksDTO) throws URISyntaxException {
        log.debug("REST request to save TopicLinks : {}", topicLinksDTO);
        if (topicLinksDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicLinks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicLinksDTO result = topicLinksService.save(topicLinksDTO);
        return ResponseEntity.created(new URI("/api/topic-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-links} : Updates an existing topicLinks.
     *
     * @param topicLinksDTO the topicLinksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicLinksDTO,
     * or with status {@code 400 (Bad Request)} if the topicLinksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicLinksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-links")
    public ResponseEntity<TopicLinksDTO> updateTopicLinks(@Valid @RequestBody TopicLinksDTO topicLinksDTO) throws URISyntaxException {
        log.debug("REST request to update TopicLinks : {}", topicLinksDTO);
        if (topicLinksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicLinksDTO result = topicLinksService.save(topicLinksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicLinksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-links} : get all the topicLinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicLinks in body.
     */
    @GetMapping("/topic-links")
    public ResponseEntity<List<TopicLinksDTO>> getAllTopicLinks(Pageable pageable) {
        log.debug("REST request to get a page of TopicLinks");
        Page<TopicLinksDTO> page = topicLinksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-links/:id} : get the "id" topicLinks.
     *
     * @param id the id of the topicLinksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicLinksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-links/{id}")
    public ResponseEntity<TopicLinksDTO> getTopicLinks(@PathVariable Long id) {
        log.debug("REST request to get TopicLinks : {}", id);
        Optional<TopicLinksDTO> topicLinksDTO = topicLinksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicLinksDTO);
    }

    /**
     * {@code DELETE  /topic-links/:id} : delete the "id" topicLinks.
     *
     * @param id the id of the topicLinksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-links/{id}")
    public ResponseEntity<Void> deleteTopicLinks(@PathVariable Long id) {
        log.debug("REST request to delete TopicLinks : {}", id);
        topicLinksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
