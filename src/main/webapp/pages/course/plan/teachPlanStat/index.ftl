<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','培养计划统计查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      statFrame.location="teachPlanStat.do?method="+kind;
   }
</script>
<table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>统计比例</B>      
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>    
       <tr>
         <td class="padding"  onclick="stat(this,'statByDepart')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/inbox.gif"> 按院系
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="stat(this,'statByStdType')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/readedMessage.gif">按学生类别
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="teachPlanStat.do?method=statByDepart" width="100%" frameborder="0" scrolling="no">
	</td>
</table>
<#include "/templates/foot.ftl"/>