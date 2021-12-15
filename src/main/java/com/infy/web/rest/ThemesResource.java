/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ThemesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ThemesDTO;

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
 * REST controller for managing {@link com.infy.domain.Themes}.
 */
@RestController
@RequestMapping("/api")
public class ThemesResource {

    private final Logger log = LoggerFactory.getLogger(ThemesResource.class);

    private static final String ENTITY_NAME = "themes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThemesService themesService;

    public ThemesResource(ThemesService themesService) {
        this.themesService = themesService;
    }

    /**
     * {@code POST  /themes} : Create a new themes.
     *
     * @param themesDTO the themesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new themesDTO, or with status {@code 400 (Bad Request)} if the themes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/themes")
    public ResponseEntity<ThemesDTO> createThemes(@Valid @RequestBody ThemesDTO themesDTO) throws URISyntaxException {
        log.debug("REST request to save Themes : {}", themesDTO);
        if (themesDTO.getId() != null) {
            throw new BadRequestAlertException("A new themes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThemesDTO result = themesService.save(themesDTO);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /themes} : Updates an existing themes.
     *
     * @param themesDTO the themesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated themesDTO,
     * or with status {@code 400 (Bad Request)} if the themesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the themesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/themes")
    public ResponseEntity<ThemesDTO> updateThemes(@Valid @RequestBody ThemesDTO themesDTO) throws URISyntaxException {
        log.debug("REST request to update Themes : {}", themesDTO);
        if (themesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ThemesDTO result = themesService.save(themesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, themesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /themes} : get all the themes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of themes in body.
     */
    @GetMapping("/themes")
    public ResponseEntity<List<ThemesDTO>> getAllThemes(Pageable pageable) {
        log.debug("REST request to get a page of Themes");
        Page<ThemesDTO> page = themesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /themes/:id} : get the "id" themes.
     *
     * @param id the id of the themesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the themesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/themes/{id}")
    public ResponseEntity<ThemesDTO> getThemes(@PathVariable Long id) {
        log.debug("REST request to get Themes : {}", id);
        Optional<ThemesDTO> themesDTO = themesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(themesDTO);
    }

    /**
     * {@code DELETE  /themes/:id} : delete the "id" themes.
     *
     * @param id the id of the themesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/themes/{id}")
    public ResponseEntity<Void> deleteThemes(@PathVariable Long id) {
        log.debug("REST request to delete Themes : {}", id);
        themesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
