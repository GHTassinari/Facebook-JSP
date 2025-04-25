package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/posts", "/post/save", "/post/update", "/post/delete", "/post/new"})
public class PostsController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getRequestURI();

        System.out.println(action);

        switch (action) {
            case "/facebook/posts": {
                loadPosts(req);
                RequestDispatcher rd = req.getRequestDispatcher("/posts.jsp");
                rd.forward(req, resp);
                break;
            }
            case "/facebook/post/new": {
                loadUsers(req);
                RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
                rd.forward(req, resp);
                break;
            }
            case "/facebook/post/update": {
                loadPost(req);
                loadUsers(req);
                RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
                rd.forward(req, resp);
                break;
            }
            case "/facebook/post/delete": {
                deletePost(req);
                resp.sendRedirect("/facebook/posts");
                break;
            }
            default:
                throw new IllegalArgumentException("Unexpected value: " + action);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getRequestURI();

        if (action.equals("/facebook/post/save")) {
            String postId = req.getParameter("post_id");
            if (postId != null && !postId.equals("")) {
                updatePost(req);
            } else {
                insertPost(req);
            }
            resp.sendRedirect("/facebook/posts");
        } else {
            throw new IllegalArgumentException("Unexpected POST action: " + action);
        }
    }

    private void loadPosts(HttpServletRequest req) {
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        try {
            List<Post> posts = dao.listAll();
            req.setAttribute("posts", posts);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers(HttpServletRequest req) {
        UserDAO dao = DAOFactory.createDAO(UserDAO.class);
        try {
            List<User> users = dao.listAll();
            req.setAttribute("users", users);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void insertPost(HttpServletRequest req) {
        Post post = createPost(req);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        try {
            dao.save(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void updatePost(HttpServletRequest req) {
        Post post = createPost(req);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        try {
            dao.update(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void deletePost(HttpServletRequest req) {
        String postIdString = req.getParameter("postId");
        int postId = Integer.parseInt(postIdString);
        Post post = new Post(postId);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        try {
            dao.delete(post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private void loadPost(HttpServletRequest req) {
        String postIdParameter = req.getParameter("postId");
        int postId = Integer.parseInt(postIdParameter);
        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
        try {
            Post post = dao.findById(postId);
            if (post == null) {
                throw new ModelException("Post não encontrado para alteração");
            }
            req.setAttribute("post", post);
        } catch (ModelException e) {
            e.printStackTrace();
        }
    }

    private Post createPost(HttpServletRequest req) {
        String postId = req.getParameter("post_id");
        String content = req.getParameter("post_content");
        String userId = req.getParameter("user_id");
        String postDate = req.getParameter("post_date");

        Post post;
        if (postId != null && !postId.equals("")) {
            post = new Post(Integer.parseInt(postId));
        } else {
            post = new Post();
        }

        post.setContent(content);

        UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
        try {
            User user = userDAO.findById(Integer.parseInt(userId));
            post.setUser(user);
        } catch (ModelException e) {
            e.printStackTrace();
        }

        try {
            if (postDate != null && !postDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(postDate);
                post.setPostDate(date);
            } else {
                post.setPostDate(new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
            post.setPostDate(new Date()); 
        }

        return post;
    }
}