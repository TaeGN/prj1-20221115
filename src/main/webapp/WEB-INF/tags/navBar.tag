<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ attribute name="active" %>
<style>
#searchTypeSelect {
	width: auto;
}
</style>

<%-- authorize tag --%>
<%-- spring security expressions --%>

<%--
<sec:authorize access="isAuthenticated()">
	<h1>로그인 됨!</h1>
</sec:authorize>

<sec:authorize access="not isAuthenticated()">
	<h1>로그인 안됨!</h1>
</sec:authorize>
--%>

<sec:authorize access="isAuthenticated()" var="loggedIn" />
<%--  <c:if test="${loggedIn }">
 	<h1>로그인 됨!</h1>
 </c:if>
 
  <c:if test="${not loggedIn }">
 	<h1>로그인 안됨!</h1>
 </c:if> --%>

<c:url value="/board/list" var="listLink"></c:url>
<c:url value="/board/register" var="registerLink"></c:url>
<c:url value="/member/signup" var="signupLink"></c:url>
<c:url value="/member/list" var="memberListLink"></c:url>
<c:url value="/member/login" var="loginLink"></c:url>
<c:url value="/member/logout" var="logoutLink"></c:url>

<sec:authentication property="name" var="username"/>
<c:url value="/member/info" var="memberInfoLink">
	<c:param name="id" value="${username }"></c:param>
</c:url>

<nav class="navbar navbar-expand-md bg-light mb-3">
  <div class="container-md">
    <a class="navbar-brand" href="${listLink }">
    	<c:url value="/content/spring-logo.jpeg" var="logoLink"></c:url>
    	<img alt="" src="${logoLink }" style="height: 60px;">
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link ${active eq 'list' ? 'active' : '' }" href="${listLink }">목록</a>
        </li>
        
        
        
        <!-- 로그인 상태 -->
		 <c:if test="${loggedIn }">
	        <li class="nav-item">
	          <a class="nav-link ${active eq 'register' ? 'active' : '' }" href="${registerLink }">작성</a>
	        </li>
		 </c:if>
		 
		 
		 <!-- 권한이 admin인 사람만 회원목록 열람 가능 -->
	     <sec:authorize access="hasAuthority('admin')">
		     <li class="nav-item">
		       <a class="nav-link ${active eq 'memberList' ? 'active' : '' }" href="${memberListLink }">회원목록</a>
		     </li>
		 </sec:authorize>
		 
		 
		 <!-- 로그아웃 상태 -->
         <c:if test="${not loggedIn }">
	        <li class="nav-item">
	          <a class="nav-link ${active eq 'signup' ? 'active' : '' }" href="${signupLink }">회원가입</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link " href="${loginLink }">로그인</a>
	        </li>
		 </c:if>
		 
		 
	     <!-- 로그인 상태 -->
		 <c:if test="${loggedIn }">   
	        <li class="nav-item">
		       <a class="nav-link ${active eq 'memberInfo' ? 'active' : '' }" href="${memberInfoLink }">회원정보</a>
		     </li>
	        <li class="nav-item">
	          <a class="nav-link" href="${logoutLink }">로그아웃</a>
	        </li>       
		 </c:if>
      </ul>
      <form action="${listLink }" class="d-flex" role="search">
      
      	<!-- select.form-select>option*4 -->
      	<select name="t" id="searchTypeSelect" class="form-select" >
      		<option value="all">전체</option>
      		<option value="title" ${param.t eq 'title' ? 'selected' : '' }>제목</option>
      		<option value="content" ${param.t eq 'content' ? 'selected' : '' }>본문</option>
      		<option value="writer" ${param.t eq 'writer' ? 'selected' : '' }>작성자</option>
      	</select>
      	
        <input value="${param.q }" class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="q">
        <button class="btn btn-outline-success" type="submit">
        	<i class="fa-solid fa-magnifying-glass"></i>
        </button>
      </form>
    </div>
  </div>
</nav>