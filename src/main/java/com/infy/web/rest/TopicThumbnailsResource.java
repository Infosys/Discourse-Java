/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicThumbnailsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicThumbnailsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicThumbnails}.
 */
@RestController
@RequestMapping("/api")
public class TopicThumbnailsResource {

    private final Logger log = LoggerFactory.getLogger(TopicThumbnailsResource.class);

    private static final String ENTITY_NAME = "topicThumbnails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicThumbnailsService topicThumbnailsService;

    public TopicThumbnailsResource(TopicThumbnailsService topicThumbnailsService) {
        this.topicThumbnailsService = topicThumbnailsService;
    }

    /**
     * {@code POST  /topic-thumbnails} : Create a new topicThumbnails.
     *
     * @param topicThumbnailsDTO the topicThumbnailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicThumbnailsDTO, or with status {@code 400 (Bad Request)} if the topicThumbnails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-thumbnails")
    public ResponseEntity<TopicThumbnailsDTO> createTopicThumbnails(@Valid @RequestBody TopicThumbnailsDTO topicThumbnailsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicThumbnails : {}", topicThumbnailsDTO);
        if (topicThumbnailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicThumbnails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicThumbnailsDTO result = topicThumbnailsService.save(topicThumbnailsDTO);
        return ResponseEntity.created(new URI("/api/topic-thumbnails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-thumbnails} : Updates an existing topicThumbnails.
     *
     * @param topicThumbnailsDTO the topicThumbnailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicThumbnailsDTO,
     * or with status {@code 400 (Bad Request)} if the topicThumbnailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicThumbnailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-thumbnails")
    public ResponseEntity<TopicThumbnailsDTO> updateTopicThumbnails(@Valid @RequestBody TopicThumbnailsDTO topicThumbnailsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicThumbnails : {}", topicThumbnailsDTO);
        if (topicThumbnailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicThumbnailsDTO result = topicThumbnailsService.save(topicThumbnailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicThumbnailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-thumbnails} : get all the topicThumbnails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicThumbnails in body.
     */
    @GetMapping("/topic-thumbnails")
    public ResponseEntity<List<TopicThumbnailsDTO>> getAllTopicThumbnails(Pageable pageable) {
        log.debug("REST request to get a page of TopicThumbnails");
        Page<TopicThumbnailsDTO> page = topicThumbnailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-thumbnails/:id} : get the "id" topicThumbnails.
     *
     * @param id the id of the topicThumbnailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicThumbnailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-thumbnails/{id}")
    public ResponseEntity<TopicThumbnailsDTO> getTopicThumbnails(@PathVariable Long id) {
        log.debug("REST request to get TopicThumbnails : {}", id);
        Optional<TopicThumbnailsDTO> topicThumbnailsDTO = topicThumbnailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicThumbnailsDTO);
    }

    /**
     * {@code DELETE  /topic-thumbnails/:id} : delete the "id" topicThumbnails.
     *
     * @param id the id of the topicThumbnailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-thumbnails/{id}")
    public ResponseEntity<Void> deleteTopicThumbnails(@PathVariable Long id) {
        log.debug("REST request to delete TopicThumbnails : {}", id);
        topicThumbnailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
