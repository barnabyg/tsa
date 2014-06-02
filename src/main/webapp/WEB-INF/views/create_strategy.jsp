<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="createstrategy.go"
 modelAttribute="createStrategyForm" enctype="multipart/form-data">

 <div id="createStrategy">

  <table>
   <tr>
    <td>Strategy name</td>
    <td><input type="text" id="name" name="name" /></td>
    <td><form:errors path="name" cssClass="error" /></td>
   </tr>
  </table>
  <br /><br />

  <input type="button" value="Add Row" onclick="addRow('rulesTable')" />
  <input type="button" value="Delete Row" onclick="deleteRow('rulesTable')" />
  <table id="rulesTable">
   <tr>
    <td><input type="checkbox" name="chk"/></td>
    <td>Rule 1</td>
    <td><form:select id="rules0" path="rules[0]" items="${ruleNames}" /></td>
   </tr>
  </table>
  <br /><br /> <input id="createStrategySubmit" type="submit" value="Create" />
</form:form>
</div>
