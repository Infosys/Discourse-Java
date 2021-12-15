import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AngularFireMessaging } from '@angular/fire/messaging';
import { BehaviorSubject } from 'rxjs';
import { NotificationService } from './notifications.service';
@Injectable()
export class MessagingService {
  currentMessage: BehaviorSubject<any> = new BehaviorSubject(null);

  constructor(private angularFireMessaging: AngularFireMessaging, private notificationService: NotificationService) {
    this.angularFireMessaging.messages.subscribe((messages: any) => {
      messages.onMessage = messages.onMessage.bind(messages);
      messages.onTokenRefresh = messages.onTokenRefresh.bind(messages);
    });
  }

  requestPermission(): void {
    this.angularFireMessaging.requestPermission.subscribe(
      () => {
        this.angularFireMessaging.requestToken.subscribe(
          token => {
            this.notificationService.registerUserFirebaseToken(token!).subscribe(
              () => {},
              (err: HttpErrorResponse) => {
                console.error('Unable to register firebase token for user.', err);
              }
            );
          },
          err => {
            console.error('Unable to get permission to notify.', err);
          }
        );
      },
      (err: any) => {
        console.error('Permission for notification denied by user.', err);
      }
    );
  }

  receiveMessage(): void {
    this.angularFireMessaging.messages.subscribe((notifications: any) => {
      this.currentMessage.next(notifications);
    });
  }
}
