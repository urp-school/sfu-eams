<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar"></table>
 <@table.table id="listTable" style="width:100%" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="classroomId"/>
    <@table.sortTd name="attr.infoname" id="classroom.name" width="20%"/>
    <@table.sortTd name="common.building" id="classroom.building.name" width="20%"/>
    <@table.sortTd name="common.classroomConfigType" id="classroom.configType" width="20%"/>
    <@table.sortTd name="attr.capacityOfExam" id="classroom.capacityOfExam" width="10%"/>
    <@table.sortTd name="attr.capacityOfCourse" id="classroom.capacityOfCourse" width="10%"/>
    <@table.sortTd text="真正容量" id="classroom.capacity" width="10%"/>
   </@>
   <@table.tbody datas=classrooms;classroom>
     <@table.selectTd id="classroomId" value=classroom.id/>
      <td>
       <a href="?method=courseTable&setting.kind=room&ids=${classroom.id}&calendar.id=${calendar.id}&setting.forCalendar=0" title="<@msg.message key="course.seeCurriculums"/>" target="_blank">
          &nbsp;${classroom.name}</a></td>
      <td>&nbsp;<@i18nName classroom.building?if_exists/></td>
      <td>&nbsp;<@i18nName classroom.configType/></td>
      <td>&nbsp;${classroom.capacityOfExam?default(-1)}</td>
      <td>&nbsp;${classroom.capacityOfCourse?default(0)}</td>
      <td>&nbsp;${classroom.capacity?default(0)}</td>
   </@>
 </@>
 <#assign courseTableType="room">
 <#include "courseTableSetting.ftl"/>
<script language="javascript">
   	var bar = new ToolBar('backBar','<@msg.message key="classroom.list"/>',null,true,true);
   	bar.setMessage('<@getMessage/>');
   	<#--
   	bar.addItem("教学活动教室占用表", "classroomActivityStat()");
   	bar.addItem("教室明细汇总", "roomActivityDetail()");
   	-->
   	bar.addItem('<@msg.message key="action.printSet"/>','displaySetting()','setting.png');
   	bar.addItem('<@msg.message key="action.preview"/>','printCourseTable()','print.gif');
   	
   	function classroomActivityStat() {
        var ids = getSelectIds("classroomId");
        var information = "所有";
        form["classroomIds"].value = "";
        if (!(null == ids || ids == "")) {
            information = "所选";
            form["classroomIds"].value = ids;
        }
        if (!confirm("要查看下面" + information + "教室其教学活动的占用情况吗？")) {
            return;
        }
        form.action = "?method=classroomActivityStat";
        form.submit();
    }
    
    function roomActivityDetail() {
        var ids = getSelectIds("classroomId");
        var information = "所有";
        form["classroomIds"].value = "";
        if (!(null == ids || ids == "")) {
            information = "所选";
            form["classroomIds"].value = ids;
        }
        if (!confirm("要查看下面" + information + "教室明细汇总吗？")) {
            return;
        }
        form.action = "?method=roomActivityDetail";
        form.submit();
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>