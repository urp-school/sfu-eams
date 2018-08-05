<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','学校专业统计',null,true,true);
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
         <td class="padding" id="defaultItem" onclick="select(this,list)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" >专业设置
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="select(this,distribution)"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif">专业布局
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="select(this,structure)" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" >专业结构
         </td>
       </tr>
       <tr>
         <td class="padding"><br>统计范围：本科专业
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<form name="statForm" method="post" action="" target="statFrame" onsubmit="return false;">
<input type="hidden" name="speciality.stdType.id" value="5">
<input type="hidden" name="speciality.state" value="1">
<input type="hidden" name="speciality.is2ndSpeciality" value="0">
<input type="hidden" name="orderBy" value="">
</form>
<script>
   var form = document.statForm;
   var action="specialityStat.do";
   
   function select(td,fun){
     clearSelected(viewTables,td);
     setSelectedRow(viewTables,td);
     fun();
   }
   function list(orderByWhat){
     form.action=action+"?method=list";
     if(null!=orderByWhat){
         addInput(form,"orderBy",orderByWhat);
     }
     form.submit();
   }
   function distribution(){
     form.action=action+"?method=distribution";
     form.submit();
   }
   function structure(){
     form.action=action+"?method=structure";
     form.submit();
   }
   document.getElementById("defaultItem").onclick();
</script>
</body>
<#include "/templates/foot.ftl"/>