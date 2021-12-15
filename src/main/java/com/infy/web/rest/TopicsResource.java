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

import com.infy.service.TopicsQueryService;
import com.infy.service.TopicsService;
import com.infy.service.dto.TopicsCriteria;
import com.infy.service.dto.TopicsDTO;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.TopicResponse;
import com.infy.service.model.UpdateTopicRequest;
import com.infy.service.model.UpdateTopicResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.infy.domain.Topics}.
 */
@RestController
@RequestMapping("/api")
public class TopicsResource {

	private final Logger log = LoggerFactory.getLogger(TopicsResource.class);

	private static final String ENTITY_NAME = "topics";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final TopicsService topicsService;

	private final TopicsQueryService topicsQueryService;

	public TopicsResource(TopicsService topicsService, TopicsQueryService topicsQueryService) {
		this.topicsService = topicsService;
		this.topicsQueryService = topicsQueryService;
	}

//	/**
//	 * {@code POST  /topics} : Create a new topics.
//	 *
//	 * @param topicsDTO the topicsDTO to create.
//	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
//	 *         body the new topicsDTO, or with status {@code 400 (Bad Request)} if
//	 *         the topics has already an ID.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PostMapping("/topics")
//	public ResponseEntity<TopicsDTO> createTopics(@Valid @RequestBody TopicsDTO topicsDTO) throws URISyntaxException {
//		log.debug("REST request to save Topics : {}", topicsDTO);
//		if (topicsDTO.getId() != null) {
//			throw new BadRequestAlertException("A new topics cannot already have an ID", ENTITY_NAME, "idexists");
//		}
//		TopicsDTO result = topicsService.save(topicsDTO);
//		return ResponseEntity
//				.created(new URI("/api/topics/" + result.getId())).headers(HeaderUtil
//						.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code PUT  /topics} : Updates an existing topics.
//	 *
//	 * @param topicsDTO the topicsDTO to update.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the updated topicsDTO, or with status {@code 400 (Bad Request)} if
//	 *         the topicsDTO is not valid, or with status
//	 *         {@code 500 (Internal Server Error)} if the topicsDTO couldn't be
//	 *         updated.
//	 * @throws URISyntaxException if the Location URI syntax is incorrect.
//	 */
//	@PutMapping("/topics")
//	public ResponseEntity<TopicsDTO> updateTopics(@Valid @RequestBody TopicsDTO topicsDTO) throws URISyntaxException {
//		log.debug("REST request to update Topics : {}", topicsDTO);
//		if (topicsDTO.getId() == null) {
//			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//		}
//		TopicsDTO result = topicsService.save(topicsDTO);
//		return ResponseEntity.ok().headers(
//				HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, topicsDTO.getId().toString()))
//				.body(result);
//	}
//
//	/**
//	 * {@code GET  /topics} : get all the topics.
//	 *
//	 * @param pageable the pagination information.
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
//	 *         of topics in body.
//	 */
//	@GetMapping("/topics")
//	public ResponseEntity<List<TopicsDTO>> getAllTopics(TopicsCriteria criteria, Pageable pageable) {
//		log.debug("REST request to get Topics by criteria: {}", criteria);
//		Page<TopicsDTO> page = topicsQueryService.findByCriteria(criteria, pageable);
//		HttpHeaders headers = PaginationUtil
//				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//		return ResponseEntity.ok().headers(headers).body(page.getContent());
//	}
//
//	/**
//	 * {@code GET  /topics/count} : count all the topics.
//	 *
//	 * @param criteria the criteria which the requested entities should match.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
//	 *         in body.
//	 */
//	@GetMapping("/topics/count")
//	public ResponseEntity<Long> countTopics(TopicsCriteria criteria) {
//		log.debug("REST request to count Topics by criteria: {}", criteria);
//		return ResponseEntity.ok().body(topicsQueryService.countByCriteria(criteria));
//	}
//
//	/**
//	 * {@code GET  /topics/:id} : get the "id" topics.
//	 *
//	 * @param id the id of the topicsDTO to retrieve.
//	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
//	 *         the topicsDTO, or with status {@code 404 (Not Found)}.
//	 */
//	@GetMapping("/topics/{id}")
//	public ResponseEntity<TopicsDTO> getTopics(@PathVariable Long id) {
//		log.debug("REST request to get Topics : {}", id);
//		Optional<TopicsDTO> topicsDTO = topicsService.findOne(id);
//		return ResponseUtil.wrapOrNotFound(topicsDTO);
//	}
//
//	/**
//	 * {@code DELETE  /topics/:id} : delete the "id" topics.
//	 *
//	 * @param id the id of the topicsDTO to delete.
//	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//	 */
//	@DeleteMapping("/topics/{id}")
//	public ResponseEntity<Void> deleteTopics(@PathVariable Long id) {
//		log.debug("REST request to delete Topics : {}", id);
//		topicsService.delete(id);
//		return ResponseEntity.noContent()
//				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
//				.build();
//	}

	@PutMapping("/topics/{id}")
	public ResponseEntity<UpdateTopicResponse> updateTopicsNew(@PathVariable Long id,
			@RequestBody UpdateTopicRequest updateTopicRequest) throws URISyntaxException {
		log.debug("REST request to update Topics : {}", updateTopicRequest);
		if (id == null || updateTopicRequest == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		UpdateTopicResponse result = topicsService.updateTopics(id, updateTopicRequest);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME,
				result.getUpdatedTopic().getId().toString())).body(result);
	}

	@GetMapping("/topics/{id}")
	public ResponseEntity<TopicResponse> getTopicAndResponse(@PathVariable Long id) {
		log.debug("REST request to get Topics : {}", id);
		Optional<TopicResponse> result = topicsService.findByIdAndResponse(id);
		return ResponseUtil.wrapOrNotFound(result);
	}

	@GetMapping("/topicsByCategory")
	public ResponseEntity<List<TopicOrPostResponse>> getAllTopicFromCategory(@RequestParam Long categoryId,
			Pageable pageable) {
		log.debug("REST request to get All Topics of Category : {}", categoryId);
		Page<TopicOrPostResponse> page = topicsService.findByCategoryIdAndResponse(categoryId, pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/topics")
	public ResponseEntity<List<TopicOrPostResponse>> getAllTopics(Pageable pageable) {
		log.debug("REST request to get a page of Topics");
		Page<TopicOrPostResponse> page = topicsService.findAllAndResponse(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/topics/user")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserTopics(Pageable pageable) {
		log.debug("REST request to get a page of Topics of current user");
		Page<TopicOrPostResponse> page = topicsService.findByCurrentLoginUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/topics-hide/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<Void> hideTopics(@PathVariable Long id) {
		log.debug("REST request to hide Topic : {}", id);
		topicsService.hideTopic(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/topics-unhide/{id}")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<Void> unHideTopics(@PathVariable Long id) {
		log.debug("REST request to unHide Topic : {}", id);
		topicsService.unHideTopic(id);
		return ResponseEntity.accepted().build();
	}

	@GetMapping("/topics/user/hidden")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserhiddenTopics(Pageable pageable) {
		log.debug("REST request to get a page of Hidden Topics of current user");
		Page<TopicOrPostResponse> page = topicsService.findAllHiddenTopicOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@GetMapping("/topics/user/private")
	public ResponseEntity<List<TopicOrPostResponse>> getAllCurrentUserPrivateTopics(Pageable pageable) {
		log.debug("REST request to get a page of Private Topics of current user");
		Page<TopicOrPostResponse> page = topicsService.findAllPrivateTopicOfCurrentUser(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/topics/private")
	public ResponseEntity<List<TopicOrPostResponse>> getAllPrivateTopics(Pageable pageable) {
		log.debug("REST request to get a page of Private Topics");
		Page<TopicOrPostResponse> page = topicsService.findAllPrivateTopics(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}
}
