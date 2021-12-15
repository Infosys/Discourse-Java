/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.EmailTokensService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.EmailTokensDTO;

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
 * REST controller for managing {@link com.infy.domain.EmailTokens}.
 */
@RestController
@RequestMapping("/api")
public class EmailTokensResource {

    private final Logger log = LoggerFactory.getLogger(EmailTokensResource.class);

    private static final String ENTITY_NAME = "emailTokens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailTokensService emailTokensService;

    public EmailTokensResource(EmailTokensService emailTokensService) {
        this.emailTokensService = emailTokensService;
    }

    /**
     * {@code POST  /email-tokens} : Create a new emailTokens.
     *
     * @param emailTokensDTO the emailTokensDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailTokensDTO, or with status {@code 400 (Bad Request)} if the emailTokens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-tokens")
    public ResponseEntity<EmailTokensDTO> createEmailTokens(@Valid @RequestBody EmailTokensDTO emailTokensDTO) throws URISyntaxException {
        log.debug("REST request to save EmailTokens : {}", emailTokensDTO);
        if (emailTokensDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailTokens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailTokensDTO result = emailTokensService.save(emailTokensDTO);
        return ResponseEntity.created(new URI("/api/email-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-tokens} : Updates an existing emailTokens.
     *
     * @param emailTokensDTO the emailTokensDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTokensDTO,
     * or with status {@code 400 (Bad Request)} if the emailTokensDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailTokensDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-tokens")
    public ResponseEntity<EmailTokensDTO> updateEmailTokens(@Valid @RequestBody EmailTokensDTO emailTokensDTO) throws URISyntaxException {
        log.debug("REST request to update EmailTokens : {}", emailTokensDTO);
        if (emailTokensDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailTokensDTO result = emailTokensService.save(emailTokensDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, emailTokensDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-tokens} : get all the emailTokens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailTokens in body.
     */
    @GetMapping("/email-tokens")
    public ResponseEntity<List<EmailTokensDTO>> getAllEmailTokens(Pageable pageable) {
        log.debug("REST request to get a page of EmailTokens");
        Page<EmailTokensDTO> page = emailTokensService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-tokens/:id} : get the "id" emailTokens.
     *
     * @param id the id of the emailTokensDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailTokensDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-tokens/{id}")
    public ResponseEntity<EmailTokensDTO> getEmailTokens(@PathVariable Long id) {
        log.debug("REST request to get EmailTokens : {}", id);
        Optional<EmailTokensDTO> emailTokensDTO = emailTokensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailTokensDTO);
    }

    /**
     * {@code DELETE  /email-tokens/:id} : delete the "id" emailTokens.
     *
     * @param id the id of the emailTokensDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-tokens/{id}")
    public ResponseEntity<Void> deleteEmailTokens(@PathVariable Long id) {
        log.debug("REST request to delete EmailTokens : {}", id);
        emailTokensService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
