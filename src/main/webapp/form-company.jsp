<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>
			
		<div id="container" class="container-fluid">
			<h3 class="page-header">Adicionar Companhia</h3>

			<form action="${pageContext.request.contextPath}/user/${action}" method="POST">
				<input type="hidden" value="${company.getId()}" name="companyId">
				<div class="row">
					<div class="form-group col-md-4">
					<label for="name">Nome</label>
						<input type="text" class="form-control" id="name" name="name" 
							   autofocus="autofocus" placeholder="Nome da companhia" 
							   required oninvalid="this.setCustomValidity('Por favor, informe o nome da companhia.')"
							   oninput="setCustomValidity('')"
							   value="${company.getName()}">
					</div>

					<div class="form-group col-md-4">
						<label for="name">Função</label>
						<input type="text" class="form-control" id="role" name="role" 
							   autofocus="autofocus" placeholder="Função da companhia" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a função da companhia.')"
							   oninput="setCustomValidity('')"
							   value="${company.getRole()}">
					</div>
					
					<div class="form-group col-md-4">
					<label for="mail">Data de abertura</label>
						<input type="datetime-local" class="form-control" id="start" name="start" 
							   autofocus="autofocus" placeholder="Data de abertura" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a data de abertura da companhia.')"
							   oninput="setCustomValidity('')"
							   value="${company.getStart()}">
					</div>
					
					<div class="form-group col-md-4">
					<label for="mail">Data de encerramento?</label>
						<input type="datetime-local" class="form-control" id="end" name="end" 
							   autofocus="autofocus" placeholder="Data de abertura" 
							   required oninvalid="this.setCustomValidity('Por favor, informe a data de encerramento da companhia. (caso não tenha encerrado, deixe a data como encerrada em 2050)')"
							   oninput="setCustomValidity('')"
							   value="${company.getEnd()}">
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/companies" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty company ? "Alterar Companhia" : "Cadastrar Companhia"}</button>
					</div>
				</div>
			</form>
		</div>

		<script src="js/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>
