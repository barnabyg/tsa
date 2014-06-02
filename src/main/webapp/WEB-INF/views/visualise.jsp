<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${ not empty resultset }">
<form:form method="post" action="visualise.go"
 modelAttribute="visualiseForm" enctype="multipart/form-data">

<fmt:setLocale value="en_GB" scope="session"/>

 <div id="visualisationHeader">
  <form:select id="runId" path="runId" items="${allRuns}" onchange="loadChart()"/>
 </div>

 <br />

 <div id="visualisationTables">
  <table id="runDetailsTable">
   <tr><td>Start date</td><td><fmt:formatDate type="both" value="${resultset.balanceHistory.startDate}" /></td></tr>
   <tr><td>End date</td><td><fmt:formatDate type="both" value="${resultset.balanceHistory.endDate}" /></td></tr>
   <tr><td>Starting funds</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.initialBalance / 100} " /></td></tr>
  </table>
  <table id="runPerformanceTable">
   <tr><td>Nominal result</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.nominalWorth / 100}" /></td></tr>
   <tr><td>Actual result</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.actualResult / 100}" /></td></tr>
   <tr><td>Performance against nominal</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.performance / 100}" /></td></tr>
   <tr><td>Performance against nominal (%)</td><td><fmt:formatNumber type="percent" value="${resultset.balanceHistory.performancePercentage}" /></td></tr>
  </table>
 </div>

 <br />

 <div id="visualisationChart">
  <table>
   <tr>
    <td>Chart type</td><td><form:select id="chartType" path="chartType" items="${chartTypes}" onchange="loadChart()" /></td>
   </tr>
  </table>
  <iframe id="myframe" src="" width="810" height="610" scrolling="no" frameborder="0">
   <p>Your browser does not support iframes</p>
  </iframe>
 </div>

</form:form>
</c:if>
<c:if test="${ empty resultset }">There are no result sets</c:if>

<script>

</script>
<script>
if (addEventListener in document) { // use W3C standard method
    document.addEventListener('load', loadChart, false);
} else { // fall back to traditional method
    window.onload = loadChart;
}
</script>