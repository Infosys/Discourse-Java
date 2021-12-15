/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TopicCustomFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TopicCustomFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.TopicCustomFields}.
 */
@RestController
@RequestMapping("/api")
public class TopicCustomFieldsResource {

    private final Logger log = LoggerFactory.getLogger(TopicCustomFieldsResource.class);

    private static final String ENTITY_NAME = "topicCustomFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopicCustomFieldsService topicCustomFieldsService;

    public TopicCustomFieldsResource(TopicCustomFieldsService topicCustomFieldsService) {
        this.topicCustomFieldsService = topicCustomFieldsService;
    }

    /**
     * {@code POST  /topic-custom-fields} : Create a new topicCustomFields.
     *
     * @param topicCustomFieldsDTO the topicCustomFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topicCustomFieldsDTO, or with status {@code 400 (Bad Request)} if the topicCustomFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/topic-custom-fields")
    public ResponseEntity<TopicCustomFieldsDTO> createTopicCustomFields(@Valid @RequestBody TopicCustomFieldsDTO topicCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save TopicCustomFields : {}", topicCustomFieldsDTO);
        if (topicCustomFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new topicCustomFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopicCustomFieldsDTO result = topicCustomFieldsService.save(topicCustomFieldsDTO);
        return ResponseEntity.created(new URI("/api/topic-custom-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /topic-custom-fields} : Updates an existing topicCustomFields.
     *
     * @param topicCustomFieldsDTO the topicCustomFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topicCustomFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the topicCustomFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topicCustomFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/topic-custom-fields")
    public ResponseEntity<TopicCustomFieldsDTO> updateTopicCustomFields(@Valid @RequestBody TopicCustomFieldsDTO topicCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update TopicCustomFields : {}", topicCustomFieldsDTO);
        if (topicCustomFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopicCustomFieldsDTO result = topicCustomFieldsService.save(topicCustomFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicCustomFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /topic-custom-fields} : get all the topicCustomFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topicCustomFields in body.
     */
    @GetMapping("/topic-custom-fields")
    public ResponseEntity<List<TopicCustomFieldsDTO>> getAllTopicCustomFields(Pageable pageable) {
        log.debug("REST request to get a page of TopicCustomFields");
        Page<TopicCustomFieldsDTO> page = topicCustomFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /topic-custom-fields/:id} : get the "id" topicCustomFields.
     *
     * @param id the id of the topicCustomFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topicCustomFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/topic-custom-fields/{id}")
    public ResponseEntity<TopicCustomFieldsDTO> getTopicCustomFields(@PathVariable Long id) {
        log.debug("REST request to get TopicCustomFields : {}", id);
        Optional<TopicCustomFieldsDTO> topicCustomFieldsDTO = topicCustomFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(topicCustomFieldsDTO);
    }

    /**
     * {@code DELETE  /topic-custom-fields/:id} : delete the "id" topicCustomFields.
     *
     * @param id the id of the topicCustomFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/topic-custom-fields/{id}")
    public ResponseEntity<Void> deleteTopicCustomFields(@PathVariable Long id) {
        log.debug("REST request to delete TopicCustomFields : {}", id);
        topicCustomFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
