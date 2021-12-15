/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.WebCrawlerRequestsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.WebCrawlerRequestsDTO;

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
 * REST controller for managing {@link com.infy.domain.WebCrawlerRequests}.
 */
@RestController
@RequestMapping("/api")
public class WebCrawlerRequestsResource {

    private final Logger log = LoggerFactory.getLogger(WebCrawlerRequestsResource.class);

    private static final String ENTITY_NAME = "webCrawlerRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebCrawlerRequestsService webCrawlerRequestsService;

    public WebCrawlerRequestsResource(WebCrawlerRequestsService webCrawlerRequestsService) {
        this.webCrawlerRequestsService = webCrawlerRequestsService;
    }

    /**
     * {@code POST  /web-crawler-requests} : Create a new webCrawlerRequests.
     *
     * @param webCrawlerRequestsDTO the webCrawlerRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webCrawlerRequestsDTO, or with status {@code 400 (Bad Request)} if the webCrawlerRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-crawler-requests")
    public ResponseEntity<WebCrawlerRequestsDTO> createWebCrawlerRequests(@Valid @RequestBody WebCrawlerRequestsDTO webCrawlerRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save WebCrawlerRequests : {}", webCrawlerRequestsDTO);
        if (webCrawlerRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new webCrawlerRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebCrawlerRequestsDTO result = webCrawlerRequestsService.save(webCrawlerRequestsDTO);
        return ResponseEntity.created(new URI("/api/web-crawler-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-crawler-requests} : Updates an existing webCrawlerRequests.
     *
     * @param webCrawlerRequestsDTO the webCrawlerRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webCrawlerRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the webCrawlerRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webCrawlerRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-crawler-requests")
    public ResponseEntity<WebCrawlerRequestsDTO> updateWebCrawlerRequests(@Valid @RequestBody WebCrawlerRequestsDTO webCrawlerRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update WebCrawlerRequests : {}", webCrawlerRequestsDTO);
        if (webCrawlerRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebCrawlerRequestsDTO result = webCrawlerRequestsService.save(webCrawlerRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, webCrawlerRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-crawler-requests} : get all the webCrawlerRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webCrawlerRequests in body.
     */
    @GetMapping("/web-crawler-requests")
    public ResponseEntity<List<WebCrawlerRequestsDTO>> getAllWebCrawlerRequests(Pageable pageable) {
        log.debug("REST request to get a page of WebCrawlerRequests");
        Page<WebCrawlerRequestsDTO> page = webCrawlerRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /web-crawler-requests/:id} : get the "id" webCrawlerRequests.
     *
     * @param id the id of the webCrawlerRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webCrawlerRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-crawler-requests/{id}")
    public ResponseEntity<WebCrawlerRequestsDTO> getWebCrawlerRequests(@PathVariable Long id) {
        log.debug("REST request to get WebCrawlerRequests : {}", id);
        Optional<WebCrawlerRequestsDTO> webCrawlerRequestsDTO = webCrawlerRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webCrawlerRequestsDTO);
    }

    /**
     * {@code DELETE  /web-crawler-requests/:id} : delete the "id" webCrawlerRequests.
     *
     * @param id the id of the webCrawlerRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-crawler-requests/{id}")
    public ResponseEntity<Void> deleteWebCrawlerRequests(@PathVariable Long id) {
        log.debug("REST request to delete WebCrawlerRequests : {}", id);
        webCrawlerRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
