/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.EmailChangeRequestsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.EmailChangeRequestsDTO;

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
 * REST controller for managing {@link com.infy.domain.EmailChangeRequests}.
 */
@RestController
@RequestMapping("/api")
public class EmailChangeRequestsResource {

    private final Logger log = LoggerFactory.getLogger(EmailChangeRequestsResource.class);

    private static final String ENTITY_NAME = "emailChangeRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailChangeRequestsService emailChangeRequestsService;

    public EmailChangeRequestsResource(EmailChangeRequestsService emailChangeRequestsService) {
        this.emailChangeRequestsService = emailChangeRequestsService;
    }

    /**
     * {@code POST  /email-change-requests} : Create a new emailChangeRequests.
     *
     * @param emailChangeRequestsDTO the emailChangeRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailChangeRequestsDTO, or with status {@code 400 (Bad Request)} if the emailChangeRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-change-requests")
    public ResponseEntity<EmailChangeRequestsDTO> createEmailChangeRequests(@Valid @RequestBody EmailChangeRequestsDTO emailChangeRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save EmailChangeRequests : {}", emailChangeRequestsDTO);
        if (emailChangeRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailChangeRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailChangeRequestsDTO result = emailChangeRequestsService.save(emailChangeRequestsDTO);
        return ResponseEntity.created(new URI("/api/email-change-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-change-requests} : Updates an existing emailChangeRequests.
     *
     * @param emailChangeRequestsDTO the emailChangeRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailChangeRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the emailChangeRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailChangeRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-change-requests")
    public ResponseEntity<EmailChangeRequestsDTO> updateEmailChangeRequests(@Valid @RequestBody EmailChangeRequestsDTO emailChangeRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update EmailChangeRequests : {}", emailChangeRequestsDTO);
        if (emailChangeRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailChangeRequestsDTO result = emailChangeRequestsService.save(emailChangeRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailChangeRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-change-requests} : get all the emailChangeRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailChangeRequests in body.
     */
    @GetMapping("/email-change-requests")
    public ResponseEntity<List<EmailChangeRequestsDTO>> getAllEmailChangeRequests(Pageable pageable) {
        log.debug("REST request to get a page of EmailChangeRequests");
        Page<EmailChangeRequestsDTO> page = emailChangeRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-change-requests/:id} : get the "id" emailChangeRequests.
     *
     * @param id the id of the emailChangeRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailChangeRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-change-requests/{id}")
    public ResponseEntity<EmailChangeRequestsDTO> getEmailChangeRequests(@PathVariable Long id) {
        log.debug("REST request to get EmailChangeRequests : {}", id);
        Optional<EmailChangeRequestsDTO> emailChangeRequestsDTO = emailChangeRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailChangeRequestsDTO);
    }

    /**
     * {@code DELETE  /email-change-requests/:id} : delete the "id" emailChangeRequests.
     *
     * @param id the id of the emailChangeRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-change-requests/{id}")
    public ResponseEntity<Void> deleteEmailChangeRequests(@PathVariable Long id) {
        log.debug("REST request to delete EmailChangeRequests : {}", id);
        emailChangeRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
