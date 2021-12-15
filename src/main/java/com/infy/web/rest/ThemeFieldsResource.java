/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ThemeFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ThemeFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.ThemeFields}.
 */
@RestController
@RequestMapping("/api")
public class ThemeFieldsResource {

    private final Logger log = LoggerFactory.getLogger(ThemeFieldsResource.class);

    private static final String ENTITY_NAME = "themeFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeFieldsService themeFieldsService;

    public ThemeFieldsResource(ThemeFieldsService themeFieldsService) {
        this.themeFieldsService = themeFieldsService;
    }

    /**
     * {@code POST  /theme-fields} : Create a new themeFields.
     *
     * @param themeFieldsDTO the themeFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeFieldsDTO, or with status {@code 400 (Bad Request)} if the themeFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-fields")
    public ResponseEntity<ThemeFieldsDTO> createThemeFields(@Valid @RequestBody ThemeFieldsDTO themeFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeFields : {}", themeFieldsDTO);
        if (themeFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeFieldsDTO result = themeFieldsService.save(themeFieldsDTO);
        return ResponseEntity.created(new URI("/api/theme-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-fields} : Updates an existing themeFields.
     *
     * @param themeFieldsDTO the themeFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the themeFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-fields")
    public ResponseEntity<ThemeFieldsDTO> updateThemeFields(@Valid @RequestBody ThemeFieldsDTO themeFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeFields : {}", themeFieldsDTO);
        if (themeFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemeFieldsDTO result = themeFieldsService.save(themeFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /theme-fields} : get all the themeFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeFields in body.
     */
    @GetMapping("/theme-fields")
    public ResponseEntity<List<ThemeFieldsDTO>> getAllThemeFields(Pageable pageable) {
        log.debug("REST request to get a page of ThemeFields");
        Page<ThemeFieldsDTO> page = themeFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-fields/:id} : get the "id" themeFields.
     *
     * @param id the id of the themeFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-fields/{id}")
    public ResponseEntity<ThemeFieldsDTO> getThemeFields(@PathVariable Long id) {
        log.debug("REST request to get ThemeFields : {}", id);
        Optional<ThemeFieldsDTO> themeFieldsDTO = themeFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeFieldsDTO);
    }

    /**
     * {@code DELETE  /theme-fields/:id} : delete the "id" themeFields.
     *
     * @param id the id of the themeFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-fields/{id}")
    public ResponseEntity<Void> deleteThemeFields(@PathVariable Long id) {
        log.debug("REST request to delete ThemeFields : {}", id);
        themeFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
