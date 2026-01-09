export type CommentDto = {
  id?: number;
  author?: string;
  content: string;
};

export type PostDto = {
  id?: number;
  title: string;
  content: string;
  tags?: string[];
  comments?: CommentDto[];
};

export type TagDto = {
  id?: number;
  name: string;
};
