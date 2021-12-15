/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ChildThemesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ChildThemesDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.ChildThemes}.
 */
@RestController
@RequestMapping("/api")
public class ChildThemesResource {

    private final Logger log = LoggerFactory.getLogger(ChildThemesResource.class);

    private static final String ENTITY_NAME = "childThemes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChildThemesService childThemesService;

    public ChildThemesResource(ChildThemesService childThemesService) {
        this.childThemesService = childThemesService;
    }

    /**
     * {@code POST  /child-themes} : Create a new childThemes.
     *
     * @param childThemesDTO the childThemesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new childThemesDTO, or with status {@code 400 (Bad Request)} if the childThemes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/child-themes")
    public ResponseEntity<ChildThemesDTO> createChildThemes(@RequestBody ChildThemesDTO childThemesDTO) throws URISyntaxException {
        log.debug("REST request to save ChildThemes : {}", childThemesDTO);
        if (childThemesDTO.getId() != null) {
            throw new BadRequestAlertException("A new childThemes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChildThemesDTO result = childThemesService.save(childThemesDTO);
        return ResponseEntity.created(new URI("/api/child-themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /child-themes} : Updates an existing childThemes.
     *
     * @param childThemesDTO the childThemesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated childThemesDTO,
     * or with status {@code 400 (Bad Request)} if the childThemesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the childThemesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/child-themes")
    public ResponseEntity<ChildThemesDTO> updateChildThemes(@RequestBody ChildThemesDTO childThemesDTO) throws URISyntaxException {
        log.debug("REST request to update ChildThemes : {}", childThemesDTO);
        if (childThemesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChildThemesDTO result = childThemesService.save(childThemesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, childThemesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /child-themes} : get all the childThemes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of childThemes in body.
     */
    @GetMapping("/child-themes")
    public ResponseEntity<List<ChildThemesDTO>> getAllChildThemes(Pageable pageable) {
        log.debug("REST request to get a page of ChildThemes");
        Page<ChildThemesDTO> page = childThemesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /child-themes/:id} : get the "id" childThemes.
     *
     * @param id the id of the childThemesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the childThemesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/child-themes/{id}")
    public ResponseEntity<ChildThemesDTO> getChildThemes(@PathVariable Long id) {
        log.debug("REST request to get ChildThemes : {}", id);
        Optional<ChildThemesDTO> childThemesDTO = childThemesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(childThemesDTO);
    }

    /**
     * {@code DELETE  /child-themes/:id} : delete the "id" childThemes.
     *
     * @param id the id of the childThemesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/child-themes/{id}")
    public ResponseEntity<Void> deleteChildThemes(@PathVariable Long id) {
        log.debug("REST request to delete ChildThemes : {}", id);
        childThemesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
