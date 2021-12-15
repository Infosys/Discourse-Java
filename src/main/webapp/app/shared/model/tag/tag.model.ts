export interface ITag {
  id?: string;
  text?: string;
  count?: number;
  pm_count?: number;
  target_tag?: string;
  name?: string;
  topic_count?: number;
  staff?: boolean;
}

export class Tag implements ITag {
  constructor(
    public id?: string,
    public text?: string,
    public count?: number,
    public pm_count?: number,
    public target_tag?: string,
    public name?: string,
    public topic_count?: number,
    public staff?: boolean
  ) {}
}
