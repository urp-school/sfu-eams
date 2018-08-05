<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '学生考勤报表（院系纬度）', null, true, false);
</script>
	</br>
  	院系名称：<@i18nName department?if_exists/>&nbsp;院系代码：${(department.code)?if_exists}
  <#if dlist??>
  	<@table.table width="100%" sortable="true" id="listTable" >
 		<thead>
 			<tr class="darkColumn" align="center">
 			<td colspan="2">班级、辅导员信息/起始日期</td>
 			<#list dateList as date>
 				<td colspan="3">${date}</td>
 			</#list>
 			<td colspan="3">总计</td>
 			</tr>
 			<tr class="grayStyle" align="center">
 			<td>班级名称</td>
 			<td>辅导员名称</td>
 			<#list dateList as date>
 				<td>缺勤</td>
 				<td>迟到</td>
 				<td>课时</td>
 			</#list>
 			<td>缺勤</td>  		 			
 			<td>缺勤课时</td>
 			<td>迟到</td>
 			</tr>
 		</thead> 		
 		<@table.tbody datas=dlist;d>
			<#if d[0]=="总计（院系）">
				<td colspan="2" class="darkColumn">${d[0]}</td>
			<#else>
				<#assign adminClass=d[0]/>
				<td><@i18nName adminClass!/></td>
				<td><@i18nName (adminClass.instructor)!/></td>
			</#if>  		 				
			<#assign qc=0/>
			<#assign cd=0/>
			<#assign qcks=0/>
			<#list 0..(dateList?size-1) as i>
				<td>${d[i*3+1]}</td>
				<td>${d[i*3+2]}</td>
				<td>${d[i*3+3]}</td>
				<#assign qc=qc+d[i*3+1]/>
				<#assign cd=cd+d[i*3+2]/>
				<#assign qcks=qcks+d[i*3+3]/>
			</#list>
			<td>${qc}</td>  		 			
 			<td>${qcks}</td>
 			<td>${cd}</td>
 		</@> 		
    </@>
 <#else>
 	</p>&nbsp;&nbsp;&nbsp;&nbsp;没有考勤信息！
 </#if>
<#include "/templates/foot.ftl"/> 