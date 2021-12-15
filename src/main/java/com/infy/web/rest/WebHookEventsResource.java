/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.WebHookEventsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.WebHookEventsDTO;

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
 * REST controller for managing {@link com.infy.domain.WebHookEvents}.
 */
@RestController
@RequestMapping("/api")
public class WebHookEventsResource {

    private final Logger log = LoggerFactory.getLogger(WebHookEventsResource.class);

    private static final String ENTITY_NAME = "webHookEvents";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebHookEventsService webHookEventsService;

    public WebHookEventsResource(WebHookEventsService webHookEventsService) {
        this.webHookEventsService = webHookEventsService;
    }

    /**
     * {@code POST  /web-hook-events} : Create a new webHookEvents.
     *
     * @param webHookEventsDTO the webHookEventsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webHookEventsDTO, or with status {@code 400 (Bad Request)} if the webHookEvents has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-hook-events")
    public ResponseEntity<WebHookEventsDTO> createWebHookEvents(@Valid @RequestBody WebHookEventsDTO webHookEventsDTO) throws URISyntaxException {
        log.debug("REST request to save WebHookEvents : {}", webHookEventsDTO);
        if (webHookEventsDTO.getId() != null) {
            throw new BadRequestAlertException("A new webHookEvents cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebHookEventsDTO result = webHookEventsService.save(webHookEventsDTO);
        return ResponseEntity.created(new URI("/api/web-hook-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-hook-events} : Updates an existing webHookEvents.
     *
     * @param webHookEventsDTO the webHookEventsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webHookEventsDTO,
     * or with status {@code 400 (Bad Request)} if the webHookEventsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webHookEventsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-hook-events")
    public ResponseEntity<WebHookEventsDTO> updateWebHookEvents(@Valid @RequestBody WebHookEventsDTO webHookEventsDTO) throws URISyntaxException {
        log.debug("REST request to update WebHookEvents : {}", webHookEventsDTO);
        if (webHookEventsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebHookEventsDTO result = webHookEventsService.save(webHookEventsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, webHookEventsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-hook-events} : get all the webHookEvents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webHookEvents in body.
     */
    @GetMapping("/web-hook-events")
    public ResponseEntity<List<WebHookEventsDTO>> getAllWebHookEvents(Pageable pageable) {
        log.debug("REST request to get a page of WebHookEvents");
        Page<WebHookEventsDTO> page = webHookEventsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /web-hook-events/:id} : get the "id" webHookEvents.
     *
     * @param id the id of the webHookEventsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webHookEventsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-hook-events/{id}")
    public ResponseEntity<WebHookEventsDTO> getWebHookEvents(@PathVariable Long id) {
        log.debug("REST request to get WebHookEvents : {}", id);
        Optional<WebHookEventsDTO> webHookEventsDTO = webHookEventsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webHookEventsDTO);
    }

    /**
     * {@code DELETE  /web-hook-events/:id} : delete the "id" webHookEvents.
     *
     * @param id the id of the webHookEventsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-hook-events/{id}")
    public ResponseEntity<Void> deleteWebHookEvents(@PathVariable Long id) {
        log.debug("REST request to delete WebHookEvents : {}", id);
        webHookEventsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
