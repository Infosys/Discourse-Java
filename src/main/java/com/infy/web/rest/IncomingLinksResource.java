/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.IncomingLinksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.IncomingLinksDTO;

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
 * REST controller for managing {@link com.infy.domain.IncomingLinks}.
 */
@RestController
@RequestMapping("/api")
public class IncomingLinksResource {

    private final Logger log = LoggerFactory.getLogger(IncomingLinksResource.class);

    private static final String ENTITY_NAME = "incomingLinks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomingLinksService incomingLinksService;

    public IncomingLinksResource(IncomingLinksService incomingLinksService) {
        this.incomingLinksService = incomingLinksService;
    }

    /**
     * {@code POST  /incoming-links} : Create a new incomingLinks.
     *
     * @param incomingLinksDTO the incomingLinksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomingLinksDTO, or with status {@code 400 (Bad Request)} if the incomingLinks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incoming-links")
    public ResponseEntity<IncomingLinksDTO> createIncomingLinks(@Valid @RequestBody IncomingLinksDTO incomingLinksDTO) throws URISyntaxException {
        log.debug("REST request to save IncomingLinks : {}", incomingLinksDTO);
        if (incomingLinksDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomingLinks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomingLinksDTO result = incomingLinksService.save(incomingLinksDTO);
        return ResponseEntity.created(new URI("/api/incoming-links/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incoming-links} : Updates an existing incomingLinks.
     *
     * @param incomingLinksDTO the incomingLinksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomingLinksDTO,
     * or with status {@code 400 (Bad Request)} if the incomingLinksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomingLinksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incoming-links")
    public ResponseEntity<IncomingLinksDTO> updateIncomingLinks(@Valid @RequestBody IncomingLinksDTO incomingLinksDTO) throws URISyntaxException {
        log.debug("REST request to update IncomingLinks : {}", incomingLinksDTO);
        if (incomingLinksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomingLinksDTO result = incomingLinksService.save(incomingLinksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incomingLinksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incoming-links} : get all the incomingLinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomingLinks in body.
     */
    @GetMapping("/incoming-links")
    public ResponseEntity<List<IncomingLinksDTO>> getAllIncomingLinks(Pageable pageable) {
        log.debug("REST request to get a page of IncomingLinks");
        Page<IncomingLinksDTO> page = incomingLinksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incoming-links/:id} : get the "id" incomingLinks.
     *
     * @param id the id of the incomingLinksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomingLinksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incoming-links/{id}")
    public ResponseEntity<IncomingLinksDTO> getIncomingLinks(@PathVariable Long id) {
        log.debug("REST request to get IncomingLinks : {}", id);
        Optional<IncomingLinksDTO> incomingLinksDTO = incomingLinksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomingLinksDTO);
    }

    /**
     * {@code DELETE  /incoming-links/:id} : delete the "id" incomingLinks.
     *
     * @param id the id of the incomingLinksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incoming-links/{id}")
    public ResponseEntity<Void> deleteIncomingLinks(@PathVariable Long id) {
        log.debug("REST request to delete IncomingLinks : {}", id);
        incomingLinksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
