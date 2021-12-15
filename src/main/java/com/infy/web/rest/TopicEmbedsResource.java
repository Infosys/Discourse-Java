/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicEmbedsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicEmbedsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicEmbeds}.
 */
@RestController
@RequestMapping("/api")
public class TopicEmbedsResource {

    private final Logger log = LoggerFactory.getLogger(TopicEmbedsResource.class);

    private static final String ENTITY_NAME = "topicEmbeds";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicEmbedsService topicEmbedsService;

    public TopicEmbedsResource(TopicEmbedsService topicEmbedsService) {
        this.topicEmbedsService = topicEmbedsService;
    }

    /**
     * {@code POST  /topic-embeds} : Create a new topicEmbeds.
     *
     * @param topicEmbedsDTO the topicEmbedsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicEmbedsDTO, or with status {@code 400 (Bad Request)} if the topicEmbeds has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-embeds")
    public ResponseEntity<TopicEmbedsDTO> createTopicEmbeds(@Valid @RequestBody TopicEmbedsDTO topicEmbedsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicEmbeds : {}", topicEmbedsDTO);
        if (topicEmbedsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicEmbeds cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicEmbedsDTO result = topicEmbedsService.save(topicEmbedsDTO);
        return ResponseEntity.created(new URI("/api/topic-embeds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-embeds} : Updates an existing topicEmbeds.
     *
     * @param topicEmbedsDTO the topicEmbedsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicEmbedsDTO,
     * or with status {@code 400 (Bad Request)} if the topicEmbedsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicEmbedsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-embeds")
    public ResponseEntity<TopicEmbedsDTO> updateTopicEmbeds(@Valid @RequestBody TopicEmbedsDTO topicEmbedsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicEmbeds : {}", topicEmbedsDTO);
        if (topicEmbedsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicEmbedsDTO result = topicEmbedsService.save(topicEmbedsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicEmbedsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-embeds} : get all the topicEmbeds.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicEmbeds in body.
     */
    @GetMapping("/topic-embeds")
    public ResponseEntity<List<TopicEmbedsDTO>> getAllTopicEmbeds(Pageable pageable) {
        log.debug("REST request to get a page of TopicEmbeds");
        Page<TopicEmbedsDTO> page = topicEmbedsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-embeds/:id} : get the "id" topicEmbeds.
     *
     * @param id the id of the topicEmbedsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicEmbedsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-embeds/{id}")
    public ResponseEntity<TopicEmbedsDTO> getTopicEmbeds(@PathVariable Long id) {
        log.debug("REST request to get TopicEmbeds : {}", id);
        Optional<TopicEmbedsDTO> topicEmbedsDTO = topicEmbedsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicEmbedsDTO);
    }

    /**
     * {@code DELETE  /topic-embeds/:id} : delete the "id" topicEmbeds.
     *
     * @param id the id of the topicEmbedsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-embeds/{id}")
    public ResponseEntity<Void> deleteTopicEmbeds(@PathVariable Long id) {
        log.debug("REST request to delete TopicEmbeds : {}", id);
        topicEmbedsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
