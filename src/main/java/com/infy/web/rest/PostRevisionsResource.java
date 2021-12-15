/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostRevisionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostRevisionsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostRevisions}.
 */
@RestController
@RequestMapping("/api")
public class PostRevisionsResource {

    private final Logger log = LoggerFactory.getLogger(PostRevisionsResource.class);

    private static final String ENTITY_NAME = "postRevisions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostRevisionsService postRevisionsService;

    public PostRevisionsResource(PostRevisionsService postRevisionsService) {
        this.postRevisionsService = postRevisionsService;
    }

    /**
     * {@code POST  /post-revisions} : Create a new postRevisions.
     *
     * @param postRevisionsDTO the postRevisionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postRevisionsDTO, or with status {@code 400 (Bad Request)} if the postRevisions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-revisions")
    public ResponseEntity<PostRevisionsDTO> createPostRevisions(@Valid @RequestBody PostRevisionsDTO postRevisionsDTO) throws URISyntaxException {
        log.debug("REST request to save PostRevisions : {}", postRevisionsDTO);
        if (postRevisionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postRevisions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostRevisionsDTO result = postRevisionsService.save(postRevisionsDTO);
        return ResponseEntity.created(new URI("/api/post-revisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-revisions} : Updates an existing postRevisions.
     *
     * @param postRevisionsDTO the postRevisionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postRevisionsDTO,
     * or with status {@code 400 (Bad Request)} if the postRevisionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postRevisionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-revisions")
    public ResponseEntity<PostRevisionsDTO> updatePostRevisions(@Valid @RequestBody PostRevisionsDTO postRevisionsDTO) throws URISyntaxException {
        log.debug("REST request to update PostRevisions : {}", postRevisionsDTO);
        if (postRevisionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostRevisionsDTO result = postRevisionsService.save(postRevisionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postRevisionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-revisions} : get all the postRevisions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postRevisions in body.
     */
    @GetMapping("/post-revisions")
    public ResponseEntity<List<PostRevisionsDTO>> getAllPostRevisions(Pageable pageable) {
        log.debug("REST request to get a page of PostRevisions");
        Page<PostRevisionsDTO> page = postRevisionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-revisions/:id} : get the "id" postRevisions.
     *
     * @param id the id of the postRevisionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postRevisionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-revisions/{id}")
    public ResponseEntity<PostRevisionsDTO> getPostRevisions(@PathVariable Long id) {
        log.debug("REST request to get PostRevisions : {}", id);
        Optional<PostRevisionsDTO> postRevisionsDTO = postRevisionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postRevisionsDTO);
    }

    /**
     * {@code DELETE  /post-revisions/:id} : delete the "id" postRevisions.
     *
     * @param id the id of the postRevisionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-revisions/{id}")
    public ResponseEntity<Void> deletePostRevisions(@PathVariable Long id) {
        log.debug("REST request to delete PostRevisions : {}", id);
        postRevisionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
