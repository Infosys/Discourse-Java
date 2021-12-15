/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PermalinksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PermalinksDTO;

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
 * REST controller for managing {@link com.infy.domain.Permalinks}.
 */
@RestController
@RequestMapping("/api")
public class PermalinksResource {

    private final Logger log = LoggerFactory.getLogger(PermalinksResource.class);

    private static final String ENTITY_NAME = "permalinks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermalinksService permalinksService;

    public PermalinksResource(PermalinksService permalinksService) {
        this.permalinksService = permalinksService;
    }

    /**
     * {@code POST  /permalinks} : Create a new permalinks.
     *
     * @param permalinksDTO the permalinksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permalinksDTO, or with status {@code 400 (Bad Request)} if the permalinks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permalinks")
    public ResponseEntity<PermalinksDTO> createPermalinks(@Valid @RequestBody PermalinksDTO permalinksDTO) throws URISyntaxException {
        log.debug("REST request to save Permalinks : {}", permalinksDTO);
        if (permalinksDTO.getId() != null) {
            throw new BadRequestAlertException("A new permalinks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermalinksDTO result = permalinksService.save(permalinksDTO);
        return ResponseEntity.created(new URI("/api/permalinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permalinks} : Updates an existing permalinks.
     *
     * @param permalinksDTO the permalinksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permalinksDTO,
     * or with status {@code 400 (Bad Request)} if the permalinksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permalinksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permalinks")
    public ResponseEntity<PermalinksDTO> updatePermalinks(@Valid @RequestBody PermalinksDTO permalinksDTO) throws URISyntaxException {
        log.debug("REST request to update Permalinks : {}", permalinksDTO);
        if (permalinksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermalinksDTO result = permalinksService.save(permalinksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, permalinksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permalinks} : get all the permalinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permalinks in body.
     */
    @GetMapping("/permalinks")
    public ResponseEntity<List<PermalinksDTO>> getAllPermalinks(Pageable pageable) {
        log.debug("REST request to get a page of Permalinks");
        Page<PermalinksDTO> page = permalinksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /permalinks/:id} : get the "id" permalinks.
     *
     * @param id the id of the permalinksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permalinksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permalinks/{id}")
    public ResponseEntity<PermalinksDTO> getPermalinks(@PathVariable Long id) {
        log.debug("REST request to get Permalinks : {}", id);
        Optional<PermalinksDTO> permalinksDTO = permalinksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permalinksDTO);
    }

    /**
     * {@code DELETE  /permalinks/:id} : delete the "id" permalinks.
     *
     * @param id the id of the permalinksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permalinks/{id}")
    public ResponseEntity<Void> deletePermalinks(@PathVariable Long id) {
        log.debug("REST request to delete Permalinks : {}", id);
        permalinksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
