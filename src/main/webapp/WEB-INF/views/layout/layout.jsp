<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
 <c:set var="url">${pageContext.request.requestURL}</c:set>
 <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />
 <link rel="stylesheet" href="style/main.css" />
 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
 <script src="js/main.js"></script>
 <script src="js/jquery.tablePagination.0.5.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
 <div id="tsa">
  <div id="header"><tiles:insertAttribute name="header" /></div>
  <div id="menu"><tiles:insertAttribute name="menu" /></div>
  <div id="bodytitle"><tiles:insertAttribute name="bodytitle" /></div>
  <div id="body"><tiles:insertAttribute name="body" /></div>
  <div id="footer"><tiles:insertAttribute name="footer" /></div>
 </div>
</body>
</html>