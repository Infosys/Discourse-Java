/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.infy.domain.Users} entity.
 */
public class UsersDTO extends AbstractAuditingDTO implements Serializable {

	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String userId;

	private String name;

	@NotNull
	private Long seenNotificationId;

	private Instant lastPostedAt;

	private String passwordHash;

	private String salt;

	@NotNull
	private Boolean active;

	@NotNull
	private String usernameLower;

	private Instant lastSeenAt;

	@NotNull
	private Boolean admin;

	private Instant lastEmailedAt;

	@NotNull
	private Integer trustLevel;

	@NotNull
	private Boolean approved;

	private String approvedById;

	private Instant approvedAt;

	private Instant previousVisitAt;

	private Instant suspendedAt;

	private Instant suspendedTill;

	private LocalDate dateOfBirth;

	@NotNull
	private Integer views;

	@NotNull
	private Integer flagLevel;

	private String ipAddress;

	private Boolean moderator;

	private String title;

	private Long uploadedAvatarId;

	private String locale;

	private Long primaryGroupId;

	private String registrationIpAddress;

	@NotNull
	private Boolean staged;

	private Instant firstSeenAt;

	private Instant silencedTill;

	private Integer groupLockedTrustLevel;

	private Integer manualLockedTrustLevel;

	private String secureIdentifier;

	@NotNull
	private Boolean privacyAccepted;

	private String fireBaseToken;

	private Boolean notificationSubscription;

	private Long userSecurityKeysId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSeenNotificationId() {
		return seenNotificationId;
	}

	public void setSeenNotificationId(Long seenNotificationId) {
		this.seenNotificationId = seenNotificationId;
	}

	public Instant getLastPostedAt() {
		return lastPostedAt;
	}

	public void setLastPostedAt(Instant lastPostedAt) {
		this.lastPostedAt = lastPostedAt;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUsernameLower() {
		return usernameLower;
	}

	public void setUsernameLower(String usernameLower) {
		this.usernameLower = usernameLower;
	}

	public Instant getLastSeenAt() {
		return lastSeenAt;
	}

	public void setLastSeenAt(Instant lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Instant getLastEmailedAt() {
		return lastEmailedAt;
	}

	public void setLastEmailedAt(Instant lastEmailedAt) {
		this.lastEmailedAt = lastEmailedAt;
	}

	public Integer getTrustLevel() {
		return trustLevel;
	}

	public void setTrustLevel(Integer trustLevel) {
		this.trustLevel = trustLevel;
	}

	public Boolean isApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getApprovedById() {
		return approvedById;
	}

	public void setApprovedById(String approvedById) {
		this.approvedById = approvedById;
	}

	public Instant getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(Instant approvedAt) {
		this.approvedAt = approvedAt;
	}

	public Instant getPreviousVisitAt() {
		return previousVisitAt;
	}

	public void setPreviousVisitAt(Instant previousVisitAt) {
		this.previousVisitAt = previousVisitAt;
	}

	public Instant getSuspendedAt() {
		return suspendedAt;
	}

	public void setSuspendedAt(Instant suspendedAt) {
		this.suspendedAt = suspendedAt;
	}

	public Instant getSuspendedTill() {
		return suspendedTill;
	}

	public void setSuspendedTill(Instant suspendedTill) {
		this.suspendedTill = suspendedTill;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getFlagLevel() {
		return flagLevel;
	}

	public void setFlagLevel(Integer flagLevel) {
		this.flagLevel = flagLevel;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean isModerator() {
		return moderator;
	}

	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUploadedAvatarId() {
		return uploadedAvatarId;
	}

	public void setUploadedAvatarId(Long uploadedAvatarId) {
		this.uploadedAvatarId = uploadedAvatarId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Long getPrimaryGroupId() {
		return primaryGroupId;
	}

	public void setPrimaryGroupId(Long primaryGroupId) {
		this.primaryGroupId = primaryGroupId;
	}

	public String getRegistrationIpAddress() {
		return registrationIpAddress;
	}

	public void setRegistrationIpAddress(String registrationIpAddress) {
		this.registrationIpAddress = registrationIpAddress;
	}

	public Boolean isStaged() {
		return staged;
	}

	public void setStaged(Boolean staged) {
		this.staged = staged;
	}

	public Instant getFirstSeenAt() {
		return firstSeenAt;
	}

	public void setFirstSeenAt(Instant firstSeenAt) {
		this.firstSeenAt = firstSeenAt;
	}

	public Instant getSilencedTill() {
		return silencedTill;
	}

	public void setSilencedTill(Instant silencedTill) {
		this.silencedTill = silencedTill;
	}

	public Integer getGroupLockedTrustLevel() {
		return groupLockedTrustLevel;
	}

	public void setGroupLockedTrustLevel(Integer groupLockedTrustLevel) {
		this.groupLockedTrustLevel = groupLockedTrustLevel;
	}

	public Integer getManualLockedTrustLevel() {
		return manualLockedTrustLevel;
	}

	public void setManualLockedTrustLevel(Integer manualLockedTrustLevel) {
		this.manualLockedTrustLevel = manualLockedTrustLevel;
	}

	public String getSecureIdentifier() {
		return secureIdentifier;
	}

	public void setSecureIdentifier(String secureIdentifier) {
		this.secureIdentifier = secureIdentifier;
	}

	public Boolean getPrivacyAccepted() {
		return privacyAccepted;
	}

	public void setPrivacyAccepted(Boolean privacyAccepted) {
		this.privacyAccepted = privacyAccepted;
	}

	public String getFireBaseToken() {
		return fireBaseToken;
	}

	public void setFireBaseToken(String fireBaseToken) {
		this.fireBaseToken = fireBaseToken;
	}

	public Boolean getNotificationSubscription() {
		return notificationSubscription;
	}

	public void setNotificarionSubscription(Boolean notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
	}

	public Long getUserSecurityKeysId() {
		return userSecurityKeysId;
	}

	public void setUserSecurityKeysId(Long userSecurityKeysId) {
		this.userSecurityKeysId = userSecurityKeysId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UsersDTO)) {
			return false;
		}

		return id != null && id.equals(((UsersDTO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "UsersDTO{" + "id=" + getId() + ", username='" + getUsername() + "'" + ", userId='" + getUserId() + "'"
				+ ", name='" + getName() + "'" + ", seenNotificationId=" + getSeenNotificationId() + ", lastPostedAt='"
				+ getLastPostedAt() + "'" + ", passwordHash='" + getPasswordHash() + "'" + ", salt='" + getSalt() + "'"
				+ ", active='" + isActive() + "'" + ", usernameLower='" + getUsernameLower() + "'" + ", lastSeenAt='"
				+ getLastSeenAt() + "'" + ", admin='" + isAdmin() + "'" + ", lastEmailedAt='" + getLastEmailedAt() + "'"
				+ ", trustLevel=" + getTrustLevel() + ", approved='" + isApproved() + "'" + ", approvedById='"
				+ getApprovedById() + "'" + ", approvedAt='" + getApprovedAt() + "'" + ", previousVisitAt='"
				+ getPreviousVisitAt() + "'" + ", suspendedAt='" + getSuspendedAt() + "'" + ", suspendedTill='"
				+ getSuspendedTill() + "'" + ", dateOfBirth='" + getDateOfBirth() + "'" + ", views=" + getViews()
				+ ", flagLevel=" + getFlagLevel() + ", ipAddress='" + getIpAddress() + "'" + ", moderator='"
				+ isModerator() + "'" + ", title='" + getTitle() + "'" + ", uploadedAvatarId=" + getUploadedAvatarId()
				+ ", locale='" + getLocale() + "'" + ", primaryGroupId=" + getPrimaryGroupId()
				+ ", registrationIpAddress='" + getRegistrationIpAddress() + "'" + ", staged='" + isStaged() + "'"
				+ ", firstSeenAt='" + getFirstSeenAt() + "'" + ", silencedTill='" + getSilencedTill() + "'"
				+ ", groupLockedTrustLevel=" + getGroupLockedTrustLevel() + ", manualLockedTrustLevel="
				+ getManualLockedTrustLevel() + ", secureIdentifier='" + getSecureIdentifier() +
				", fireBaseToken='" + getFireBaseToken() +"'"
				+ ", privacyAccepted='" + getPrivacyAccepted() + "'" + ", userSecurityKeysId=" + getUserSecurityKeysId()
				+ ", notificationSubscription='" + getNotificationSubscription() + "'"
				+ "}";
	}
}
