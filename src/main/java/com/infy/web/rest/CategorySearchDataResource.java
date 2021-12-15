/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategorySearchDataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategorySearchDataDTO;

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
 * REST controller for managing {@link com.infy.domain.CategorySearchData}.
 */
@RestController
@RequestMapping("/api")
public class CategorySearchDataResource {

    private final Logger log = LoggerFactory.getLogger(CategorySearchDataResource.class);

    private static final String ENTITY_NAME = "categorySearchData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorySearchDataService categorySearchDataService;

    public CategorySearchDataResource(CategorySearchDataService categorySearchDataService) {
        this.categorySearchDataService = categorySearchDataService;
    }

    /**
     * {@code POST  /category-search-data} : Create a new categorySearchData.
     *
     * @param categorySearchDataDTO the categorySearchDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorySearchDataDTO, or with status {@code 400 (Bad Request)} if the categorySearchData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-search-data")
    public ResponseEntity<CategorySearchDataDTO> createCategorySearchData(@Valid @RequestBody CategorySearchDataDTO categorySearchDataDTO) throws URISyntaxException {
        log.debug("REST request to save CategorySearchData : {}", categorySearchDataDTO);
        if (categorySearchDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorySearchData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorySearchDataDTO result = categorySearchDataService.save(categorySearchDataDTO);
        return ResponseEntity.created(new URI("/api/category-search-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-search-data} : Updates an existing categorySearchData.
     *
     * @param categorySearchDataDTO the categorySearchDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorySearchDataDTO,
     * or with status {@code 400 (Bad Request)} if the categorySearchDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorySearchDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-search-data")
    public ResponseEntity<CategorySearchDataDTO> updateCategorySearchData(@Valid @RequestBody CategorySearchDataDTO categorySearchDataDTO) throws URISyntaxException {
        log.debug("REST request to update CategorySearchData : {}", categorySearchDataDTO);
        if (categorySearchDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorySearchDataDTO result = categorySearchDataService.save(categorySearchDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categorySearchDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-search-data} : get all the categorySearchData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorySearchData in body.
     */
    @GetMapping("/category-search-data")
    public ResponseEntity<List<CategorySearchDataDTO>> getAllCategorySearchData(Pageable pageable) {
        log.debug("REST request to get a page of CategorySearchData");
        Page<CategorySearchDataDTO> page = categorySearchDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-search-data/:id} : get the "id" categorySearchData.
     *
     * @param id the id of the categorySearchDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorySearchDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-search-data/{id}")
    public ResponseEntity<CategorySearchDataDTO> getCategorySearchData(@PathVariable Long id) {
        log.debug("REST request to get CategorySearchData : {}", id);
        Optional<CategorySearchDataDTO> categorySearchDataDTO = categorySearchDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorySearchDataDTO);
    }

    /**
     * {@code DELETE  /category-search-data/:id} : delete the "id" categorySearchData.
     *
     * @param id the id of the categorySearchDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-search-data/{id}")
    public ResponseEntity<Void> deleteCategorySearchData(@PathVariable Long id) {
        log.debug("REST request to delete CategorySearchData : {}", id);
        categorySearchDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
