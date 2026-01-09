import { Link, Route, Routes } from "react-router-dom";
import PostsListPage from "./pages/PostsListPage";
import NewPostPage from "./pages/NewPostPage";
import PostDetailsPage from "./pages/PostDetailsPage";

export default function App() {
  return (
    <>
      <header className="topbar">
        <div className="topbar-inner">
          <div className="brand">
            <Link to="/" className="brand-title">
              WykopBlog
            </Link>
            <span className="muted">demo</span>
          </div>

          <nav className="nav">
            <Link to="/" className="btn">Posty</Link>
            <Link to="/new" className="btn btn-primary">Dodaj post</Link>
          </nav>
        </div>
      </header>

      <main className="container">
        <Routes>
          <Route path="/" element={<PostsListPage />} />
          <Route path="/new" element={<NewPostPage />} />
          <Route path="/posts/:id" element={<PostDetailsPage />} />
        </Routes>
      </main>
    </>
  );
}