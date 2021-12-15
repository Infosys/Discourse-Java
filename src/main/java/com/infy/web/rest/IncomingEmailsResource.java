/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.IncomingEmailsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.IncomingEmailsDTO;

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
 * REST controller for managing {@link com.infy.domain.IncomingEmails}.
 */
@RestController
@RequestMapping("/api")
public class IncomingEmailsResource {

    private final Logger log = LoggerFactory.getLogger(IncomingEmailsResource.class);

    private static final String ENTITY_NAME = "incomingEmails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomingEmailsService incomingEmailsService;

    public IncomingEmailsResource(IncomingEmailsService incomingEmailsService) {
        this.incomingEmailsService = incomingEmailsService;
    }

    /**
     * {@code POST  /incoming-emails} : Create a new incomingEmails.
     *
     * @param incomingEmailsDTO the incomingEmailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomingEmailsDTO, or with status {@code 400 (Bad Request)} if the incomingEmails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incoming-emails")
    public ResponseEntity<IncomingEmailsDTO> createIncomingEmails(@Valid @RequestBody IncomingEmailsDTO incomingEmailsDTO) throws URISyntaxException {
        log.debug("REST request to save IncomingEmails : {}", incomingEmailsDTO);
        if (incomingEmailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomingEmails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomingEmailsDTO result = incomingEmailsService.save(incomingEmailsDTO);
        return ResponseEntity.created(new URI("/api/incoming-emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incoming-emails} : Updates an existing incomingEmails.
     *
     * @param incomingEmailsDTO the incomingEmailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomingEmailsDTO,
     * or with status {@code 400 (Bad Request)} if the incomingEmailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomingEmailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incoming-emails")
    public ResponseEntity<IncomingEmailsDTO> updateIncomingEmails(@Valid @RequestBody IncomingEmailsDTO incomingEmailsDTO) throws URISyntaxException {
        log.debug("REST request to update IncomingEmails : {}", incomingEmailsDTO);
        if (incomingEmailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomingEmailsDTO result = incomingEmailsService.save(incomingEmailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incomingEmailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incoming-emails} : get all the incomingEmails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomingEmails in body.
     */
    @GetMapping("/incoming-emails")
    public ResponseEntity<List<IncomingEmailsDTO>> getAllIncomingEmails(Pageable pageable) {
        log.debug("REST request to get a page of IncomingEmails");
        Page<IncomingEmailsDTO> page = incomingEmailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incoming-emails/:id} : get the "id" incomingEmails.
     *
     * @param id the id of the incomingEmailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomingEmailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incoming-emails/{id}")
    public ResponseEntity<IncomingEmailsDTO> getIncomingEmails(@PathVariable Long id) {
        log.debug("REST request to get IncomingEmails : {}", id);
        Optional<IncomingEmailsDTO> incomingEmailsDTO = incomingEmailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomingEmailsDTO);
    }

    /**
     * {@code DELETE  /incoming-emails/:id} : delete the "id" incomingEmails.
     *
     * @param id the id of the incomingEmailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incoming-emails/{id}")
    public ResponseEntity<Void> deleteIncomingEmails(@PathVariable Long id) {
        log.debug("REST request to delete IncomingEmails : {}", id);
        incomingEmailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
