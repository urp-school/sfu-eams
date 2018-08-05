<#if majorType.id == 1>
 	<div align="center"><@msg.message key="attr.stdNo"/>:${stdGP.std.code} <@msg.message key="attr.personName"/>:<@i18nName stdGP.std/> <@msg.message key="entity.department"/>:<@i18nName stdGP.std.department?if_exists/> <@msg.message key="entity.speciality"/>:<@i18nName stdGP.std.firstMajor?if_exists/> <@msg.message key="entity.specialityAspect"/>:<@i18nName stdGP.std.firstAspect?if_exists/></div>
<#else>
 	<div align="center"><@msg.message key="attr.stdNo"/>:${stdGP.std.code} <@msg.message key="attr.personName"/>:<@i18nName stdGP.std/> <@msg.message key="entity.department"/>:<@i18nName (stdGP.std.secondMajor.department)?if_exists/> <@msg.message key="entity.speciality"/>:<@i18nName (stdGP.std.secondMajor)?if_exists/> <@msg.message key="entity.specialityAspect"/>:<@i18nName (stdGP.std.secondAspect)?if_exists/></div>
</#if>

<#include "/pages/course/grade/gradeMacros.ftl"/>
<@table.table width="90%" align="center">
   	<@table.thead>
     	<@table.td name="attr.year2year"/>
     	<@table.td name="attr.term"/>
     	<@table.td name="std.grade.courseNumber"/>
     	<@table.td name="std.totalCredit"/>
     	<@table.td name="grade.avgPoints"/>
   	</@>
   	<@table.tbody datas=stdGP.GPList;stdGPTerm>
     	<td>${stdGPTerm.calendar.year}</td>
     	<td>${stdGPTerm.calendar.term}</td>
     	<td>${stdGPTerm.count}</td>
     	<td>${stdGPTerm.credits}</td>
     	<td><@reserve2 stdGPTerm.GPA/></td>
   	</@>
   	<tr align="center">
     	<td colspan="2"><@msg.message key="grade.schoolSummary"/></td>
     	<td>${stdGP.count}</td>
     	<td>${stdGP.credits}</td>
     	<td><@reserve2 stdGP.GPA/></td>
   	</tr>
</@>
