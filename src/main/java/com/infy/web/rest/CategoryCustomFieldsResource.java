/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryCustomFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryCustomFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryCustomFields}.
 */
@RestController
@RequestMapping("/api")
public class CategoryCustomFieldsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryCustomFieldsResource.class);

    private static final String ENTITY_NAME = "categoryCustomFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryCustomFieldsService categoryCustomFieldsService;

    public CategoryCustomFieldsResource(CategoryCustomFieldsService categoryCustomFieldsService) {
        this.categoryCustomFieldsService = categoryCustomFieldsService;
    }

    /**
     * {@code POST  /category-custom-fields} : Create a new categoryCustomFields.
     *
     * @param categoryCustomFieldsDTO the categoryCustomFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryCustomFieldsDTO, or with status {@code 400 (Bad Request)} if the categoryCustomFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-custom-fields")
    public ResponseEntity<CategoryCustomFieldsDTO> createCategoryCustomFields(@Valid @RequestBody CategoryCustomFieldsDTO categoryCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryCustomFields : {}", categoryCustomFieldsDTO);
        if (categoryCustomFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryCustomFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryCustomFieldsDTO result = categoryCustomFieldsService.save(categoryCustomFieldsDTO);
        return ResponseEntity.created(new URI("/api/category-custom-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-custom-fields} : Updates an existing categoryCustomFields.
     *
     * @param categoryCustomFieldsDTO the categoryCustomFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryCustomFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryCustomFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryCustomFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-custom-fields")
    public ResponseEntity<CategoryCustomFieldsDTO> updateCategoryCustomFields(@Valid @RequestBody CategoryCustomFieldsDTO categoryCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryCustomFields : {}", categoryCustomFieldsDTO);
        if (categoryCustomFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryCustomFieldsDTO result = categoryCustomFieldsService.save(categoryCustomFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryCustomFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-custom-fields} : get all the categoryCustomFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryCustomFields in body.
     */
    @GetMapping("/category-custom-fields")
    public ResponseEntity<List<CategoryCustomFieldsDTO>> getAllCategoryCustomFields(Pageable pageable) {
        log.debug("REST request to get a page of CategoryCustomFields");
        Page<CategoryCustomFieldsDTO> page = categoryCustomFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-custom-fields/:id} : get the "id" categoryCustomFields.
     *
     * @param id the id of the categoryCustomFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryCustomFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-custom-fields/{id}")
    public ResponseEntity<CategoryCustomFieldsDTO> getCategoryCustomFields(@PathVariable Long id) {
        log.debug("REST request to get CategoryCustomFields : {}", id);
        Optional<CategoryCustomFieldsDTO> categoryCustomFieldsDTO = categoryCustomFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryCustomFieldsDTO);
    }

    /**
     * {@code DELETE  /category-custom-fields/:id} : delete the "id" categoryCustomFields.
     *
     * @param id the id of the categoryCustomFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-custom-fields/{id}")
    public ResponseEntity<Void> deleteCategoryCustomFields(@PathVariable Long id) {
        log.debug("REST request to delete CategoryCustomFields : {}", id);
        categoryCustomFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
