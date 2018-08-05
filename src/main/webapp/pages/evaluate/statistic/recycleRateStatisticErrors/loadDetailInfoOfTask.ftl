<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','<@bean.message key="field.recycleRate.queryRate"/>',null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
</script>
<table cellpadding="0" cellspacing="0" align="center" width="100%" border="0">
   	<tr>
     	<td>
     		<table id="tableId" width="100%" align="center" class="listTable">
	    <tr>
	     	<td align="center" class="darkColumn" colspan="10">
	     		<@bean.message key="field.recycleRate.taskDetailInfo"/>
	     	</td>
	    </tr>
	   	<tr align="center" class="brightStyle">
	     <td><B><@bean.message key="field.recycleRate.taskId"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.courseCode"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.courseName"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.college"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.studentDepartment"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.teachClassName"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.evaluatePerson"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.totlePerson"/></B></td>
	     <td><B><@bean.message key="field.recycleRate.recycleRate"/></B></td>
	    </tr>
	    <#list result.detailTaskInfoList?if_exists as detailTaskInfo>
	    	<#if detailTaskInfo_index%2==1 ><#assign class="grayStyle" ></#if>
	   		<#if detailTaskInfo_index%2==0 ><#assign class="brightStyle" ></#if>
	   		<tr class="${class}">
	    	<#list detailTaskInfo as taskInfo>
	    		<#if taskInfo_index==3>
	    			<td>${taskInfo.seqNo}</td>
	    			<td>${taskInfo.course.code?if_exists}</td>
	    			<td>${taskInfo.course.name?if_exists}</td>
	    			<td>${taskInfo.arrangeInfo.teachDepart?if_exists.name?if_exists}</td>
	    			<td>${taskInfo.teachClass.depart?if_exists.name?if_exists}</td>
	    			<td>${taskInfo.teachClass.name?if_exists}</td>
	    		</#if>
	    	</#list>
	    	<#list detailTaskInfo as taskInfo>
	    		<#if taskInfo_index<2>
	    			<td>${taskInfo?if_exists}</td>
	    		<#elseif taskInfo_index==2>
	    			<td>${taskInfo?string("0.#")}%</td>
	    		</#if>
	    	</#list>
	    	</tr>
	    </#list>
	    </table>
     </td>
   	</tr>
</table>
   <#list 1..3 as i><br></#list>
</body>
<#include "/templates/foot.ftl"/>