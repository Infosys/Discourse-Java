import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { Observable } from 'rxjs';
import { INotification } from '../model/notification/notification.model';
import { createRequestOption } from '../util/request-util';

type EntityArrayResponseTypeNotification = HttpResponse<INotification[]>;

@Injectable({
  providedIn: 'root',
})
export class NotificationService {
  private registerUserFirebaseTokenResourceUrl = SERVER_API_URL + 'api/users/token';
  private getNotificationsResourceUrl = SERVER_API_URL + 'api/notifications';
  private readNotificationsResourceUrl = SERVER_API_URL + 'api/notifications/seen';
  private togglePushNotificationsSubscriptionResourceUrl = SERVER_API_URL + 'api/users/notification-subscription';

  constructor(protected http: HttpClient) {}

  registerUserFirebaseToken(token: string): Observable<any> {
    return this.http.post<any>(this.registerUserFirebaseTokenResourceUrl, { token }, { observe: 'response' });
  }

  getNotifications(): Observable<EntityArrayResponseTypeNotification> {
    const reqOptions = {
      start: 0,
      size: 10,
      sort: ['createdDate,desc'],
    };
    const params = createRequestOption(reqOptions);
    return this.http.get<INotification[]>(this.getNotificationsResourceUrl, { params, observe: 'response' });
  }

  markMultipleNotificationsAsRead(notificationIds: string[]): any {
    const notificationIdsRequestData = {
      notificationIds,
    };
    return this.http.post<any>(this.readNotificationsResourceUrl, notificationIdsRequestData, { observe: 'response' });
  }

  markSingleNotificationAsRead(notificationId: string): any {
    return this.http.get<any>(`${this.readNotificationsResourceUrl}/${notificationId}`, { observe: 'response' });
  }

  togglePushNotificationsSubscription(subscribeFlag: boolean): any {
    const params = createRequestOption({ subscribe: subscribeFlag });
    return this.http.get<any>(this.togglePushNotificationsSubscriptionResourceUrl, { params, observe: 'response' });
  }
}
