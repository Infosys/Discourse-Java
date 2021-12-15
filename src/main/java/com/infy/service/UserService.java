/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.config.Constants;
import com.infy.domain.Authority;
import com.infy.domain.User;
import com.infy.repository.AuthorityRepository;
import com.infy.repository.UserRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.dto.UserDTO;
import com.infy.service.dto.UserStatsDTO;
import com.infy.service.dto.UsersDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final AuthorityRepository authorityRepository;

	private final UsersService usersService;

	private final UserStatsService userStatsService;

	public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
			UsersService usersService, UserStatsService userStatsService) {
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
		this.usersService = usersService;
		this.userStatsService = userStatsService;
	}

	/**
	 * Update basic information (first name, last name, email, language) for the
	 * current user.
	 *
	 * @param firstName first name of user.
	 * @param lastName  last name of user.
	 * @param email     email id of user.
	 * @param langKey   language key.
	 * @param imageUrl  image URL of user.
	 */
	public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
		SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByLogin).ifPresent(user -> {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			if (email != null) {
				user.setEmail(email.toLowerCase());
			}
			user.setLangKey(langKey);
			user.setImageUrl(imageUrl);
			log.debug("Changed Information for User: {}", user);
		});
	}

	@Transactional(readOnly = true)
	public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
		return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
	}

	@Transactional(readOnly = true)
	public Optional<User> getUserWithAuthoritiesByLogin(String login) {
		return userRepository.findOneWithAuthoritiesByLogin(login);
	}

	/**
	 * Gets a list of all the authorities.
	 *
	 * @return a list of all the authorities.
	 */
	@Transactional(readOnly = true)
	public List<String> getAuthorities() {
		return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
	}

	private User syncUserWithIdP(Map<String, Object> details, User user) {
		// save authorities in to sync user roles/groups between IdP and JHipster's
		// local database
		Collection<String> dbAuthorities = getAuthorities();
		Collection<String> userAuthorities = user.getAuthorities().stream().map(Authority::getName)
				.collect(Collectors.toList());
		for (String authority : userAuthorities) {
			if (!dbAuthorities.contains(authority)) {
				log.debug("Saving authority '{}' in local database", authority);
				Authority authorityToSave = new Authority();
				authorityToSave.setName(authority);
				authorityRepository.save(authorityToSave);
			}
		}
		// save account in to sync users between IdP and JHipster's local database
		Optional<User> existingUser = userRepository.findOneByLogin(user.getLogin());
		if (existingUser.isPresent()) {
			// if IdP sends last updated information, use it to determine if an update
			// should happen
			if (details.get("updated_at") != null) {
				Instant dbModifiedDate = existingUser.get().getLastModifiedDate();
				Instant idpModifiedDate = (Instant) details.get("updated_at");
				if (idpModifiedDate.isAfter(dbModifiedDate)) {
					log.debug("Updating user '{}' in local database", user.getLogin());
					updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getLangKey(),
							user.getImageUrl());
				}
				// no last updated info, blindly update
			} else {
				log.debug("Updating user '{}' in local database", user.getLogin());
				updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getLangKey(),
						user.getImageUrl());
			}
		} else {
			log.debug("Saving user '{}' in local database", user.getLogin());
			saveUsers(user);
			userRepository.save(user);
		}
		return user;
	}

	/**
	 * Returns the user from an OAuth 2.0 login or resource server with JWT.
	 * Synchronizes the user in the local repository.
	 *
	 * @param authToken the authentication token.
	 * @return the user from the authentication.
	 */
	@Transactional
	public UserDTO getUserFromAuthentication(AbstractAuthenticationToken authToken) {
		Map<String, Object> attributes;
		if (authToken instanceof OAuth2AuthenticationToken) {
			attributes = ((OAuth2AuthenticationToken) authToken).getPrincipal().getAttributes();
		} else if (authToken instanceof JwtAuthenticationToken) {
			attributes = ((JwtAuthenticationToken) authToken).getTokenAttributes();
		} else {
			throw new IllegalArgumentException("AuthenticationToken is not OAuth2 or JWT!");
		}
		User user = getUser(attributes);
		user.setAuthorities(authToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(authority -> {
			Authority auth = new Authority();
			auth.setName(authority);
			return auth;
		}).collect(Collectors.toSet()));
		return new UserDTO(syncUserWithIdP(attributes, user));
	}

	private static User getUser(Map<String, Object> details) {
		User user = new User();
		// handle resource server JWT, where sub claim is email and uid is ID
		if (details.get("uid") != null) {
			user.setId((String) details.get("uid"));
			user.setLogin((String) details.get("sub"));
		} else {
			user.setId((String) details.get("sub"));
		}
		if (details.get("preferred_username") != null) {
			user.setLogin(((String) details.get("preferred_username")));
		} else if (user.getLogin() == null) {
			user.setLogin(user.getId());
		}
		if (details.get("given_name") != null) {
			user.setFirstName((String) details.get("given_name"));
		}
		if (details.get("family_name") != null) {
			user.setLastName((String) details.get("family_name"));
		}
		if (details.get("email_verified") != null) {
			user.setActivated((Boolean) details.get("email_verified"));
		}
		if (details.get("email") != null) {
			user.setEmail(((String) details.get("email")).toLowerCase());
		} else {
			user.setEmail((String) details.get("sub"));
		}
		if (details.get("langKey") != null) {
			user.setLangKey((String) details.get("langKey"));
		} else if (details.get("locale") != null) {
			// trim off country code if it exists
			String locale = (String) details.get("locale");
			if (locale.contains("_")) {
				locale = locale.substring(0, locale.indexOf('_'));
			} else if (locale.contains("-")) {
				locale = locale.substring(0, locale.indexOf('-'));
			}
			user.setLangKey(locale.toLowerCase());
		} else {
			// set langKey to default if not specified by IdP
			user.setLangKey(Constants.DEFAULT_LANGUAGE);
		}
		if (details.get("picture") != null) {
			user.setImageUrl((String) details.get("picture"));
		}
		user.setActivated(true);
		return user;
	}

	private void saveUsers(User user) {

		UsersDTO usersDTO = new UsersDTO();

		usersDTO.setUsername(user.getLogin());
		usersDTO.setUsernameLower(user.getLogin().toLowerCase());
		usersDTO.setUserId(user.getId());
		usersDTO.setName(user.getFirstName());
		usersDTO.setPrivacyAccepted(false);
		usersDTO.setNotificarionSubscription(true);
		usersDTO.setActive(true);
		usersDTO.setAdmin(false);
		usersDTO.setModerator(false);
		usersDTO.setTrustLevel(1);
		usersDTO.setActive(true);
		usersDTO.setStaged(false);
		usersDTO.setSeenNotificationId(1L);
		usersDTO.setViews(1);
		usersDTO.setFlagLevel(1);
		usersDTO.setApproved(true);
		usersDTO.setApprovedAt(Instant.now());
		usersDTO.setLastSeenAt(Instant.now());

		user.getAuthorities().forEach(a -> {
			if (a.getName().equalsIgnoreCase("ROLE_ADMIN")) {
				usersDTO.setAdmin(true);
				usersDTO.setModerator(true);
			}
		});

		UsersDTO savedUsersDTO = usersService.save(usersDTO);

		UserStatsDTO userStatsDTO = new UserStatsDTO();
		userStatsDTO.setUserId(savedUsersDTO.getUserId());
		userStatsDTO.setLikesGiven(0);
		userStatsDTO.setLikesReceived(0);
		userStatsDTO.setDaysVisited(1);
		userStatsDTO.setNewSince(Instant.now());
		userStatsDTO.setPostCount(0);
		userStatsDTO.setTopicCount(0);
		userStatsDTO.setPostsReadCount(0);
		userStatsDTO.setTopicsEntered(0);
		userStatsDTO.setTimeRead(0);
		userStatsDTO.setBounceScore(1.0);
		userStatsDTO.setFlagsAgreed(0);
		userStatsDTO.setFlagsDisagreed(0);
		userStatsDTO.setFlagsIgnored(0);
		userStatsDTO.setFirstUnreadAt(Instant.now());
		userStatsDTO.setDistinctBadgeCount(0);
		userStatsDTO.setFirstUnreadPmAt(Instant.now());

		userStatsService.save(userStatsDTO);
	}
}
