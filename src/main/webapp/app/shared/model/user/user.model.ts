import { IGroup } from '../group/group.model';

interface IUserAuthToken {
  id?: string;
  client_ip?: string;
  location?: string;
  browser?: string;
  device?: string;
  os?: string;
  icon?: string;
  created_at?: string;
  seen_at?: string;
  is_active?: boolean;
}

interface IGroupUser {
  group_id?: string;
  user_id?: string;
  notification_level?: number;
}

interface IUserOption {
  user_id?: string;
  mailing_list_mode?: boolean;
  mailing_list_mode_frequency?: number;
  email_digests?: boolean;
  email_level?: number;
  email_messages_level?: number;
  external_links_in_new_tab?: boolean;
  color_scheme_id?: string;
  dark_scheme_id?: string;
  dynamic_favicon?: boolean;
  enable_quoting?: boolean;
  enable_defer?: boolean;
  digest_after_minutes?: number;
  automatically_unpin_topics?: boolean;
  auto_track_topics_after_msecs?: number;
  notification_level_when_replying?: number;
  new_topic_duration_minutes?: number;
  email_previous_replies?: number;
  email_in_reply_to?: boolean;
  like_notification_frequency?: number;
  include_tlnumber_in_digests?: boolean;
  theme_ids?: string[];
  theme_key_seq?: number;
  allow_private_messages?: boolean;
  enable_allowed_pm_users?: boolean;
  homepage_id?: string;
  hide_profile_and_presence?: boolean;
  text_size?: string;
  text_size_seq?: number;
  title_count_mode?: string;
  timezone?: string;
  skip_new_user_tips?: boolean;
}

interface IPenaltyCount {
  silenced?: number;
  suspended?: number;
  total?: number;
}

interface ITl3Requirements {
  time_period?: number;
  requirements_met?: boolean;
  requirements_lost?: boolean;
  trust_level_locked?: boolean;
  on_grace_period?: boolean;
  days_visited?: number;
  min_days_visited?: number;
  num_topics_replied_to?: number;
  min_topics_replied_to?: number;
  topics_viewed?: number;
  min_topics_viewed?: number;
  posts_read?: number;
  min_posts_read?: number;
  topics_viewed_all_time?: number;
  min_topics_viewed_all_time?: number;
  posts_read_all_time?: number;
  min_posts_read_all_time?: number;
  num_flagged_posts?: number;
  max_flagged_posts?: number;
  num_flagged_by_users?: number;
  max_flagged_by_users?: number;
  num_likes_given?: number;
  min_likes_given?: number;
  num_likes_received?: number;
  min_likes_received?: number;
  num_likes_received_days?: number;
  min_likes_received_days?: number;
  num_likes_received_users?: number;
  min_likes_received_users?: number;
  penalty_counts?: IPenaltyCount;
}

export interface IUser {
  id?: string;
  user_id?: string;
  username?: string;
  name?: string;
  avatar_template?: string;
  last_posted_at?: string;
  last_seen_at?: string;
  created_at?: string;
  ignored?: boolean;
  muted?: boolean;
  can_ignore_user?: boolean;
  can_mute_user?: boolean;
  can_send_private_messages?: boolean;
  can_send_private_message_to_user?: boolean;
  trust_level?: number;
  moderator?: boolean;
  admin?: boolean;
  title?: string;
  badge_count?: number;
  user_fields?: any;
  custom_fields?: any;
  time_read?: number;
  recent_time_read?: number;
  primary_group_id?: string;
  primary_group_name?: string;
  primary_group_flair_url?: string;
  primary_group_flair_bg_color?: string;
  primary_group_flair_color?: string;
  featured_topic?: string;
  staged?: boolean;
  can_edit?: boolean;
  can_edit_username?: boolean;
  can_edit_email?: boolean;
  can_edit_name?: boolean;
  uploaded_avatar_id?: string;
  has_title_badges?: boolean;
  pending_count?: number;
  profile_view_count?: number;
  second_factor_enabled?: boolean;
  can_upload_profile_header?: boolean;
  can_upload_user_card_background?: boolean;
  post_count?: number;
  can_be_deleted?: boolean;
  can_delete_all_posts?: boolean;
  locale?: string;
  muted_category_ids?: string[];
  regular_category_ids?: string[];
  watched_tags?: any[];
  watching_first_post_tags?: any[];
  tracked_tags?: any[];
  muted_tags?: any[];
  tracked_category_ids?: string[];
  watched_category_ids?: string[];
  watched_first_post_category_ids?: string[];
  system_avatar_upload_id?: string;
  system_avatar_template?: string;
  muted_usernames?: any[];
  ignored_usernames?: any[];
  allowed_pm_usernames?: any[];
  mailing_list_posts_per_day?: number;
  can_change_bio?: boolean;
  can_change_location?: boolean;
  can_change_website?: boolean;
  user_api_keys?: string;
  user_auth_tokens?: IUserAuthToken[];
  featured_user_badge_ids?: string[];
  invited_by?: string;
  groups?: IGroup[];
  group_users?: IGroupUser[];
  user_option?: IUserOption;
  email?: string;
  secondary_emails?: any[];
  active?: boolean;
  last_emailed_at?: any;
  last_seen_age?: any;
  last_emailed_age?: any;
  created_at_age?: any;
  manual_locked_trust_level?: any;
  flag_level?: number;
  days_visited?: number;
  posts_read_count?: number;
  topics_entered?: number;
  associated_accounts?: any[];
  can_send_activation_email?: boolean;
  can_activate?: boolean;
  can_deactivate?: boolean;
  ip_address?: string;
  registration_ip_address?: any;
  can_grant_admin?: boolean;
  can_revoke_admin?: boolean;
  can_grant_moderation?: boolean;
  can_revoke_moderation?: boolean;
  can_impersonate?: boolean;
  like_count?: number;
  like_given_count?: number;
  topic_count?: number;
  flags_given_count?: number;
  flags_received_count?: number;
  private_topics_count?: number;
  can_be_anonymized?: boolean;
  can_be_merged?: boolean;
  full_suspend_reason?: any;
  silence_reason?: any;
  warnings_received_count?: number;
  bounce_score?: any;
  reset_bounce_score_after?: any;
  can_view_action_logs?: boolean;
  can_disable_second_factor?: boolean;
  can_delete_sso_record?: boolean;
  api_key_count?: number;
  single_sign_on_record?: any;
  approved_by?: any;
  suspended_by?: any;
  silenced_by?: any;
  tl3_requirements?: ITl3Requirements;
  added_at?: string;
  timezone?: string;
  privacy_accepted?: boolean;
  notification_subscription?: boolean;
}

export class User implements IUser {
  constructor(
    public id?: string,
    public user_id?: string,
    public username?: string,
    public name?: string,
    public avatar_template?: string,
    public last_posted_at?: string,
    public last_seen_at?: string,
    public created_at?: string,
    public ignored?: boolean,
    public muted?: boolean,
    public can_ignore_user?: boolean,
    public can_mute_user?: boolean,
    public can_send_private_messages?: boolean,
    public can_send_private_message_to_user?: boolean,
    public trust_level?: number,
    public moderator?: boolean,
    public admin?: boolean,
    public title?: string,
    public badge_count?: number,
    public user_fields?: any,
    public custom_fields?: any,
    public time_read?: number,
    public recent_time_read?: number,
    public primary_group_id?: string,
    public primary_group_name?: string,
    public primary_group_flair_url?: string,
    public primary_group_flair_bg_color?: string,
    public primary_group_flair_color?: string,
    public featured_topic?: string,
    public staged?: boolean,
    public can_edit?: boolean,
    public can_edit_username?: boolean,
    public can_edit_email?: boolean,
    public can_edit_name?: boolean,
    public uploaded_avatar_id?: string,
    public has_title_badges?: boolean,
    public pending_count?: number,
    public profile_view_count?: number,
    public second_factor_enabled?: boolean,
    public can_upload_profile_header?: boolean,
    public can_upload_user_card_background?: boolean,
    public post_count?: number,
    public can_be_deleted?: boolean,
    public can_delete_all_posts?: boolean,
    public locale?: string,
    public muted_category_ids?: string[],
    public regular_category_ids?: string[],
    public watched_tags?: any[],
    public watching_first_post_tags?: any[],
    public tracked_tags?: any[],
    public muted_tags?: any[],
    public tracked_category_ids?: string[],
    public watched_category_ids?: string[],
    public watched_first_post_category_ids?: string[],
    public system_avatar_upload_id?: string,
    public system_avatar_template?: string,
    public muted_usernames?: any[],
    public ignored_usernames?: any[],
    public allowed_pm_usernames?: any[],
    public mailing_list_posts_per_day?: number,
    public can_change_bio?: boolean,
    public can_change_location?: boolean,
    public can_change_website?: boolean,
    public user_api_keys?: string,
    public user_auth_tokens?: IUserAuthToken[],
    public featured_user_badge_ids?: string[],
    public invited_by?: string,
    public groups?: IGroup[],
    public group_users?: IGroupUser[],
    public user_option?: IUserOption,
    public email?: string,
    public secondary_emails?: any[],
    public active?: boolean,
    public last_emailed_at?: any,
    public last_seen_age?: any,
    public last_emailed_age?: any,
    public created_at_age?: any,
    public manual_locked_trust_level?: any,
    public flag_level?: number,
    public days_visited?: number,
    public posts_read_count?: number,
    public topics_entered?: number,
    public associated_accounts?: any[],
    public can_send_activation_email?: boolean,
    public can_activate?: boolean,
    public can_deactivate?: boolean,
    public ip_address?: string,
    public registration_ip_address?: any,
    public can_grant_admin?: boolean,
    public can_revoke_admin?: boolean,
    public can_grant_moderation?: boolean,
    public can_revoke_moderation?: boolean,
    public can_impersonate?: boolean,
    public like_count?: number,
    public like_given_count?: number,
    public topic_count?: number,
    public flags_given_count?: number,
    public flags_received_count?: number,
    public private_topics_count?: number,
    public can_be_anonymized?: boolean,
    public can_be_merged?: boolean,
    public full_suspend_reason?: any,
    public silence_reason?: any,
    public warnings_received_count?: number,
    public bounce_score?: any,
    public reset_bounce_score_after?: any,
    public can_view_action_logs?: boolean,
    public can_disable_second_factor?: boolean,
    public can_delete_sso_record?: boolean,
    public api_key_count?: number,
    public single_sign_on_record?: any,
    public approved_by?: any,
    public suspended_by?: any,
    public silenced_by?: any,
    public tl3_requirements?: ITl3Requirements,
    public added_at?: string,
    public timezone?: string,
    public privacy_accepted?: boolean,
    public notification_subscription?: boolean
  ) {}
}
