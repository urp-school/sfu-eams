<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '院系名称:${department.name} 院系代码:${department.code}', null, true, false);
</script>
  	<@table.table  sortable="true" id="listTable" >
  		 			<tr class="darkColumn" align="center">
  		 			<td  height="40px" colspan="2">
  		 			班级信息，辅导员信息/起始日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  		 			<#list dateList as date>
  		 				<td colspan="3">${date}</td>
  		 			</#list>
  		 			<td colspan="3">总计</td>
  		 			</tr>
  		 			<tr class="darkColumn" align="center">
  		 			<td >班级名称</td>
  		 			<td >辅导员名称</td>
  		 			<#list dateList as date>
  		 				<td>缺勤</td>
  		 				<td>迟到</td>
  		 				<td>课时</td>
  		 			</#list>
  		 			<td>缺勤</td>
  		 			<td>迟到</td>
  		 			<td>课时</td>
  		 			</tr>
  		 		<#if adminClassBeans??>
  		 		<@table.tbody datas=adminClassBeans;bean>
  		 				<td>${bean.adminClass.name!}</td>
  		 				<td>
  		 				<#if bean.adminClass.instructor??>
  		 					${bean.adminClass.instructor.name!}
  		 				</#if>
  		 				</td>
  		 				<#list bean.dateBeans as dateBean>
  		 					<td>${dateBean.absenceCount}</td>
  		 					<td>${dateBean.latCounet}</td>
  		 					<td>${dateBean.ksCount}</td>
  		 				</#list>
  		 				<td>${bean.absenceTotal}</td>
  		 				<td>${bean.lateTotal}</td>
  		 				<td>${bean.ksTotal}</td>
  		 		</@>
  		 		<#if adminClasses?size!=0>
  		 		<tr class="darkColumn" align="center">
  		 		<td colspan="2">总计</td>
  		 		<#list dateList as date>
  		 				<td>
  		 			
  		 				${absenseDateMap[date]}
  		 				</td>
  		 				<td>
  		 				${lateDateMap[date]}
  		 				</td>
  		 				<td>
  		 				${ksDateMap[date]}
  		 				</td>
  		 		</#list>
  		 			<td width="8%">
  		 			${absenseDateMap['total']}
  		 			</td>
  		 			<td width="8%">
  		 			${lateDateMap['total']}
  		 			</td>
  		 			<td width="8%">
  		 			${ksDateMap['total']}
  		 			</td>
  		 		</tr>
  		 		</#if>
  		 		</#if>
    </@>
<#include "/templates/foot.ftl"/> 