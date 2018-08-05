<#assign FiveLevelNames={'优':'Excellent','良':'Good','中':'Medium','及格':'Pass','不及格':'Fail'} />
 <#macro displayGrades(grade)>
   <td><#if grade.course.engName?exists>${grade.course.engName}<#else>${grade.course.name}</#if></td>
   <td>&nbsp;${grade.credit?if_exists}</td>
   <#local scoreText=(grade.getScoreDisplay(setting.gradeType))?default("")/>
   <td>&nbsp;${FiveLevelNames[scoreText]?default(scoreText)}</td>
 </#macro>
<#assign columns = 4/>
 <#list stdGradeReports as report>
     <#assign stdTypeName><@i18nName report.std.type/></#assign>
     <#assign schoolName><@i18nName systemConfig.school/></#assign>
 	 <center><h2><@i18nName systemConfig.school/> ${(report.std.enrollYear)?replace("-3","(Spring)")?replace("-9","(Fall)")} Grade <@i18nName report.std.type/> Academic Achievements</h2></center>
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
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;&nbsp;<#if setting.majorType.id=1>Major<#else>Minor</#if>：<#if aspectName?exists><#if aspectName?starts_with(majorName)>${aspectName}<#else>${majorName}<#if aspectName?length !=0>(${aspectName})</#if></#if><#else>${majorName}</#if></td>
 	 </#if>
 	 	  <td width="${tableTitleColumnLen}%">Study years：${report.std.schoolingLength?if_exists} year(s)</td>
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;Student No：${report.std.code}</td>
 	 	  <td width="${tableTitleColumnLen}%">&nbsp;Name：<@i18nName report.std/></td>
 	 	  <td>&nbsp;Gender：<@i18nName (report.std.basicInfo.gender)?if_exists/></td>
 	    <tr>
 	 </table>
     
     <table ${style}  class="listTable">
	   	<tr align="center">
	   		<#list 1..columns as i>
		     	<td align="center">Course Name</td>
		    	<td align="center">Credit</td>
		     	<td width="5%">Score</td>
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
 	 	  <td width="80%">Total of credit：${report.credit?if_exists}</td>	
          <#--不管以哪个成绩大卡打印，总平均绩点均为最好绩点。-->
          <td>&nbsp;<#if setting.printGP>GPA：<@reserve2 stdGPMap[report.std.id?string].GPA/></#if></td>
 	 	</tr>	
	 </table>
	 <br>	
 	 <table ${style}>
		<tr>
 	 	  <td width='30%'>&nbsp;</td>	
 	 	  <td width='20%'>&nbsp;</td>
 	 	  <td width='25%'>Date：
			<script type='text/javascript'>
 	 	  			var s='';
 	 	  			d = new Date(); 
					s += d.getFullYear()+'-';
					s += (d.getMonth()+1)+'-'; 
					s += d.getDate();    
					document.writeln(s);								
 	 	  		</script></td>
 	 	  <td>&nbsp;</td> 
 	 	  <td>Person to Print：Office of Academic Affairs</td>	
 	 	</tr>
	 </table>
 	 <#if report_has_next>
	 	 <div style='PAGE-BREAK-AFTER: always'></div> 
 	 </#if>
</#list>
