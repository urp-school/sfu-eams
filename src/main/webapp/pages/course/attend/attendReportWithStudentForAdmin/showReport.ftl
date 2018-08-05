<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '学生学号:${student.code} |学生姓名:${student.name} 本学期应出勤总次数:0|本学期应出勤总次数:0', null, true, false);
</script>
  	<@table.table  sortable="true" id="listTable" >
  		 			<tr class="darkColumn" align="center">
  		 			<td  height="40px" colspan="3">
  		 			课程信息/起始日期&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
  		 			<#list dateList as date>
  		 				<td colspan="3">${date}</td>
  		 			</#list>
  		 			<td colspan="3">总计</td>
  		 			</tr>
  		 			<tr class="darkColumn" align="center">
  		 			<td >课程序号</td>
  		 			<td >课程代码</td>
  		 			<td >课程名称</td>
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
  		 		<@table.tbody datas=courseTakeBeans;bean>
  		 				<td>${bean.courseTake.task.seqNo!}</td>
  		 				<td>
  		 					${bean.courseTake.task.course.code!}
  		 				</td>
  		 				<td>
  		 					${bean.courseTake.task.course.name!}
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