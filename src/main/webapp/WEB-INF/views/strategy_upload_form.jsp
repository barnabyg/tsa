<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form method="post" action="strategysave.go"
 modelAttribute="uploadForm" enctype="multipart/form-data">

 <div id="strategyUpload">
  <table>
   <tr>
    <td>Strategy name</td>
    <td><input id="names0" name="names[0]" type="text" size="40" onkeyup="setStrategyUploadSubmit()" onchange="setStrategyUploadSubmit()" /></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>File</td>
    <td><input id="files0" name="files[0]" type="file" size="40" /></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>File path</td>
    <td><input type="text" id="filePath" name="filePath" size="40" /></td>
    <td><form:errors path="filePath" cssClass="error" /></td>
   </tr>
  </table>
  <br /> <input id="strategyUploadSubmit" type="submit" value="Upload" />
</form:form>
</div>
<script>
setStrategyUploadSubmit();
</script>
