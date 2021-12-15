/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.IncomingReferersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.IncomingReferersDTO;

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
 * REST controller for managing {@link com.infy.domain.IncomingReferers}.
 */
@RestController
@RequestMapping("/api")
public class IncomingReferersResource {

    private final Logger log = LoggerFactory.getLogger(IncomingReferersResource.class);

    private static final String ENTITY_NAME = "incomingReferers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomingReferersService incomingReferersService;

    public IncomingReferersResource(IncomingReferersService incomingReferersService) {
        this.incomingReferersService = incomingReferersService;
    }

    /**
     * {@code POST  /incoming-referers} : Create a new incomingReferers.
     *
     * @param incomingReferersDTO the incomingReferersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incomingReferersDTO, or with status {@code 400 (Bad Request)} if the incomingReferers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incoming-referers")
    public ResponseEntity<IncomingReferersDTO> createIncomingReferers(@Valid @RequestBody IncomingReferersDTO incomingReferersDTO) throws URISyntaxException {
        log.debug("REST request to save IncomingReferers : {}", incomingReferersDTO);
        if (incomingReferersDTO.getId() != null) {
            throw new BadRequestAlertException("A new incomingReferers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncomingReferersDTO result = incomingReferersService.save(incomingReferersDTO);
        return ResponseEntity.created(new URI("/api/incoming-referers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incoming-referers} : Updates an existing incomingReferers.
     *
     * @param incomingReferersDTO the incomingReferersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incomingReferersDTO,
     * or with status {@code 400 (Bad Request)} if the incomingReferersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incomingReferersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incoming-referers")
    public ResponseEntity<IncomingReferersDTO> updateIncomingReferers(@Valid @RequestBody IncomingReferersDTO incomingReferersDTO) throws URISyntaxException {
        log.debug("REST request to update IncomingReferers : {}", incomingReferersDTO);
        if (incomingReferersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IncomingReferersDTO result = incomingReferersService.save(incomingReferersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incomingReferersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /incoming-referers} : get all the incomingReferers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incomingReferers in body.
     */
    @GetMapping("/incoming-referers")
    public ResponseEntity<List<IncomingReferersDTO>> getAllIncomingReferers(Pageable pageable) {
        log.debug("REST request to get a page of IncomingReferers");
        Page<IncomingReferersDTO> page = incomingReferersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incoming-referers/:id} : get the "id" incomingReferers.
     *
     * @param id the id of the incomingReferersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incomingReferersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incoming-referers/{id}")
    public ResponseEntity<IncomingReferersDTO> getIncomingReferers(@PathVariable Long id) {
        log.debug("REST request to get IncomingReferers : {}", id);
        Optional<IncomingReferersDTO> incomingReferersDTO = incomingReferersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incomingReferersDTO);
    }

    /**
     * {@code DELETE  /incoming-referers/:id} : delete the "id" incomingReferers.
     *
     * @param id the id of the incomingReferersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incoming-referers/{id}")
    public ResponseEntity<Void> deleteIncomingReferers(@PathVariable Long id) {
        log.debug("REST request to delete IncomingReferers : {}", id);
        incomingReferersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
