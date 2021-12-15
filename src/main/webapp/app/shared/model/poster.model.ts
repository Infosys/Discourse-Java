export interface IPoster {
  extras?: string;
  description?: string;
  user_id?: string;
  primary_Group_d?: string;
}

export class Poster implements IPoster {
  constructor(public extras?: string, public description?: string, public user_id?: string, public primary_group_id?: string) {}
}
