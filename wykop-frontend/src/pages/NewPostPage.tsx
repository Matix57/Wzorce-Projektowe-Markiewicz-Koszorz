import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createPost } from "../features/blog/blogApi";
import type { PostDto } from "../types/blog";

export default function NewPostPage() {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [tagsRaw, setTagsRaw] = useState("");
  const qc = useQueryClient();
  const nav = useNavigate();

  const m = useMutation({
    mutationFn: (payload: PostDto) => createPost(payload),
    onSuccess: async () => {
      await qc.invalidateQueries({ queryKey: ["posts"] });
      nav("/");
    },
  });

  return (
    <div style={{ display: "grid", gap: 14, maxWidth: 720 }}>
      <h1 className="h1">Dodaj post</h1>

      <div className="card">
        <div className="card-body">
          <form
            className="form"
            onSubmit={(e) => {
              e.preventDefault();
              const tags = tagsRaw
                .split(",")
                .map((t) => t.trim())
                .filter(Boolean);

              m.mutate({ title, content, tags });
            }}
          >
            <label>
              <div className="muted" style={{ marginBottom: 6 }}>
                Tytuł
              </div>
              <input
                className="input"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Np. Nowy post o Springu"
                required
              />
            </label>

            <label>
              <div className="muted" style={{ marginBottom: 6 }}>
                Treść
              </div>
              <textarea
                className="textarea"
                value={content}
                onChange={(e) => setContent(e.target.value)}
                placeholder="Napisz coś…"
                rows={10}
                required
              />
            </label>

            <label>
              <div className="muted" style={{ marginBottom: 6 }}>
                Tagi
              </div>
              <input
                className="input"
                value={tagsRaw}
                onChange={(e) => setTagsRaw(e.target.value)}
                placeholder="Np. java, spring, docker"
              />
            </label>

            <div style={{ display: "flex", gap: 10 }}>
              <button className="btn btn-primary" type="submit" disabled={m.isPending}>
                {m.isPending ? "Zapisywanie…" : "Dodaj"}
              </button>
              <button className="btn" type="button" onClick={() => nav("/")}>
                Anuluj
              </button>
            </div>

            {m.isError ? (
              <div className="error">Nie udało się dodać posta.</div>
            ) : null}
          </form>
        </div>
      </div>
    </div>
  );
}