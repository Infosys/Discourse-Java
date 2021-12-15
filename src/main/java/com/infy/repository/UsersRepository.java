/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import com.infy.domain.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	List<Users> findByUsername(String username);

	Optional<Users> findByUserId(String userId);

	@Modifying
	@Query(value = "UPDATE Users u SET u.fireBaseToken = null WHERE u.fireBaseToken=?1 AND u.userId!=?2")
	void invalidOtherUsersToken(String token,String userId);

}
