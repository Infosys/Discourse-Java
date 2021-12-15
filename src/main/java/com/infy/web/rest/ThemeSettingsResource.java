/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ThemeSettingsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ThemeSettingsDTO;

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
 * REST controller for managing {@link com.infy.domain.ThemeSettings}.
 */
@RestController
@RequestMapping("/api")
public class ThemeSettingsResource {

    private final Logger log = LoggerFactory.getLogger(ThemeSettingsResource.class);

    private static final String ENTITY_NAME = "themeSettings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeSettingsService themeSettingsService;

    public ThemeSettingsResource(ThemeSettingsService themeSettingsService) {
        this.themeSettingsService = themeSettingsService;
    }

    /**
     * {@code POST  /theme-settings} : Create a new themeSettings.
     *
     * @param themeSettingsDTO the themeSettingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeSettingsDTO, or with status {@code 400 (Bad Request)} if the themeSettings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-settings")
    public ResponseEntity<ThemeSettingsDTO> createThemeSettings(@Valid @RequestBody ThemeSettingsDTO themeSettingsDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeSettings : {}", themeSettingsDTO);
        if (themeSettingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeSettings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeSettingsDTO result = themeSettingsService.save(themeSettingsDTO);
        return ResponseEntity.created(new URI("/api/theme-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-settings} : Updates an existing themeSettings.
     *
     * @param themeSettingsDTO the themeSettingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeSettingsDTO,
     * or with status {@code 400 (Bad Request)} if the themeSettingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeSettingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-settings")
    public ResponseEntity<ThemeSettingsDTO> updateThemeSettings(@Valid @RequestBody ThemeSettingsDTO themeSettingsDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeSettings : {}", themeSettingsDTO);
        if (themeSettingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemeSettingsDTO result = themeSettingsService.save(themeSettingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeSettingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /theme-settings} : get all the themeSettings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeSettings in body.
     */
    @GetMapping("/theme-settings")
    public ResponseEntity<List<ThemeSettingsDTO>> getAllThemeSettings(Pageable pageable) {
        log.debug("REST request to get a page of ThemeSettings");
        Page<ThemeSettingsDTO> page = themeSettingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-settings/:id} : get the "id" themeSettings.
     *
     * @param id the id of the themeSettingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeSettingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-settings/{id}")
    public ResponseEntity<ThemeSettingsDTO> getThemeSettings(@PathVariable Long id) {
        log.debug("REST request to get ThemeSettings : {}", id);
        Optional<ThemeSettingsDTO> themeSettingsDTO = themeSettingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeSettingsDTO);
    }

    /**
     * {@code DELETE  /theme-settings/:id} : delete the "id" themeSettings.
     *
     * @param id the id of the themeSettingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-settings/{id}")
    public ResponseEntity<Void> deleteThemeSettings(@PathVariable Long id) {
        log.debug("REST request to delete ThemeSettings : {}", id);
        themeSettingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
