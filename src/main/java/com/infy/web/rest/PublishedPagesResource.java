/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PublishedPagesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PublishedPagesDTO;

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
 * REST controller for managing {@link com.infy.domain.PublishedPages}.
 */
@RestController
@RequestMapping("/api")
public class PublishedPagesResource {

    private final Logger log = LoggerFactory.getLogger(PublishedPagesResource.class);

    private static final String ENTITY_NAME = "publishedPages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PublishedPagesService publishedPagesService;

    public PublishedPagesResource(PublishedPagesService publishedPagesService) {
        this.publishedPagesService = publishedPagesService;
    }

    /**
     * {@code POST  /published-pages} : Create a new publishedPages.
     *
     * @param publishedPagesDTO the publishedPagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publishedPagesDTO, or with status {@code 400 (Bad Request)} if the publishedPages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/published-pages")
    public ResponseEntity<PublishedPagesDTO> createPublishedPages(@Valid @RequestBody PublishedPagesDTO publishedPagesDTO) throws URISyntaxException {
        log.debug("REST request to save PublishedPages : {}", publishedPagesDTO);
        if (publishedPagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new publishedPages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PublishedPagesDTO result = publishedPagesService.save(publishedPagesDTO);
        return ResponseEntity.created(new URI("/api/published-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /published-pages} : Updates an existing publishedPages.
     *
     * @param publishedPagesDTO the publishedPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publishedPagesDTO,
     * or with status {@code 400 (Bad Request)} if the publishedPagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the publishedPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/published-pages")
    public ResponseEntity<PublishedPagesDTO> updatePublishedPages(@Valid @RequestBody PublishedPagesDTO publishedPagesDTO) throws URISyntaxException {
        log.debug("REST request to update PublishedPages : {}", publishedPagesDTO);
        if (publishedPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PublishedPagesDTO result = publishedPagesService.save(publishedPagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, publishedPagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /published-pages} : get all the publishedPages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publishedPages in body.
     */
    @GetMapping("/published-pages")
    public ResponseEntity<List<PublishedPagesDTO>> getAllPublishedPages(Pageable pageable) {
        log.debug("REST request to get a page of PublishedPages");
        Page<PublishedPagesDTO> page = publishedPagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /published-pages/:id} : get the "id" publishedPages.
     *
     * @param id the id of the publishedPagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publishedPagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/published-pages/{id}")
    public ResponseEntity<PublishedPagesDTO> getPublishedPages(@PathVariable Long id) {
        log.debug("REST request to get PublishedPages : {}", id);
        Optional<PublishedPagesDTO> publishedPagesDTO = publishedPagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(publishedPagesDTO);
    }

    /**
     * {@code DELETE  /published-pages/:id} : delete the "id" publishedPages.
     *
     * @param id the id of the publishedPagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/published-pages/{id}")
    public ResponseEntity<Void> deletePublishedPages(@PathVariable Long id) {
        log.debug("REST request to delete PublishedPages : {}", id);
        publishedPagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
