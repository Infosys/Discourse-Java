import { INotification } from './notification.model';

export interface INotificationResponse {
  notifications: INotification[];
  total_rows_notifications: number;
  seen_notification_id: string;
  load_more_notifications: string;
}
