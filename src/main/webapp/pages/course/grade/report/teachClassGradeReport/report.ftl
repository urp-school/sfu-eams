<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <style>
 .reportTable {
	border-collapse: collapse;
    border:solid;
	border-width:1px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.reportTable td{
	border:solid;
	border-width:0px;
	border-right-width:1;
	border-bottom-width:1;
	border-color:#006CB2;
	height:26px;

}
.printTableStyle {
	border-collapse: collapse;
    border:solid;
	border-width:2px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.printTableStyle td{
	border:solid;
	border-width:0px;
	border-right-width:2;
	border-bottom-width:2;
	border-color:#006CB2;
        height:20px;
}
 </style>
 <table id="myBar" width="100%"> </table>
 <#macro displayGrades(index,grade,gradeTypes)>
    <td>${index+1}</td>
    <td>${grade.std.code}</td>
    <td><@i18nName grade.std/></td>
    <#list gradeTypes as gradeType>
     <#if gradeType.id=USUAL && (grade.courseTakeType.id)?if_exists=REEXAM>
     <td>免修</td>
     <#elseif grade.getExamGrade(gradeType)?exists>
       <#if !grade.getExamGrade(gradeType).examStatus.isAttend>
         <td><@i18nName grade.getExamGrade(gradeType).examStatus/></td>
       <#else>
         <td>${grade.getScoreDisplay(gradeType)}</td>
       </#if>
     <#else>
     <td>${grade.getScoreDisplay(gradeType)}</td>
     </#if>
    </#list>
    <td></td>
 </#macro>
 <div id = "DATA" width="100%" align="center" cellpadding="0" cellspacing="0">
 <#assign pageSize=50>
 <#list reports as report>
 	 <#assign grades=report.courseGrades>
 	 <#assign pages=(grades?size/pageSize)?int />
 	 <#if grades?size==0><#break></#if>
 	 <#if (pages*pageSize<grades?size)><#assign pages=pages+1></#if>
     <#assign teachTask = report.task>
     <#list 1..pages as page>
 	 <div align='center'><h3><@i18nName systemConfig.school/>${teachTask.calendar.studentType.name}课程成绩登记表</h3></div>
 	 <div align='center'>${teachTask.calendar.year}年度 <#if teachTask.calendar.term='1'>第一学期<#elseif teachTask.calendar.term='2'>第二学期<#else>${teachTask.calendar.term}</#if></div>
 	 <table width='100%' align='center' border='0' style="font-size:13px" >
 	 	<tr>
	 	 	<td width='25%'><@msg.message key="attr.courseNo"/>:${teachTask.course.code}</td>
	 	 	<td width='40%'><@msg.message key="attr.courseName"/>:<@i18nName teachTask.course/></td>
	 	 	<td align='left'><@msg.message key="entity.courseType"/>:<@i18nName teachTask.courseType/></td>
 	 	</tr>
 	 	<tr>
	 	 	<td><@msg.message key="attr.taskNo"/>:${teachTask.seqNo?if_exists}</td>
	 	 	<td><@msg.message key="task.arrangeInfo.primaryTeacher"/>:<@getTeacherNames (teachTask.arrangeInfo.teachers)?if_exists/></td>
	 	 	<td align='left'>授课院系:<@i18nName (teachTask.arrangeInfo.teachDepart)?if_exists/></td>
 	 	</tr>	
 	 	<tr>
 	 	    <td colspan="3"><#list report.gradeTypes as gradeType><#if teachTask.gradeState.getPercent(gradeType)?exists><@i18nName gradeType/>${teachTask.gradeState.getPercent(gradeType)?string.percent}&nbsp;</#if></#list></td>
 	 	</tr>
 	 </table>
     <table width="100%" class="reportTable">
	   <tr align="center">
	     <td align="center" width="4%"><@msg.message key="attr.index"/></td>
	     <td align="center" width="8%"><@bean.message key="attr.stdNo"/></td>
	     <td width="8%"><@bean.message key="attr.personName" /></td>
	     <#list report.gradeTypes as gradeType>
	     <td width="9%"><@i18nName gradeType/></td>
	     </#list>
	     <td align="center" width="4%"><@msg.message key="attr.remark"/></td>
	     <td align="center" width="4%"><@msg.message key="attr.index"/></td>
	     <td align="center" width="8%"><@bean.message key="attr.stdNo"/></td>
	     <td width="8%"><@bean.message key="attr.personName"/></td>
	     <#list report.gradeTypes as gradeType>
	     <td width="9%"><@i18nName gradeType/></td>
	     </#list>
	     <td align="center" width="5%"><@msg.message key="attr.remark"/></td>
	   </tr>
	   <#list 0..(pageSize/2-1) as i>
	   <tr>
		   <#assign j=i+(page-1)*pageSize>
		   <#if grades[j]?exists>
		     <@displayGrades j,grades[j],report.gradeTypes/>
		   <#else>
		      <#break>
		   </#if>
		   <#assign j=i+(page-1)*pageSize+(pageSize/2)>
		   <#if grades[j]?exists>
			    <@displayGrades j,grades[j],report.gradeTypes/>
		   <#else>
		     <#list 1..(4+report.gradeTypes?size) as i>
		       <td>&nbsp;</td>
		     </#list>
		   </#if>
	   </tr>
	   </#list>
     </table>

     <#if page_has_next><div style='PAGE-BREAK-AFTER: always'></div></#if>
     </#list>

     
     <table class="printTableStyle" width="100%">
		    <tr  width="100%">
			   <td rowspan='7' width="40%">
			   实际参加考试考查人数____________人<br>
			   缺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数____________人<br>
			   教师签名:_______________________<br>
			   系主任签名:_____________________<br>
			   <br>
			   日期:_____________年_____月_____日
			   </td>
			   <td colspan='3' width="60%" align="center">总评成绩分段统计</td>
			  <tr>
			   <td width="30%"  align="center">成绩</td>
			   <td width="15%"  align="center">人数</td>
			   <td width="15%"  align="center">百分比</td>
			  <tr>
			   <td align="center">90-100(优)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">80-89.9(良)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">70-79.9(中)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">60-69.9(及格)</td>
			   <td></td>
			   <td align="right">%</td>
			  <tr>
			   <td align="center">0-59.9(不及格)</td>
			   <td></td>
			   <td align="right">%</td>
		    </tr>
	  		</table>  
     <#if report_has_next><div style='PAGE-BREAK-AFTER: always'></div></#if>

</#list>  
</div>
<script>
   var bar = new ToolBar("myBar","教学班成绩打印",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addItem("<@msg.message key="action.export"/>","exportData()");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   function exportData(){
       <#if RequestParameters['taskIds']?exists>
       self.location="teachClassGradeReport.do?method=export&template=teachClassGradeReport.xls&taskIds=${RequestParameters['taskIds']}";
       <#--该页面可能从单个成绩的录入跳转过来-->
       <#elseif RequestParameters['taskId']?exists>
       self.location="teachClassGradeReport.do?method=export&template=teachClassGradeReport.xls&taskIds=${RequestParameters['taskId']}";
       </#if>
   }
</script>
</body>
<#include "/templates/foot.ftl"/>