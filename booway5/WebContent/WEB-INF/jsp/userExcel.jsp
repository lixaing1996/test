<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = "http://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();
			request.setAttribute("basePath", path);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Excel展示界面</title>
<link rel="shortcut icon" href="img/favicon.ico" />

<!-- CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/font-awesome.min.css" rel="stylesheet" />
<link href="css/style.css" rel="stylesheet" />
</head>
<body>
	<div class="row">
		<div class="col-md-12">
			<nav role="navigation" class="navbar navbar-inverse lite-nav">
			<div class="navbar-header">
				<button data-target="#bs-example-navbar-collapse-2"
					data-toggle="collapse" class="navbar-toggle" type="button">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a href="#" class="navbar-brand">大作业</a>
			</div>
			<div id="bs-example-navbar-collapse-2"
				class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="/booway5/user">数据库实现 <i
							class="fa fa-home"></i></a></li>
					<li><a href="/booway5/userExcel">Excel实现 <i
							class="fa fa-calendar"></i></a></li>
					<li><a href="/booway5/userXml">Xml实现 <i class="fa fa-cogs"></i></a></li>
				</ul>
			</div>
			</nav>
		</div>
	</div>
	<form role="search" class="navbar-form navbar-left"
		action="/booway5/userExcel" method="get">
		<div class="form-group">
			<input type="text" placeholder="Search" class="form-control"
				name="username"> <input type="hidden" placeholder="Search"
				class="form-control" name="flag" value="search">
		</div>
		<button class="btn btn-danger" type="submit">查询</button>
	</form>

	<div class="col-md-6"
		style="margin: auto; position: absolute; top: 150px; left: 0px; right: 0px">
		<button class="btn btn-danger" id="all">查询所有</button>
		<div class="lt-tables">
			<div class="lt-box">
				<div class="table-responsive">
					<table class="table table-hover">
						<thead>
							<tr>
								<th class="no_sort">序号</th>
								<th class="no_sort">姓名</th>
								<th class="no_sort">年龄</th>
								<th class="no_sort">修改</th>
								<th class="no_sort">删除</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${userExcel}" var="u" varStatus="status"
								begin="${(pageNosExcel-1)*5 }" end="${pageNosExcel*5-1}">
								<tr class="${u.id}">
									<td>${u.id}</td>
									<td>${u.name}</td>
									<td>${u.age}</td>
									<td><button class="btn btn-radius btn-success"
											onclick="updateUser()" name="${u.id}">
											<i class="fa fa-pencil"></i>&nbsp;修改
										</button></td>
									<td><button class="btn btn-radius btn-danger"
											onclick="deleteUser()" name="${u.id}">
											<i class="fa fa-trash-o"></i>&nbsp;删除
										</button></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="5"><button id="addUser"
										class="btn btn-radius btn-info">
										<i class="fa fa-check"></i>&nbsp;增加
									</button></td>
							</tr>
						</tbody>
					</table>
					<center>
						<c:if test="${pageNosExcel>1 }">
							<a href="/booway5/page?pageNos=1&flag=2"
								class="btn  btn-primary-alt">首页</a>
							<a href="/booway5/page?pageNos=${pageNosExcel-1 }&flag=2"
								class="btn  btn-primary-alt">上一页</a>
						</c:if>
						<c:if test="${pageNosExcel <countPageExcel }">
							<a href="/booway5/page?pageNos=${pageNosExcel+1 }&flag=2"
								class="btn  btn-primary-alt">下一页</a>
							<a href="/booway5/page?pageNos=${countPageExcel }&flag=2"
								class="btn btn-primary-alt">末页</a>
						</c:if>
					</center>
					<form action="/booway5/page">
						<h4 align="center">
							共${countPageExcel}页 <input type="text" value="${pageNosExcel}"
								style="width: 5%; color: #555; background-color: #fff"
								name="pageNos" size="1">页 <input type="hidden"
								name="flag" value="2">
							<button type="submit" class="btn btn-primary-alt btn-radius">go</button>
						</h4>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${basePath}/resources/js/jquery.js"></script>
	<script type="text/javascript"
		src="${basePath}/resources/js/jquery.min.js"></script>
	<script type="text/javascript">
		$("#addUser").click(function() {
			window.location = '/booway5/userExcelAdd?flag=tiaozhuan';
		});

		$("#all").click(function() {
			window.location = '/booway5/userExcel';
		});

		function deleteUser() {
			$.ajax({
				type : 'get',
				url : '/booway5/userExcelDelete',
				dataType : 'json',
				data : {
					"id" : event.srcElement.name
				},

				error : function(XmlHttpRequest, textStatus, errorThrown) {
					alert("操作失败!");
				},
				success : function(result) {
					$("tr[class^=" + result + "]").hide();
				}
			});
		}

		function updateUser() {
			window.location = "/booway5/userExcelUpdate?id="
					+ event.srcElement.name + "&flag=id";
		}
	</script>
</body>
</html>