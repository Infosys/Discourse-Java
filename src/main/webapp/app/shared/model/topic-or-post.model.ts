import { IActionsSummary } from './actions-summary.model';
import { IPostStream } from './post-stream.model';
import { IPoster } from './poster.model';
import { IUser } from './user/user.model';

interface IDetails {
  notification_level?: number;
  can_move_posts?: boolean;
  can_edit?: boolean;
  can_delete?: boolean;
  can_remove_allowed_users?: boolean;
  can_create_post?: boolean;
  can_reply_as_new_topic?: boolean;
  can_flag_topic?: boolean;
  can_convert_topic?: boolean;
  can_review_topic?: boolean;
  can_remove_self_id?: string;
  participants?: IUser[];
  created_by?: IUser;
  last_poster?: IUser;
}

export interface ITopicOrPost {
  id?: string;
  name?: string;
  username?: string;
  current_user_like?: boolean;
  avatar_template?: string;
  created_at?: Date;
  cooked?: string;
  post_number?: number;
  post_type?: number;
  updated_at?: string;
  reply_count?: number;
  reply_to_post_number?: string;
  quote_count?: number;
  incoming_link_count?: number;
  reads?: number;
  readers_count?: number;
  score?: number;
  yours?: boolean;
  topic_id?: string;
  topic_slug?: string;
  display_username?: string;
  primary_group_name?: string;
  primary_group_flair_url?: string;
  primary_group_flair_bg_color?: string;
  primary_group_flair_color?: string;
  version?: number;
  can_edit?: boolean;
  can_delete?: boolean;
  can_recover?: boolean;
  can_wiki?: boolean;
  user_title?: string;
  actions_summary?: IActionsSummary[];
  moderator?: boolean;
  admin?: boolean;
  staff?: boolean;
  user_id?: string;
  draft_sequence?: number;
  hidden?: boolean;
  trust_level?: number;
  deleted_at?: string;
  user_deleted?: boolean;
  edit_reason?: string;
  can_view_edit_history?: boolean;
  wiki?: boolean;
  reviewable_id?: string;
  reviewable_score_count?: number;
  reviewable_score_pending_count?: number;
  raw?: string;
  topic_title?: string;
  topic_html_title?: string;
  category_id?: string;
  posters?: IPoster[];
  title?: string;
  fancy_title?: string;
  slug?: string;
  posts_count?: number;
  highest_post_number?: number;
  image_url?: string;
  last_posted_at?: string;
  bumped?: boolean;
  bumped_at?: string;
  archetype?: string;
  unseen?: boolean;
  last_read_post_number?: number;
  unread?: number;
  new_posts?: number;
  pinned?: boolean;
  unpinned?: string;
  visible?: boolean;
  closed?: boolean;
  archived?: boolean;
  notification_level?: number;
  bookmarked?: boolean;
  liked?: boolean;
  views?: number;
  like_count?: number;
  has_summary?: boolean;
  last_poster_username?: string;
  op_like_count?: number;
  pinned_globally?: boolean;
  featured_link?: string;
  post_stream?: IPostStream;
  timeline_lookup?: any[];
  suggested_topics?: ITopicOrPost[];
  word_count?: number;
  pinned_at?: string;
  pinned_until?: string;
  draft?: string;
  draft_key?: string;
  current_post_number?: number;
  deleted_by?: string;
  has_deleted?: boolean;
  chunk_size?: number;
  topic_timer?: string;
  message_bus_last_id?: string;
  participant_count?: number;
  show_read_indicator?: boolean;
  thumbnails?: string;
  details?: IDetails;
  tags?: string;
  notice?: any;
}

export class TopicOrPost implements ITopicOrPost {
  constructor(
    public id?: string,
    public name?: string,
    public username?: string,
    public current_user_like?: boolean,
    public avatar_template?: string,
    public created_at?: Date,
    public cooked?: string,
    public post_number?: number,
    public post_type?: number,
    public updated_at?: string,
    public reply_count?: number,
    public reply_to_post_number?: string,
    public quote_count?: number,
    public incoming_link_count?: number,
    public reads?: number,
    public readers_count?: number,
    public score?: number,
    public yours?: boolean,
    public topic_id?: string,
    public topic_slug?: string,
    public display_username?: string,
    public primary_group_name?: string,
    public primary_group_flair_url?: string,
    public primary_group_flair_bg_color?: string,
    public primary_group_flair_color?: string,
    public version?: number,
    public can_edit?: boolean,
    public can_delete?: boolean,
    public can_recover?: boolean,
    public can_wiki?: boolean,
    public user_title?: string,
    public actions_summary?: IActionsSummary[],
    public moderator?: boolean,
    public admin?: boolean,
    public staff?: boolean,
    public user_id?: string,
    public draft_sequence?: number,
    public hidden?: boolean,
    public trust_level?: number,
    public deleted_at?: string,
    public user_deleted?: boolean,
    public edit_reason?: string,
    public can_view_edit_history?: boolean,
    public wiki?: boolean,
    public reviewable_id?: string,
    public reviewable_score_count?: number,
    public reviewable_score_pending_count?: number,
    public raw?: string,
    public topic_title?: string,
    public topic_html_title?: string,
    public category_id?: string,
    public posters?: IPoster[],
    public title?: string,
    public fancy_title?: string,
    public slug?: string,
    public posts_count?: number,
    public highest_post_number?: number,
    public image_url?: string,
    public last_posted_at?: string,
    public bumped?: boolean,
    public bumped_at?: string,
    public archetype?: string,
    public unseen?: boolean,
    public last_read_post_number?: number,
    public unread?: number,
    public new_posts?: number,
    public pinned?: boolean,
    public unpinned?: string,
    public visible?: boolean,
    public closed?: boolean,
    public archived?: boolean,
    public notification_level?: number,
    public bookmarked?: boolean,
    public liked?: boolean,
    public views?: number,
    public like_count?: number,
    public has_summary?: boolean,
    public last_poster_username?: string,
    public op_like_count?: number,
    public pinned_globally?: boolean,
    public featured_link?: string,
    public post_stream?: IPostStream,
    public timeline_lookup?: any[],
    public suggested_topics?: ITopicOrPost[],
    public word_count?: number,
    public pinned_at?: string,
    public pinned_until?: string,
    public draft?: string,
    public draft_key?: string,
    public current_post_number?: number,
    public deleted_by?: string,
    public has_deleted?: boolean,
    public chunk_size?: number,
    public topic_timer?: string,
    public message_bus_last_id?: string,
    public participant_count?: number,
    public show_read_indicator?: boolean,
    public thumbnails?: string,
    public details?: IDetails,
    public tags?: string,
    public notice?: any
  ) {}
}
