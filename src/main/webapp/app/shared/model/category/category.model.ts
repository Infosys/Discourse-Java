import { IGroupPermission } from './group-permission.model';

export interface ICategory {
  id?: string;
  name?: string;
  color?: string;
  text_color?: string;
  slug?: string;
  topic_count?: number;
  post_count?: number;
  position?: number;
  description?: any;
  description_text?: any;
  description_excerpt?: any;
  topic_url?: any;
  read_restricted?: boolean;
  permission?: any;
  notification_level?: number;
  can_edit?: boolean;
  topic_template?: any;
  has_children?: any;
  sort_order?: any;
  sort_ascending?: any;
  show_subcategory_list?: boolean;
  num_featured_topics?: number;
  default_view?: any;
  subcategory_list_style?: string;
  default_top_period?: string;
  default_list_filter?: string;
  minimum_required_tags?: number;
  navigate_to_first_post_after_read?: boolean;
  custom_fields?: any;
  min_tags_from_required_group?: number;
  required_tag_group_name?: any;
  read_only_banner?: any;
  available_groups?: any[];
  auto_close_hours?: any;
  auto_close_based_on_last_post?: boolean;
  group_permissions?: IGroupPermission[];
  email_in?: any;
  email_in_allow_strangers?: boolean;
  mailinglist_mirror?: boolean;
  all_topics_wiki?: boolean;
  can_delete?: boolean;
  cannot_delete_reason?: any;
  allow_badges?: boolean;
  topic_featured_link_allowed?: boolean;
  search_priority?: number;
  uploaded_logo?: any;
  uploaded_background?: any;
  subcategory_ids?: string[];
  topics_day?: number;
  topics_week?: number;
  topics_month?: number;
  topics_year?: number;
  topics_all_time?: number;
  is_uncategorized?: boolean;
}

export class Category implements ICategory {
  constructor(
    public id?: string,
    public name?: string,
    public color?: string,
    public text_color?: string,
    public slug?: string,
    public topic_count?: number,
    public post_count?: number,
    public position?: number,
    public description?: any,
    public description_text?: any,
    public description_excerpt?: any,
    public topic_url?: any,
    public read_restricted?: boolean,
    public permission?: any,
    public notification_level?: number,
    public can_edit?: boolean,
    public topic_template?: any,
    public has_children?: any,
    public sort_order?: any,
    public sort_ascending?: any,
    public show_subcategory_list?: boolean,
    public num_featured_topics?: number,
    public default_view?: any,
    public subcategory_list_style?: string,
    public default_top_period?: string,
    public default_list_filter?: string,
    public minimum_required_tags?: number,
    public navigate_to_first_post_after_read?: boolean,
    public custom_fields?: any,
    public min_tags_from_required_group?: number,
    public required_tag_group_name?: any,
    public read_only_banner?: any,
    public available_groups?: any[],
    public auto_close_hours?: any,
    public auto_close_based_on_last_post?: boolean,
    public group_permissions?: IGroupPermission[],
    public email_in?: any,
    public email_in_allow_strangers?: boolean,
    public mailinglist_mirror?: boolean,
    public all_topics_wiki?: boolean,
    public can_delete?: boolean,
    public cannot_delete_reason?: any,
    public allow_badges?: boolean,
    public topic_featured_link_allowed?: boolean,
    public search_priority?: number,
    public uploaded_logo?: any,
    public uploaded_background?: any,
    public subcategory_ids?: string[],
    public topics_day?: number,
    public topics_week?: number,
    public topics_month?: number,
    public topics_year?: number,
    public topics_all_time?: number,
    public is_uncategorized?: boolean
  ) {}
}
