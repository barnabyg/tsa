<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="analysisrun.go"
 modelAttribute="analysisRunForm" enctype="multipart/form-data">

 <div id="configureRun">
  <table>
   <tr>
    <td>Strategy Name</td>
    <td><form:select id="strategyName" path="strategyName" items="${strategyNames}" onchange="setAnalysisSubmit()" /></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>Dataset Name</td>
    <td><form:select id="datasetName" path="datasetName" items="${datasetNames}" onchange="setAnalysisSubmit()" /></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>Starting Funds</td>
    <td>£<input type="text" id="startingFunds" name="startingFunds" onkeyup="setAnalysisSubmit()" onchange="setAnalysisSubmit()" onmousedown="setAnalysisSubmit()" /></td>
    <td><form:errors path="startingFunds" cssClass="error" /></td>
   </tr>
  </table>
  <br /> <input id="analysisSubmit" type="submit" value="Run" />
</form:form>
</div>
<script>
setAnalysisSubmit();
</script>
