/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.IncomingDomainsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.IncomingDomainsDTO;

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
 * REST controller for managing {@link com.infy.domain.IncomingDomains}.
 */
@RestController
@RequestMapping("/api")
public class IncomingDomainsResource {

    private final Logger log = LoggerFactory.getLogger(IncomingDomainsResource.class);

    private static final String ENTITY_NAME = "incomingDomains";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomingDomainsService incomingDomainsService;

    public IncomingDomainsResource(IncomingDomainsService incomingDomainsService) {
        this.incomingDomainsService = incomingDomainsService;
    }

    /**
     * {@code POST  /incoming-domains} : Create a new incomingDomains.
     *
     * @param incomingDomainsDTO the incomingDomainsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomingDomainsDTO, or with status {@code 400 (Bad Request)} if the incomingDomains has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incoming-domains")
    public ResponseEntity<IncomingDomainsDTO> createIncomingDomains(@Valid @RequestBody IncomingDomainsDTO incomingDomainsDTO) throws URISyntaxException {
        log.debug("REST request to save IncomingDomains : {}", incomingDomainsDTO);
        if (incomingDomainsDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomingDomains cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomingDomainsDTO result = incomingDomainsService.save(incomingDomainsDTO);
        return ResponseEntity.created(new URI("/api/incoming-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incoming-domains} : Updates an existing incomingDomains.
     *
     * @param incomingDomainsDTO the incomingDomainsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomingDomainsDTO,
     * or with status {@code 400 (Bad Request)} if the incomingDomainsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomingDomainsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incoming-domains")
    public ResponseEntity<IncomingDomainsDTO> updateIncomingDomains(@Valid @RequestBody IncomingDomainsDTO incomingDomainsDTO) throws URISyntaxException {
        log.debug("REST request to update IncomingDomains : {}", incomingDomainsDTO);
        if (incomingDomainsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomingDomainsDTO result = incomingDomainsService.save(incomingDomainsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incomingDomainsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incoming-domains} : get all the incomingDomains.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomingDomains in body.
     */
    @GetMapping("/incoming-domains")
    public ResponseEntity<List<IncomingDomainsDTO>> getAllIncomingDomains(Pageable pageable) {
        log.debug("REST request to get a page of IncomingDomains");
        Page<IncomingDomainsDTO> page = incomingDomainsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incoming-domains/:id} : get the "id" incomingDomains.
     *
     * @param id the id of the incomingDomainsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomingDomainsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incoming-domains/{id}")
    public ResponseEntity<IncomingDomainsDTO> getIncomingDomains(@PathVariable Long id) {
        log.debug("REST request to get IncomingDomains : {}", id);
        Optional<IncomingDomainsDTO> incomingDomainsDTO = incomingDomainsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomingDomainsDTO);
    }

    /**
     * {@code DELETE  /incoming-domains/:id} : delete the "id" incomingDomains.
     *
     * @param id the id of the incomingDomainsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incoming-domains/{id}")
    public ResponseEntity<Void> deleteIncomingDomains(@PathVariable Long id) {
        log.debug("REST request to delete IncomingDomains : {}", id);
        incomingDomainsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
