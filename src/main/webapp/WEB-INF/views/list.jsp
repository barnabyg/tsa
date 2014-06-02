<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${ not empty listMessage }"><c:out value="${ listMessage }" /></c:if>

<c:if test="${ not empty ruleNames }">
 <div id="ruleNames">
   <table class="PrettyTable">
    <thead>
    <tr>
     <td>Rules</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${ ruleNames }">
     <tr>
      <td><c:out value="${ name }" /></td>
     </tr>
    </c:forEach>
    </tbody>
   </table>
 </div>
</c:if>

<c:if test="${ not empty datasetNames }">
 <div id="datasetNames">
   <table class="PrettyTable">
    <thead>
    <tr>
     <td>Datasets</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${ datasetNames }">
     <tr>
      <td><c:out value="${ name }" /></td>
     </tr>
    </c:forEach>
    </tbody>
   </table>
 </div>
</c:if>

<c:if test="${ not empty strategyNames }">
 <div id="strategyNames">
   <table class="PrettyTable">
    <thead>
    <tr>
     <td>Strategies</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${ strategyNames }">
     <tr>
      <td><c:out value="${ name }" /></td>
     </tr>
    </c:forEach>
    </tbody>
   </table>
 </div>
</c:if>

<c:if test="${ not empty indicatorNames }">
 <div id="indicatorNames">
   <table class="PrettyTable">
    <thead>
    <tr>
     <td>Indicators</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${ indicatorNames }">
     <tr>
      <td><c:out value="${ name }" /></td>
     </tr>
    </c:forEach>
    </tbody>
   </table>
 </div>
</c:if>

<c:if test="${ not empty resultsetNames }">
 <div id="resultsetNames">
   <table class="PrettyTable">
    <thead>
    <tr>
     <td>Result sets</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="name" items="${ resultsetNames }">
     <tr>
      <td><c:out value="${ name }" /></td>
     </tr>
    </c:forEach>
    </tbody>
   </table>
 </div>
</c:if>

<script>
var options = {
        optionsForRows : [5,10]
      }
$('.PrettyTable').tablePagination(options);
</script>