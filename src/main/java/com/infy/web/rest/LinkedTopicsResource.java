/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.LinkedTopicsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.LinkedTopicsDTO;

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
 * REST controller for managing {@link com.infy.domain.LinkedTopics}.
 */
@RestController
@RequestMapping("/api")
public class LinkedTopicsResource {

    private final Logger log = LoggerFactory.getLogger(LinkedTopicsResource.class);

    private static final String ENTITY_NAME = "linkedTopics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinkedTopicsService linkedTopicsService;

    public LinkedTopicsResource(LinkedTopicsService linkedTopicsService) {
        this.linkedTopicsService = linkedTopicsService;
    }

    /**
     * {@code POST  /linked-topics} : Create a new linkedTopics.
     *
     * @param linkedTopicsDTO the linkedTopicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linkedTopicsDTO, or with status {@code 400 (Bad Request)} if the linkedTopics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linked-topics")
    public ResponseEntity<LinkedTopicsDTO> createLinkedTopics(@Valid @RequestBody LinkedTopicsDTO linkedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to save LinkedTopics : {}", linkedTopicsDTO);
        if (linkedTopicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new linkedTopics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinkedTopicsDTO result = linkedTopicsService.save(linkedTopicsDTO);
        return ResponseEntity.created(new URI("/api/linked-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linked-topics} : Updates an existing linkedTopics.
     *
     * @param linkedTopicsDTO the linkedTopicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linkedTopicsDTO,
     * or with status {@code 400 (Bad Request)} if the linkedTopicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linkedTopicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linked-topics")
    public ResponseEntity<LinkedTopicsDTO> updateLinkedTopics(@Valid @RequestBody LinkedTopicsDTO linkedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to update LinkedTopics : {}", linkedTopicsDTO);
        if (linkedTopicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinkedTopicsDTO result = linkedTopicsService.save(linkedTopicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, linkedTopicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linked-topics} : get all the linkedTopics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linkedTopics in body.
     */
    @GetMapping("/linked-topics")
    public ResponseEntity<List<LinkedTopicsDTO>> getAllLinkedTopics(Pageable pageable) {
        log.debug("REST request to get a page of LinkedTopics");
        Page<LinkedTopicsDTO> page = linkedTopicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /linked-topics/:id} : get the "id" linkedTopics.
     *
     * @param id the id of the linkedTopicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linkedTopicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linked-topics/{id}")
    public ResponseEntity<LinkedTopicsDTO> getLinkedTopics(@PathVariable Long id) {
        log.debug("REST request to get LinkedTopics : {}", id);
        Optional<LinkedTopicsDTO> linkedTopicsDTO = linkedTopicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(linkedTopicsDTO);
    }

    /**
     * {@code DELETE  /linked-topics/:id} : delete the "id" linkedTopics.
     *
     * @param id the id of the linkedTopicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linked-topics/{id}")
    public ResponseEntity<Void> deleteLinkedTopics(@PathVariable Long id) {
        log.debug("REST request to delete LinkedTopics : {}", id);
        linkedTopicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
