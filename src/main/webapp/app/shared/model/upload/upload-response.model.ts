export interface IUploadResponse {
  id?: string;
  url?: string;
  original_filename?: string;
  filesize?: number;
  width?: number;
  height?: number;
  thumbnail_width?: number;
  thumbnail_height?: number;
  extension?: string;
  short_url?: string;
  short_path?: string;
  retain_hours?: any;
  human_filesize?: string;
}
