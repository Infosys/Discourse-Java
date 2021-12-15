export const enum RichTextEditorType {
  TOPIC = 'TOPIC', // Everything is displayed
  REPLY = 'REPLY', // Only angular rich text editor is displayed
  EDIT_TOPIC_DISABLE_RTE = 'EDIT_TOPIC_DISABLE_RTE', // When topic's basic details are to be edited but not it's html content data (Rich Text Editor will not be displayed)
}
