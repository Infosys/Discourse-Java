import { ICategory } from './category.model';

interface ICategoryListWithExtraData {
  can_create_category?: boolean;
  can_create_topic?: boolean;
  draft?: any;
  draft_key?: string;
  draft_sequence?: number;
  categories?: ICategory[];
}

export interface ICategoryResponse {
  category_list?: ICategoryListWithExtraData;
  success?: string;
  category?: ICategory;
  categoryResponses?: ICategory[];
}
