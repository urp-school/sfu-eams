<#include "/templates/head.ftl"/>
<table id="teacherAvailInfoBar"></table>
<#include "/pages/course/arrange/availTime/availTimeInfo.ftl"/>
<form name="teacherForm" method="post" action="" onsubmit="return false;">
	<input type="hidden" name="teacher.id" value="${teacher.id}"/>
</form>
<script>
    function edit(){
         document.teacherForm.action="teacherAvailTime.do?method=editTeacherAvailTime";
         document.teacherForm.submit();
     }
   var bar = new ToolBar('teacherAvailInfoBar','<@bean.message key="info.availTimeOfTeacher" arg0="${teacher.name}"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.modify"/>",edit,'update.gif');
</script> 
<#include "/templates/foot.ftl"/>