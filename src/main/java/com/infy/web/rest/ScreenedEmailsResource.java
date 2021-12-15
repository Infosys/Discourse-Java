/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ScreenedEmailsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ScreenedEmailsDTO;

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
 * REST controller for managing {@link com.infy.domain.ScreenedEmails}.
 */
@RestController
@RequestMapping("/api")
public class ScreenedEmailsResource {

    private final Logger log = LoggerFactory.getLogger(ScreenedEmailsResource.class);

    private static final String ENTITY_NAME = "screenedEmails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScreenedEmailsService screenedEmailsService;

    public ScreenedEmailsResource(ScreenedEmailsService screenedEmailsService) {
        this.screenedEmailsService = screenedEmailsService;
    }

    /**
     * {@code POST  /screened-emails} : Create a new screenedEmails.
     *
     * @param screenedEmailsDTO the screenedEmailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new screenedEmailsDTO, or with status {@code 400 (Bad Request)} if the screenedEmails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/screened-emails")
    public ResponseEntity<ScreenedEmailsDTO> createScreenedEmails(@Valid @RequestBody ScreenedEmailsDTO screenedEmailsDTO) throws URISyntaxException {
        log.debug("REST request to save ScreenedEmails : {}", screenedEmailsDTO);
        if (screenedEmailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new screenedEmails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScreenedEmailsDTO result = screenedEmailsService.save(screenedEmailsDTO);
        return ResponseEntity.created(new URI("/api/screened-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /screened-emails} : Updates an existing screenedEmails.
     *
     * @param screenedEmailsDTO the screenedEmailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated screenedEmailsDTO,
     * or with status {@code 400 (Bad Request)} if the screenedEmailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the screenedEmailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/screened-emails")
    public ResponseEntity<ScreenedEmailsDTO> updateScreenedEmails(@Valid @RequestBody ScreenedEmailsDTO screenedEmailsDTO) throws URISyntaxException {
        log.debug("REST request to update ScreenedEmails : {}", screenedEmailsDTO);
        if (screenedEmailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScreenedEmailsDTO result = screenedEmailsService.save(screenedEmailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, screenedEmailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /screened-emails} : get all the screenedEmails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of screenedEmails in body.
     */
    @GetMapping("/screened-emails")
    public ResponseEntity<List<ScreenedEmailsDTO>> getAllScreenedEmails(Pageable pageable) {
        log.debug("REST request to get a page of ScreenedEmails");
        Page<ScreenedEmailsDTO> page = screenedEmailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /screened-emails/:id} : get the "id" screenedEmails.
     *
     * @param id the id of the screenedEmailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the screenedEmailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/screened-emails/{id}")
    public ResponseEntity<ScreenedEmailsDTO> getScreenedEmails(@PathVariable Long id) {
        log.debug("REST request to get ScreenedEmails : {}", id);
        Optional<ScreenedEmailsDTO> screenedEmailsDTO = screenedEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(screenedEmailsDTO);
    }

    /**
     * {@code DELETE  /screened-emails/:id} : delete the "id" screenedEmails.
     *
     * @param id the id of the screenedEmailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/screened-emails/{id}")
    public ResponseEntity<Void> deleteScreenedEmails(@PathVariable Long id) {
        log.debug("REST request to delete ScreenedEmails : {}", id);
        screenedEmailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
