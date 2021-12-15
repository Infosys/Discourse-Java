import { Moment } from 'moment';
import { NotificationStatus } from 'app/shared/model/enumerations/notification-status.model';

export interface INotification {
  id?: number;
  userId?: string;
  topicId?: number;
  postId?: number;
  text?: string;
  title?: string;
  seenAt?: Moment;
  notificationStatus?: NotificationStatus;
}

export class Notification implements INotification {
  constructor(
    public id?: number,
    public userId?: string,
    public topicId?: number,
    public postId?: number,
    public text?: string,
    public title?: string,
    public seenAt?: Moment,
    public notificationStatus?: NotificationStatus
  ) {}
}
