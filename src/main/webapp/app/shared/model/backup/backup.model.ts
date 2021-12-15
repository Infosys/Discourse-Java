export interface IBackup {
  filename?: string;
  size?: number;
  last_modified?: string;
}

export class Backup implements IBackup {
  constructor(public filename: string, public size: number, public last_modified: string) {}
}
