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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.service.PostsQueryService;
import com.infy.service.PostsService;
import com.infy.service.dto.PostsCriteria;
import com.infy.service.dto.PostsDTO;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.UpdatePostRequest;
import com.infy.service.model.UpdatePostResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Posts}.
 */
@RestController
@RequestMapping("/api")
public class PostsResource {

	private final Logger log = LoggerFactory.getLogger(PostsResource.class);

	private static final String ENTITY_NAME = "posts";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PostsService postsService;

	private final PostsQueryService postsQueryService;

	public PostsResource(PostsService postsService, PostsQueryService postsQueryService) {
		this.postsService = postsService;
		this.postsQueryService = postsQueryService;
	}

//	/**
//	 * {@code POST  /posts} : Create a new posts.
//	 *
//	 * @param postsDTO the postsDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new postsDTO, or with status {@code 400 (Bad Request)} if
//	 *         the posts has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/posts")
//	public ResponseEntity<PostsDTO> createPosts(@Valid @RequestBody PostsDTO postsDTO) throws URISyntaxException {
//		log.debug("REST request to save Posts : {}", postsDTO);
//		if (postsDTO.getId() != null) {
//			throw new BadRequestAlertException("A new posts cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		PostsDTO result = postsService.save(postsDTO);
//		return ResponseEntity
//				.created(new URI("/api/posts/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /posts} : Updates an existing posts.
//	 *
//	 * @param postsDTO the postsDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated postsDTO, or with status {@code 400 (Bad Request)} if the
//	 *         postsDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the postsDTO couldn't be
//	 *         updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/posts")
//	public ResponseEntity<PostsDTO> updatePosts(@Valid @RequestBody PostsDTO postsDTO) throws URISyntaxException {
//		log.debug("REST request to update Posts : {}", postsDTO);
//		if (postsDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		PostsDTO result = postsService.save(postsDTO);
//		return ResponseEntity.ok().headers(
//				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postsDTO.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code GET  /posts} : get all the posts.
//	 *
//	 * @param pageable the pagination information.
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of posts in body.
//	 */
//	@GetMapping("/posts")
//	public ResponseEntity<List<PostsDTO>> getAllPosts(PostsCriteria criteria, Pageable pageable) {
//		log.debug("REST request to get Posts by criteria: {}", criteria);
//		Page<PostsDTO> page = postsQueryService.findByCriteria(criteria, pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /posts/count} : count all the posts.
//	 *
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
//	 *         in body.
//	 */
//	@GetMapping("/posts/count")
//	public ResponseEntity<Long> countPosts(PostsCriteria criteria) {
//		log.debug("REST request to count Posts by criteria: {}", criteria);
//		return ResponseEntity.ok().body(postsQueryService.countByCriteria(criteria));
//	}
//
//	/**
//	 * {@code GET  /posts/:id} : get the "id" posts.
//	 *
//	 * @param id the id of the postsDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the postsDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/posts/{id}")
//	public ResponseEntity<PostsDTO> getPosts(@PathVariable Long id) {
//		log.debug("REST request to get Posts : {}", id);
//		Optional<PostsDTO> postsDTO = postsService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(postsDTO);
//	}

	/**
	 * {@code DELETE  /posts/:id} : delete the "id" posts.
	 *
	 * @param id the id of the postsDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Void> deletePosts(@PathVariable Long id) {
		log.debug("REST request to delete Posts : {}", id);
		postsService.safeDeletePost(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
				.build();
	}

	@PutMapping("/posts/{id}")
	public ResponseEntity<UpdatePostResponse> updatePostsNew(@PathVariable Long id,
			@Valid @RequestBody UpdatePostRequest updatePostRequest) {
		log.debug("REST request to update post : {}", updatePostRequest);

		if (id == null || updatePostRequest == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		UpdatePostResponse updatePostResponse = postsService.updatePosts(id, updatePostRequest);
		return ResponseEntity.ok().body(updatePostResponse);
	}

	@GetMapping("/posts/{id}")
	public ResponseEntity<TopicOrPostResponse> getPostAndResponse(@PathVariable Long id) {
		log.debug("REST request to get Post : {}", id);
		Optional<TopicOrPostResponse> result = postsService.findByIdAndResponse(id);
		return ResponseUtil.wrapOrNotFound(result);
	}

	@GetMapping("/postsByTopic")
	public ResponseEntity<List<TopicOrPostResponse>> getAllPostsByTopic(@RequestParam Long topicId, Pageable pageable) {
		log.debug("REST request to get a page of Posts By Topic : {}", topicId);
		Page<TopicOrPostResponse> page = postsService.findByTopicIdAndResponse(topicId, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts")
	public ResponseEntity<List<TopicOrPostResponse>> getAllPosts(Pageable pageable) {
		log.debug("REST request to get a page of Posts");
		Page<TopicOrPostResponse> page = postsService.findAllAndResponse(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts/replies/{id}")
	public ResponseEntity<List<TopicOrPostResponse>> getAllReplyPosts(@PathVariable Long id, Pageable pageable) {
		log.debug("REST request to get a page of Posts reply");
		if (id == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Page<TopicOrPostResponse> page = postsService.findAllReplyPosts(id, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts-hide/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<Void> hidePosts(@PathVariable Long id) {
		log.debug("REST request to hide Post : {}", id);
		postsService.hidePost(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/posts-unhide/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<Void> unHidePosts(@PathVariable Long id) {
		log.debug("REST request to unHide Post : {}", id);
		postsService.unHidePost(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/posts/user")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserPosts(Pageable pageable) {
		log.debug("REST request to get a page of Posts of current user");
		Page<TopicOrPostResponse> page = postsService.findByCurrentLoginUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts/user/likes")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserLikedPosts(Pageable pageable) {
		log.debug("REST request to get a page of Liked Posts of current user");
		Page<TopicOrPostResponse> page = postsService.findAllLikedPostOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts/user/reply")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserReplyPosts(Pageable pageable) {
		log.debug("REST request to get a page of Reply Posts of current user");
		Page<TopicOrPostResponse> page = postsService.findAllReplyPostOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts/user/hidden")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserHiddenPosts(Pageable pageable) {
		log.debug("REST request to get a page of Hidden Posts of current user");
		Page<TopicOrPostResponse> page = postsService.findAllHiddenPostOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/posts/user/private")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserPrivatePosts(Pageable pageable) {
		log.debug("REST request to get a page of Private Posts of current user");
		Page<TopicOrPostResponse> page = postsService.findAllPrivatePostOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
