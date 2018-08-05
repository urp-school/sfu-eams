<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <table id="myBar" width="100%"></table>
 
 <#macro displayGrade(index,std)>
    <td>${index+1}</td>
    <td>${std.code}</td>
    <td>${std.name}</td>
    <#if gradeMap[std.id?string]?exists>
        <td><!--${gradeMap[std.id?string].score}-->&nbsp;</td>
    <#else>
     <td>&nbsp;</td>
    </#if>
</#macro>
<#list adminClasses as adminClass>
 	 <div align='center' style="font-size:15px"><B><@i18nName systemConfig.school/>德育成绩表</B><br>
 	   <B><#if calendar.term='1'><@bean.message key="course.yearTerm1" arg0=calendar.year/><#elseif calendar.term='2'><@bean.message key="course.yearTerm2" arg0=calendar.year/><#else><@bean.message key="course.yearTermOther" arg0=calendar.year arg1=calendar.term/></#if></B>
 	 </div>
 	 <table width='90%' align='center' border='0' style="font-size:13px">
 	 	<tr>
	 	 	<td width='25%'>辅导员:${adminClass.instructor.name}</font></td>
	 	 	<td width='25%'>班级:${adminClass.name}</font></td>
	 	 	<td width='40%'>人数:${adminClass.students?size}</font></td>
 	 	</tr>
 	 </table>
     <table width="90%" class="listTable" align="center" onkeypress="onReturn.focus(event)";>
	   <tr align="center">
	     <td align="center" width="5%"><@msg.message key="attr.index"/></td>
	     <td align="center" width="12%"><@bean.message key="attr.stdNo"/></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td>德育成绩</td>
	     <td align="center" width="5%"><@msg.message key="attr.index"/></td>     
	     <td align="center" width="12%"><@bean.message key="attr.stdNo"/></td>
	     <td width="15%"><@bean.message key="attr.personName"/></td>
	     <td>德育成绩</td>
	   </tr>
	   <#assign students=adminClass.students?sort_by('code')?if_exists>
	   <#assign pageSize=((students?size+1)/2)?int />
	   <#list students as student>
	   <tr>
		   <#if students[student_index]?exists>		   
		     <@displayGrade student_index,students[student_index]/>
		   <#else>
		      <#break>
		   </#if>
		   <#assign j=student_index+pageSize> 
		   <#if students[j]?exists>
			    <@displayGrade j,students[j]/>
		   <#else>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		       <td>&nbsp;</td>
		   </#if>
		   <#if ((student_index+1)*2>=students?size)><#break></#if>
	   </tr>
	   </#list>
     </table>
     <#if !adminClass_has_next>	   
	   	 <table class="listTable" align="center" width="90%">
		    <tr  width="100%">
			   <td rowspan='7' width="40%">
			   实际参加考试考查人数____________人<br>
			   缺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;考&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数____________人<br>
			   教师签名:_______________________<br>
			   系主任签名:_____________________<br>
			   <br>
			   日期:_____________年_____月_____日
			   </td>
			   <td colspan='3' width="60%" align="center">成绩统计</td>
			  <tr>
			   <td width="20%"  align="center">成绩</td>
			   <td width="20%"  align="center">人数</td>
			   <td width="20%"  align="center">百分比</td>
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
     		<#if adminClass_has_next>
	 	  		<div style='PAGE-BREAK-AFTER: always'></div> 
 	     	</#if>
 	 		 </#if>
</#list>
<script>
   var bar = new ToolBar("myBar","德育成绩单",null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
</script>
 </body>
<#include "/templates/foot.ftl"/>      