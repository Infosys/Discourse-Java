/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostReplyKeysService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostReplyKeysDTO;

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
 * REST controller for managing {@link com.infy.domain.PostReplyKeys}.
 */
@RestController
@RequestMapping("/api")
public class PostReplyKeysResource {

    private final Logger log = LoggerFactory.getLogger(PostReplyKeysResource.class);

    private static final String ENTITY_NAME = "postReplyKeys";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostReplyKeysService postReplyKeysService;

    public PostReplyKeysResource(PostReplyKeysService postReplyKeysService) {
        this.postReplyKeysService = postReplyKeysService;
    }

    /**
     * {@code POST  /post-reply-keys} : Create a new postReplyKeys.
     *
     * @param postReplyKeysDTO the postReplyKeysDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postReplyKeysDTO, or with status {@code 400 (Bad Request)} if the postReplyKeys has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-reply-keys")
    public ResponseEntity<PostReplyKeysDTO> createPostReplyKeys(@Valid @RequestBody PostReplyKeysDTO postReplyKeysDTO) throws URISyntaxException {
        log.debug("REST request to save PostReplyKeys : {}", postReplyKeysDTO);
        if (postReplyKeysDTO.getId() != null) {
            throw new BadRequestAlertException("A new postReplyKeys cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostReplyKeysDTO result = postReplyKeysService.save(postReplyKeysDTO);
        return ResponseEntity.created(new URI("/api/post-reply-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-reply-keys} : Updates an existing postReplyKeys.
     *
     * @param postReplyKeysDTO the postReplyKeysDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postReplyKeysDTO,
     * or with status {@code 400 (Bad Request)} if the postReplyKeysDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postReplyKeysDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-reply-keys")
    public ResponseEntity<PostReplyKeysDTO> updatePostReplyKeys(@Valid @RequestBody PostReplyKeysDTO postReplyKeysDTO) throws URISyntaxException {
        log.debug("REST request to update PostReplyKeys : {}", postReplyKeysDTO);
        if (postReplyKeysDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostReplyKeysDTO result = postReplyKeysService.save(postReplyKeysDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postReplyKeysDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-reply-keys} : get all the postReplyKeys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postReplyKeys in body.
     */
    @GetMapping("/post-reply-keys")
    public ResponseEntity<List<PostReplyKeysDTO>> getAllPostReplyKeys(Pageable pageable) {
        log.debug("REST request to get a page of PostReplyKeys");
        Page<PostReplyKeysDTO> page = postReplyKeysService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-reply-keys/:id} : get the "id" postReplyKeys.
     *
     * @param id the id of the postReplyKeysDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postReplyKeysDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-reply-keys/{id}")
    public ResponseEntity<PostReplyKeysDTO> getPostReplyKeys(@PathVariable Long id) {
        log.debug("REST request to get PostReplyKeys : {}", id);
        Optional<PostReplyKeysDTO> postReplyKeysDTO = postReplyKeysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postReplyKeysDTO);
    }

    /**
     * {@code DELETE  /post-reply-keys/:id} : delete the "id" postReplyKeys.
     *
     * @param id the id of the postReplyKeysDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-reply-keys/{id}")
    public ResponseEntity<Void> deletePostReplyKeys(@PathVariable Long id) {
        log.debug("REST request to delete PostReplyKeys : {}", id);
        postReplyKeysService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
