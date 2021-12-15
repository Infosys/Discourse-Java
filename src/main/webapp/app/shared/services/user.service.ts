import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { ITopicOrPost } from '../model/topic-or-post.model';
import { IUserResponse } from '../model/user/user-response.model';

type EntityArrayResponseTypeTopic = HttpResponse<ITopicOrPost[]>;
type EntityArrayPostResponse = HttpResponse<ITopicOrPost[]>;
type EntityArrayResponseTypeUserResponse = HttpResponse<IUserResponse>;
@Injectable({
  providedIn: 'root',
})
export class UserService {
  private topicsByUserResourceUrl = SERVER_API_URL + 'api/topics/user';
  private postsByUserResourceUrl = SERVER_API_URL + 'api/posts/user';
  private currentLoggedInUserResourceUrl = SERVER_API_URL + 'api/currentLoginUsers';
  private likesByUserResourceUrl = SERVER_API_URL + 'api/posts/user/likes';
  private hiddenPostsByUserResourceUrl = SERVER_API_URL + 'api/posts/user/hidden';
  private usersAcceptPolicyResourceUrl = SERVER_API_URL + 'api/users-accept-privacy';

  constructor(protected http: HttpClient) {}
  getTopicsByUser(): Observable<EntityArrayResponseTypeTopic> {
    return this.http.get<ITopicOrPost[]>(this.topicsByUserResourceUrl, { observe: 'response' });
  }

  getPostsByUser(): Observable<EntityArrayPostResponse> {
    return this.http.get<ITopicOrPost[]>(this.postsByUserResourceUrl, { observe: 'response' });
  }

  usersAcceptPolicy(): Observable<EntityArrayResponseTypeUserResponse> {
    return this.http.get<IUserResponse>(this.usersAcceptPolicyResourceUrl, { observe: 'response' });
  }

  getCurrentlyLoggedInUser(): Observable<EntityArrayResponseTypeUserResponse> {
    return this.http.get<IUserResponse>(this.currentLoggedInUserResourceUrl, { observe: 'response' });
  }
  getLikesByUser(): Observable<EntityArrayPostResponse> {
    return this.http.get<ITopicOrPost[]>(this.likesByUserResourceUrl, { observe: 'response' });
  }

  getHiddenPostsByUser(): Observable<EntityArrayPostResponse> {
    return this.http.get<ITopicOrPost[]>(this.hiddenPostsByUserResourceUrl, { observe: 'response' });
  }
}
