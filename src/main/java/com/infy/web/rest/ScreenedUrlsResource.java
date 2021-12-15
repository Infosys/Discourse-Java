/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ScreenedUrlsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ScreenedUrlsDTO;

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
 * REST controller for managing {@link com.infy.domain.ScreenedUrls}.
 */
@RestController
@RequestMapping("/api")
public class ScreenedUrlsResource {

    private final Logger log = LoggerFactory.getLogger(ScreenedUrlsResource.class);

    private static final String ENTITY_NAME = "screenedUrls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScreenedUrlsService screenedUrlsService;

    public ScreenedUrlsResource(ScreenedUrlsService screenedUrlsService) {
        this.screenedUrlsService = screenedUrlsService;
    }

    /**
     * {@code POST  /screened-urls} : Create a new screenedUrls.
     *
     * @param screenedUrlsDTO the screenedUrlsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new screenedUrlsDTO, or with status {@code 400 (Bad Request)} if the screenedUrls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/screened-urls")
    public ResponseEntity<ScreenedUrlsDTO> createScreenedUrls(@Valid @RequestBody ScreenedUrlsDTO screenedUrlsDTO) throws URISyntaxException {
        log.debug("REST request to save ScreenedUrls : {}", screenedUrlsDTO);
        if (screenedUrlsDTO.getId() != null) {
            throw new BadRequestAlertException("A new screenedUrls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScreenedUrlsDTO result = screenedUrlsService.save(screenedUrlsDTO);
        return ResponseEntity.created(new URI("/api/screened-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /screened-urls} : Updates an existing screenedUrls.
     *
     * @param screenedUrlsDTO the screenedUrlsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated screenedUrlsDTO,
     * or with status {@code 400 (Bad Request)} if the screenedUrlsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the screenedUrlsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/screened-urls")
    public ResponseEntity<ScreenedUrlsDTO> updateScreenedUrls(@Valid @RequestBody ScreenedUrlsDTO screenedUrlsDTO) throws URISyntaxException {
        log.debug("REST request to update ScreenedUrls : {}", screenedUrlsDTO);
        if (screenedUrlsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScreenedUrlsDTO result = screenedUrlsService.save(screenedUrlsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, screenedUrlsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /screened-urls} : get all the screenedUrls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of screenedUrls in body.
     */
    @GetMapping("/screened-urls")
    public ResponseEntity<List<ScreenedUrlsDTO>> getAllScreenedUrls(Pageable pageable) {
        log.debug("REST request to get a page of ScreenedUrls");
        Page<ScreenedUrlsDTO> page = screenedUrlsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /screened-urls/:id} : get the "id" screenedUrls.
     *
     * @param id the id of the screenedUrlsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the screenedUrlsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/screened-urls/{id}")
    public ResponseEntity<ScreenedUrlsDTO> getScreenedUrls(@PathVariable Long id) {
        log.debug("REST request to get ScreenedUrls : {}", id);
        Optional<ScreenedUrlsDTO> screenedUrlsDTO = screenedUrlsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(screenedUrlsDTO);
    }

    /**
     * {@code DELETE  /screened-urls/:id} : delete the "id" screenedUrls.
     *
     * @param id the id of the screenedUrlsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/screened-urls/{id}")
    public ResponseEntity<Void> deleteScreenedUrls(@PathVariable Long id) {
        log.debug("REST request to delete ScreenedUrls : {}", id);
        screenedUrlsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
