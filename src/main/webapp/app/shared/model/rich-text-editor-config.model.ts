import { RichTextEditorType } from './enumerations/rich-text-editor-type.model';
import { IRichTextEditorValues } from './rich-text-editor-values.model';

export interface IRichTextEditorConfig {
  editorType?: RichTextEditorType;
  values?: IRichTextEditorValues;
  submitButtonText?: string;
  heading?: string;
}

export class RichTextEditorConfig implements IRichTextEditorConfig {
  constructor(
    public editorType?: RichTextEditorType,
    public values?: IRichTextEditorValues,
    public submitButtonText?: string,
    public heading?: string
  ) {}
}
