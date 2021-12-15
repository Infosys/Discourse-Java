/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostRepliesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostRepliesDTO;

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
 * REST controller for managing {@link com.infy.domain.PostReplies}.
 */
@RestController
@RequestMapping("/api")
public class PostRepliesResource {

    private final Logger log = LoggerFactory.getLogger(PostRepliesResource.class);

    private static final String ENTITY_NAME = "postReplies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostRepliesService postRepliesService;

    public PostRepliesResource(PostRepliesService postRepliesService) {
        this.postRepliesService = postRepliesService;
    }

    /**
     * {@code POST  /post-replies} : Create a new postReplies.
     *
     * @param postRepliesDTO the postRepliesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postRepliesDTO, or with status {@code 400 (Bad Request)} if the postReplies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-replies")
    public ResponseEntity<PostRepliesDTO> createPostReplies(@RequestBody PostRepliesDTO postRepliesDTO) throws URISyntaxException {
        log.debug("REST request to save PostReplies : {}", postRepliesDTO);
        if (postRepliesDTO.getId() != null) {
            throw new BadRequestAlertException("A new postReplies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostRepliesDTO result = postRepliesService.save(postRepliesDTO);
        return ResponseEntity.created(new URI("/api/post-replies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-replies} : Updates an existing postReplies.
     *
     * @param postRepliesDTO the postRepliesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postRepliesDTO,
     * or with status {@code 400 (Bad Request)} if the postRepliesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postRepliesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-replies")
    public ResponseEntity<PostRepliesDTO> updatePostReplies(@RequestBody PostRepliesDTO postRepliesDTO) throws URISyntaxException {
        log.debug("REST request to update PostReplies : {}", postRepliesDTO);
        if (postRepliesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostRepliesDTO result = postRepliesService.save(postRepliesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postRepliesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-replies} : get all the postReplies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postReplies in body.
     */
    @GetMapping("/post-replies")
    public ResponseEntity<List<PostRepliesDTO>> getAllPostReplies(Pageable pageable) {
        log.debug("REST request to get a page of PostReplies");
        Page<PostRepliesDTO> page = postRepliesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-replies/:id} : get the "id" postReplies.
     *
     * @param id the id of the postRepliesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postRepliesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-replies/{id}")
    public ResponseEntity<PostRepliesDTO> getPostReplies(@PathVariable Long id) {
        log.debug("REST request to get PostReplies : {}", id);
        Optional<PostRepliesDTO> postRepliesDTO = postRepliesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postRepliesDTO);
    }

    /**
     * {@code DELETE  /post-replies/:id} : delete the "id" postReplies.
     *
     * @param id the id of the postRepliesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-replies/{id}")
    public ResponseEntity<Void> deletePostReplies(@PathVariable Long id) {
        log.debug("REST request to delete PostReplies : {}", id);
        postRepliesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
