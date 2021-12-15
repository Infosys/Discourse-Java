/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.WebHookEventTypesHooksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.WebHookEventTypesHooksDTO;

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
 * REST controller for managing {@link com.infy.domain.WebHookEventTypesHooks}.
 */
@RestController
@RequestMapping("/api")
public class WebHookEventTypesHooksResource {

    private final Logger log = LoggerFactory.getLogger(WebHookEventTypesHooksResource.class);

    private static final String ENTITY_NAME = "webHookEventTypesHooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebHookEventTypesHooksService webHookEventTypesHooksService;

    public WebHookEventTypesHooksResource(WebHookEventTypesHooksService webHookEventTypesHooksService) {
        this.webHookEventTypesHooksService = webHookEventTypesHooksService;
    }

    /**
     * {@code POST  /web-hook-event-types-hooks} : Create a new webHookEventTypesHooks.
     *
     * @param webHookEventTypesHooksDTO the webHookEventTypesHooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webHookEventTypesHooksDTO, or with status {@code 400 (Bad Request)} if the webHookEventTypesHooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-hook-event-types-hooks")
    public ResponseEntity<WebHookEventTypesHooksDTO> createWebHookEventTypesHooks(@Valid @RequestBody WebHookEventTypesHooksDTO webHookEventTypesHooksDTO) throws URISyntaxException {
        log.debug("REST request to save WebHookEventTypesHooks : {}", webHookEventTypesHooksDTO);
        if (webHookEventTypesHooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new webHookEventTypesHooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebHookEventTypesHooksDTO result = webHookEventTypesHooksService.save(webHookEventTypesHooksDTO);
        return ResponseEntity.created(new URI("/api/web-hook-event-types-hooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-hook-event-types-hooks} : Updates an existing webHookEventTypesHooks.
     *
     * @param webHookEventTypesHooksDTO the webHookEventTypesHooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webHookEventTypesHooksDTO,
     * or with status {@code 400 (Bad Request)} if the webHookEventTypesHooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webHookEventTypesHooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-hook-event-types-hooks")
    public ResponseEntity<WebHookEventTypesHooksDTO> updateWebHookEventTypesHooks(@Valid @RequestBody WebHookEventTypesHooksDTO webHookEventTypesHooksDTO) throws URISyntaxException {
        log.debug("REST request to update WebHookEventTypesHooks : {}", webHookEventTypesHooksDTO);
        if (webHookEventTypesHooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebHookEventTypesHooksDTO result = webHookEventTypesHooksService.save(webHookEventTypesHooksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, webHookEventTypesHooksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-hook-event-types-hooks} : get all the webHookEventTypesHooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webHookEventTypesHooks in body.
     */
    @GetMapping("/web-hook-event-types-hooks")
    public ResponseEntity<List<WebHookEventTypesHooksDTO>> getAllWebHookEventTypesHooks(Pageable pageable) {
        log.debug("REST request to get a page of WebHookEventTypesHooks");
        Page<WebHookEventTypesHooksDTO> page = webHookEventTypesHooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /web-hook-event-types-hooks/:id} : get the "id" webHookEventTypesHooks.
     *
     * @param id the id of the webHookEventTypesHooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webHookEventTypesHooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-hook-event-types-hooks/{id}")
    public ResponseEntity<WebHookEventTypesHooksDTO> getWebHookEventTypesHooks(@PathVariable Long id) {
        log.debug("REST request to get WebHookEventTypesHooks : {}", id);
        Optional<WebHookEventTypesHooksDTO> webHookEventTypesHooksDTO = webHookEventTypesHooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webHookEventTypesHooksDTO);
    }

    /**
     * {@code DELETE  /web-hook-event-types-hooks/:id} : delete the "id" webHookEventTypesHooks.
     *
     * @param id the id of the webHookEventTypesHooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-hook-event-types-hooks/{id}")
    public ResponseEntity<Void> deleteWebHookEventTypesHooks(@PathVariable Long id) {
        log.debug("REST request to delete WebHookEventTypesHooks : {}", id);
        webHookEventTypesHooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
