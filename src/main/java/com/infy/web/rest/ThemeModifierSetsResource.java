/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ThemeModifierSetsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ThemeModifierSetsDTO;

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
 * REST controller for managing {@link com.infy.domain.ThemeModifierSets}.
 */
@RestController
@RequestMapping("/api")
public class ThemeModifierSetsResource {

    private final Logger log = LoggerFactory.getLogger(ThemeModifierSetsResource.class);

    private static final String ENTITY_NAME = "themeModifierSets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeModifierSetsService themeModifierSetsService;

    public ThemeModifierSetsResource(ThemeModifierSetsService themeModifierSetsService) {
        this.themeModifierSetsService = themeModifierSetsService;
    }

    /**
     * {@code POST  /theme-modifier-sets} : Create a new themeModifierSets.
     *
     * @param themeModifierSetsDTO the themeModifierSetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeModifierSetsDTO, or with status {@code 400 (Bad Request)} if the themeModifierSets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-modifier-sets")
    public ResponseEntity<ThemeModifierSetsDTO> createThemeModifierSets(@Valid @RequestBody ThemeModifierSetsDTO themeModifierSetsDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeModifierSets : {}", themeModifierSetsDTO);
        if (themeModifierSetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeModifierSets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeModifierSetsDTO result = themeModifierSetsService.save(themeModifierSetsDTO);
        return ResponseEntity.created(new URI("/api/theme-modifier-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-modifier-sets} : Updates an existing themeModifierSets.
     *
     * @param themeModifierSetsDTO the themeModifierSetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeModifierSetsDTO,
     * or with status {@code 400 (Bad Request)} if the themeModifierSetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeModifierSetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-modifier-sets")
    public ResponseEntity<ThemeModifierSetsDTO> updateThemeModifierSets(@Valid @RequestBody ThemeModifierSetsDTO themeModifierSetsDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeModifierSets : {}", themeModifierSetsDTO);
        if (themeModifierSetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemeModifierSetsDTO result = themeModifierSetsService.save(themeModifierSetsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeModifierSetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /theme-modifier-sets} : get all the themeModifierSets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeModifierSets in body.
     */
    @GetMapping("/theme-modifier-sets")
    public ResponseEntity<List<ThemeModifierSetsDTO>> getAllThemeModifierSets(Pageable pageable) {
        log.debug("REST request to get a page of ThemeModifierSets");
        Page<ThemeModifierSetsDTO> page = themeModifierSetsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-modifier-sets/:id} : get the "id" themeModifierSets.
     *
     * @param id the id of the themeModifierSetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeModifierSetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-modifier-sets/{id}")
    public ResponseEntity<ThemeModifierSetsDTO> getThemeModifierSets(@PathVariable Long id) {
        log.debug("REST request to get ThemeModifierSets : {}", id);
        Optional<ThemeModifierSetsDTO> themeModifierSetsDTO = themeModifierSetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeModifierSetsDTO);
    }

    /**
     * {@code DELETE  /theme-modifier-sets/:id} : delete the "id" themeModifierSets.
     *
     * @param id the id of the themeModifierSetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-modifier-sets/{id}")
    public ResponseEntity<Void> deleteThemeModifierSets(@PathVariable Long id) {
        log.debug("REST request to delete ThemeModifierSets : {}", id);
        themeModifierSetsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
