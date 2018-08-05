<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教学任务统计查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
     <table  class="frameTable_title">
      <tr><td  class="infoTitle" align="left" valign="bottom"><B>选择以下统计项目</B></td><td class="separator">|</td>
      <form name="taskStatForm" target="statFrame" method="post" action="teachTaskStat.do?method=index" onsubmit="return false;">
      <input type="hidden" name="teachTask.calendar.id" value="${calendar.id}"/>
      <#--记住上次在“任务统计”中所选择的菜单项-->
      <input type="hidden" name="kind" value="${kind?default("defaultItem")}"/>
      <#include "/pages/course/calendar.ftl"/>
      </form>
      </tr>
     </table>
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>任务统计</B>
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="stat(this,'class')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >按听课班级
         </td>
       </tr>
       <tr>
         <td class="padding" id="teacher" onclick="stat(this,'teacher')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">按授课教师
         </td>
       </tr>
       <tr>
         <td class="padding" id="courseType" onclick="stat(this,'courseType')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">按课程类别
         </td>
       </tr>
       <tr>
         <td class="padding" id="teachDepart" onclick="stat(this,'teachDepart')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">按开课院系
         </td>
       </tr>
       <tr>
         <td class="padding" id="depart" onclick="stat(this,'depart')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">按上课院系
         </td>
       </tr>
       <tr >
         <td class="padding" id="studentType" onclick="stat(this,'studentType')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">按学生类别
         </td>
       </tr>
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>确认统计</B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding"  onclick="statConfirm(this,'teachDepart')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">开课院系
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="statConfirm(this,'courseType')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif"><@msg.message key="entity.courseType"/>
         </td>
       </tr>
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>其他统计</B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" onclick="statWhat(this,'statBilingual')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">双语统计
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="statWhat(this,'statRoomConfigType')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/new.gif">上课教室统计
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no">
     	</iframe>
	</td>
</table>
<script>
   var form = document.taskStatForm;
   $(form["kind"].value).onclick();
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action="teachTaskStat.do?method=statTask";
      form["kind"].value = kind;
      form.submit();
   }
   function statConfirm(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action="teachTaskStat.do?method=statConfirm";
      form["kind"].value = kind;
      form.submit();
   }
   function statWhat(td,method){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action="teachTaskStat.do?method="+method;
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>