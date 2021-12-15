import { ITopicOrPost } from './topic-or-post.model';

export interface IPostStream {
  posts?: ITopicOrPost[];
  stream?: any[];
  id?: string;
}
