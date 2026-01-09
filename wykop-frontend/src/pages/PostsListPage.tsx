import { useQuery } from "@tanstack/react-query";
import { getAllPosts } from "../features/blog/blogApi";
import { Link } from "react-router-dom";

export default function PostsListPage() {
  const { data, isLoading, error } = useQuery({
    queryKey: ["posts"],
    queryFn: getAllPosts,
  });

  if (isLoading) return <div className="muted">Ładowanie…</div>;
  if (error) return <div className="error">Błąd pobierania postów.</div>;

  return (
    <div style={{ display: "grid", gap: 14 }}>
      <h1 className="h1">Posty</h1>

      {data?.length ? (
        <ul className="list">
          {data.map((p) => (
            <li key={p.id} className="card">
              <div className="card-body">
               <h2 className="post-title">
  <Link to={`/posts/${p.id}`}>{p.title}</Link>
</h2>
                <p className="post-excerpt">
                  {p.content?.slice(0, 240)}
                  {p.content && p.content.length > 240 ? "…" : ""}
                </p>

                {p.tags?.length ? (
                  <div className="tags">
                    {p.tags.map((t) => (
                      <span key={t} className="tag">
                        #{t}
                      </span>
                    ))}
                  </div>
                ) : null}
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <div className="card">
          <div className="card-body muted">Brak postów.</div>
        </div>
      )}
    </div>
  );
}