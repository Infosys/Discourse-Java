/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CustomEmojisService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CustomEmojisDTO;

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
 * REST controller for managing {@link com.infy.domain.CustomEmojis}.
 */
@RestController
@RequestMapping("/api")
public class CustomEmojisResource {

    private final Logger log = LoggerFactory.getLogger(CustomEmojisResource.class);

    private static final String ENTITY_NAME = "customEmojis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomEmojisService customEmojisService;

    public CustomEmojisResource(CustomEmojisService customEmojisService) {
        this.customEmojisService = customEmojisService;
    }

    /**
     * {@code POST  /custom-emojis} : Create a new customEmojis.
     *
     * @param customEmojisDTO the customEmojisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customEmojisDTO, or with status {@code 400 (Bad Request)} if the customEmojis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/custom-emojis")
    public ResponseEntity<CustomEmojisDTO> createCustomEmojis(@Valid @RequestBody CustomEmojisDTO customEmojisDTO) throws URISyntaxException {
        log.debug("REST request to save CustomEmojis : {}", customEmojisDTO);
        if (customEmojisDTO.getId() != null) {
            throw new BadRequestAlertException("A new customEmojis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomEmojisDTO result = customEmojisService.save(customEmojisDTO);
        return ResponseEntity.created(new URI("/api/custom-emojis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /custom-emojis} : Updates an existing customEmojis.
     *
     * @param customEmojisDTO the customEmojisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customEmojisDTO,
     * or with status {@code 400 (Bad Request)} if the customEmojisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customEmojisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/custom-emojis")
    public ResponseEntity<CustomEmojisDTO> updateCustomEmojis(@Valid @RequestBody CustomEmojisDTO customEmojisDTO) throws URISyntaxException {
        log.debug("REST request to update CustomEmojis : {}", customEmojisDTO);
        if (customEmojisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CustomEmojisDTO result = customEmojisService.save(customEmojisDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, customEmojisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /custom-emojis} : get all the customEmojis.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customEmojis in body.
     */
    @GetMapping("/custom-emojis")
    public ResponseEntity<List<CustomEmojisDTO>> getAllCustomEmojis(Pageable pageable) {
        log.debug("REST request to get a page of CustomEmojis");
        Page<CustomEmojisDTO> page = customEmojisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /custom-emojis/:id} : get the "id" customEmojis.
     *
     * @param id the id of the customEmojisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customEmojisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/custom-emojis/{id}")
    public ResponseEntity<CustomEmojisDTO> getCustomEmojis(@PathVariable Long id) {
        log.debug("REST request to get CustomEmojis : {}", id);
        Optional<CustomEmojisDTO> customEmojisDTO = customEmojisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customEmojisDTO);
    }

    /**
     * {@code DELETE  /custom-emojis/:id} : delete the "id" customEmojis.
     *
     * @param id the id of the customEmojisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/custom-emojis/{id}")
    public ResponseEntity<Void> deleteCustomEmojis(@PathVariable Long id) {
        log.debug("REST request to delete CustomEmojis : {}", id);
        customEmojisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
