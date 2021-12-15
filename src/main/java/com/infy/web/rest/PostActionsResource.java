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

import com.infy.service.PostActionsService;
import com.infy.service.dto.PostActionsDTO;
import com.infy.service.model.CreatePostActionRequest;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.PostActions}.
 */
@RestController
@RequestMapping("/api")
public class PostActionsResource {

	private final Logger log = LoggerFactory.getLogger(PostActionsResource.class);

	private static final String ENTITY_NAME = "postActions";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PostActionsService postActionsService;

	public PostActionsResource(PostActionsService postActionsService) {
		this.postActionsService = postActionsService;
	}

//	/**
//	 * {@code POST  /post-actions} : Create a new postActions.
//	 *
//	 * @param postActionsDTO the postActionsDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new postActionsDTO, or with status {@code 400 (Bad Request)}
//	 *         if the postActions has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/post-actions")
//	public ResponseEntity<PostActionsDTO> createPostActions(@Valid @RequestBody PostActionsDTO postActionsDTO)
//			throws URISyntaxException {
//		log.debug("REST request to save PostActions : {}", postActionsDTO);
//		if (postActionsDTO.getId() != null) {
//			throw new BadRequestAlertException("A new postActions cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		PostActionsDTO result = postActionsService.save(postActionsDTO);
//		return ResponseEntity
//				.created(new URI("/api/post-actions/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /post-actions} : Updates an existing postActions.
//	 *
//	 * @param postActionsDTO the postActionsDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated postActionsDTO, or with status {@code 400 (Bad Request)}
//	 *         if the postActionsDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the postActionsDTO couldn't be
//	 *         updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/post-actions")
//	public ResponseEntity<PostActionsDTO> updatePostActions(@Valid @RequestBody PostActionsDTO postActionsDTO)
//			throws URISyntaxException {
//		log.debug("REST request to update PostActions : {}", postActionsDTO);
//		if (postActionsDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		PostActionsDTO result = postActionsService.save(postActionsDTO);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
//				postActionsDTO.getId().toString())).body(result);
//	}
//
//	/**
//	 * {@code GET  /post-actions} : get all the postActions.
//	 *
//	 * @param pageable the pagination information.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of postActions in body.
//	 */
//	@GetMapping("/post-actions")
//	public ResponseEntity<List<PostActionsDTO>> getAllPostActions(Pageable pageable) {
//		log.debug("REST request to get a page of PostActions");
//		Page<PostActionsDTO> page = postActionsService.findAll(pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /post-actions/:id} : get the "id" postActions.
//	 *
//	 * @param id the id of the postActionsDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the postActionsDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/post-actions/{id}")
//	public ResponseEntity<PostActionsDTO> getPostActions(@PathVariable Long id) {
//		log.debug("REST request to get PostActions : {}", id);
//		Optional<PostActionsDTO> postActionsDTO = postActionsService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(postActionsDTO);
//	}
//
//	/**
//	 * {@code DELETE  /post-actions/:id} : delete the "id" postActions.
//	 *
//	 * @param id the id of the postActionsDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/post-actions/{id}")
//	public ResponseEntity<Void> deletePostActions(@PathVariable Long id) {
//		log.debug("REST request to delete PostActions : {}", id);
//		postActionsService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@PostMapping("/post-actions")
	public ResponseEntity<TopicOrPostResponse> createPostActionsAndResponse(
			@Valid @RequestBody CreatePostActionRequest createPostActionRequest) throws URISyntaxException {
		log.debug("REST request to save PostActions : {}", createPostActionRequest);
		TopicOrPostResponse result = postActionsService.createPostAction(createPostActionRequest);
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/post-actions/reset")
	public ResponseEntity<TopicOrPostResponse> removePostActionsAndResponse(
			@Valid @RequestBody CreatePostActionRequest createPostActionRequest) throws URISyntaxException {
		log.debug("REST request to remove PostActions : {}", createPostActionRequest);
		TopicOrPostResponse result = postActionsService.removePostAction(createPostActionRequest);
		return ResponseEntity.ok().body(result);
	}
}
