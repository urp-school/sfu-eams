<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table>
   <table align='center' width="100%">
     <form name="pageGoForm" action="manualArrange.do?method=importSchedule&task.calendar.id=${RequestParameters['task.calendar.id']}"  method="post" enctype="multipart/form-data">
     <tr>
     	<td>
	      <input type="file" size=50  name="studentFile" class="buttonStyle" value=''/>
	      <input type="button" value="<@bean.message key="system.button.submit" />" name="submitNow" onClick="validateExtendName(this.form)" class="buttonStyle"><br><br>
	      <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if key != "method">&${key}=${RequestParameters[key]}</#if></#list>"/>
        </td>
     </tr>
     <tr>
       <td>
          <font color="red" size="2">
          上传文件中的所有信息均要采用文本格式。对于日期和数字等信息也是一样。
          </font>
       </td>
     </tr>
   </form>
   </table>
 </body>
<#include "/templates/foot.ftl"/>
<script>
var bar = new ToolBar("myBar","排课结果导入",true,true);
bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");

  function validateExtendName(form){
  	var value=form.studentFile.value;
  	var index=value.indexOf(".xls");
	if((index<1)||(index<(value.length-4))){
		alert("<@bean.message key="filed.file" />'.xls'");
		return false;
	}
	form.submit();
  }
</script>