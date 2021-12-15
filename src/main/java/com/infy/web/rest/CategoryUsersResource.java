/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryUsers}.
 */
@RestController
@RequestMapping("/api")
public class CategoryUsersResource {

    private final Logger log = LoggerFactory.getLogger(CategoryUsersResource.class);

    private static final String ENTITY_NAME = "categoryUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryUsersService categoryUsersService;

    public CategoryUsersResource(CategoryUsersService categoryUsersService) {
        this.categoryUsersService = categoryUsersService;
    }

    /**
     * {@code POST  /category-users} : Create a new categoryUsers.
     *
     * @param categoryUsersDTO the categoryUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryUsersDTO, or with status {@code 400 (Bad Request)} if the categoryUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-users")
    public ResponseEntity<CategoryUsersDTO> createCategoryUsers(@Valid @RequestBody CategoryUsersDTO categoryUsersDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryUsers : {}", categoryUsersDTO);
        if (categoryUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryUsersDTO result = categoryUsersService.save(categoryUsersDTO);
        return ResponseEntity.created(new URI("/api/category-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-users} : Updates an existing categoryUsers.
     *
     * @param categoryUsersDTO the categoryUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryUsersDTO,
     * or with status {@code 400 (Bad Request)} if the categoryUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-users")
    public ResponseEntity<CategoryUsersDTO> updateCategoryUsers(@Valid @RequestBody CategoryUsersDTO categoryUsersDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryUsers : {}", categoryUsersDTO);
        if (categoryUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryUsersDTO result = categoryUsersService.save(categoryUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-users} : get all the categoryUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryUsers in body.
     */
    @GetMapping("/category-users")
    public ResponseEntity<List<CategoryUsersDTO>> getAllCategoryUsers(Pageable pageable) {
        log.debug("REST request to get a page of CategoryUsers");
        Page<CategoryUsersDTO> page = categoryUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-users/:id} : get the "id" categoryUsers.
     *
     * @param id the id of the categoryUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-users/{id}")
    public ResponseEntity<CategoryUsersDTO> getCategoryUsers(@PathVariable Long id) {
        log.debug("REST request to get CategoryUsers : {}", id);
        Optional<CategoryUsersDTO> categoryUsersDTO = categoryUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryUsersDTO);
    }

    /**
     * {@code DELETE  /category-users/:id} : delete the "id" categoryUsers.
     *
     * @param id the id of the categoryUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-users/{id}")
    public ResponseEntity<Void> deleteCategoryUsers(@PathVariable Long id) {
        log.debug("REST request to delete CategoryUsers : {}", id);
        categoryUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
