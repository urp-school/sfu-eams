<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src='scripts/common/multiSelectChoice.js'></script>
<body>

<#assign labInfo>上传文件:文件名有效长度不超过150个字符!</#assign>
<#include "/templates/back.ftl"/>
       <table border=0 align='left' class="infoTitle">
          <form name="pageGoForm" action="document.do?method=upload&kind=${RequestParameters['kind']}" onsubmit="return false;" method="post" enctype="multipart/form-data"> 
         <tr>
         	<td colspan="6">
		      <input type="file" name="file1" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
         <tr>
         	<td colspan="6">
		      <input type="file" name="file2" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
         <tr>
         	<td colspan="6">
		      <input type="file" name="file3" size=50 class="buttonStyle" value=''>&nbsp; 
	        </td>
	     </tr>
	     <#if RequestParameters['kind'] == "std">
	     <tr valign="top">
	     	<td width="10%">学生类别：</td>
	     	<td></td>
	     	<td></td>
	     	<td width="10%">所在院系：</td>
	     	<td></td>
	     	<td></td>
	     </tr>
	     <tr>
	     	<td><@htm.i18nSelect datas=stdTypes?sort_by(["code"]) selected="" name="stdTypeIdFrom" style="width:150px" size="10" MULTIPLE="true" onDblClick="selectRemoveAnyOne('stdTypeIdFrom', 'stdTypeId')"/></td>
	     	<td align="center">
	     		<button style="width:25px" onclick="selectRemoveAll('stdTypeIdFrom', 'stdTypeId')">&gt;&gt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAnyOne('stdTypeIdFrom', 'stdTypeId')">&gt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAnyOne('stdTypeId', 'stdTypeIdFrom')">&lt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAll('stdTypeId', 'stdTypeIdFrom')">&lt;&lt;</button>
	     	</td>
	     	<td>
	     		<select name="stdTypeId" style="width:150" MULTIPLE size="10" onDblClick="selectRemoveAnyOne('stdTypeId', 'stdTypeIdFrom')"></select>
	     	</td>
	     	<td><@htm.i18nSelect datas=departments?sort_by(["code"]) selected="" name="departmentIdFrom" style="width:150px" size="10" MULTIPLE="true" onDblClick="selectRemoveAnyOne('departmentIdFrom', 'departmentId')"/></td>
	     	<td align="center">
	     		<button style="width:25px" onclick="selectRemoveAll('departmentIdFrom', 'departmentId')">&gt;&gt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAnyOne('departmentIdFrom', 'departmentId')">&gt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAnyOne('departmentId', 'departmentIdFrom')">&lt;</button><br><br>
	     		<button style="width:25px" onclick="selectRemoveAll('departmentId', 'departmentIdFrom')">&lt;&lt;</button>
	     	</td>
	     	<td>
	     		<select name="departmentId" style="width:150" MULTIPLE size="10" onDblClick="selectRemoveAnyOne('departmentId', 'departmentIdFrom')"></select>
	     	</td>
	     </tr>
	     </#if>
	     <tr>
		     <td colspan="6">上传文件置顶显示 <input name="isUp" type="checkbox" onchange="changeCheckBoxValue(this.form);"/><br>
		     </td>
	     </tr>
         <tr><td colspan="6">上传文件大小不得大于30M <input type="button" value="<@bean.message key="system.button.submit"/>" name="submitNow" onClick="uploadFile()" class="buttonStyle"><br><br></td></tr>
        </form>
	   </table>
	   <script>
	   	var form = document.pageGoForm;
	   	function uploadFile() {
	   		var file1 = form["file1"].value;
	   		var file2 = form["file2"].value;
	   		var file3 = form["file3"].value;
	   		if (null != file1 && "" != file1 || null != file2 && "" != file2 || null != file3 && "" != file3) {
	   			if(null != file1 && "" != file1){
	   				if(file1.length>150){
	   					alert("文件名有效长度不超过150个字符!");
	   					return;
	   				}
	   			}
	   			if(null != file2 && "" != file2){
	   				if(file2.length>150){
	   					alert("文件名有效长度不超过150个字符!");
	   					return;
	   				}
	   			}
	   			if(null != file3 && "" != file3){
	   				if(file3.length>150){
	   					alert("文件名有效长度不超过150个字符!");
	   					return;
	   				}
	   			}
		   		<#if RequestParameters['kind'] == "std">
		   		var stdTypeIds = getAllOptionValue(form["stdTypeId"]);
		   		var departmentIds = getAllOptionValue(form["departmentId"]);
		   		if (null == stdTypeIds || "" == stdTypeIds || null == departmentIds || "" == departmentIds) {
		   			alert("请选择上传文件的学生类别和所在院系。");
		   			return;
		   		}
		   		form.action += "&stdTypeIds=" + stdTypeIds + "&departmentIds=" + departmentIds;
		   		</#if>
		   		form.submit();
		   		return;
	   		}
	   		alert("请至少上传一个文件。");
	   	}
	   	
	   	function changeCheckBoxValue(form) {
	      	if(event.srcElement.checked) {
	      		form.action = "document.do?method=upload&kind=${RequestParameters['kind']}&isUp=true";
	      	} else {
	      		form.action = "document.do?method=upload&kind=${RequestParameters['kind']}&isUp=false";
	      	}
	   	}
	   </script>
</body>
<#include "/templates/foot.ftl"/>