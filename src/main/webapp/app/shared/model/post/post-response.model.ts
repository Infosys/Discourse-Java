import { ITopicOrPost } from '../topic-or-post.model';

export interface IPostResponse {
  post?: ITopicOrPost;
  latest_posts?: ITopicOrPost[];
  locked?: boolean;
}
