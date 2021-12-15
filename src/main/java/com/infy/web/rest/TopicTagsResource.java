/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicTagsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicTagsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicTags}.
 */
@RestController
@RequestMapping("/api")
public class TopicTagsResource {

    private final Logger log = LoggerFactory.getLogger(TopicTagsResource.class);

    private static final String ENTITY_NAME = "topicTags";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicTagsService topicTagsService;

    public TopicTagsResource(TopicTagsService topicTagsService) {
        this.topicTagsService = topicTagsService;
    }

    /**
     * {@code POST  /topic-tags} : Create a new topicTags.
     *
     * @param topicTagsDTO the topicTagsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicTagsDTO, or with status {@code 400 (Bad Request)} if the topicTags has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-tags")
    public ResponseEntity<TopicTagsDTO> createTopicTags(@Valid @RequestBody TopicTagsDTO topicTagsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicTags : {}", topicTagsDTO);
        if (topicTagsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicTagsDTO result = topicTagsService.save(topicTagsDTO);
        return ResponseEntity.created(new URI("/api/topic-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-tags} : Updates an existing topicTags.
     *
     * @param topicTagsDTO the topicTagsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicTagsDTO,
     * or with status {@code 400 (Bad Request)} if the topicTagsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicTagsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-tags")
    public ResponseEntity<TopicTagsDTO> updateTopicTags(@Valid @RequestBody TopicTagsDTO topicTagsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicTags : {}", topicTagsDTO);
        if (topicTagsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicTagsDTO result = topicTagsService.save(topicTagsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicTagsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-tags} : get all the topicTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicTags in body.
     */
    @GetMapping("/topic-tags")
    public ResponseEntity<List<TopicTagsDTO>> getAllTopicTags(Pageable pageable) {
        log.debug("REST request to get a page of TopicTags");
        Page<TopicTagsDTO> page = topicTagsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-tags/:id} : get the "id" topicTags.
     *
     * @param id the id of the topicTagsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicTagsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-tags/{id}")
    public ResponseEntity<TopicTagsDTO> getTopicTags(@PathVariable Long id) {
        log.debug("REST request to get TopicTags : {}", id);
        Optional<TopicTagsDTO> topicTagsDTO = topicTagsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicTagsDTO);
    }

    /**
     * {@code DELETE  /topic-tags/:id} : delete the "id" topicTags.
     *
     * @param id the id of the topicTagsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-tags/{id}")
    public ResponseEntity<Void> deleteTopicTags(@PathVariable Long id) {
        log.debug("REST request to delete TopicTags : {}", id);
        topicTagsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
