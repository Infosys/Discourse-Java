/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.EmailLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.EmailLogsDTO;

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
 * REST controller for managing {@link com.infy.domain.EmailLogs}.
 */
@RestController
@RequestMapping("/api")
public class EmailLogsResource {

    private final Logger log = LoggerFactory.getLogger(EmailLogsResource.class);

    private static final String ENTITY_NAME = "emailLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailLogsService emailLogsService;

    public EmailLogsResource(EmailLogsService emailLogsService) {
        this.emailLogsService = emailLogsService;
    }

    /**
     * {@code POST  /email-logs} : Create a new emailLogs.
     *
     * @param emailLogsDTO the emailLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailLogsDTO, or with status {@code 400 (Bad Request)} if the emailLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-logs")
    public ResponseEntity<EmailLogsDTO> createEmailLogs(@Valid @RequestBody EmailLogsDTO emailLogsDTO) throws URISyntaxException {
        log.debug("REST request to save EmailLogs : {}", emailLogsDTO);
        if (emailLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailLogsDTO result = emailLogsService.save(emailLogsDTO);
        return ResponseEntity.created(new URI("/api/email-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-logs} : Updates an existing emailLogs.
     *
     * @param emailLogsDTO the emailLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailLogsDTO,
     * or with status {@code 400 (Bad Request)} if the emailLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-logs")
    public ResponseEntity<EmailLogsDTO> updateEmailLogs(@Valid @RequestBody EmailLogsDTO emailLogsDTO) throws URISyntaxException {
        log.debug("REST request to update EmailLogs : {}", emailLogsDTO);
        if (emailLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailLogsDTO result = emailLogsService.save(emailLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-logs} : get all the emailLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailLogs in body.
     */
    @GetMapping("/email-logs")
    public ResponseEntity<List<EmailLogsDTO>> getAllEmailLogs(Pageable pageable) {
        log.debug("REST request to get a page of EmailLogs");
        Page<EmailLogsDTO> page = emailLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-logs/:id} : get the "id" emailLogs.
     *
     * @param id the id of the emailLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-logs/{id}")
    public ResponseEntity<EmailLogsDTO> getEmailLogs(@PathVariable Long id) {
        log.debug("REST request to get EmailLogs : {}", id);
        Optional<EmailLogsDTO> emailLogsDTO = emailLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailLogsDTO);
    }

    /**
     * {@code DELETE  /email-logs/:id} : delete the "id" emailLogs.
     *
     * @param id the id of the emailLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-logs/{id}")
    public ResponseEntity<Void> deleteEmailLogs(@PathVariable Long id) {
        log.debug("REST request to delete EmailLogs : {}", id);
        emailLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
