export interface IActionsSummary {
  id?: string;
  can_act?: boolean;
  count?: number;
  acted?: boolean;
  can_undo?: boolean;
}

export class ActionsSummary implements IActionsSummary {
  constructor(public id?: string, public can_act?: boolean, public count?: number, public acted?: boolean, public can_undo?: boolean) {}
}
