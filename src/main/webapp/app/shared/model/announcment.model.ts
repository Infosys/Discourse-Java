import { Moment } from 'moment';
import { AnnouncmentStatus } from '../model/enumerations/announcment-status.model';

export interface IAnnouncement {
  id?: number;
  title?: string;
  raw?: any;
  deletedBy?: string;
  deletedAt?: Moment;
  status?: AnnouncmentStatus;
  created_at?: Moment;
}
