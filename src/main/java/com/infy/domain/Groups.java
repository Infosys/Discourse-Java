/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Groups.
 */
@Entity
@Table(name = "groups")
public class Groups extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "automatic", nullable = false)
    private Boolean automatic;

    @NotNull
    @Column(name = "user_count", nullable = false)
    private Integer userCount;

    @Column(name = "automatic_membership_email_domains")
    private String automaticMembershipEmailDomains;

    @NotNull
    @Column(name = "primary_group", nullable = false)
    private Boolean primaryGroup;

    @Column(name = "title")
    private String title;

    @Column(name = "grant_trust_level")
    private Integer grantTrustLevel;

    @Column(name = "incoming_email")
    private String incomingEmail;

    @NotNull
    @Column(name = "has_messages", nullable = false)
    private Boolean hasMessages;

    @Column(name = "flair_url")
    private String flairUrl;

    @Column(name = "flair_bg_color")
    private String flairBgColor;

    @Column(name = "flair_color")
    private String flairColor;

    @Column(name = "bio_raw")
    private String bioRaw;

    @Column(name = "bio_cooked")
    private String bioCooked;

    @NotNull
    @Column(name = "allow_membership_requests", nullable = false)
    private Boolean allowMembershipRequests;

    @Column(name = "full_name")
    private String fullName;

    @NotNull
    @Column(name = "default_notification_level", nullable = false)
    private Integer defaultNotificationLevel;

    @NotNull
    @Column(name = "visibility_level", nullable = false)
    private Integer visibilityLevel;

    @NotNull
    @Column(name = "public_exit", nullable = false)
    private Boolean publicExit;

    @NotNull
    @Column(name = "public_admission", nullable = false)
    private Boolean publicAdmission;

    @Column(name = "membership_request_template")
    private String membershipRequestTemplate;

    @Column(name = "messageable_level")
    private Integer messageableLevel;

    @Column(name = "mentionable_level")
    private Integer mentionableLevel;

    @Column(name = "smtp_server")
    private String smtpServer;

    @Column(name = "smtp_port")
    private Integer smtpPort;

    @Column(name = "smtp_ssl")
    private Boolean smtpSsl;

    @Column(name = "imap_server")
    private String imapServer;

    @Column(name = "imap_port")
    private Integer imapPort;

    @Column(name = "imap_ssl")
    private Boolean imapSsl;

    @NotNull
    @Column(name = "imap_mailbox_name", nullable = false)
    private String imapMailboxName;

    @NotNull
    @Column(name = "imap_uid_validity", nullable = false)
    private Integer imapUidValidity;

    @NotNull
    @Column(name = "imap_last_uid", nullable = false)
    private Integer imapLastUid;

    @Column(name = "email_username")
    private String emailUsername;

    @Column(name = "email_password")
    private String emailPassword;

    @NotNull
    @Column(name = "publish_read_state", nullable = false)
    private Boolean publishReadState;

    @NotNull
    @Column(name = "members_visibility_level", nullable = false)
    private Integer membersVisibilityLevel;

    @Column(name = "imap_last_error")
    private String imapLastError;

    @Column(name = "imap_old_emails")
    private Integer imapOldEmails;

    @Column(name = "imap_new_emails")
    private Integer imapNewEmails;

    @Column(name = "flair_icon")
    private String flairIcon;

    @Column(name = "flair_upload_id")
    private Integer flairUploadId;

    @NotNull
    @Column(name = "allow_unknown_sender_topic_replies", nullable = false)
    private Boolean allowUnknownSenderTopicReplies;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Groups name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAutomatic() {
        return automatic;
    }

    public Groups automatic(Boolean automatic) {
        this.automatic = automatic;
        return this;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public Groups userCount(Integer userCount) {
        this.userCount = userCount;
        return this;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getAutomaticMembershipEmailDomains() {
        return automaticMembershipEmailDomains;
    }

    public Groups automaticMembershipEmailDomains(String automaticMembershipEmailDomains) {
        this.automaticMembershipEmailDomains = automaticMembershipEmailDomains;
        return this;
    }

    public void setAutomaticMembershipEmailDomains(String automaticMembershipEmailDomains) {
        this.automaticMembershipEmailDomains = automaticMembershipEmailDomains;
    }

    public Boolean isPrimaryGroup() {
        return primaryGroup;
    }

    public Groups primaryGroup(Boolean primaryGroup) {
        this.primaryGroup = primaryGroup;
        return this;
    }

    public void setPrimaryGroup(Boolean primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    public String getTitle() {
        return title;
    }

    public Groups title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGrantTrustLevel() {
        return grantTrustLevel;
    }

    public Groups grantTrustLevel(Integer grantTrustLevel) {
        this.grantTrustLevel = grantTrustLevel;
        return this;
    }

    public void setGrantTrustLevel(Integer grantTrustLevel) {
        this.grantTrustLevel = grantTrustLevel;
    }

    public String getIncomingEmail() {
        return incomingEmail;
    }

    public Groups incomingEmail(String incomingEmail) {
        this.incomingEmail = incomingEmail;
        return this;
    }

    public void setIncomingEmail(String incomingEmail) {
        this.incomingEmail = incomingEmail;
    }

    public Boolean isHasMessages() {
        return hasMessages;
    }

    public Groups hasMessages(Boolean hasMessages) {
        this.hasMessages = hasMessages;
        return this;
    }

    public void setHasMessages(Boolean hasMessages) {
        this.hasMessages = hasMessages;
    }

    public String getFlairUrl() {
        return flairUrl;
    }

    public Groups flairUrl(String flairUrl) {
        this.flairUrl = flairUrl;
        return this;
    }

    public void setFlairUrl(String flairUrl) {
        this.flairUrl = flairUrl;
    }

    public String getFlairBgColor() {
        return flairBgColor;
    }

    public Groups flairBgColor(String flairBgColor) {
        this.flairBgColor = flairBgColor;
        return this;
    }

    public void setFlairBgColor(String flairBgColor) {
        this.flairBgColor = flairBgColor;
    }

    public String getFlairColor() {
        return flairColor;
    }

    public Groups flairColor(String flairColor) {
        this.flairColor = flairColor;
        return this;
    }

    public void setFlairColor(String flairColor) {
        this.flairColor = flairColor;
    }

    public String getBioRaw() {
        return bioRaw;
    }

    public Groups bioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
        return this;
    }

    public void setBioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
    }

    public String getBioCooked() {
        return bioCooked;
    }

    public Groups bioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
        return this;
    }

    public void setBioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
    }

    public Boolean isAllowMembershipRequests() {
        return allowMembershipRequests;
    }

    public Groups allowMembershipRequests(Boolean allowMembershipRequests) {
        this.allowMembershipRequests = allowMembershipRequests;
        return this;
    }

    public void setAllowMembershipRequests(Boolean allowMembershipRequests) {
        this.allowMembershipRequests = allowMembershipRequests;
    }

    public String getFullName() {
        return fullName;
    }

    public Groups fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getDefaultNotificationLevel() {
        return defaultNotificationLevel;
    }

    public Groups defaultNotificationLevel(Integer defaultNotificationLevel) {
        this.defaultNotificationLevel = defaultNotificationLevel;
        return this;
    }

    public void setDefaultNotificationLevel(Integer defaultNotificationLevel) {
        this.defaultNotificationLevel = defaultNotificationLevel;
    }

    public Integer getVisibilityLevel() {
        return visibilityLevel;
    }

    public Groups visibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
        return this;
    }

    public void setVisibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public Boolean isPublicExit() {
        return publicExit;
    }

    public Groups publicExit(Boolean publicExit) {
        this.publicExit = publicExit;
        return this;
    }

    public void setPublicExit(Boolean publicExit) {
        this.publicExit = publicExit;
    }

    public Boolean isPublicAdmission() {
        return publicAdmission;
    }

    public Groups publicAdmission(Boolean publicAdmission) {
        this.publicAdmission = publicAdmission;
        return this;
    }

    public void setPublicAdmission(Boolean publicAdmission) {
        this.publicAdmission = publicAdmission;
    }

    public String getMembershipRequestTemplate() {
        return membershipRequestTemplate;
    }

    public Groups membershipRequestTemplate(String membershipRequestTemplate) {
        this.membershipRequestTemplate = membershipRequestTemplate;
        return this;
    }

    public void setMembershipRequestTemplate(String membershipRequestTemplate) {
        this.membershipRequestTemplate = membershipRequestTemplate;
    }

    public Integer getMessageableLevel() {
        return messageableLevel;
    }

    public Groups messageableLevel(Integer messageableLevel) {
        this.messageableLevel = messageableLevel;
        return this;
    }

    public void setMessageableLevel(Integer messageableLevel) {
        this.messageableLevel = messageableLevel;
    }

    public Integer getMentionableLevel() {
        return mentionableLevel;
    }

    public Groups mentionableLevel(Integer mentionableLevel) {
        this.mentionableLevel = mentionableLevel;
        return this;
    }

    public void setMentionableLevel(Integer mentionableLevel) {
        this.mentionableLevel = mentionableLevel;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public Groups smtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
        return this;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public Groups smtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
        return this;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Boolean isSmtpSsl() {
        return smtpSsl;
    }

    public Groups smtpSsl(Boolean smtpSsl) {
        this.smtpSsl = smtpSsl;
        return this;
    }

    public void setSmtpSsl(Boolean smtpSsl) {
        this.smtpSsl = smtpSsl;
    }

    public String getImapServer() {
        return imapServer;
    }

    public Groups imapServer(String imapServer) {
        this.imapServer = imapServer;
        return this;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public Integer getImapPort() {
        return imapPort;
    }

    public Groups imapPort(Integer imapPort) {
        this.imapPort = imapPort;
        return this;
    }

    public void setImapPort(Integer imapPort) {
        this.imapPort = imapPort;
    }

    public Boolean isImapSsl() {
        return imapSsl;
    }

    public Groups imapSsl(Boolean imapSsl) {
        this.imapSsl = imapSsl;
        return this;
    }

    public void setImapSsl(Boolean imapSsl) {
        this.imapSsl = imapSsl;
    }

    public String getImapMailboxName() {
        return imapMailboxName;
    }

    public Groups imapMailboxName(String imapMailboxName) {
        this.imapMailboxName = imapMailboxName;
        return this;
    }

    public void setImapMailboxName(String imapMailboxName) {
        this.imapMailboxName = imapMailboxName;
    }

    public Integer getImapUidValidity() {
        return imapUidValidity;
    }

    public Groups imapUidValidity(Integer imapUidValidity) {
        this.imapUidValidity = imapUidValidity;
        return this;
    }

    public void setImapUidValidity(Integer imapUidValidity) {
        this.imapUidValidity = imapUidValidity;
    }

    public Integer getImapLastUid() {
        return imapLastUid;
    }

    public Groups imapLastUid(Integer imapLastUid) {
        this.imapLastUid = imapLastUid;
        return this;
    }

    public void setImapLastUid(Integer imapLastUid) {
        this.imapLastUid = imapLastUid;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public Groups emailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
        return this;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public Groups emailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
        return this;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public Boolean isPublishReadState() {
        return publishReadState;
    }

    public Groups publishReadState(Boolean publishReadState) {
        this.publishReadState = publishReadState;
        return this;
    }

    public void setPublishReadState(Boolean publishReadState) {
        this.publishReadState = publishReadState;
    }

    public Integer getMembersVisibilityLevel() {
        return membersVisibilityLevel;
    }

    public Groups membersVisibilityLevel(Integer membersVisibilityLevel) {
        this.membersVisibilityLevel = membersVisibilityLevel;
        return this;
    }

    public void setMembersVisibilityLevel(Integer membersVisibilityLevel) {
        this.membersVisibilityLevel = membersVisibilityLevel;
    }

    public String getImapLastError() {
        return imapLastError;
    }

    public Groups imapLastError(String imapLastError) {
        this.imapLastError = imapLastError;
        return this;
    }

    public void setImapLastError(String imapLastError) {
        this.imapLastError = imapLastError;
    }

    public Integer getImapOldEmails() {
        return imapOldEmails;
    }

    public Groups imapOldEmails(Integer imapOldEmails) {
        this.imapOldEmails = imapOldEmails;
        return this;
    }

    public void setImapOldEmails(Integer imapOldEmails) {
        this.imapOldEmails = imapOldEmails;
    }

    public Integer getImapNewEmails() {
        return imapNewEmails;
    }

    public Groups imapNewEmails(Integer imapNewEmails) {
        this.imapNewEmails = imapNewEmails;
        return this;
    }

    public void setImapNewEmails(Integer imapNewEmails) {
        this.imapNewEmails = imapNewEmails;
    }

    public String getFlairIcon() {
        return flairIcon;
    }

    public Groups flairIcon(String flairIcon) {
        this.flairIcon = flairIcon;
        return this;
    }

    public void setFlairIcon(String flairIcon) {
        this.flairIcon = flairIcon;
    }

    public Integer getFlairUploadId() {
        return flairUploadId;
    }

    public Groups flairUploadId(Integer flairUploadId) {
        this.flairUploadId = flairUploadId;
        return this;
    }

    public void setFlairUploadId(Integer flairUploadId) {
        this.flairUploadId = flairUploadId;
    }

    public Boolean isAllowUnknownSenderTopicReplies() {
        return allowUnknownSenderTopicReplies;
    }

    public Groups allowUnknownSenderTopicReplies(Boolean allowUnknownSenderTopicReplies) {
        this.allowUnknownSenderTopicReplies = allowUnknownSenderTopicReplies;
        return this;
    }

    public void setAllowUnknownSenderTopicReplies(Boolean allowUnknownSenderTopicReplies) {
        this.allowUnknownSenderTopicReplies = allowUnknownSenderTopicReplies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Groups)) {
            return false;
        }
        return id != null && id.equals(((Groups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Groups{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", automatic='" + isAutomatic() + "'" +
            ", userCount=" + getUserCount() +
            ", automaticMembershipEmailDomains='" + getAutomaticMembershipEmailDomains() + "'" +
            ", primaryGroup='" + isPrimaryGroup() + "'" +
            ", title='" + getTitle() + "'" +
            ", grantTrustLevel=" + getGrantTrustLevel() +
            ", incomingEmail='" + getIncomingEmail() + "'" +
            ", hasMessages='" + isHasMessages() + "'" +
            ", flairUrl='" + getFlairUrl() + "'" +
            ", flairBgColor='" + getFlairBgColor() + "'" +
            ", flairColor='" + getFlairColor() + "'" +
            ", bioRaw='" + getBioRaw() + "'" +
            ", bioCooked='" + getBioCooked() + "'" +
            ", allowMembershipRequests='" + isAllowMembershipRequests() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", defaultNotificationLevel=" + getDefaultNotificationLevel() +
            ", visibilityLevel=" + getVisibilityLevel() +
            ", publicExit='" + isPublicExit() + "'" +
            ", publicAdmission='" + isPublicAdmission() + "'" +
            ", membershipRequestTemplate='" + getMembershipRequestTemplate() + "'" +
            ", messageableLevel=" + getMessageableLevel() +
            ", mentionableLevel=" + getMentionableLevel() +
            ", smtpServer='" + getSmtpServer() + "'" +
            ", smtpPort=" + getSmtpPort() +
            ", smtpSsl='" + isSmtpSsl() + "'" +
            ", imapServer='" + getImapServer() + "'" +
            ", imapPort=" + getImapPort() +
            ", imapSsl='" + isImapSsl() + "'" +
            ", imapMailboxName='" + getImapMailboxName() + "'" +
            ", imapUidValidity=" + getImapUidValidity() +
            ", imapLastUid=" + getImapLastUid() +
            ", emailUsername='" + getEmailUsername() + "'" +
            ", emailPassword='" + getEmailPassword() + "'" +
            ", publishReadState='" + isPublishReadState() + "'" +
            ", membersVisibilityLevel=" + getMembersVisibilityLevel() +
            ", imapLastError='" + getImapLastError() + "'" +
            ", imapOldEmails=" + getImapOldEmails() +
            ", imapNewEmails=" + getImapNewEmails() +
            ", flairIcon='" + getFlairIcon() + "'" +
            ", flairUploadId=" + getFlairUploadId() +
            ", allowUnknownSenderTopicReplies='" + isAllowUnknownSenderTopicReplies() + "'" +
            "}";
    }
}
