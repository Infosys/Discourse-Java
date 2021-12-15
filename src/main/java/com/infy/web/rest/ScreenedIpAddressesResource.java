/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ScreenedIpAddressesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ScreenedIpAddressesDTO;

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
 * REST controller for managing {@link com.infy.domain.ScreenedIpAddresses}.
 */
@RestController
@RequestMapping("/api")
public class ScreenedIpAddressesResource {

    private final Logger log = LoggerFactory.getLogger(ScreenedIpAddressesResource.class);

    private static final String ENTITY_NAME = "screenedIpAddresses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScreenedIpAddressesService screenedIpAddressesService;

    public ScreenedIpAddressesResource(ScreenedIpAddressesService screenedIpAddressesService) {
        this.screenedIpAddressesService = screenedIpAddressesService;
    }

    /**
     * {@code POST  /screened-ip-addresses} : Create a new screenedIpAddresses.
     *
     * @param screenedIpAddressesDTO the screenedIpAddressesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new screenedIpAddressesDTO, or with status {@code 400 (Bad Request)} if the screenedIpAddresses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/screened-ip-addresses")
    public ResponseEntity<ScreenedIpAddressesDTO> createScreenedIpAddresses(@Valid @RequestBody ScreenedIpAddressesDTO screenedIpAddressesDTO) throws URISyntaxException {
        log.debug("REST request to save ScreenedIpAddresses : {}", screenedIpAddressesDTO);
        if (screenedIpAddressesDTO.getId() != null) {
            throw new BadRequestAlertException("A new screenedIpAddresses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScreenedIpAddressesDTO result = screenedIpAddressesService.save(screenedIpAddressesDTO);
        return ResponseEntity.created(new URI("/api/screened-ip-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /screened-ip-addresses} : Updates an existing screenedIpAddresses.
     *
     * @param screenedIpAddressesDTO the screenedIpAddressesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated screenedIpAddressesDTO,
     * or with status {@code 400 (Bad Request)} if the screenedIpAddressesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the screenedIpAddressesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/screened-ip-addresses")
    public ResponseEntity<ScreenedIpAddressesDTO> updateScreenedIpAddresses(@Valid @RequestBody ScreenedIpAddressesDTO screenedIpAddressesDTO) throws URISyntaxException {
        log.debug("REST request to update ScreenedIpAddresses : {}", screenedIpAddressesDTO);
        if (screenedIpAddressesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScreenedIpAddressesDTO result = screenedIpAddressesService.save(screenedIpAddressesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, screenedIpAddressesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /screened-ip-addresses} : get all the screenedIpAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of screenedIpAddresses in body.
     */
    @GetMapping("/screened-ip-addresses")
    public ResponseEntity<List<ScreenedIpAddressesDTO>> getAllScreenedIpAddresses(Pageable pageable) {
        log.debug("REST request to get a page of ScreenedIpAddresses");
        Page<ScreenedIpAddressesDTO> page = screenedIpAddressesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /screened-ip-addresses/:id} : get the "id" screenedIpAddresses.
     *
     * @param id the id of the screenedIpAddressesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the screenedIpAddressesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/screened-ip-addresses/{id}")
    public ResponseEntity<ScreenedIpAddressesDTO> getScreenedIpAddresses(@PathVariable Long id) {
        log.debug("REST request to get ScreenedIpAddresses : {}", id);
        Optional<ScreenedIpAddressesDTO> screenedIpAddressesDTO = screenedIpAddressesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(screenedIpAddressesDTO);
    }

    /**
     * {@code DELETE  /screened-ip-addresses/:id} : delete the "id" screenedIpAddresses.
     *
     * @param id the id of the screenedIpAddressesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/screened-ip-addresses/{id}")
    public ResponseEntity<Void> deleteScreenedIpAddresses(@PathVariable Long id) {
        log.debug("REST request to delete ScreenedIpAddresses : {}", id);
        screenedIpAddressesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
