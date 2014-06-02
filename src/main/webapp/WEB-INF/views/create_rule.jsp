<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="createrule.go"
 modelAttribute="createRuleForm" enctype="multipart/form-data">

 <div id="createRule">
  <table>
   <tr>
    <td>Rule name</td>
    <td><input type="text" id="name" name="name" /></td>
    <td><form:errors path="name" cssClass="error" /></td>
   </tr>
   <tr>
    <td>Rule type</td>
    <td><form:select id="ruleType" path="ruleType" items="${ruleTypes}" onchange="setIndicatorType()" /></td>
    <td><form:errors path="ruleType" cssClass="error" /></td>
   </tr>
   <tr>
    <td>Indicator type</td>
    <td><form:select id="valueIndicatorName" path="valueIndicatorName" items="${valueIndicatorNames}" /><form:select id="directionIndicatorName" path="directionIndicatorName" items="${directionIndicatorNames}" /></td>
    <td><form:errors path="valueIndicatorName" cssClass="error" /><form:errors path="directionIndicatorName" cssClass="error" /></td>
   </tr>
   <tr>
    <td>Operation</td>
    <td><form:select id="valueOperation" path="valueOperation" items="${valueOperations}" /><form:select id="directionOperation" path="directionOperation" items="${directionOperations}" /></td>
    <td><form:errors path="valueOperation" cssClass="error" /><form:errors path="directionOperation" cssClass="error" /></td>
   </tr>
   <tr id="valueRow">
    <td>Value</td>
    <td><input type="text" id="value" name="value" /></td>
    <td><form:errors path="value" cssClass="error" /></td>
   </tr>
   <tr>
    <td>Instrument</td>
    <td><input type="text" id="instrument" name="instrument" /></td>
    <td><form:errors path="instrument" cssClass="error" /></td>
   </tr>
   <tr>
    <td>Trade type</td>
    <td><form:select id="tradeType" path="tradeType" items="${tradeTypes}" onchange="setTradeType()" /></td>
    <td><form:errors path="tradeType" cssClass="error" /></td>
   </tr>
   <tr id="quantityRow">
    <td>Quantity</td>
    <td><input type="text" id="quantity" name="quantity" /></td>
    <td><form:errors path="quantity" cssClass="error" /></td>
   </tr>
  </table>
  <br /> <input id="createRuleSubmit" type="submit" value="Create" />
</form:form>
</div>

<script>
setIndicatorType();
setTradeType();

function setIndicatorType() {
 var ruleType = document.getElementById("ruleType");

 if (ruleType.options[ruleType.selectedIndex].text == "INDICATOR_VALUE") {

  document.getElementById("valueIndicatorName").style.display = 'block';
  document.getElementById("directionIndicatorName").style.display = 'none';

  document.getElementById("valueOperation").style.display = 'block';
  document.getElementById("directionOperation").style.display = 'none';

  document.getElementById("valueRow").style.display = 'table-row';

 } else {

  document.getElementById("valueIndicatorName").style.display = 'none';
  document.getElementById("directionIndicatorName").style.display = 'block';

  document.getElementById("valueOperation").style.display = 'none';
  document.getElementById("directionOperation").style.display = 'block';

  document.getElementById("valueRow").style.display = 'none';
 }
}

function setTradeType() {
 var tradeType = document.getElementById("tradeType");

 if (tradeType.options[tradeType.selectedIndex].text == "BUYALL"
		 || tradeType.options[tradeType.selectedIndex].text == "SELLALL") {

	 document.getElementById("quantityRow").style.display = 'none';

 } else {

	 document.getElementById("quantityRow").style.display = 'table-row';
 }
}
</script>