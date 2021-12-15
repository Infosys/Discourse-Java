/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ThemeTranslationOverridesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ThemeTranslationOverridesDTO;

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
 * REST controller for managing {@link com.infy.domain.ThemeTranslationOverrides}.
 */
@RestController
@RequestMapping("/api")
public class ThemeTranslationOverridesResource {

    private final Logger log = LoggerFactory.getLogger(ThemeTranslationOverridesResource.class);

    private static final String ENTITY_NAME = "themeTranslationOverrides";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemeTranslationOverridesService themeTranslationOverridesService;

    public ThemeTranslationOverridesResource(ThemeTranslationOverridesService themeTranslationOverridesService) {
        this.themeTranslationOverridesService = themeTranslationOverridesService;
    }

    /**
     * {@code POST  /theme-translation-overrides} : Create a new themeTranslationOverrides.
     *
     * @param themeTranslationOverridesDTO the themeTranslationOverridesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themeTranslationOverridesDTO, or with status {@code 400 (Bad Request)} if the themeTranslationOverrides has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theme-translation-overrides")
    public ResponseEntity<ThemeTranslationOverridesDTO> createThemeTranslationOverrides(@Valid @RequestBody ThemeTranslationOverridesDTO themeTranslationOverridesDTO) throws URISyntaxException {
        log.debug("REST request to save ThemeTranslationOverrides : {}", themeTranslationOverridesDTO);
        if (themeTranslationOverridesDTO.getId() != null) {
            throw new BadRequestAlertException("A new themeTranslationOverrides cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemeTranslationOverridesDTO result = themeTranslationOverridesService.save(themeTranslationOverridesDTO);
        return ResponseEntity.created(new URI("/api/theme-translation-overrides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theme-translation-overrides} : Updates an existing themeTranslationOverrides.
     *
     * @param themeTranslationOverridesDTO the themeTranslationOverridesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themeTranslationOverridesDTO,
     * or with status {@code 400 (Bad Request)} if the themeTranslationOverridesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themeTranslationOverridesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theme-translation-overrides")
    public ResponseEntity<ThemeTranslationOverridesDTO> updateThemeTranslationOverrides(@Valid @RequestBody ThemeTranslationOverridesDTO themeTranslationOverridesDTO) throws URISyntaxException {
        log.debug("REST request to update ThemeTranslationOverrides : {}", themeTranslationOverridesDTO);
        if (themeTranslationOverridesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemeTranslationOverridesDTO result = themeTranslationOverridesService.save(themeTranslationOverridesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themeTranslationOverridesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /theme-translation-overrides} : get all the themeTranslationOverrides.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themeTranslationOverrides in body.
     */
    @GetMapping("/theme-translation-overrides")
    public ResponseEntity<List<ThemeTranslationOverridesDTO>> getAllThemeTranslationOverrides(Pageable pageable) {
        log.debug("REST request to get a page of ThemeTranslationOverrides");
        Page<ThemeTranslationOverridesDTO> page = themeTranslationOverridesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /theme-translation-overrides/:id} : get the "id" themeTranslationOverrides.
     *
     * @param id the id of the themeTranslationOverridesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themeTranslationOverridesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theme-translation-overrides/{id}")
    public ResponseEntity<ThemeTranslationOverridesDTO> getThemeTranslationOverrides(@PathVariable Long id) {
        log.debug("REST request to get ThemeTranslationOverrides : {}", id);
        Optional<ThemeTranslationOverridesDTO> themeTranslationOverridesDTO = themeTranslationOverridesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themeTranslationOverridesDTO);
    }

    /**
     * {@code DELETE  /theme-translation-overrides/:id} : delete the "id" themeTranslationOverrides.
     *
     * @param id the id of the themeTranslationOverridesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theme-translation-overrides/{id}")
    public ResponseEntity<Void> deleteThemeTranslationOverrides(@PathVariable Long id) {
        log.debug("REST request to delete ThemeTranslationOverrides : {}", id);
        themeTranslationOverridesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
