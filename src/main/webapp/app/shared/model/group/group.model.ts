export interface IGroup {
  id?: string;
  automatic?: boolean;
  name?: string;
  user_count?: number;
  mentionable_level?: number;
  messageable_level?: number;
  visibility_level?: number;
  primary_group?: boolean;
  title?: any;
  grant_trust_level?: any;
  incoming_email?: any;
  has_messages?: boolean;
  flair_url?: any;
  flair_bg_color?: any;
  flair_color?: any;
  bio_raw?: any;
  bio_cooked?: any;
  bio_excerpt?: any;
  public_admission?: boolean;
  public_exit?: boolean;
  allow_membership_requests?: boolean;
  full_name?: any;
  default_notification_level?: number;
  membership_request_template?: any;
  is_group_user?: boolean;
  members_visibility_level?: number;
  can_see_members?: boolean;
  can_admin_group?: boolean;
  publish_read_state?: boolean;
  is_group_owner_display?: boolean;
  mentionable?: boolean;
  messageable?: boolean;
  automatic_membership_email_domains?: any;
  smtp_server?: any;
  smtp_port?: any;
  smtp_ssl?: any;
  imap_server?: any;
  imap_port?: any;
  imap_ssl?: any;
  imap_mailbox_name?: string;
  imap_mailboxes?: any[];
  email_username?: any;
  email_password?: any;
  imap_last_error?: any;
  imap_old_emails?: any;
  imap_new_emails?: any;
  message_count?: number;
  allow_unknown_sender_topic_replies?: boolean;
  watching_category_ids?: string[];
  tracking_category_ids?: string[];
  watching_first_post_category_ids?: string[];
  regular_category_ids?: string[];
  muted_category_ids?: string[];
  automatic_membership_retroactive?: boolean;
  membership_visibility_level?: number;
  display_name?: string;
  is_group_owner?: boolean;
}

export class Group implements IGroup {
  constructor(
    public id?: string,
    public automatic?: boolean,
    public name?: string,
    public user_count?: number,
    public mentionable_level?: number,
    public messageable_level?: number,
    public visibility_level?: number,
    public primary_group?: boolean,
    public title?: any,
    public grant_trust_level?: any,
    public incoming_email?: any,
    public has_messages?: boolean,
    public flair_url?: any,
    public flair_bg_color?: any,
    public flair_color?: any,
    public bio_raw?: any,
    public bio_cooked?: any,
    public bio_excerpt?: any,
    public public_admission?: boolean,
    public public_exit?: boolean,
    public allow_membership_requests?: boolean,
    public full_name?: any,
    public default_notification_level?: number,
    public membership_request_template?: any,
    public is_group_user?: boolean,
    public members_visibility_level?: number,
    public can_see_members?: boolean,
    public can_admin_group?: boolean,
    public publish_read_state?: boolean,
    public is_group_owner_display?: boolean,
    public mentionable?: boolean,
    public messageable?: boolean,
    public automatic_membership_email_domains?: any,
    public smtp_server?: any,
    public smtp_port?: any,
    public smtp_ssl?: any,
    public imap_server?: any,
    public imap_port?: any,
    public imap_ssl?: any,
    public imap_mailbox_name?: string,
    public imap_mailboxes?: any[],
    public email_username?: any,
    public email_password?: any,
    public imap_last_error?: any,
    public imap_old_emails?: any,
    public imap_new_emails?: any,
    public message_count?: number,
    public allow_unknown_sender_topic_replies?: boolean,
    public watching_category_ids?: string[],
    public tracking_category_ids?: string[],
    public watching_first_post_category_ids?: string[],
    public regular_category_ids?: string[],
    public muted_category_ids?: string[],
    public automatic_membership_retroactive?: boolean,
    public membership_visibility_level?: number,
    public display_name?: string,
    public is_group_owner?: boolean
  ) {}
}
