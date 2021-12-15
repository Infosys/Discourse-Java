/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ColorSchemesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ColorSchemesDTO;

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
 * REST controller for managing {@link com.infy.domain.ColorSchemes}.
 */
@RestController
@RequestMapping("/api")
public class ColorSchemesResource {

    private final Logger log = LoggerFactory.getLogger(ColorSchemesResource.class);

    private static final String ENTITY_NAME = "colorSchemes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColorSchemesService colorSchemesService;

    public ColorSchemesResource(ColorSchemesService colorSchemesService) {
        this.colorSchemesService = colorSchemesService;
    }

    /**
     * {@code POST  /color-schemes} : Create a new colorSchemes.
     *
     * @param colorSchemesDTO the colorSchemesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colorSchemesDTO, or with status {@code 400 (Bad Request)} if the colorSchemes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/color-schemes")
    public ResponseEntity<ColorSchemesDTO> createColorSchemes(@Valid @RequestBody ColorSchemesDTO colorSchemesDTO) throws URISyntaxException {
        log.debug("REST request to save ColorSchemes : {}", colorSchemesDTO);
        if (colorSchemesDTO.getId() != null) {
            throw new BadRequestAlertException("A new colorSchemes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColorSchemesDTO result = colorSchemesService.save(colorSchemesDTO);
        return ResponseEntity.created(new URI("/api/color-schemes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /color-schemes} : Updates an existing colorSchemes.
     *
     * @param colorSchemesDTO the colorSchemesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colorSchemesDTO,
     * or with status {@code 400 (Bad Request)} if the colorSchemesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colorSchemesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/color-schemes")
    public ResponseEntity<ColorSchemesDTO> updateColorSchemes(@Valid @RequestBody ColorSchemesDTO colorSchemesDTO) throws URISyntaxException {
        log.debug("REST request to update ColorSchemes : {}", colorSchemesDTO);
        if (colorSchemesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColorSchemesDTO result = colorSchemesService.save(colorSchemesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, colorSchemesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /color-schemes} : get all the colorSchemes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colorSchemes in body.
     */
    @GetMapping("/color-schemes")
    public ResponseEntity<List<ColorSchemesDTO>> getAllColorSchemes(Pageable pageable) {
        log.debug("REST request to get a page of ColorSchemes");
        Page<ColorSchemesDTO> page = colorSchemesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /color-schemes/:id} : get the "id" colorSchemes.
     *
     * @param id the id of the colorSchemesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colorSchemesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/color-schemes/{id}")
    public ResponseEntity<ColorSchemesDTO> getColorSchemes(@PathVariable Long id) {
        log.debug("REST request to get ColorSchemes : {}", id);
        Optional<ColorSchemesDTO> colorSchemesDTO = colorSchemesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colorSchemesDTO);
    }

    /**
     * {@code DELETE  /color-schemes/:id} : delete the "id" colorSchemes.
     *
     * @param id the id of the colorSchemesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/color-schemes/{id}")
    public ResponseEntity<Void> deleteColorSchemes(@PathVariable Long id) {
        log.debug("REST request to delete ColorSchemes : {}", id);
        colorSchemesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
