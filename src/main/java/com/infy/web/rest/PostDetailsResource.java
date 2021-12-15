/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostDetailsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostDetailsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostDetails}.
 */
@RestController
@RequestMapping("/api")
public class PostDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PostDetailsResource.class);

    private static final String ENTITY_NAME = "postDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostDetailsService postDetailsService;

    public PostDetailsResource(PostDetailsService postDetailsService) {
        this.postDetailsService = postDetailsService;
    }

    /**
     * {@code POST  /post-details} : Create a new postDetails.
     *
     * @param postDetailsDTO the postDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postDetailsDTO, or with status {@code 400 (Bad Request)} if the postDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-details")
    public ResponseEntity<PostDetailsDTO> createPostDetails(@RequestBody PostDetailsDTO postDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save PostDetails : {}", postDetailsDTO);
        if (postDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostDetailsDTO result = postDetailsService.save(postDetailsDTO);
        return ResponseEntity.created(new URI("/api/post-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-details} : Updates an existing postDetails.
     *
     * @param postDetailsDTO the postDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the postDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-details")
    public ResponseEntity<PostDetailsDTO> updatePostDetails(@RequestBody PostDetailsDTO postDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update PostDetails : {}", postDetailsDTO);
        if (postDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostDetailsDTO result = postDetailsService.save(postDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-details} : get all the postDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postDetails in body.
     */
    @GetMapping("/post-details")
    public ResponseEntity<List<PostDetailsDTO>> getAllPostDetails(Pageable pageable) {
        log.debug("REST request to get a page of PostDetails");
        Page<PostDetailsDTO> page = postDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-details/:id} : get the "id" postDetails.
     *
     * @param id the id of the postDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-details/{id}")
    public ResponseEntity<PostDetailsDTO> getPostDetails(@PathVariable Long id) {
        log.debug("REST request to get PostDetails : {}", id);
        Optional<PostDetailsDTO> postDetailsDTO = postDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postDetailsDTO);
    }

    /**
     * {@code DELETE  /post-details/:id} : delete the "id" postDetails.
     *
     * @param id the id of the postDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-details/{id}")
    public ResponseEntity<Void> deletePostDetails(@PathVariable Long id) {
        log.debug("REST request to delete PostDetails : {}", id);
        postDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
