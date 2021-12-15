export interface IRichTextEditorValues {
  title?: string;
  categoryId?: string | null;
  tags?: string;
  htmlContent?: string;
}

export class RichTextEditorValues implements IRichTextEditorValues {
  constructor(public title?: string, public categoryId?: string | null, public tags?: string, public htmlContent?: string) {}
}
