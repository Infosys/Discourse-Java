import { ITag } from './tag/tag.model';
import { ITopicOrPost } from './topic-or-post.model';
import { IUser } from './user/user.model';

interface IParticipants {
  extras?: string;
  description?: string;
  user_id?: string;
  primary_group_id?: string;
}

interface ITopicOrPostWithParticipants extends ITopicOrPost {
  participants?: IParticipants[];
}

interface ITopicList {
  can_create_topic?: boolean;
  draft?: string;
  draft_key?: string;
  draft_sequence?: number;
  for_period?: string;
  per_page?: number;
  topics?: ITopicOrPostWithParticipants;
  tags?: ITag[];
}

export interface ITopicsOrPrivateMessagesListResponse {
  users?: IUser[];
  primary_groups?: any[];
  topic_list?: ITopicList;
}
