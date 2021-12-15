/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicSearchDataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicSearchDataDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicSearchData}.
 */
@RestController
@RequestMapping("/api")
public class TopicSearchDataResource {

    private final Logger log = LoggerFactory.getLogger(TopicSearchDataResource.class);

    private static final String ENTITY_NAME = "topicSearchData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicSearchDataService topicSearchDataService;

    public TopicSearchDataResource(TopicSearchDataService topicSearchDataService) {
        this.topicSearchDataService = topicSearchDataService;
    }

    /**
     * {@code POST  /topic-search-data} : Create a new topicSearchData.
     *
     * @param topicSearchDataDTO the topicSearchDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicSearchDataDTO, or with status {@code 400 (Bad Request)} if the topicSearchData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-search-data")
    public ResponseEntity<TopicSearchDataDTO> createTopicSearchData(@Valid @RequestBody TopicSearchDataDTO topicSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to save TopicSearchData : {}", topicSearchDataDTO);
        if (topicSearchDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicSearchData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicSearchDataDTO result = topicSearchDataService.save(topicSearchDataDTO);
        return ResponseEntity.created(new URI("/api/topic-search-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-search-data} : Updates an existing topicSearchData.
     *
     * @param topicSearchDataDTO the topicSearchDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicSearchDataDTO,
     * or with status {@code 400 (Bad Request)} if the topicSearchDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicSearchDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-search-data")
    public ResponseEntity<TopicSearchDataDTO> updateTopicSearchData(@Valid @RequestBody TopicSearchDataDTO topicSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to update TopicSearchData : {}", topicSearchDataDTO);
        if (topicSearchDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicSearchDataDTO result = topicSearchDataService.save(topicSearchDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicSearchDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-search-data} : get all the topicSearchData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicSearchData in body.
     */
    @GetMapping("/topic-search-data")
    public ResponseEntity<List<TopicSearchDataDTO>> getAllTopicSearchData(Pageable pageable) {
        log.debug("REST request to get a page of TopicSearchData");
        Page<TopicSearchDataDTO> page = topicSearchDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-search-data/:id} : get the "id" topicSearchData.
     *
     * @param id the id of the topicSearchDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicSearchDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-search-data/{id}")
    public ResponseEntity<TopicSearchDataDTO> getTopicSearchData(@PathVariable Long id) {
        log.debug("REST request to get TopicSearchData : {}", id);
        Optional<TopicSearchDataDTO> topicSearchDataDTO = topicSearchDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicSearchDataDTO);
    }

    /**
     * {@code DELETE  /topic-search-data/:id} : delete the "id" topicSearchData.
     *
     * @param id the id of the topicSearchDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-search-data/{id}")
    public ResponseEntity<Void> deleteTopicSearchData(@PathVariable Long id) {
        log.debug("REST request to delete TopicSearchData : {}", id);
        topicSearchDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
