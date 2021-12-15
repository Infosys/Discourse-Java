import { IUserAction } from './user-action.model';
import { IUser } from './user.model';

interface ISuspension {
  suspend_reason?: string;
  full_suspend_reason?: string;
  suspended_till?: string;
  suspended_at?: string;
}

interface IDirectoryItems {
  id?: string;
  likes_received?: number;
  likes_given?: number;
  topics_entered?: number;
  topic_count?: number;
  post_count?: number;
  posts_read?: number;
  days_visited?: number;
  user?: IUser;
}

interface IMeta {
  last_updated_at?: any;
  total_rows_directory_items?: number;
  load_more_directory_items?: string;
}

export interface IUserResponse {
  suspension?: ISuspension;
  user_actions?: IUserAction[];
  gravatar_upload_id?: string;
  gravatar_avatar_template?: any;
  success?: boolean;
  active?: boolean;
  message?: string;
  user_id?: string;
  user_badges?: any[];
  user?: IUser;
  directory_items?: IDirectoryItems[];
  meta?: IMeta;
  deleted?: boolean;
  user_found?: boolean;
}
