<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en_GB" scope="session"/>

<div>
<table>
<tr><td>Run ID</td><td id="runId"><c:out value="${resultset.name}" /></td></tr>
<tr><td>Start date</td><td><fmt:formatDate type="both" value="${resultset.balanceHistory.startDate}" /></td></tr>
<tr><td>End date</td><td><fmt:formatDate type="both" value="${resultset.balanceHistory.endDate}" /></td></tr>
<tr><td>Starting funds</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.initialBalance / 100} " /></td></tr>
<tr><td>Nominal result</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.nominalWorth / 100}" /></td></tr>
<tr><td>Actual result</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.actualResult / 100}" /></td></tr>
<tr><td>Performance against nominal</td><td><fmt:formatNumber type="currency" value="${resultset.balanceHistory.performance / 100}" /></td></tr>
<tr><td>Performance against nominal (%)</td><td><fmt:formatNumber type="percent" value="${resultset.balanceHistory.performancePercentage}" /></td></tr>
</table>
</div>

<div id="trades">
 <h2>List of Trades</h2>

 <c:if test="${ empty resultset.trades }">No trades found</c:if>

 <c:if test="${ not empty resultset.trades }">
  <table border="1" class="PrettyTable">
   <tr>
    <td>Date Time</td>
    <td>Type</td>
    <td>Instrument</td>
    <td>Quantity</td>
    <td>Price</td>
   </tr>
   <c:forEach var="trade" items="${resultset.trades}">
    <tr>
     <td><fmt:formatDate type="both" value="${trade.date}" /></td>
     <td><c:out value="${trade.type}" /></td>
     <td><c:out value="${trade.instrument}" /></td>
     <td><c:out value="${trade.quantity}" /></td>
     <td><fmt:formatNumber type="currency" value="${trade.price / 100}" /></td>
    </tr>
   </c:forEach>
  </table>
 </c:if>
</div>

<div id="normalisedValue">
 <h2>Normalised Value</h2>

 <c:if test="${ empty resultset.balanceHistory.snapshots }">No balance history found</c:if>

 <c:if test="${ not empty resultset.balanceHistory.snapshots }">
  <table border="1" class="PrettyTable">
   <tr>
    <td>Date Time</td>
    <td>Normalised Balance</td>
    <td>Shares</td>
   </tr>
   <c:forEach var="balance" items="${resultset.balanceHistory.snapshots}">
    <tr>
     <td><fmt:formatDate type="both" value="${balance.date}" /></td>
     <td><fmt:formatNumber type="currency" value="${balance.currentWorth / 100}" /></td>
     <td><c:out value="${balance.shares}" /></td>
    </tr>
   </c:forEach>
  </table>
 </c:if>
</div>

<div id="cashBalance">
 <h2>Cash Balance</h2>

 <c:if test="${ empty resultset.balanceHistory.snapshots }">No balance history found</c:if>

 <c:if test="${ not empty resultset.balanceHistory.snapshots }">
  <table border="1" class="PrettyTable">
   <tr>
    <td>Date Time</td>
    <td>Balance</td>
   </tr>
   <c:forEach var="balance" items="${resultset.balanceHistory.snapshots}">
    <tr>
     <td><fmt:formatDate type="both" value="${balance.date}" /></td>
     <td><fmt:formatNumber type="currency" value="${balance.balance / 100}" /></td>
    </tr>
   </c:forEach>
  </table>
 </c:if>
</div>