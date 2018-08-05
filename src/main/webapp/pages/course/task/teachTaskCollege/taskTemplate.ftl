<#include "/templates/head.ftl"/>
<style>
.task{
	font-size: 20pt;
	border-collapse: collapse;
    border:solid;
	border-width:1px;
	font-family: 隶书; 
}
.task_title {
  	text-align:center;
  	font-style: normal; 
	font-size: 30pt;
}
.task_signature {
  	text-align:right;
  	font-style: normal;
	font-size: 20pt;
}
</style>
<#assign blank><U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U></#assign>
<#assign longBlank><U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U></#assign>
<#assign shortBlank><U>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</U></#assign>
<body>
<table  align="center" id="templateBar" width="100%"></table>
<script>
   var bar = new ToolBar("templateBar","教学任务任课书打印模板",null,true,true);
   bar.addItem("生成打印版本","genTaskInfo()");
   function genTaskInfo(){
       var form=document.templateForm;
       var errorInfo="";
       if(form['department'].value=="") errorInfo+="部门没有填写\n";
       if(form['date'].value=="") errorInfo+="日期没有填写\n";
       //if(form['telephone'].value=="") errorInfo+="电话没有填写\n";
       //if(form['email'].value=="") errorInfo+="电子邮件没有填写\n";
       if(errorInfo!=""){alert(errorInfo);return;}
       else{
         form.submit();
       }
   }
</script>
	<table width="95%" align="center" class="task">
	 <form name="templateForm" method="post" action="?method=printTaskForTeacher" onsubmit="return false;">
	 <input name="teachTaskIds" value="${RequestParameters['teachTaskIds']}" type="hidden">
	<tr><td class="task_title"><@i18nName systemConfig.school/>${shortBlank}生</td></tr>
	<tr><td class="task_title">任 课 通 知 书	</td></tr>
	<tr><td>${blank}老师：</td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;${blank}学年${blank}请您担任<input type="text" name="teachClassName" size=20 value="自动生成">的《${longBlank}》课程。</td></tr>
	<tr><td><@msg.message key="entity.courseType"/>：${blank}<@msg.message key="attr.courseNo"/>：${blank}</td></tr>
	<tr><td><@msg.message key="attr.taskNo"/>：${blank}<@msg.message key="attr.credit"/>：${blank}</td></tr>
	<tr><td>人&nbsp;&nbsp;&nbsp;&nbsp;数：${shortBlank}</td></tr>
	<tr><td>时间地点样式：<input name="format" value=":teacher2:day:time :weeks周 :room(:district :building)" size=50></td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（每周${shortBlank}课时，上课${shortBlank}周，合计${shortBlank}课时）</td></tr>
	<tr><td>首次上课时间：${longBlank}（年-月-日）</td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td valign="top">&nbsp;&nbsp;&nbsp;&nbsp;其他说明：
	<textarea name="remark"  cols="80" rows=5 >请您按时上课，如有意见请及时与我们联系。	可在下学期初登录${systemConfig.systemName}（${systemConfig.url}）进行教学任务查询（学生点名册请于开学三周后在系统上打印）。</textarea>
	</td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;联系电话：<input type="text" name="telephone" size="12" value=""/></td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;EMAIL：<input type="text" name="email" size="50" value=""/></td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
	<tr><td class="task_signature">部门：<input type="text" name="department" size="30" value=""/></td></tr>
	<tr><td class="task_signature">日期：<input type="text" name="date" size="10"  value=""/>&nbsp;<button name="selectData" onclick="calendar(this.form['date'])">选择日期</button></td></tr>
	<tr><td><p>说明：<U>&nbsp;&nbsp;</U>为系统自动填充.将要打印${taskCount}个教学任务的任课通知书</p></td></tr>
	</form>
	</table>
</body>
<#include "/templates/foot.ftl"/>