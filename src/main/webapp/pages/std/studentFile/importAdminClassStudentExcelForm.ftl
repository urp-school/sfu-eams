<#include "/templates/head.ftl"/>
<html>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table width="100%">
    <tr>
      <td  class="infoTitle" width="20%" style="height:22px;">
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B>班级学生列表数据上传</B>
      </td>
      <td  class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;<@html.errors/></font>
      </td>
    </tr>    
    <tr>
      <td  colspan="5" style="font-size:0px" >
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
</table>
<form name="pageGoForm" action="studentFile.do?method=importAdminClassStudentExcel" method="post" enctype="multipart/form-data" onsubmit="return false;"> 
	<table border=0 align='center'>
		<tr>
			<td>
	      		<input type="file" name="studentFile" class="buttonStyle" value='${RequestParameters.studentFile?if_exists}'>&nbsp; 
	      		<input type="button" value="<@bean.message key="system.button.submit" />" name="submitNow" onClick="validateExtendName(this.form)" class="buttonStyle"><br><br>
        	</td>
     	</tr>
	</table>
</form>
  <pre>
     注意:上传的文件格式为:xls
  </pre>
</body>
<#include "/templates/foot.ftl"/>
<script>
  var picFormat = {
  'xls':true
  };
  function validateExtendName(form){
  	var value=form.studentFile.value;
  	var index=value.lastIndexOf(".");
  	var postfix=value.substring(index+1);
  	if(picFormat[postfix.toLowerCase()]){
  	}else{
		alert("请按照提示上传指定格式的文件!");
		return;
	}
	form.submit();
  }
</script>