import { IPostStream } from '../post-stream.model';
import { ITopicOrPost } from '../topic-or-post.model';

export interface ITopicResponse {
  basic_topic?: ITopicOrPost;
  post_stream?: IPostStream;
  success?: string;
  execute_at?: string;
  duration?: string;
  based_on_last_post?: boolean;
  closed?: boolean;
  category_id?: string;
  topic?: ITopicOrPost;
  posts?: ITopicOrPost;
}
