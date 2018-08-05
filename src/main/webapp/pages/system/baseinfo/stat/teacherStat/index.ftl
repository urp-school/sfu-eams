<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','学校任课教师统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>统计项目</B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="stat(this,'Age')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >年龄结构
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="stat(this,'EduDegree')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">学历结构
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="stat(this,'Degree')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" >学位结构
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="stat(this,'Title')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >职称结构
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="stat(this,'GraduateSchool')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >学缘结构
         </td>
       </tr>
       
       <tr>
         <td class="padding"><br>统计范围：任课教师
         </td>
       </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding"  onclick="statTask(this)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >授课情况
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="auto"></iframe>
	</td>
</table>
<form name="statForm" method="post" target="statFrame" onsubmit="return false;">
<input type="hidden" name="start" value="36">
<input type="hidden" name="span" value="10">
<input type="hidden" name="count" value="2">
</form>
<script>
   var form = document.statForm;
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action="teacherStat.do?method=statBy"+kind;
      form.submit();
   }
   function statTask(td){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action="teacherStat.do?method=statTask";
      form.submit();
   }
   document.getElementById("defaultItem").onclick();
</script>
</body>
<#include "/templates/foot.ftl"/>