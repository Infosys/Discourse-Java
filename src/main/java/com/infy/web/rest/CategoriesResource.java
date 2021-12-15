/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.CategoriesQueryService;
import com.infy.service.CategoriesService;
import com.infy.service.dto.CategoriesCriteria;
import com.infy.service.dto.CategoriesDTO;
import com.infy.service.model.CategoryList;
import com.infy.service.model.CategoryResponse;
import com.infy.service.model.CreateCategoryRequest;
import com.infy.service.model.UpdateCategoryResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Categories}.
 */
@RestController
@RequestMapping("/api")
public class CategoriesResource {

	private final Logger log = LoggerFactory.getLogger(CategoriesResource.class);

	private static final String ENTITY_NAME = "categories";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final CategoriesService categoriesService;

	private final CategoriesQueryService categoriesQueryService;

	public CategoriesResource(CategoriesService categoriesService, CategoriesQueryService categoriesQueryService) {
		this.categoriesService = categoriesService;
		this.categoriesQueryService = categoriesQueryService;
	}

//	/**
//	 * {@code POST  /categories} : Create a new categories.
//	 *
//	 * @param categoriesDTO the categoriesDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new categoriesDTO, or with status {@code 400 (Bad Request)}
//	 *         if the categories has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/categories")
//	public ResponseEntity<CategoriesDTO> createCategories(@Valid @RequestBody CategoriesDTO categoriesDTO)
//			throws URISyntaxException {
//		log.debug("REST request to save Categories : {}", categoriesDTO);
//		if (categoriesDTO.getId() != null) {
//			throw new BadRequestAlertException("A new categories cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		CategoriesDTO result = categoriesService.save(categoriesDTO);
//		return ResponseEntity
//				.created(new URI("/api/categories/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /categories} : Updates an existing categories.
//	 *
//	 * @param categoriesDTO the categoriesDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated categoriesDTO, or with status {@code 400 (Bad Request)}
//	 *         if the categoriesDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the categoriesDTO couldn't be
//	 *         updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/categories")
//	public ResponseEntity<CategoriesDTO> updateCategories(@Valid @RequestBody CategoriesDTO categoriesDTO)
//			throws URISyntaxException {
//		log.debug("REST request to update Categories : {}", categoriesDTO);
//		if (categoriesDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		CategoriesDTO result = categoriesService.save(categoriesDTO);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
//				categoriesDTO.getId().toString())).body(result);
//	}
//
//	/**
//	 * {@code GET  /categories} : get all the categories.
//	 *
//	 * @param pageable the pagination information.
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of categories in body.
//	 */
//	@GetMapping("/categories")
//	public ResponseEntity<List<CategoriesDTO>> getAllCategories(CategoriesCriteria criteria, Pageable pageable) {
//		log.debug("REST request to get Categories by criteria: {}", criteria);
//		Page<CategoriesDTO> page = categoriesQueryService.findByCriteria(criteria, pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /categories/count} : count all the categories.
//	 *
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
//	 *         in body.
//	 */
//	@GetMapping("/categories/count")
//	public ResponseEntity<Long> countCategories(CategoriesCriteria criteria) {
//		log.debug("REST request to count Categories by criteria: {}", criteria);
//		return ResponseEntity.ok().body(categoriesQueryService.countByCriteria(criteria));
//	}
//
//	/**
//	 * {@code GET  /categories/:id} : get the "id" categories.
//	 *
//	 * @param id the id of the categoriesDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the categoriesDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/categories/{id}")
//	public ResponseEntity<CategoriesDTO> getCategories(@PathVariable Long id) {
//		log.debug("REST request to get Categories : {}", id);
//		Optional<CategoriesDTO> categoriesDTO = categoriesService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(categoriesDTO);
//	}
//
//	/**
//	 * {@code DELETE  /categories/:id} : delete the "id" categories.
//	 *
//	 * @param id the id of the categoriesDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/categories/{id}")
//	public ResponseEntity<Void> deleteCategories(@PathVariable Long id) {
//		log.debug("REST request to delete Categories : {}", id);
//		categoriesService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@PostMapping("/categories")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<CategoryResponse> createCategoriesNew(
			@Valid @RequestBody CreateCategoryRequest createCategoryRequest) throws URISyntaxException {
		log.debug("REST request to save Categories : {}", createCategoryRequest);
		CategoryResponse result = categoriesService.saveAndResponse(createCategoryRequest);
		return ResponseEntity
				.created(new URI("/api/categories/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	@GetMapping("/categories")
	public ResponseEntity<CategoryList> getAllCategoriesNew(Pageable pageable) {
		log.debug("REST request to get All Categories ");
		CategoryList result = categoriesService.getAllCategories(pageable);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/categories/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<UpdateCategoryResponse> updateCategories(@PathVariable Long id,
			@Valid @RequestBody CreateCategoryRequest createCategoryRequest) throws URISyntaxException {
		log.debug("REST request to update Categories : {}", createCategoryRequest);
		if (id == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		UpdateCategoryResponse result = categoriesService.updateCategory(id, createCategoryRequest);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				result.getCategory().getId().toString())).body(result);
	}
}
