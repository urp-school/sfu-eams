<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','课程统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>     
      	<table  width="100%" id ="viewTables" class="frameTable_title">
      		<#assign statNames=['计划课程查询','计划课程统计','任务课程查询']/>
       		<#assign statMethods=['planCourseSearchHome','planCourseStatHome','taskCourseHome']/>
           <tr>
       	   <#list statNames as name>
         	<td class="padding" <#if name_index==0>id="defaultItem"</#if> onclick="statBy(this,'${statMethods[name_index]}')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         	<image src="${static_base}/images/action/list.gif" align="bottom" ><font color="blue">${name}</td>
         	</#list>
         	<td width="40%"></td>
       		</tr>
       			<tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
 	      </td>
    	</tr>
       </table>

<table class="frameTable">	
	<tr>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
	</tr>
</table>
<form name="infoForm" method="post" action="" onsubmit="return false;"></form>
<script>
   document.getElementById("defaultItem").onclick();   
   function statBy(td,method){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      var form = document.infoForm;
      form.action="courseStat.do?method="+method;
      form.target="statFrame";
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>