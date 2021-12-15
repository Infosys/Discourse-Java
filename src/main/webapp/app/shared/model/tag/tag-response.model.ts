import { ICategory } from '../category/category.model';
import { ITag } from './tag.model';

interface IPermissions {
  everyone?: number;
  staff?: number;
}

interface IExtras {
  categories?: ICategory[];
}

interface ITagGroup {
  id?: string;
  name?: string;
  tag_names?: any[];
  parent_tag_name?: any[];
  one_per_topic?: boolean;
  permissions?: IPermissions;
}

export interface ITagResponse {
  success?: string;
  tag_group?: ITagGroup;
  tag_groups?: ITagGroup[];
  tags?: ITag[];
  extras?: IExtras;
}
