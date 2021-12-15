export interface IUserAction {
  excerpt?: string;
  action_type?: number;
  created_at?: string;
  avatar_template?: string;
  acting_avatar_template?: string;
  slug?: string;
  topic_id?: string;
  target_user_id?: string;
  target_name?: any;
  target_username?: string;
  post_number?: number;
  post_id?: string;
  username?: string;
  name?: any;
  user_id?: string;
  acting_username?: string;
  acting_name?: any;
  acting_user_id?: string;
  title?: string;
  deleted?: boolean;
  hidden?: any;
  post_type?: any;
  action_code?: any;
  category_id?: string;
  closed?: boolean;
  archived?: boolean;
}

export class UserAction implements IUserAction {
  constructor(
    public excerpt?: string,
    public action_type?: number,
    public created_at?: string,
    public avatar_template?: string,
    public acting_avatar_template?: string,
    public slug?: string,
    public topic_id?: string,
    public target_user_id?: string,
    public target_name?: any,
    public target_username?: string,
    public post_number?: number,
    public post_id?: string,
    public username?: string,
    public name?: any,
    public user_id?: string,
    public acting_username?: string,
    public acting_name?: any,
    public acting_user_id?: string,
    public title?: string,
    public deleted?: boolean,
    public hidden?: any,
    public post_type?: any,
    public action_code?: any,
    public category_id?: string,
    public closed?: boolean,
    public archived?: boolean
  ) {}
}
