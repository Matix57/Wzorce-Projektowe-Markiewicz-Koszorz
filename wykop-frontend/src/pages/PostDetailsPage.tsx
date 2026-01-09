import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useMemo, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { addComment, getComments, getPostDecorated } from "../features/blog/blogApi";
import type { CommentDto } from "../types/blog";

export default function PostDetailsPage() {
  const params = useParams();
  const postId = useMemo(() => Number(params.id), [params.id]);
  const qc = useQueryClient();

  const postQ = useQuery({
    queryKey: ["post", postId],
    queryFn: () => getPostDecorated(postId, { excludeComments: true }),
    enabled: Number.isFinite(postId),
  });

  const commentsQ = useQuery({
    queryKey: ["post", postId, "comments"],
    queryFn: () => getComments(postId),
    enabled: Number.isFinite(postId),
  });

  const [author, setAuthor] = useState("");
  const [content, setContent] = useState("");

  const addCommentM = useMutation({
    mutationFn: (payload: CommentDto) => addComment(postId, payload),
    onSuccess: async () => {
      setContent("");
      await qc.invalidateQueries({ queryKey: ["post", postId, "comments"] });
    },
  });

  if (postQ.isLoading) return <div className="muted">Ładowanie…</div>;
  if (postQ.error) return <div className="error">Nie znaleziono posta.</div>;

  const post = postQ.data!;

  return (
    <div style={{ display: "grid", gap: 14 }}>
      <div>
        <Link to="/" className="btn">
          ← Wróć
        </Link>
      </div>

      <article className="card">
        <div className="card-body">
          <h1 className="h1" style={{ marginBottom: 8 }}>
            {post.title}
          </h1>
          <p style={{ whiteSpace: "pre-wrap", margin: 0 }}>{post.content}</p>

          {post.tags?.length ? (
            <div className="tags">
              {post.tags.map((t) => (
                <span key={t} className="tag">
                  #{t}
                </span>
              ))}
            </div>
          ) : null}
        </div>
      </article>

      <section className="card">
        <div className="card-body">
          <h2 style={{ marginTop: 0 }}>Komentarze</h2>

          {commentsQ.isLoading ? (
            <div className="muted">Ładowanie komentarzy…</div>
          ) : commentsQ.error ? (
            <div className="error">Błąd pobierania komentarzy.</div>
          ) : commentsQ.data?.length ? (
            <ul className="list">
              {commentsQ.data.map((c) => (
                <li key={c.id} className="card" style={{ boxShadow: "none" }}>
                  <div className="card-body">
                    <div className="muted" style={{ fontSize: 13 }}>
                      {c.author?.trim() ? c.author : "anon"}
                    </div>
                    <div style={{ whiteSpace: "pre-wrap" }}>{c.content}</div>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <div className="muted">Brak komentarzy.</div>
          )}

          <hr className="hr" />

          <h3>Dodaj komentarz</h3>

          <form
            className="form"
            onSubmit={(e) => {
              e.preventDefault();
              addCommentM.mutate({
                author: author.trim() ? author.trim() : undefined,
                content,
              });
            }}
          >
            <input
              className="input"
              value={author}
              onChange={(e) => setAuthor(e.target.value)}
              placeholder="Nick (opcjonalnie)"
            />
            <textarea
              className="textarea"
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="Treść komentarza"
              rows={4}
              required
            />
            <button className="btn btn-primary" type="submit" disabled={addCommentM.isPending}>
              {addCommentM.isPending ? "Dodawanie…" : "Dodaj"}
            </button>

            {addCommentM.isError ? (
              <div className="error">Nie udało się dodać komentarza.</div>
            ) : null}
          </form>
        </div>
      </section>
    </div>
  );
}