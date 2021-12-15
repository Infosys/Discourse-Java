/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopTopicsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopTopicsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopTopics}.
 */
@RestController
@RequestMapping("/api")
public class TopTopicsResource {

    private final Logger log = LoggerFactory.getLogger(TopTopicsResource.class);

    private static final String ENTITY_NAME = "topTopics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopTopicsService topTopicsService;

    public TopTopicsResource(TopTopicsService topTopicsService) {
        this.topTopicsService = topTopicsService;
    }

    /**
     * {@code POST  /top-topics} : Create a new topTopics.
     *
     * @param topTopicsDTO the topTopicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topTopicsDTO, or with status {@code 400 (Bad Request)} if the topTopics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/top-topics")
    public ResponseEntity<TopTopicsDTO> createTopTopics(@Valid @RequestBody TopTopicsDTO topTopicsDTO) throws URISyntaxException {
        log.debug("REST request to save TopTopics : {}", topTopicsDTO);
        if (topTopicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topTopics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopTopicsDTO result = topTopicsService.save(topTopicsDTO);
        return ResponseEntity.created(new URI("/api/top-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /top-topics} : Updates an existing topTopics.
     *
     * @param topTopicsDTO the topTopicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topTopicsDTO,
     * or with status {@code 400 (Bad Request)} if the topTopicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topTopicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/top-topics")
    public ResponseEntity<TopTopicsDTO> updateTopTopics(@Valid @RequestBody TopTopicsDTO topTopicsDTO) throws URISyntaxException {
        log.debug("REST request to update TopTopics : {}", topTopicsDTO);
        if (topTopicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopTopicsDTO result = topTopicsService.save(topTopicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topTopicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /top-topics} : get all the topTopics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topTopics in body.
     */
    @GetMapping("/top-topics")
    public ResponseEntity<List<TopTopicsDTO>> getAllTopTopics(Pageable pageable) {
        log.debug("REST request to get a page of TopTopics");
        Page<TopTopicsDTO> page = topTopicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /top-topics/:id} : get the "id" topTopics.
     *
     * @param id the id of the topTopicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topTopicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/top-topics/{id}")
    public ResponseEntity<TopTopicsDTO> getTopTopics(@PathVariable Long id) {
        log.debug("REST request to get TopTopics : {}", id);
        Optional<TopTopicsDTO> topTopicsDTO = topTopicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topTopicsDTO);
    }

    /**
     * {@code DELETE  /top-topics/:id} : delete the "id" topTopics.
     *
     * @param id the id of the topTopicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/top-topics/{id}")
    public ResponseEntity<Void> deleteTopTopics(@PathVariable Long id) {
        log.debug("REST request to delete TopTopics : {}", id);
        topTopicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
