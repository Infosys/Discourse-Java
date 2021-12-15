/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PluginStoreRowsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PluginStoreRowsDTO;

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
 * REST controller for managing {@link com.infy.domain.PluginStoreRows}.
 */
@RestController
@RequestMapping("/api")
public class PluginStoreRowsResource {

    private final Logger log = LoggerFactory.getLogger(PluginStoreRowsResource.class);

    private static final String ENTITY_NAME = "pluginStoreRows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PluginStoreRowsService pluginStoreRowsService;

    public PluginStoreRowsResource(PluginStoreRowsService pluginStoreRowsService) {
        this.pluginStoreRowsService = pluginStoreRowsService;
    }

    /**
     * {@code POST  /plugin-store-rows} : Create a new pluginStoreRows.
     *
     * @param pluginStoreRowsDTO the pluginStoreRowsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pluginStoreRowsDTO, or with status {@code 400 (Bad Request)} if the pluginStoreRows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plugin-store-rows")
    public ResponseEntity<PluginStoreRowsDTO> createPluginStoreRows(@Valid @RequestBody PluginStoreRowsDTO pluginStoreRowsDTO) throws URISyntaxException {
        log.debug("REST request to save PluginStoreRows : {}", pluginStoreRowsDTO);
        if (pluginStoreRowsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pluginStoreRows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PluginStoreRowsDTO result = pluginStoreRowsService.save(pluginStoreRowsDTO);
        return ResponseEntity.created(new URI("/api/plugin-store-rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plugin-store-rows} : Updates an existing pluginStoreRows.
     *
     * @param pluginStoreRowsDTO the pluginStoreRowsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pluginStoreRowsDTO,
     * or with status {@code 400 (Bad Request)} if the pluginStoreRowsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pluginStoreRowsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plugin-store-rows")
    public ResponseEntity<PluginStoreRowsDTO> updatePluginStoreRows(@Valid @RequestBody PluginStoreRowsDTO pluginStoreRowsDTO) throws URISyntaxException {
        log.debug("REST request to update PluginStoreRows : {}", pluginStoreRowsDTO);
        if (pluginStoreRowsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PluginStoreRowsDTO result = pluginStoreRowsService.save(pluginStoreRowsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pluginStoreRowsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /plugin-store-rows} : get all the pluginStoreRows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pluginStoreRows in body.
     */
    @GetMapping("/plugin-store-rows")
    public ResponseEntity<List<PluginStoreRowsDTO>> getAllPluginStoreRows(Pageable pageable) {
        log.debug("REST request to get a page of PluginStoreRows");
        Page<PluginStoreRowsDTO> page = pluginStoreRowsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plugin-store-rows/:id} : get the "id" pluginStoreRows.
     *
     * @param id the id of the pluginStoreRowsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pluginStoreRowsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plugin-store-rows/{id}")
    public ResponseEntity<PluginStoreRowsDTO> getPluginStoreRows(@PathVariable Long id) {
        log.debug("REST request to get PluginStoreRows : {}", id);
        Optional<PluginStoreRowsDTO> pluginStoreRowsDTO = pluginStoreRowsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pluginStoreRowsDTO);
    }

    /**
     * {@code DELETE  /plugin-store-rows/:id} : delete the "id" pluginStoreRows.
     *
     * @param id the id of the pluginStoreRowsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plugin-store-rows/{id}")
    public ResponseEntity<Void> deletePluginStoreRows(@PathVariable Long id) {
        log.debug("REST request to delete PluginStoreRows : {}", id);
        pluginStoreRowsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
