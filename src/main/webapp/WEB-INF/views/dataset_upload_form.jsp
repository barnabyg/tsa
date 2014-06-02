<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" action="datasetsave.go"
 modelAttribute="uploadForm" enctype="multipart/form-data">
 <div id="datasetUpload">
  <table>
   <tr>
    <td>Dataset name</td>
    <td><input type="text" id="names0" name="names[0]" onkeyup="setDatasetUploadSubmit()" onchange="setDatasetUploadSubmit()"/></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>Data type</td>
    <td><form:select id="dataType" path="dataType"
      items="${dataTypeValues}" /></td>
    <td><form:errors path="dataType" cssClass="error" /></td>
   </tr>
   <tr>
    <td>File</td>
    <td><input type="file" id="files0" name="files[0]" /></td>
    <td>&nbsp;</td>
   </tr>
   <tr>
    <td>File path</td>
    <td><input type="text" id="filePath" name="filePath" size="40" /></td>
    <td><form:errors path="filePath" cssClass="error" /></td>
   </tr>
  </table>
  <br /> <input id="datasetUploadSubmit" type="submit" value="Upload" />
</form:form>
</div>
<script>
setDatasetUploadSubmit();
</script>