/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoriesWebHooksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoriesWebHooksDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoriesWebHooks}.
 */
@RestController
@RequestMapping("/api")
public class CategoriesWebHooksResource {

    private final Logger log = LoggerFactory.getLogger(CategoriesWebHooksResource.class);

    private static final String ENTITY_NAME = "categoriesWebHooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriesWebHooksService categoriesWebHooksService;

    public CategoriesWebHooksResource(CategoriesWebHooksService categoriesWebHooksService) {
        this.categoriesWebHooksService = categoriesWebHooksService;
    }

    /**
     * {@code POST  /categories-web-hooks} : Create a new categoriesWebHooks.
     *
     * @param categoriesWebHooksDTO the categoriesWebHooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriesWebHooksDTO, or with status {@code 400 (Bad Request)} if the categoriesWebHooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categories-web-hooks")
    public ResponseEntity<CategoriesWebHooksDTO> createCategoriesWebHooks(@Valid @RequestBody CategoriesWebHooksDTO categoriesWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriesWebHooks : {}", categoriesWebHooksDTO);
        if (categoriesWebHooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriesWebHooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriesWebHooksDTO result = categoriesWebHooksService.save(categoriesWebHooksDTO);
        return ResponseEntity.created(new URI("/api/categories-web-hooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categories-web-hooks} : Updates an existing categoriesWebHooks.
     *
     * @param categoriesWebHooksDTO the categoriesWebHooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriesWebHooksDTO,
     * or with status {@code 400 (Bad Request)} if the categoriesWebHooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriesWebHooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categories-web-hooks")
    public ResponseEntity<CategoriesWebHooksDTO> updateCategoriesWebHooks(@Valid @RequestBody CategoriesWebHooksDTO categoriesWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriesWebHooks : {}", categoriesWebHooksDTO);
        if (categoriesWebHooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriesWebHooksDTO result = categoriesWebHooksService.save(categoriesWebHooksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoriesWebHooksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categories-web-hooks} : get all the categoriesWebHooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriesWebHooks in body.
     */
    @GetMapping("/categories-web-hooks")
    public ResponseEntity<List<CategoriesWebHooksDTO>> getAllCategoriesWebHooks(Pageable pageable) {
        log.debug("REST request to get a page of CategoriesWebHooks");
        Page<CategoriesWebHooksDTO> page = categoriesWebHooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categories-web-hooks/:id} : get the "id" categoriesWebHooks.
     *
     * @param id the id of the categoriesWebHooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriesWebHooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categories-web-hooks/{id}")
    public ResponseEntity<CategoriesWebHooksDTO> getCategoriesWebHooks(@PathVariable Long id) {
        log.debug("REST request to get CategoriesWebHooks : {}", id);
        Optional<CategoriesWebHooksDTO> categoriesWebHooksDTO = categoriesWebHooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriesWebHooksDTO);
    }

    /**
     * {@code DELETE  /categories-web-hooks/:id} : delete the "id" categoriesWebHooks.
     *
     * @param id the id of the categoriesWebHooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categories-web-hooks/{id}")
    public ResponseEntity<Void> deleteCategoriesWebHooks(@PathVariable Long id) {
        log.debug("REST request to delete CategoriesWebHooks : {}", id);
        categoriesWebHooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
