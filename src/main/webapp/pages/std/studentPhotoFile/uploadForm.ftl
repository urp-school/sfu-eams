<#include "/templates/head.ftl"/>
<html>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table width="100%">
    <tr>
      <td class="infoTitle" width="20%" style="height:22px;">
       <img src="${static_base}/images/action/info.gif" align="top"/><B>
          <B>学生照片上传</B>
      </td>
      <td class="infoTitle" width="20%" style="height:22px;">
 		<font color="red">&nbsp;<@html.errors/></font>
      </td>
    </tr>    
    <tr>
      <td colspan="5" style="font-size:0px">
          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
      </td>
   </tr>
</table>
  <form name="pageGoForm" action="<#if studentId?exists>studentPhoto.do?method=upload&student.id=${studentId}<#else>photoByStudent.do?method=upload</#if>"  method="post" enctype="multipart/form-data"> 
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
     注意:上传的图片格式包括:jpg,jpeg,png,gif.
          上传的图片应遵守照片的比例大小,以便于显示.
          本照片会用于考试和其他输出证件上,请上传本人照片!
  </pre>
 </body>
<#include "/templates/foot.ftl"/>     
<script>
  var picFormat = {
  'jpg':true,
  'png':true,
  'jpeg':true,
  'gif':true
  };
  function validateExtendName(form){
  	var value=form.studentFile.value;
  	var index=value.lastIndexOf(".");
  	var postfix=value.substring(index+1);
  	if(picFormat[postfix.toLowerCase()]){
  	}else{
		alert("请按照提示上传指定格式的图片!");
		return;
	}
	form.submit();
  }
</script>