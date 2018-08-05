<#include "/templates/head.ftl"/>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '学生考勤报表（班级纬度）', null, true, false);
</script>
	</br>
  	<@bean.message key="std.adminClass.baseInfo.name"/>：<@i18nName adminClass?if_exists/>&nbsp;<@bean.message key="std.adminClass.baseInfo.code"/>：${(adminClass.code)?if_exists}
  <#if dlist??>
  	<@table.table width="100%" sortable="true" id="listTable" >
 		<thead>
 			<tr class="darkColumn" align="center">
 			<td colspan="3">学生信息/起始日期</td>
 			<#list dateList as date>
 				<td colspan="3">${date}</td>
 			</#list>
 			<td colspan="3">总计</td>
 			</tr>
 			<tr class="grayStyle" align="center">
 			<td>学号</td>
 			<td>姓名</td>
 			<td>性别</td>
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
			<#if d[0]=="总计（班级）">
				<td colspan="3" class="darkColumn">${d[0]}</td>
			<#else>
				<#assign std=d[0]/>
				<td>${std.code!}</td>
				<td>${std.name!}</td>
				<td><@i18nName (std.basicInfo.gender)!/></td>
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