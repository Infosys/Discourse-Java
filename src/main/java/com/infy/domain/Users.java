/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
public class Users extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@NotNull
	@Column(name = "username", nullable = false)
	private String username;

	@NotNull
	@Column(name = "userId", nullable = false)
	private String userId;

	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "seen_notification_id", nullable = false)
	private Long seenNotificationId;

	@Column(name = "last_posted_at")
	private Instant lastPostedAt;

	@Column(name = "password_hash")
	private String passwordHash;

	@Column(name = "salt")
	private String salt;

	@NotNull
	@Column(name = "active", nullable = false)
	private Boolean active;

	@NotNull
	@Column(name = "username_lower", nullable = false)
	private String usernameLower;

	@Column(name = "last_seen_at")
	private Instant lastSeenAt;

	@NotNull
	@Column(name = "admin", nullable = false)
	private Boolean admin;

	@Column(name = "last_emailed_at")
	private Instant lastEmailedAt;

	@NotNull
	@Column(name = "trust_level", nullable = false)
	private Integer trustLevel;

	@NotNull
	@Column(name = "approved", nullable = false)
	private Boolean approved;

	@Column(name = "approved_by_id")
	private String approvedById;

	@Column(name = "approved_at")
	private Instant approvedAt;

	@Column(name = "previous_visit_at")
	private Instant previousVisitAt;

	@Column(name = "suspended_at")
	private Instant suspendedAt;

	@Column(name = "suspended_till")
	private Instant suspendedTill;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@NotNull
	@Column(name = "views", nullable = false)
	private Integer views;

	@NotNull
	@Column(name = "flag_level", nullable = false)
	private Integer flagLevel;

	@Column(name = "ip_address")
	private String ipAddress;

	@Column(name = "moderator")
	private Boolean moderator;

	@Column(name = "title")
	private String title;

	@Column(name = "uploaded_avatar_id")
	private Long uploadedAvatarId;

	@Column(name = "locale")
	private String locale;

	@Column(name = "primary_group_id")
	private Long primaryGroupId;

	@Column(name = "registration_ip_address")
	private String registrationIpAddress;

	@NotNull
	@Column(name = "staged", nullable = false)
	private Boolean staged;

	@Column(name = "first_seen_at")
	private Instant firstSeenAt;

	@Column(name = "silenced_till")
	private Instant silencedTill;

	@Column(name = "group_locked_trust_level")
	private Integer groupLockedTrustLevel;

	@Column(name = "manual_locked_trust_level")
	private Integer manualLockedTrustLevel;

	@Column(name = "secure_identifier")
	private String secureIdentifier;

	@NotNull
	@Column(name = "privacy_accepted", nullable = false)
	private Boolean privacyAccepted;

	@Column(name = "fire_base_token")
	private String fireBaseToken;

	@NotNull
	@Column(name = "notification_subscription")
	private Boolean notificationSubscription;

	@ManyToOne
	@JsonIgnoreProperties(value = "users", allowSetters = true)
	private UserSecurityKeys userSecurityKeys;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public Users username(String username) {
		this.username = username;
		return this;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public Users userId(String userId) {
		this.userId = userId;
		return this;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public Users name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSeenNotificationId() {
		return seenNotificationId;
	}

	public Users seenNotificationId(Long seenNotificationId) {
		this.seenNotificationId = seenNotificationId;
		return this;
	}

	public void setSeenNotificationId(Long seenNotificationId) {
		this.seenNotificationId = seenNotificationId;
	}

	public Instant getLastPostedAt() {
		return lastPostedAt;
	}

	public Users lastPostedAt(Instant lastPostedAt) {
		this.lastPostedAt = lastPostedAt;
		return this;
	}

	public void setLastPostedAt(Instant lastPostedAt) {
		this.lastPostedAt = lastPostedAt;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public Users passwordHash(String passwordHash) {
		this.passwordHash = passwordHash;
		return this;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getSalt() {
		return salt;
	}

	public Users salt(String salt) {
		this.salt = salt;
		return this;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Boolean isActive() {
		return active;
	}

	public Users active(Boolean active) {
		this.active = active;
		return this;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getUsernameLower() {
		return usernameLower;
	}

	public Users usernameLower(String usernameLower) {
		this.usernameLower = usernameLower;
		return this;
	}

	public void setUsernameLower(String usernameLower) {
		this.usernameLower = usernameLower;
	}

	public Instant getLastSeenAt() {
		return lastSeenAt;
	}

	public Users lastSeenAt(Instant lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
		return this;
	}

	public void setLastSeenAt(Instant lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public Users admin(Boolean admin) {
		this.admin = admin;
		return this;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Instant getLastEmailedAt() {
		return lastEmailedAt;
	}

	public Users lastEmailedAt(Instant lastEmailedAt) {
		this.lastEmailedAt = lastEmailedAt;
		return this;
	}

	public void setLastEmailedAt(Instant lastEmailedAt) {
		this.lastEmailedAt = lastEmailedAt;
	}

	public Integer getTrustLevel() {
		return trustLevel;
	}

	public Users trustLevel(Integer trustLevel) {
		this.trustLevel = trustLevel;
		return this;
	}

	public void setTrustLevel(Integer trustLevel) {
		this.trustLevel = trustLevel;
	}

	public Boolean isApproved() {
		return approved;
	}

	public Users approved(Boolean approved) {
		this.approved = approved;
		return this;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public String getApprovedById() {
		return approvedById;
	}

	public Users approvedById(String approvedById) {
		this.approvedById = approvedById;
		return this;
	}

	public void setApprovedById(String approvedById) {
		this.approvedById = approvedById;
	}

	public Instant getApprovedAt() {
		return approvedAt;
	}

	public Users approvedAt(Instant approvedAt) {
		this.approvedAt = approvedAt;
		return this;
	}

	public void setApprovedAt(Instant approvedAt) {
		this.approvedAt = approvedAt;
	}

	public Instant getPreviousVisitAt() {
		return previousVisitAt;
	}

	public Users previousVisitAt(Instant previousVisitAt) {
		this.previousVisitAt = previousVisitAt;
		return this;
	}

	public void setPreviousVisitAt(Instant previousVisitAt) {
		this.previousVisitAt = previousVisitAt;
	}

	public Instant getSuspendedAt() {
		return suspendedAt;
	}

	public Users suspendedAt(Instant suspendedAt) {
		this.suspendedAt = suspendedAt;
		return this;
	}

	public void setSuspendedAt(Instant suspendedAt) {
		this.suspendedAt = suspendedAt;
	}

	public Instant getSuspendedTill() {
		return suspendedTill;
	}

	public Users suspendedTill(Instant suspendedTill) {
		this.suspendedTill = suspendedTill;
		return this;
	}

	public void setSuspendedTill(Instant suspendedTill) {
		this.suspendedTill = suspendedTill;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Users dateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		return this;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getViews() {
		return views;
	}

	public Users views(Integer views) {
		this.views = views;
		return this;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getFlagLevel() {
		return flagLevel;
	}

	public Users flagLevel(Integer flagLevel) {
		this.flagLevel = flagLevel;
		return this;
	}

	public void setFlagLevel(Integer flagLevel) {
		this.flagLevel = flagLevel;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Users ipAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean isModerator() {
		return moderator;
	}

	public Users moderator(Boolean moderator) {
		this.moderator = moderator;
		return this;
	}

	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}

	public String getTitle() {
		return title;
	}

	public Users title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUploadedAvatarId() {
		return uploadedAvatarId;
	}

	public Users uploadedAvatarId(Long uploadedAvatarId) {
		this.uploadedAvatarId = uploadedAvatarId;
		return this;
	}

	public void setUploadedAvatarId(Long uploadedAvatarId) {
		this.uploadedAvatarId = uploadedAvatarId;
	}

	public String getLocale() {
		return locale;
	}

	public Users locale(String locale) {
		this.locale = locale;
		return this;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Long getPrimaryGroupId() {
		return primaryGroupId;
	}

	public Users primaryGroupId(Long primaryGroupId) {
		this.primaryGroupId = primaryGroupId;
		return this;
	}

	public void setPrimaryGroupId(Long primaryGroupId) {
		this.primaryGroupId = primaryGroupId;
	}

	public String getRegistrationIpAddress() {
		return registrationIpAddress;
	}

	public Users registrationIpAddress(String registrationIpAddress) {
		this.registrationIpAddress = registrationIpAddress;
		return this;
	}

	public void setRegistrationIpAddress(String registrationIpAddress) {
		this.registrationIpAddress = registrationIpAddress;
	}

	public Boolean isStaged() {
		return staged;
	}

	public Users staged(Boolean staged) {
		this.staged = staged;
		return this;
	}

	public void setStaged(Boolean staged) {
		this.staged = staged;
	}

	public Instant getFirstSeenAt() {
		return firstSeenAt;
	}

	public Users firstSeenAt(Instant firstSeenAt) {
		this.firstSeenAt = firstSeenAt;
		return this;
	}

	public void setFirstSeenAt(Instant firstSeenAt) {
		this.firstSeenAt = firstSeenAt;
	}

	public Instant getSilencedTill() {
		return silencedTill;
	}

	public Users silencedTill(Instant silencedTill) {
		this.silencedTill = silencedTill;
		return this;
	}

	public void setSilencedTill(Instant silencedTill) {
		this.silencedTill = silencedTill;
	}

	public Integer getGroupLockedTrustLevel() {
		return groupLockedTrustLevel;
	}

	public Users groupLockedTrustLevel(Integer groupLockedTrustLevel) {
		this.groupLockedTrustLevel = groupLockedTrustLevel;
		return this;
	}

	public void setGroupLockedTrustLevel(Integer groupLockedTrustLevel) {
		this.groupLockedTrustLevel = groupLockedTrustLevel;
	}

	public Integer getManualLockedTrustLevel() {
		return manualLockedTrustLevel;
	}

	public Users manualLockedTrustLevel(Integer manualLockedTrustLevel) {
		this.manualLockedTrustLevel = manualLockedTrustLevel;
		return this;
	}

	public void setManualLockedTrustLevel(Integer manualLockedTrustLevel) {
		this.manualLockedTrustLevel = manualLockedTrustLevel;
	}

	public String getSecureIdentifier() {
		return secureIdentifier;
	}

	public Users secureIdentifier(String secureIdentifier) {
		this.secureIdentifier = secureIdentifier;
		return this;
	}

	public void setSecureIdentifier(String secureIdentifier) {
		this.secureIdentifier = secureIdentifier;
	}

	public Boolean isPrivacyAccepted() {
		return privacyAccepted;
	}

	public Users privacyAccepted(Boolean privacyAccepted) {
		this.privacyAccepted = privacyAccepted;
		return this;
	}

	public void setPrivacyAccepted(Boolean privacyAccepted) {
		this.privacyAccepted = privacyAccepted;
	}

	public String getFireBaseToken() {
		return fireBaseToken;
	}

	public Users fireBaseToken(String fireBaseToken) {
		this.fireBaseToken = fireBaseToken;
		return this;
	}

	public void setFireBaseToken(String fireBaseToken) {
		this.fireBaseToken = fireBaseToken;
	}

//    public Boolean isNoti() {
//        return privacyAccepted;
//    }
//
//    public Users privacyAccepted(Boolean privacyAccepted) {
//        this.privacyAccepted = privacyAccepted;
//        return this;
//    }
//
//    public void setPrivacyAccepted(Boolean privacyAccepted) {
//        this.privacyAccepted = privacyAccepted;
//    }

	public Boolean getNotificationSubscription() {
		return notificationSubscription;
	}

	public Users notificationSubscription(Boolean notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
		return this;
	}

	public void setNotificationSubscription(Boolean notificationSubscription) {
		this.notificationSubscription = notificationSubscription;
	}

	public UserSecurityKeys getUserSecurityKeys() {
		return userSecurityKeys;
	}

	public Users userSecurityKeys(UserSecurityKeys userSecurityKeys) {
		this.userSecurityKeys = userSecurityKeys;
		return this;
	}

	public void setUserSecurityKeys(UserSecurityKeys userSecurityKeys) {
		this.userSecurityKeys = userSecurityKeys;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Users)) {
			return false;
		}
		return id != null && id.equals(((Users) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Users{" + "id=" + getId() + ", username='" + getUsername() + "'" + ", userId='" + getUserId() + "'"
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
				+ getManualLockedTrustLevel() + ", privacyAccepted='" + isPrivacyAccepted() + "'" + ", fireBaseToken="
				+ ", privacyAccepted='" + isPrivacyAccepted() + "'" + getFireBaseToken() + ", secureIdentifier='"
				+ getSecureIdentifier() + "'" + "}";
	}
}
