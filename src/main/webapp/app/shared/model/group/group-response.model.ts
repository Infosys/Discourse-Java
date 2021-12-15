import { IGroup } from './group.model';

interface IExtras {
  visible_group_names?: any[];
  type_filters?: any[];
}

interface IUserForGroups {
  id?: string;
  username?: string;
  name?: any;
  avatar_template?: string;
  title?: any;
  last_posted_at?: string;
  last_seen_at?: string;
  added_at?: string;
  timezone?: string;
}

interface IMeta {
  total?: number;
  limit?: number;
  offset?: number;
}

export interface IGroupResponse {
  basic_group?: IGroup;
  group?: IGroup;
  extras?: IExtras;
  members?: IUserForGroups[];
  owners?: IUserForGroups[];
  meta?: IMeta;
  success?: string;
  usernames?: any[];
  emails?: any[];
  skipped_usernames?: any[];
  total_rows_groups?: number;
  load_more_groups?: string;
  groups?: IGroup[];
}
