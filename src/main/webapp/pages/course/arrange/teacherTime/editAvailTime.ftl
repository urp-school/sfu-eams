<#include "/templates/head.ftl"/>
<body>
<table id="teacherAvailBar"></table>
<#assign availTime=teacher.availableTime/>
<#include "/pages/course/arrange/availTime/availTimeTable.ftl" />
<form name="teacherForm" method="post" action="" onsubmit="return false;"/>
  <input type="hidden" name="availTime" value=""/>
  <input type="hidden" name="teacher.id" value="${teacher.id}"/>
  <input type="hidden" name="availTime.id" value="${availTime.id}"/>
  <input type="hidden" name="availTime.remark" value=""/>;
</form>
<script>
     function save(){
        teacherForm.action="teacherTime.do?method=saveAvailTime"  
        teacherForm.availTime.value = getAvailTime();
        teacherForm['availTime.remark'].value = getRemark();
        teacherForm.submit();
     }
   var bar = new ToolBar('teacherAvailBar','<@bean.message key="action.modify" /><@bean.message key="info.availTimeOfTeacher" arg0="${teacher.name}"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.clear"/>",clearAll,'action.gif');
   bar.addItem("<@bean.message key="action.inverse"/>",inverse,'action.gif');
   bar.addItem("<@bean.message key="action.save"/>",save,'save.gif');
   bar.addBack("<@bean.message key="action.back"/>");
</script> 
<#include "/templates/foot.ftl"/>