import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { IPostResponse } from '../model/post/post-response.model';
import { ITopicOrPost } from '../model/topic-or-post.model';
import { createRequestOption } from '../util/request-util';

type EntityTopicOrPostResponse = HttpResponse<ITopicOrPost>;
type EntityArrayTopicOrPostResponse = HttpResponse<ITopicOrPost[]>;
type EntityPostResponse = HttpResponse<IPostResponse>;
@Injectable({
  providedIn: 'root',
})
export class PostService {
  private postsByTopicResourceUrl = SERVER_API_URL + 'api/postsByTopic';
  private getRepliesOfTopicResourceUrl = SERVER_API_URL + 'api/posts/replies';
  private postResourceUrl = SERVER_API_URL + 'api/posts';
  private likePostResourceUrl = SERVER_API_URL + 'api/post-actions';
  private unlikePostResourceUrl = SERVER_API_URL + 'api/post-actions/reset';
  private hidePostResourceUrl = SERVER_API_URL + 'api/posts-hide';
  private unhidePostResourceUrl = SERVER_API_URL + 'api/posts-unhide';
  private deletePostResourceUrl = SERVER_API_URL + 'api/posts';

  constructor(private http: HttpClient) {}

  getPostByTopic(topicId: string, req?: any): Observable<EntityArrayTopicOrPostResponse> {
    if (typeof req === 'undefined') {
      req = {};
    }
    req['topicId'] = topicId;
    const params = createRequestOption(req);
    return this.http.get<ITopicOrPost[]>(this.postsByTopicResourceUrl, { params, observe: 'response' });
  }

  updatePost(postId: string, data: any): Observable<EntityPostResponse> {
    return this.http.put<IPostResponse>(`${this.postResourceUrl}/${postId}`, data, { observe: 'response' });
  }

  getRepliesById(id: string): Observable<EntityArrayTopicOrPostResponse> {
    return this.http.get<ITopicOrPost[]>(`${this.getRepliesOfTopicResourceUrl}/${id}`, { observe: 'response' });
  }

  likePost(id: string): Observable<EntityTopicOrPostResponse> {
    const requestBody = {
      id,
      post_action_type_id: 1,
    };
    return this.http.post<ITopicOrPost>(this.likePostResourceUrl, requestBody, { observe: 'response' });
  }

  unlikePost(id: string): Observable<EntityTopicOrPostResponse> {
    const requestBody = {
      id,
      post_action_type_id: 1,
    };
    return this.http.post<ITopicOrPost>(this.unlikePostResourceUrl, requestBody, { observe: 'response' });
  }

  hidePost(id: string): Observable<any> {
    return this.http.get<any>(`${this.hidePostResourceUrl}/${id}`, { observe: 'response' });
  }

  unhidePost(id: string): Observable<any> {
    return this.http.get<any>(`${this.unhidePostResourceUrl}/${id}`, { observe: 'response' });
  }

  deletePost(id: string): Observable<any> {
    return this.http.delete<any>(`${this.deletePostResourceUrl}/${id}`, { observe: 'response' });
  }
}
