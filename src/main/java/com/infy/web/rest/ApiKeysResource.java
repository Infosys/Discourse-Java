/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ApiKeysService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ApiKeysDTO;

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
 * REST controller for managing {@link com.infy.domain.ApiKeys}.
 */
@RestController
@RequestMapping("/api")
public class ApiKeysResource {

    private final Logger log = LoggerFactory.getLogger(ApiKeysResource.class);

    private static final String ENTITY_NAME = "apiKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiKeysService apiKeysService;

    public ApiKeysResource(ApiKeysService apiKeysService) {
        this.apiKeysService = apiKeysService;
    }

    /**
     * {@code POST  /api-keys} : Create a new apiKeys.
     *
     * @param apiKeysDTO the apiKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiKeysDTO, or with status {@code 400 (Bad Request)} if the apiKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/api-keys")
    public ResponseEntity<ApiKeysDTO> createApiKeys(@Valid @RequestBody ApiKeysDTO apiKeysDTO) throws URISyntaxException {
        log.debug("REST request to save ApiKeys : {}", apiKeysDTO);
        if (apiKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiKeysDTO result = apiKeysService.save(apiKeysDTO);
        return ResponseEntity.created(new URI("/api/api-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /api-keys} : Updates an existing apiKeys.
     *
     * @param apiKeysDTO the apiKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiKeysDTO,
     * or with status {@code 400 (Bad Request)} if the apiKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/api-keys")
    public ResponseEntity<ApiKeysDTO> updateApiKeys(@Valid @RequestBody ApiKeysDTO apiKeysDTO) throws URISyntaxException {
        log.debug("REST request to update ApiKeys : {}", apiKeysDTO);
        if (apiKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiKeysDTO result = apiKeysService.save(apiKeysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, apiKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /api-keys} : get all the apiKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiKeys in body.
     */
    @GetMapping("/api-keys")
    public ResponseEntity<List<ApiKeysDTO>> getAllApiKeys(Pageable pageable) {
        log.debug("REST request to get a page of ApiKeys");
        Page<ApiKeysDTO> page = apiKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /api-keys/:id} : get the "id" apiKeys.
     *
     * @param id the id of the apiKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/api-keys/{id}")
    public ResponseEntity<ApiKeysDTO> getApiKeys(@PathVariable Long id) {
        log.debug("REST request to get ApiKeys : {}", id);
        Optional<ApiKeysDTO> apiKeysDTO = apiKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiKeysDTO);
    }

    /**
     * {@code DELETE  /api-keys/:id} : delete the "id" apiKeys.
     *
     * @param id the id of the apiKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/api-keys/{id}")
    public ResponseEntity<Void> deleteApiKeys(@PathVariable Long id) {
        log.debug("REST request to delete ApiKeys : {}", id);
        apiKeysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
