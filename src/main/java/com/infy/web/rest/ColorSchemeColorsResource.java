/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ColorSchemeColorsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ColorSchemeColorsDTO;

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
 * REST controller for managing {@link com.infy.domain.ColorSchemeColors}.
 */
@RestController
@RequestMapping("/api")
public class ColorSchemeColorsResource {

    private final Logger log = LoggerFactory.getLogger(ColorSchemeColorsResource.class);

    private static final String ENTITY_NAME = "colorSchemeColors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColorSchemeColorsService colorSchemeColorsService;

    public ColorSchemeColorsResource(ColorSchemeColorsService colorSchemeColorsService) {
        this.colorSchemeColorsService = colorSchemeColorsService;
    }

    /**
     * {@code POST  /color-scheme-colors} : Create a new colorSchemeColors.
     *
     * @param colorSchemeColorsDTO the colorSchemeColorsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colorSchemeColorsDTO, or with status {@code 400 (Bad Request)} if the colorSchemeColors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/color-scheme-colors")
    public ResponseEntity<ColorSchemeColorsDTO> createColorSchemeColors(@Valid @RequestBody ColorSchemeColorsDTO colorSchemeColorsDTO) throws URISyntaxException {
        log.debug("REST request to save ColorSchemeColors : {}", colorSchemeColorsDTO);
        if (colorSchemeColorsDTO.getId() != null) {
            throw new BadRequestAlertException("A new colorSchemeColors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColorSchemeColorsDTO result = colorSchemeColorsService.save(colorSchemeColorsDTO);
        return ResponseEntity.created(new URI("/api/color-scheme-colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /color-scheme-colors} : Updates an existing colorSchemeColors.
     *
     * @param colorSchemeColorsDTO the colorSchemeColorsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colorSchemeColorsDTO,
     * or with status {@code 400 (Bad Request)} if the colorSchemeColorsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colorSchemeColorsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/color-scheme-colors")
    public ResponseEntity<ColorSchemeColorsDTO> updateColorSchemeColors(@Valid @RequestBody ColorSchemeColorsDTO colorSchemeColorsDTO) throws URISyntaxException {
        log.debug("REST request to update ColorSchemeColors : {}", colorSchemeColorsDTO);
        if (colorSchemeColorsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColorSchemeColorsDTO result = colorSchemeColorsService.save(colorSchemeColorsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, colorSchemeColorsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /color-scheme-colors} : get all the colorSchemeColors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colorSchemeColors in body.
     */
    @GetMapping("/color-scheme-colors")
    public ResponseEntity<List<ColorSchemeColorsDTO>> getAllColorSchemeColors(Pageable pageable) {
        log.debug("REST request to get a page of ColorSchemeColors");
        Page<ColorSchemeColorsDTO> page = colorSchemeColorsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /color-scheme-colors/:id} : get the "id" colorSchemeColors.
     *
     * @param id the id of the colorSchemeColorsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colorSchemeColorsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/color-scheme-colors/{id}")
    public ResponseEntity<ColorSchemeColorsDTO> getColorSchemeColors(@PathVariable Long id) {
        log.debug("REST request to get ColorSchemeColors : {}", id);
        Optional<ColorSchemeColorsDTO> colorSchemeColorsDTO = colorSchemeColorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(colorSchemeColorsDTO);
    }

    /**
     * {@code DELETE  /color-scheme-colors/:id} : delete the "id" colorSchemeColors.
     *
     * @param id the id of the colorSchemeColorsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/color-scheme-colors/{id}")
    public ResponseEntity<Void> deleteColorSchemeColors(@PathVariable Long id) {
        log.debug("REST request to delete ColorSchemeColors : {}", id);
        colorSchemeColorsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
