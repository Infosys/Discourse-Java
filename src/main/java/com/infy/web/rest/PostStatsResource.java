/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostStatsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostStatsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostStats}.
 */
@RestController
@RequestMapping("/api")
public class PostStatsResource {

    private final Logger log = LoggerFactory.getLogger(PostStatsResource.class);

    private static final String ENTITY_NAME = "postStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostStatsService postStatsService;

    public PostStatsResource(PostStatsService postStatsService) {
        this.postStatsService = postStatsService;
    }

    /**
     * {@code POST  /post-stats} : Create a new postStats.
     *
     * @param postStatsDTO the postStatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postStatsDTO, or with status {@code 400 (Bad Request)} if the postStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-stats")
    public ResponseEntity<PostStatsDTO> createPostStats(@RequestBody PostStatsDTO postStatsDTO) throws URISyntaxException {
        log.debug("REST request to save PostStats : {}", postStatsDTO);
        if (postStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostStatsDTO result = postStatsService.save(postStatsDTO);
        return ResponseEntity.created(new URI("/api/post-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-stats} : Updates an existing postStats.
     *
     * @param postStatsDTO the postStatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postStatsDTO,
     * or with status {@code 400 (Bad Request)} if the postStatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postStatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-stats")
    public ResponseEntity<PostStatsDTO> updatePostStats(@RequestBody PostStatsDTO postStatsDTO) throws URISyntaxException {
        log.debug("REST request to update PostStats : {}", postStatsDTO);
        if (postStatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostStatsDTO result = postStatsService.save(postStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-stats} : get all the postStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postStats in body.
     */
    @GetMapping("/post-stats")
    public ResponseEntity<List<PostStatsDTO>> getAllPostStats(Pageable pageable) {
        log.debug("REST request to get a page of PostStats");
        Page<PostStatsDTO> page = postStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-stats/:id} : get the "id" postStats.
     *
     * @param id the id of the postStatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postStatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-stats/{id}")
    public ResponseEntity<PostStatsDTO> getPostStats(@PathVariable Long id) {
        log.debug("REST request to get PostStats : {}", id);
        Optional<PostStatsDTO> postStatsDTO = postStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postStatsDTO);
    }

    /**
     * {@code DELETE  /post-stats/:id} : delete the "id" postStats.
     *
     * @param id the id of the postStatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-stats/{id}")
    public ResponseEntity<Void> deletePostStats(@PathVariable Long id) {
        log.debug("REST request to delete PostStats : {}", id);
        postStatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
