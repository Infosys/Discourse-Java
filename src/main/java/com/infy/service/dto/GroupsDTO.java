/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.infy.domain.Groups} entity.
 */
public class GroupsDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean automatic;

    @NotNull
    private Integer userCount;

    private String automaticMembershipEmailDomains;

    @NotNull
    private Boolean primaryGroup;

    private String title;

    private Integer grantTrustLevel;

    private String incomingEmail;

    @NotNull
    private Boolean hasMessages;

    private String flairUrl;

    private String flairBgColor;

    private String flairColor;

    private String bioRaw;

    private String bioCooked;

    @NotNull
    private Boolean allowMembershipRequests;

    private String fullName;

    @NotNull
    private Integer defaultNotificationLevel;

    @NotNull
    private Integer visibilityLevel;

    @NotNull
    private Boolean publicExit;

    @NotNull
    private Boolean publicAdmission;

    private String membershipRequestTemplate;

    private Integer messageableLevel;

    private Integer mentionableLevel;

    private String smtpServer;

    private Integer smtpPort;

    private Boolean smtpSsl;

    private String imapServer;

    private Integer imapPort;

    private Boolean imapSsl;

    @NotNull
    private String imapMailboxName;

    @NotNull
    private Integer imapUidValidity;

    @NotNull
    private Integer imapLastUid;

    private String emailUsername;

    private String emailPassword;

    @NotNull
    private Boolean publishReadState;

    @NotNull
    private Integer membersVisibilityLevel;

    private String imapLastError;

    private Integer imapOldEmails;

    private Integer imapNewEmails;

    private String flairIcon;

    private Integer flairUploadId;

    @NotNull
    private Boolean allowUnknownSenderTopicReplies;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(Boolean automatic) {
        this.automatic = automatic;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getAutomaticMembershipEmailDomains() {
        return automaticMembershipEmailDomains;
    }

    public void setAutomaticMembershipEmailDomains(String automaticMembershipEmailDomains) {
        this.automaticMembershipEmailDomains = automaticMembershipEmailDomains;
    }

    public Boolean isPrimaryGroup() {
        return primaryGroup;
    }

    public void setPrimaryGroup(Boolean primaryGroup) {
        this.primaryGroup = primaryGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGrantTrustLevel() {
        return grantTrustLevel;
    }

    public void setGrantTrustLevel(Integer grantTrustLevel) {
        this.grantTrustLevel = grantTrustLevel;
    }

    public String getIncomingEmail() {
        return incomingEmail;
    }

    public void setIncomingEmail(String incomingEmail) {
        this.incomingEmail = incomingEmail;
    }

    public Boolean isHasMessages() {
        return hasMessages;
    }

    public void setHasMessages(Boolean hasMessages) {
        this.hasMessages = hasMessages;
    }

    public String getFlairUrl() {
        return flairUrl;
    }

    public void setFlairUrl(String flairUrl) {
        this.flairUrl = flairUrl;
    }

    public String getFlairBgColor() {
        return flairBgColor;
    }

    public void setFlairBgColor(String flairBgColor) {
        this.flairBgColor = flairBgColor;
    }

    public String getFlairColor() {
        return flairColor;
    }

    public void setFlairColor(String flairColor) {
        this.flairColor = flairColor;
    }

    public String getBioRaw() {
        return bioRaw;
    }

    public void setBioRaw(String bioRaw) {
        this.bioRaw = bioRaw;
    }

    public String getBioCooked() {
        return bioCooked;
    }

    public void setBioCooked(String bioCooked) {
        this.bioCooked = bioCooked;
    }

    public Boolean isAllowMembershipRequests() {
        return allowMembershipRequests;
    }

    public void setAllowMembershipRequests(Boolean allowMembershipRequests) {
        this.allowMembershipRequests = allowMembershipRequests;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getDefaultNotificationLevel() {
        return defaultNotificationLevel;
    }

    public void setDefaultNotificationLevel(Integer defaultNotificationLevel) {
        this.defaultNotificationLevel = defaultNotificationLevel;
    }

    public Integer getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(Integer visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public Boolean isPublicExit() {
        return publicExit;
    }

    public void setPublicExit(Boolean publicExit) {
        this.publicExit = publicExit;
    }

    public Boolean isPublicAdmission() {
        return publicAdmission;
    }

    public void setPublicAdmission(Boolean publicAdmission) {
        this.publicAdmission = publicAdmission;
    }

    public String getMembershipRequestTemplate() {
        return membershipRequestTemplate;
    }

    public void setMembershipRequestTemplate(String membershipRequestTemplate) {
        this.membershipRequestTemplate = membershipRequestTemplate;
    }

    public Integer getMessageableLevel() {
        return messageableLevel;
    }

    public void setMessageableLevel(Integer messageableLevel) {
        this.messageableLevel = messageableLevel;
    }

    public Integer getMentionableLevel() {
        return mentionableLevel;
    }

    public void setMentionableLevel(Integer mentionableLevel) {
        this.mentionableLevel = mentionableLevel;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public Boolean isSmtpSsl() {
        return smtpSsl;
    }

    public void setSmtpSsl(Boolean smtpSsl) {
        this.smtpSsl = smtpSsl;
    }

    public String getImapServer() {
        return imapServer;
    }

    public void setImapServer(String imapServer) {
        this.imapServer = imapServer;
    }

    public Integer getImapPort() {
        return imapPort;
    }

    public void setImapPort(Integer imapPort) {
        this.imapPort = imapPort;
    }

    public Boolean isImapSsl() {
        return imapSsl;
    }

    public void setImapSsl(Boolean imapSsl) {
        this.imapSsl = imapSsl;
    }

    public String getImapMailboxName() {
        return imapMailboxName;
    }

    public void setImapMailboxName(String imapMailboxName) {
        this.imapMailboxName = imapMailboxName;
    }

    public Integer getImapUidValidity() {
        return imapUidValidity;
    }

    public void setImapUidValidity(Integer imapUidValidity) {
        this.imapUidValidity = imapUidValidity;
    }

    public Integer getImapLastUid() {
        return imapLastUid;
    }

    public void setImapLastUid(Integer imapLastUid) {
        this.imapLastUid = imapLastUid;
    }

    public String getEmailUsername() {
        return emailUsername;
    }

    public void setEmailUsername(String emailUsername) {
        this.emailUsername = emailUsername;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public Boolean isPublishReadState() {
        return publishReadState;
    }

    public void setPublishReadState(Boolean publishReadState) {
        this.publishReadState = publishReadState;
    }

    public Integer getMembersVisibilityLevel() {
        return membersVisibilityLevel;
    }

    public void setMembersVisibilityLevel(Integer membersVisibilityLevel) {
        this.membersVisibilityLevel = membersVisibilityLevel;
    }

    public String getImapLastError() {
        return imapLastError;
    }

    public void setImapLastError(String imapLastError) {
        this.imapLastError = imapLastError;
    }

    public Integer getImapOldEmails() {
        return imapOldEmails;
    }

    public void setImapOldEmails(Integer imapOldEmails) {
        this.imapOldEmails = imapOldEmails;
    }

    public Integer getImapNewEmails() {
        return imapNewEmails;
    }

    public void setImapNewEmails(Integer imapNewEmails) {
        this.imapNewEmails = imapNewEmails;
    }

    public String getFlairIcon() {
        return flairIcon;
    }

    public void setFlairIcon(String flairIcon) {
        this.flairIcon = flairIcon;
    }

    public Integer getFlairUploadId() {
        return flairUploadId;
    }

    public void setFlairUploadId(Integer flairUploadId) {
        this.flairUploadId = flairUploadId;
    }

    public Boolean isAllowUnknownSenderTopicReplies() {
        return allowUnknownSenderTopicReplies;
    }

    public void setAllowUnknownSenderTopicReplies(Boolean allowUnknownSenderTopicReplies) {
        this.allowUnknownSenderTopicReplies = allowUnknownSenderTopicReplies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupsDTO)) {
            return false;
        }

        return id != null && id.equals(((GroupsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupsDTO{" +
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
