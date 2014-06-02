<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="deletedataset.go"
 modelAttribute="deleteForm" enctype="multipart/form-data">

 <div id="delete">
  <form:errors path="*" cssClass="error" />
  <table>
   <tr>
    <td>Dataset Name</td>
    <td><form:select id="name" path="name" items="${datasetNames}" /></td>
   </tr>
  </table>
  <br /> <input id="deleteSubmit" type="submit" value="Delete" />
</form:form>
</div>