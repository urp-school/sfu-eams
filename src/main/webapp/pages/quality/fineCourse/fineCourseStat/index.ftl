<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','学校精品课程统计',null,true,true);
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
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >精品课程
         </td>
       </tr>
       <#list levels as level>
       <tr>
         <td class="padding"  onclick="list(this,'${level.id}')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">${level.name}
         </td>
       </tr>
       </#list>
       <form name="statForm" method="post" target="statFrame" action="" onsubmit="return false;">
       <tr>
         <td><br>统计年份：<input type="text" name="years" value="2003,2004,2005,2006,2007">
         <input type="hidden" name="levelIds" value="<#list levels as level>${level.id}<#if level_has_next>,</#if></#list>"/>
         <button onclick="this.form.submit()">统计</button>
         </td>
       </tr>
       </form>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<script>
   var form = document.statForm;
   var action="fineCourseStat.do";
   
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action=action+"?method=stat";
      form.submit();
   }
   function list(td,levelId){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      form.action=action+"?method=list&fineCourse.level.id="+levelId;
      form.submit();
   }
   document.getElementById("defaultItem").onclick();
</script>
</body>
<#include "/templates/foot.ftl"/>