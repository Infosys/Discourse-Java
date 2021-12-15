import { NotificationType } from '../enumerations/notification-type.model';
import { NotificationStatus } from '../enumerations/notification-status.model';

interface IData {
  badge_id: string;
  badge_name: string;
  badge_slug: string;
  badge_title: boolean;
  username: string;
}

export interface INotification {
  id?: string;
  userId?: string;
  notificationType?: NotificationType;
  read?: boolean;
  created_at?: string;
  postId?: string;
  topicId?: string;
  slug?: string;
  data?: IData;
  text?: string;
  title?: string;
  notificationStatus?: NotificationStatus;
  seenAt?: string;
}
