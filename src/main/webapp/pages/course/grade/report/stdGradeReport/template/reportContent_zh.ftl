 <#macro displayGrades(grade)>
   <td>${grade.course.name}</td>
   <td>&nbsp;${grade.credit?if_exists}</td>
   <td>&nbsp;${grade.getScoreDisplay(setting.gradeType)}</td>
 </#macro>
<#assign columns = 4/>
 <#list stdGradeReports as report>
     <#assign stdTypeName><@i18nName report.std.type/></#assign>
     <#assign schoolName><@i18nName systemConfig.school/></#assign>
 	 <center><h2>${schoolName}${(report.std.enrollYear + "级")?replace("-3级","(春季)级")?replace("-9级","(秋季)级")}${stdTypeName}学习成绩表</h2></center>
 	 <table ${style}>
 	 	<tr>
 	 	<#assign tableTitleColumnLen = (100 - 8) / 3/>
 	 <#if !isSingle?exists>
 	 	<#assign tableTitleColumnLen = (100 - 8) / 4/>
 	 	<#if setting.majorType.id=1>
 	 	    <#assign majorName><@i18nName report.std.firstMajor?if_exists/></#assign>
 	 		<#assign aspectName><@i18nName report.std.firstAspect?if_exists/></#assign>
 	 	<#else>
            <#assign majorName><@i18nName report.std.secondMajor?if_exists/></#assign>
 	 		<#assign aspectName><@i18nName report.std.secondAspect?if_exists/></#assign>
 	 	</#if>
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;&nbsp;<@msg.message key="entity.speciality"/>：<#if aspectName?exists><#if aspectName?starts_with(majorName)>${aspectName}<#else>${majorName}<#if aspectName?length !=0>(${aspectName})</#if></#if><#else>${majorName}</#if></td>
 	 </#if>
 	 	  <td width="${tableTitleColumnLen}%"><@msg.message key="attr.eduLength"/>：${report.std.schoolingLength?if_exists} <@msg.message key="common.year"/></td>
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;<@msg.message key="attr.stdNo"/>：${report.std.code}</td>
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;<@msg.message key="attr.personName"/>：${report.std.name?if_exists}</td>
 	 	  <td>&nbsp;<@msg.message key="entity.gender"/>：<@i18nName (report.std.basicInfo.gender)?if_exists/></td>
 	    <tr>
 	 </table>
     
     <table ${style}  class="listTable">
	   	<tr align="center">
	   		<#list 1..columns as i>
		     	<td align="center"><@msg.message key="attr.courseName"/></td>
		    	<td align="center"><@msg.message key="attr.credit"/></td>
		     	<td width="5%">成绩</td>
	   		</#list>
	   	</tr>
	   <#assign grades=report.grades>
	   <#if (grades?size > 0)>
	   		<#assign isBreak = false/>
		   <#list 1..grades?size as i> 	
		   	<tr>
		   		<#list 1..columns as j>
		   			<#if (((i - 1) * columns + j) > grades?size)>
		   				<#if grades?size % columns != 0>
		   					<@emptyTd (columns - grades?size % columns) * 3/>
		   				</#if>
		   				<#assign isBreak = true/>
		   				<#break/>
		   			</#if>
					<@displayGrades grades[(i - 1) * columns + (j - 1)]?if_exists/>
		   		</#list>
		   		<#if isBreak><#break/></#if>
				<#if (i % columns == 0 && i < grades?size)>
			</tr>
			<tr>
				</#if>
			</tr>
		   </#list>
	   <#else>
	   		<@emptyTd columns * 3/>
	   </#if>
     </table>
 	 <table ${style}>
 	 	<tr>
 	 	  <td width="80%"><@msg.message key="grade.creditTotal"/>：${report.credit?if_exists}</td>
 	 	  <#--不管以哪个成绩大卡打印，总平均绩点均为最好绩点。-->
 	 	  <td>&nbsp;<#if setting.printGP><@msg.message key="filed.averageGradeNod"/>：<@reserve2 stdGPMap[report.std.id?string].GPA/></#if></td>
 	 	</tr>	
	 </table>
	 <br>	
 	 <table ${style}>
 	 	<tr>
 	 	  <td>论文题目:</td>	
 	 	  <td>&nbsp;${thesisSubjectMap[report.std.code]?if_exists}</td>	
 	 	  <td>责任部门: 教务处</td>	
 	 	  <td><@msg.message key="common.printDate"/>:
 	 	  		<script type='text/javascript'>
 	 	  			var s='';
 	 	  			d = new Date(); 
					s += d.getFullYear()+'-';
					s += (d.getMonth()+1)+'-'; 
					s += d.getDate();    
					document.writeln(s);								
 	 	  		</script>
 	 	  </td>	 	  	 	  
 	 	</tr>
	 </table>
 	 <#if report_has_next>
	 	 <div style='PAGE-BREAK-AFTER: always'></div> 
 	 </#if>
</#list>
