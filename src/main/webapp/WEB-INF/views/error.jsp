<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>Error</h2>
${exception}
<br />
<c:forEach items="${exception.stackTrace}" var="element">
    <c:out value="${element}" /><br />
</c:forEach>