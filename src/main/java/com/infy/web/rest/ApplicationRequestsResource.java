/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ApplicationRequestsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ApplicationRequestsDTO;

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
 * REST controller for managing {@link com.infy.domain.ApplicationRequests}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationRequestsResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationRequestsResource.class);

    private static final String ENTITY_NAME = "applicationRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationRequestsService applicationRequestsService;

    public ApplicationRequestsResource(ApplicationRequestsService applicationRequestsService) {
        this.applicationRequestsService = applicationRequestsService;
    }

    /**
     * {@code POST  /application-requests} : Create a new applicationRequests.
     *
     * @param applicationRequestsDTO the applicationRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationRequestsDTO, or with status {@code 400 (Bad Request)} if the applicationRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-requests")
    public ResponseEntity<ApplicationRequestsDTO> createApplicationRequests(@Valid @RequestBody ApplicationRequestsDTO applicationRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationRequests : {}", applicationRequestsDTO);
        if (applicationRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationRequestsDTO result = applicationRequestsService.save(applicationRequestsDTO);
        return ResponseEntity.created(new URI("/api/application-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-requests} : Updates an existing applicationRequests.
     *
     * @param applicationRequestsDTO the applicationRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the applicationRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-requests")
    public ResponseEntity<ApplicationRequestsDTO> updateApplicationRequests(@Valid @RequestBody ApplicationRequestsDTO applicationRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationRequests : {}", applicationRequestsDTO);
        if (applicationRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationRequestsDTO result = applicationRequestsService.save(applicationRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, applicationRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-requests} : get all the applicationRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationRequests in body.
     */
    @GetMapping("/application-requests")
    public ResponseEntity<List<ApplicationRequestsDTO>> getAllApplicationRequests(Pageable pageable) {
        log.debug("REST request to get a page of ApplicationRequests");
        Page<ApplicationRequestsDTO> page = applicationRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-requests/:id} : get the "id" applicationRequests.
     *
     * @param id the id of the applicationRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-requests/{id}")
    public ResponseEntity<ApplicationRequestsDTO> getApplicationRequests(@PathVariable Long id) {
        log.debug("REST request to get ApplicationRequests : {}", id);
        Optional<ApplicationRequestsDTO> applicationRequestsDTO = applicationRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationRequestsDTO);
    }

    /**
     * {@code DELETE  /application-requests/:id} : delete the "id" applicationRequests.
     *
     * @param id the id of the applicationRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-requests/{id}")
    public ResponseEntity<Void> deleteApplicationRequests(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationRequests : {}", id);
        applicationRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
