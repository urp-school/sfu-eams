<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '学生考勤报表（院系-班级）', null, true, false);
</script>
  	<@table.table width="100%" sortable="true" id="listTable" >
  		 		<thead>
  		 			<tr class="darkColumn" align="center">
  		 			<td width="10%">月份</td>
  		 			<#list monthList as month>
  		 				<td colspan="2">${month}月</td>
  		 			</#list>
  		 			<td colspan="3">总计</td>
  		 			</tr>
  		 			<tr class="grayStyle" align="center">
  		 			<td>班级</td>
  		 			<#list monthList as month>
  		 				<td>缺勤</td>
  		 				<td>迟到</td>
  		 			</#list>
  		 			<td width="8%">缺勤率（%）</td>
  		 			<td width="8%">迟到率（%）</td>
  		 			<td width="8%">出勤率（%）</td>
  		 			</tr>
  		 		</thead>
  		 		<#if classBeans??>
  		 		<@table.tbody datas=classBeans;clazz>
  		 				<td>(${clazz.adminClass.code!})${clazz.adminClass.name!}</td>
  		 				<#list clazz.monthBeans as monthBean>
  		 					<td>${monthBean.absenceCount}</td>
  		 					<td>${monthBean.latCounet}</td>
  		 				</#list>
  		 				<td>${clazz.percentBean.absencePer}</td>
  		 				<td>${clazz.percentBean.latPer}</td>
  		 				<td>${clazz.percentBean.totalPer}</td>
  		 		</@>
  		 		</#if>
    </@>
<#include "/templates/foot.ftl"/> 