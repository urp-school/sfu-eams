<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','教材需求统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
     <table  class="frameTable_title">
      <tr><td  class="infoTitle" align="left" valign="bottom"></td><td class="separator">|</td>
      <form name="taskStatForm" target="statFrame" method="post" action="bookRequireStat.do?method=index" onsubmit="return false;">
      <input type="hidden" name="require.task.calendar.id" value="${calendar.id}" >
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
	          <B>需求统计</B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="stat(this,'press','<@msg.message key="entity.press"/>')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" ><@msg.message key="entity.press"/>
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="stat(this,'awardLevel','<@msg.message key="entity.textbookAwardLevel"/>')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="entity.textbookAwardLevel"/>
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="stat(this,'courseType','<@msg.message key="entity.courseType"/>')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="entity.courseType"/>
         </td>
       </tr>
       <tr>
         <td class="padding" onclick="stat(this,'teachDepart','<@msg.message key="attr.teachDepart"/>')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="attr.teachDepart"/>
         </td>
       </tr>
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>审核统计</B>      
	      </td>
	    </tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding"  onclick="statCheck(this,'teachDepart','<@msg.message key="attr.teachDepart"/>')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="attr.teachDepart"/>
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
   document.getElementById("defaultItem").onclick();   
   function stat(td,kind,kindName){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      addInput(form,"kindName",kindName);
      form.action="bookRequireStat.do?method=statBy&kind="+kind;
      form.submit();
   }
   
   function statCheck(td,kind,kindName){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      addInput(form,"kindName",kindName);
      form.action="bookRequireStat.do?method=statCheck&kind="+kind;
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>