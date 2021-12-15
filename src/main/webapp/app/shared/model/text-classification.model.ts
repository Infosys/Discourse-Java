import { TextClassficationType } from 'app/shared/model/enumerations/text-classfication-type.model';

export interface ITextClassification {
  id?: number;
  contentId?: number;
  toxicity?: number;
  severeToxicity?: number;
  obscene?: number;
  threat?: number;
  insult?: number;
  identityHate?: number;
  type?: TextClassficationType;
}

export class TextClassification implements ITextClassification {
  constructor(
    public id?: number,
    public contentId?: number,
    public toxicity?: number,
    public severeToxicity?: number,
    public obscene?: number,
    public threat?: number,
    public insult?: number,
    public identityHate?: number,
    public type?: TextClassficationType
  ) {}
}
