<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<#if teacher.availableTime?exists>
<table id="teacherAvailInfoBar"></table>
<#assign availTime=teacher.availableTime>
<#include "/pages/course/arrange/availTime/availTimeInfo.ftl" />
<form name="teacherForm" method="post" action="" onsubmit="return false;"> 
	<input type="hidden" name="teacher.id" value="${teacher.id}"/>
</form>
<script>
    function edit(){
         document.teacherForm.action="teacherTime.do?method=editAvailTime";
         document.teacherForm.submit();  
     }
   var bar = new ToolBar('teacherAvailInfoBar','<@bean.message key="info.availTimeOfTeacher" arg0="${teacher.name}"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.modify"/>",edit,'update.gif');       
</script> 
<#else>
   <blockquote>
     系统还没有你对于排课时间安排的偏好数据.<br>
     该数据是提供给排课人员参考的.如果提供这些数据,将使的你的课程满意度提高.<br>
     现在就<A class="buttonStyle" href="teacherTime.do?method=editAvailTime">设置可用时间设置.</A>
   </blockquote>
</#if>
</BODY>
<#include "/templates/foot.ftl"/>