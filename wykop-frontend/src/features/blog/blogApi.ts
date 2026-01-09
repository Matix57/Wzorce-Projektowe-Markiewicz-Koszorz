import { api } from "../../lib/api";
import type { CommentDto, PostDto, TagDto } from "../../types/blog";

export async function getAllPosts() {
  const { data } = await api.get<PostDto[]>("/api/posts");
  return data;
}

export async function getPostDecorated(
  id: number,
  opts?: { excludeTags?: boolean; excludeComments?: boolean }
) {
  const { data } = await api.get<PostDto>(`/api/posts/${id}/decorated`, {
    params: {
      excludeTags: opts?.excludeTags ?? false,
      excludeComments: opts?.excludeComments ?? false,
    },
  });
  return data;
}

export async function createPost(payload: PostDto) {
  const { data } = await api.post<PostDto>("/api/posts", payload);
  return data;
}

export async function getComments(postId: number) {
  const { data } = await api.get<CommentDto[]>(`/api/posts/${postId}/comments`);
  return data;
}

export async function addComment(postId: number, payload: CommentDto) {
  const { data } = await api.post<CommentDto>(
    `/api/posts/${postId}/comments`,
    payload
  );
  return data;
}

export async function getAllTags() {
  const { data } = await api.get<TagDto[]>("/api/tags");
  return data;
}
