/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryGroups}.
 */
@RestController
@RequestMapping("/api")
public class CategoryGroupsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryGroupsResource.class);

    private static final String ENTITY_NAME = "categoryGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryGroupsService categoryGroupsService;

    public CategoryGroupsResource(CategoryGroupsService categoryGroupsService) {
        this.categoryGroupsService = categoryGroupsService;
    }

    /**
     * {@code POST  /category-groups} : Create a new categoryGroups.
     *
     * @param categoryGroupsDTO the categoryGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryGroupsDTO, or with status {@code 400 (Bad Request)} if the categoryGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-groups")
    public ResponseEntity<CategoryGroupsDTO> createCategoryGroups(@Valid @RequestBody CategoryGroupsDTO categoryGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryGroups : {}", categoryGroupsDTO);
        if (categoryGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryGroupsDTO result = categoryGroupsService.save(categoryGroupsDTO);
        return ResponseEntity.created(new URI("/api/category-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-groups} : Updates an existing categoryGroups.
     *
     * @param categoryGroupsDTO the categoryGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-groups")
    public ResponseEntity<CategoryGroupsDTO> updateCategoryGroups(@Valid @RequestBody CategoryGroupsDTO categoryGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryGroups : {}", categoryGroupsDTO);
        if (categoryGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryGroupsDTO result = categoryGroupsService.save(categoryGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-groups} : get all the categoryGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryGroups in body.
     */
    @GetMapping("/category-groups")
    public ResponseEntity<List<CategoryGroupsDTO>> getAllCategoryGroups(Pageable pageable) {
        log.debug("REST request to get a page of CategoryGroups");
        Page<CategoryGroupsDTO> page = categoryGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-groups/:id} : get the "id" categoryGroups.
     *
     * @param id the id of the categoryGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-groups/{id}")
    public ResponseEntity<CategoryGroupsDTO> getCategoryGroups(@PathVariable Long id) {
        log.debug("REST request to get CategoryGroups : {}", id);
        Optional<CategoryGroupsDTO> categoryGroupsDTO = categoryGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryGroupsDTO);
    }

    /**
     * {@code DELETE  /category-groups/:id} : delete the "id" categoryGroups.
     *
     * @param id the id of the categoryGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-groups/{id}")
    public ResponseEntity<Void> deleteCategoryGroups(@PathVariable Long id) {
        log.debug("REST request to delete CategoryGroups : {}", id);
        categoryGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
