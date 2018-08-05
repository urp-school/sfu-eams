<#include "/templates/head.ftl"/>
<#include "/pages/course/grade/gradeMacros.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <table id="myBar"></table>
 <script>
   var bar = new ToolBar("myBar","每学期成绩打印",null,true,true);
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 </script>
 <#assign style>style="font-size:${setting.fontSize}px" width="100%"</#assign>
 <#list stdGradeReports as report> 
 	 <div align='center'><h3><@i18nName systemConfig.school/><@i18nName report.std.type/>成绩单</h3></div>
 	 <div ${style} align='center'><font size='2'>(${calendar.year}年度 <#if calendar.term='1'>第一学期<#elseif calendar.term='2'>第二学期<#else>${calendar.term}</#if>)</font></div><br> 	 
 	 <div ${style} align='center'>
 	 	<@msg.message key="entity.department"/>:<@i18nName report.std.department?if_exists/>
 	 	<@msg.message key="entity.speciality"/>:
 	 		<#if setting.majorType.id=1>
 	 			<@i18nName report.std.firstMajor?if_exists/>
 	 		<#else>	
 	 		    <@i18nName report.std.secondMajor?if_exists/>
 	 		</#if>
 	 	<@msg.message key="attr.stdNo"/>:${report.std.code}
 	 	<@msg.message key="attr.personName"/>：<@i18nName report.std/>
	 </div><br>
	 <div ${style} align='center'>
	 		选修学分:${report.electedCredit?if_exists}&nbsp;&nbsp;
	 		实修学分:${report.credit?if_exists}&nbsp;&nbsp;
	 		<#if setting.printGP>
	 		平均绩点:<@reserve2 report.GPA?if_exists/>&nbsp;&nbsp;
	 		</#if>
	 		<#if setting.printAwardCredit>
	 		奖励学分:${report.awardedCredit?if_exists}
	 	    </#if>
	 </div>
	 <br>
     <table width="100%" ${style} class="listTable">
	   <tr align="center">
	     <td align="center" width="25%"><@msg.message key="attr.courseName"/></td>
	     <td align="center" width="5%"><@msg.message key="attr.credit"/></td>
	     <td width="15%">性质</td>
	     <td width="5%"><@bean.message key="field.exam.exam"/></td>
	     <#if setting.printGP>
	     <td align="center" width="5%">绩点</td>
	     </#if>
	     <td align="center" width="5%">备注</td>
	   </tr>	
	   <#list report.grades?if_exists as grade>
	   <tr> 
		   <td>&nbsp;<font size='2'><@i18nName grade.course/></font></td>
		   <td>&nbsp;${grade.credit}</td>
		    <td>&nbsp;<font size='2'><@i18nName grade.courseType?if_exists/></font></td>
		    <td>&nbsp;${grade.getScoreDisplay(setting.gradeType)}</td>
		    <#if setting.printGP>
		    <td>&nbsp;${((grade.GP*100)?int/100)?string('#0.00')?if_exists}</td>
		    </#if>
		    <td>&nbsp;</td>
	   </tr>
	   </#list>
     </table>
 	 <div ${style}>说明</div>
	 <div ${style}>&nbsp;1.成绩单不得涂改，否则无效。</div>
	 <div ${style}>&nbsp;2.成绩必须学校盖章方有效。</div>
	 <div ${style}>&nbsp;3.成绩单要妥为保存，经备查考。</div>
	<br><br>
 	 <#if report_has_next>
	 	 <div style='PAGE-BREAK-AFTER: always'></div> 
 	 </#if>
</#list>
</body>
<#include "/templates/foot.ftl"/>