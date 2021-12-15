/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.WebHooksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.WebHooksDTO;

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
 * REST controller for managing {@link com.infy.domain.WebHooks}.
 */
@RestController
@RequestMapping("/api")
public class WebHooksResource {

    private final Logger log = LoggerFactory.getLogger(WebHooksResource.class);

    private static final String ENTITY_NAME = "webHooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebHooksService webHooksService;

    public WebHooksResource(WebHooksService webHooksService) {
        this.webHooksService = webHooksService;
    }

    /**
     * {@code POST  /web-hooks} : Create a new webHooks.
     *
     * @param webHooksDTO the webHooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webHooksDTO, or with status {@code 400 (Bad Request)} if the webHooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-hooks")
    public ResponseEntity<WebHooksDTO> createWebHooks(@Valid @RequestBody WebHooksDTO webHooksDTO) throws URISyntaxException {
        log.debug("REST request to save WebHooks : {}", webHooksDTO);
        if (webHooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new webHooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebHooksDTO result = webHooksService.save(webHooksDTO);
        return ResponseEntity.created(new URI("/api/web-hooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-hooks} : Updates an existing webHooks.
     *
     * @param webHooksDTO the webHooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webHooksDTO,
     * or with status {@code 400 (Bad Request)} if the webHooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webHooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-hooks")
    public ResponseEntity<WebHooksDTO> updateWebHooks(@Valid @RequestBody WebHooksDTO webHooksDTO) throws URISyntaxException {
        log.debug("REST request to update WebHooks : {}", webHooksDTO);
        if (webHooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebHooksDTO result = webHooksService.save(webHooksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, webHooksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-hooks} : get all the webHooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webHooks in body.
     */
    @GetMapping("/web-hooks")
    public ResponseEntity<List<WebHooksDTO>> getAllWebHooks(Pageable pageable) {
        log.debug("REST request to get a page of WebHooks");
        Page<WebHooksDTO> page = webHooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /web-hooks/:id} : get the "id" webHooks.
     *
     * @param id the id of the webHooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webHooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-hooks/{id}")
    public ResponseEntity<WebHooksDTO> getWebHooks(@PathVariable Long id) {
        log.debug("REST request to get WebHooks : {}", id);
        Optional<WebHooksDTO> webHooksDTO = webHooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webHooksDTO);
    }

    /**
     * {@code DELETE  /web-hooks/:id} : delete the "id" webHooks.
     *
     * @param id the id of the webHooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-hooks/{id}")
    public ResponseEntity<Void> deleteWebHooks(@PathVariable Long id) {
        log.debug("REST request to delete WebHooks : {}", id);
        webHooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
