<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<%@ page import="cybersoft.java18.crm.util.UrlUtil" %>
<%@ page import="cybersoft.java18.crm.util.RoleUtil" %>
<%@ page import="cybersoft.java18.crm.model.UserModel" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/png" sizes="16x16" href="${pageContext.request.contextPath}/resources/plugins/images/favicon.png">
    <title>Pixel Admin</title>
    <!-- Bootstrap Core CSS -->
    <link href="${pageContext.request.contextPath}/resources/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Menu CSS -->
    <link href="${pageContext.request.contextPath}/resources/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css">
    <!-- animation CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/animate.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <!-- color CSS -->
    <link href="${pageContext.request.contextPath}/resources/css/colors/blue-dark.css" id="theme" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/custom.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <!-- Preloader -->
    <div class="preloader">
        <div class="cssload-speeding-wheel"></div>
    </div>
    <div id="wrapper">
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top m-b-0">
                <div class="navbar-header"> 
                    <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse">
                        <i class="fa fa-bars"></i>
                    </a>
                    <div class="top-left-part">
                        <a class="logo" href="index.html">
                            <b>
                                <img src="${pageContext.request.contextPath}/resources/plugins/images/pixeladmin-logo.png" alt="home" />
                            </b>
                            <span class="hidden-xs">
                                <img src="${pageContext.request.contextPath}/resources/plugins/images/pixeladmin-text.png" alt="home" />
                            </span>
                        </a>
                    </div>
                    <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
                        <li>
                            <form role="search" class="app-search hidden-xs">
                                <input type="text" placeholder="Search..." class="form-control"> 
                                <a href="">
                                    <i class="fa fa-search"></i>
                                </a>
                            </form>
                        </li>
                    </ul>
                    <ul class="nav navbar-top-links navbar-right pull-right">
                        <li>
                            <div class="dropdown">
                                <a class="profile-pic dropdown-toggle" data-toggle="dropdown" href="#"> 
                                    <img src="${pageContext.request.contextPath}/resources/plugins/images/users/varun.jpg" alt="user-img" width="36" class="img-circle" />
                                    <b class="hidden-xs">${sessionScope.currentUser.fullname}</b>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a href="<%=request.getContextPath() + UrlUtil.URL_USER_PROFILE %>">Thông tin cá nhân</a></li>
                                    <li><a href="#">Thống kê công việc</a></li>
                                    <li class="divider"></li>
                                    <li><a href="<%=request.getContextPath() + UrlUtil.URL_LOGOUT %>">Đăng xuất</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
                <!-- /.navbar-header -->
                <!-- /.navbar-top-links -->
                <!-- /.navbar-static-side -->
            </nav>
        <!-- Left navbar-header -->
        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse slimscrollsidebar">
                <ul class="nav" id="side-menu">
                    <li style="padding: 10px 0 0;">
                        <a href="<%=request.getContextPath() + UrlUtil.URL_HOME %>" class="waves-effect"><i class="fa fa-clock-o fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_USER %>" class="waves-effect"><i class="fa fa-user fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_ROLE %>" class="waves-effect"><i class="fa fa-modx fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Quyền</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_JOB %>" class="waves-effect"><i class="fa fa-table fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Dự án</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_TASK %>" class="waves-effect"><i class="fa fa-table fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Công việc</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_BLANK %>" class="waves-effect"><i class="fa fa-columns fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Blank Page</span></a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath() + UrlUtil.URL_ERROR %>" class="waves-effect"><i class="fa fa-info-circle fa-fw"
                                aria-hidden="true"></i><span class="hide-menu">Error 404</span></a>
                    </li>
                </ul>
            </div>
        </div>
        <!-- Left navbar-header end -->
        <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                <div class="row bg-title">
                    <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                        <h4 class="page-title">Danh sách công việc</h4>
                    </div>
                    <div class="col-lg-9 col-sm-8 col-md-8 col-xs-12 text-right">
                        <a href="<%=request.getContextPath() + UrlUtil.URL_TASK_ADD %>" class="btn btn-sm btn-success">Add task</a>
                    </div>
                    <!-- /.col-lg-12 -->
                </div>
                <!-- /row -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="white-box">
                            <div class="table-responsive">
                                <table class="table" id="example">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên Công Việc</th>
                                            <th>Dự Án</th>
                                            <th>Người Thực Hiện</th>
                                            <th>Ngày Bắt Đầu</th>
                                            <th>Ngày Kết Thúc</th>
                                            <th>Trạng Thái</th>
                                            <th>Hành Động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="task" items="${tasks}" varStatus="loop">
                                             <tr>
                                                 <td>${loop.index + 1}</td>
                                                 <td>${task.name}</td>
                                                 <td>${task.jobName}</td>
                                                 <td>${task.personInCharge}</td>
                                                 <td>${task.startTime}</td>
                                                 <td>${task.endTime}</td>
                                                 <td>${task.statusName}</td>
                                                 <c:choose>
                                                    <c:when test="${sessionScope.currentUser.role.name ne RoleUtil.ROLE_MEMBER }">
                                                        <td>
                                                            <a href="<%=request.getContextPath() + UrlUtil.URL_TASK_MODIFY %>?taskId=${task.id}" class="btn btn-sm btn-primary">Sửa</a>
                                                            <a onClick="showConfirmDialog('${sessionScope.currentUser.role.name}', '${task.id}')" class="btn btn-sm btn-danger">Xóa</a>
                                                        </td>
                                                    </c:when>
                                                    <c:when test="${sessionScope.currentUser.role.name eq RoleUtil.ROLE_MEMBER }">
                                                        <td style="pointer-events: none;">
                                                            <a href="<%=request.getContextPath() + UrlUtil.URL_TASK_MODIFY %>?taskId=${task.id}" class="btn btn-sm btn-primary">Sửa</a>
                                                            <a onClick="showConfirmDialog('${sessionScope.currentUser.role.name}', '${task.id}')" class="btn btn-sm btn-danger">Xóa</a>
                                                        </td>
                                                    </c:when>
                                                 </c:choose>
                                             </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.row -->
            </div>
            <!-- /.container-fluid -->
            <footer class="footer text-center"> 2018 &copy; myclass.com </footer>
        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
    <!-- jQuery -->
    <script src="${pageContext.request.contextPath}/resources/plugins/bower_components/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- Menu Plugin JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
    <!--slimscroll JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/jquery.slimscroll.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <!--Wave Effects -->
    <script src="${pageContext.request.contextPath}/resources/js/waves.js"></script>
    <!-- Custom Theme JavaScript -->
    <script src="${pageContext.request.contextPath}/resources/js/custom.min.js"></script>
    <script>
        $(document).ready(function () {
            $('#example').DataTable();
        });

        function showConfirmDialog(role, taskId) {
            var adminRole = "${RoleUtil.ROLE_ADMIN}"
            console.log("${pageContext.request.contextPath}${UrlUtil.URL_TASK_DELETE }?taskId="+taskId)
            if(role === adminRole) {
                if(confirm("Do you want to delete this task") == true) {
                    console.log('OK')
                     $.ajax({
                         url: "${pageContext.request.contextPath}${UrlUtil.URL_TASK_DELETE}?taskId="+taskId,
                         type: 'get',
                         success: function() {
                            window.location.reload()
                         }
                     })
                } else {
                    console.log('Cancel')
                }
            } else {
            }
        }
    </script>
</body>

</html>