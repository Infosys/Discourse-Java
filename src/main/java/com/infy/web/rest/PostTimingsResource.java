/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostTimingsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostTimingsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostTimings}.
 */
@RestController
@RequestMapping("/api")
public class PostTimingsResource {

    private final Logger log = LoggerFactory.getLogger(PostTimingsResource.class);

    private static final String ENTITY_NAME = "postTimings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostTimingsService postTimingsService;

    public PostTimingsResource(PostTimingsService postTimingsService) {
        this.postTimingsService = postTimingsService;
    }

    /**
     * {@code POST  /post-timings} : Create a new postTimings.
     *
     * @param postTimingsDTO the postTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postTimingsDTO, or with status {@code 400 (Bad Request)} if the postTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-timings")
    public ResponseEntity<PostTimingsDTO> createPostTimings(@Valid @RequestBody PostTimingsDTO postTimingsDTO) throws URISyntaxException {
        log.debug("REST request to save PostTimings : {}", postTimingsDTO);
        if (postTimingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostTimingsDTO result = postTimingsService.save(postTimingsDTO);
        return ResponseEntity.created(new URI("/api/post-timings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-timings} : Updates an existing postTimings.
     *
     * @param postTimingsDTO the postTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the postTimingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-timings")
    public ResponseEntity<PostTimingsDTO> updatePostTimings(@Valid @RequestBody PostTimingsDTO postTimingsDTO) throws URISyntaxException {
        log.debug("REST request to update PostTimings : {}", postTimingsDTO);
        if (postTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostTimingsDTO result = postTimingsService.save(postTimingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postTimingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-timings} : get all the postTimings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postTimings in body.
     */
    @GetMapping("/post-timings")
    public ResponseEntity<List<PostTimingsDTO>> getAllPostTimings(Pageable pageable) {
        log.debug("REST request to get a page of PostTimings");
        Page<PostTimingsDTO> page = postTimingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-timings/:id} : get the "id" postTimings.
     *
     * @param id the id of the postTimingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postTimingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-timings/{id}")
    public ResponseEntity<PostTimingsDTO> getPostTimings(@PathVariable Long id) {
        log.debug("REST request to get PostTimings : {}", id);
        Optional<PostTimingsDTO> postTimingsDTO = postTimingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postTimingsDTO);
    }

    /**
     * {@code DELETE  /post-timings/:id} : delete the "id" postTimings.
     *
     * @param id the id of the postTimingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-timings/{id}")
    public ResponseEntity<Void> deletePostTimings(@PathVariable Long id) {
        log.debug("REST request to delete PostTimings : {}", id);
        postTimingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
