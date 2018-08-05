<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '全校考勤报表', null, true, false);
</script>
  	<@table.table  sortable="true" id="listTable" >
  		 			<tr class="darkColumn" align="center">
  		 			<td width="100px" height="40px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  		 			日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  		 			<#list dateList as date>
  		 				<td >${date}</td>
  		 			</#list>
  		 			<td >总计</td>
  		 			</tr>
  		 			<tr class="darkColumn" align="center">
  		 			<td >院系</td>
  		 			<#list dateList as date>
  		 				<td>出勤率（%）</td>
  		 			</#list>
  		 			<td width="8%">出勤率（%）</td>
  		 			</tr>
  		 		<#if departmentBeans??>
  		 		<@table.tbody datas=departmentBeans;bean>
  		 				<td>${bean.department.name}</td>
  		 				<#list bean.dateBeans as dateBean>
  		 					<td>${dateBean.normal}</td>
  		 				</#list>
  		 				<td>${bean.totalNormal}</td>
  		 		</@>
  		 		<tr class="darkColumn" align="center">
  		 		<td >总计</td>
  		 		<#list dateList as date>
  		 				<td>${dateNormalMap[date]}</td>
  		 			</#list>
  		 			<td width="8%">${dateNormalMap['total']}</td>
  		 		</tr>
  		 		</#if>
    </@>
<#include "/templates/foot.ftl"/> 