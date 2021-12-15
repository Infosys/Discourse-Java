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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.PostActionTypesService;
import com.infy.service.dto.PostActionTypesDTO;
import com.infy.service.model.CreatePostActionTypeRequest;
import com.infy.service.model.PostActionTypeResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.PostActionTypes}.
 */
@RestController
@RequestMapping("/api")
public class PostActionTypesResource {

    private final Logger log = LoggerFactory.getLogger(PostActionTypesResource.class);

    private static final String ENTITY_NAME = "postActionTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostActionTypesService postActionTypesService;

    public PostActionTypesResource(PostActionTypesService postActionTypesService) {
        this.postActionTypesService = postActionTypesService;
    }

    /**
     * {@code POST  /post-action-types} : Create a new postActionTypes.
     *
     * @param postActionTypesDTO the postActionTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postActionTypesDTO, or with status {@code 400 (Bad Request)} if the postActionTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-action-types")
    public ResponseEntity<PostActionTypesDTO> createPostActionTypes(@Valid @RequestBody PostActionTypesDTO postActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to save PostActionTypes : {}", postActionTypesDTO);
        if (postActionTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new postActionTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostActionTypesDTO result = postActionTypesService.save(postActionTypesDTO);
        return ResponseEntity.created(new URI("/api/post-action-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-action-types} : Updates an existing postActionTypes.
     *
     * @param postActionTypesDTO the postActionTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postActionTypesDTO,
     * or with status {@code 400 (Bad Request)} if the postActionTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postActionTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-action-types")
    public ResponseEntity<PostActionTypesDTO> updatePostActionTypes(@Valid @RequestBody PostActionTypesDTO postActionTypesDTO) throws URISyntaxException {
        log.debug("REST request to update PostActionTypes : {}", postActionTypesDTO);
        if (postActionTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostActionTypesDTO result = postActionTypesService.save(postActionTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postActionTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-action-types} : get all the postActionTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postActionTypes in body.
     */
    @GetMapping("/post-action-types")
    public ResponseEntity<List<PostActionTypesDTO>> getAllPostActionTypes(Pageable pageable) {
        log.debug("REST request to get a page of PostActionTypes");
        Page<PostActionTypesDTO> page = postActionTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-action-types/:id} : get the "id" postActionTypes.
     *
     * @param id the id of the postActionTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postActionTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-action-types/{id}")
    public ResponseEntity<PostActionTypesDTO> getPostActionTypes(@PathVariable Long id) {
        log.debug("REST request to get PostActionTypes : {}", id);
        Optional<PostActionTypesDTO> postActionTypesDTO = postActionTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postActionTypesDTO);
    }

    /**
     * {@code DELETE  /post-action-types/:id} : delete the "id" postActionTypes.
     *
     * @param id the id of the postActionTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-action-types/{id}")
    public ResponseEntity<Void> deletePostActionTypes(@PathVariable Long id) {
        log.debug("REST request to delete PostActionTypes : {}", id);
        postActionTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/post-action-types-new")
    public ResponseEntity<PostActionTypeResponse> createPostActionTypes(@Valid @RequestBody CreatePostActionTypeRequest createPostActionTypeRequest) throws URISyntaxException {
        log.debug("REST request to save PostActionTypes : {}", createPostActionTypeRequest);
        PostActionTypeResponse result = postActionTypesService.saveAndResponse(createPostActionTypeRequest);
        return ResponseEntity.created(new URI("/api/post-action-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
}
