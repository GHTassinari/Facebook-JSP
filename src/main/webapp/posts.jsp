<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <title>Facebook</title>
</head>
<body>
    <div class="container">
        <div class="row pt-5">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">
                    <i class="bi bi-house"></i> Início
                </a>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h1 class="m-0">Posts</h1>
                    <a href="${pageContext.request.contextPath}/post/new" class="btn btn-primary">
                        <i class="bi bi-plus"></i> Novo Post
                    </a>
                </div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Conteúdo</th>
                            <th scope="col">Data</th>
                            <th scope="col">Usuário</th>
                            <th scope="col">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="post" items="${posts}">
                            <tr>
                                <th scope="row">${post.id}</th>
                                <td>${post.content}</td>
                                <td>${post.postDate}</td>
                                <td>${post.user.name}</td>
                                <td>
                                    <a class="bi bi-pencil-square me-2" 
                                       href="${pageContext.request.contextPath}/post/update?postId=${post.id}"></a>
                                    <a class="bi bi-trash" 
                                       href="${pageContext.request.contextPath}/post/delete?postId=${post.id}" 
                                       onclick="return confirm('Tem certeza que deseja excluir?')"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-1"></div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>