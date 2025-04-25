<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <title>${post == null ? 'Cadastro de Posts' : 'Editar Post'}</title>
</head>
<body>
    <div class="container">
        <div class="row pt-5">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <h1>${post == null ? 'Cadastro de Posts' : 'Editar Post'}</h1>
                <form action="${pageContext.request.contextPath}/post/save" method="POST">
                    <input type="hidden" id="post_id" name="post_id" value="${post != null ? post.id : ''}">

                    <div class="mb-3">
                        <label for="user_id" class="form-label">Usuário</label>
                        <select id="user_id" name="user_id" class="form-control" required>
                            <option value="">Selecione um usuário</option>
                            <c:forEach var="user" items="${users}">
                                <option value="${user.id}" ${post != null && user.id == post.user.id ? 'selected' : ''}>
                                    ${user.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="post_content" class="form-label">Conteúdo</label>
                        <textarea id="post_content" name="post_content" class="form-control" required>${post != null ? post.content : ''}</textarea>
                    </div>

                    <div class="mb-3">
                        <label for="post_date" class="form-label">Data</label>
                        <input type="date" id="post_date" name="post_date" class="form-control" 
                               value="${post != null ? post.postDate != null ? post.postDate.toString().substring(0, 10) : '' : ''}" required>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-save"></i> Enviar
                    </button>
                    <a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">
                        <i class="bi bi-arrow-left"></i> Voltar
                    </a>
                </form>
            </div>
            <div class="col-md-1"></div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>