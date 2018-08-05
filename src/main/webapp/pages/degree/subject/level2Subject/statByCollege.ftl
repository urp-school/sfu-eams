<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
  <table id="subjectStatBar"></table>
  <script>    
    var bar = new ToolBar("subjectStatBar","学科点院系分布",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
</script>
<table width="100%" align="center" class="listTable">
	<tr>
		<td align="center" colspan="5"><h3><@i18nName systemConfig.school/><br>硕士,博士学科点院系分布</h3></td>
	</tr>
     <form name='level2SubjectForm' method="post" action="" onsubmit="return false;">
     <tr align="center">
     	<td>院(系,所)</td>
     	<td>博士点(${statByCollegeMap['doctor']?default(0)})</td>
     	<td>说明</td>
     	<td>硕士点(${statByCollegeMap['master']?default(0)})</td>
     	<td>说明</td>
     </tr>
     <#list departments?if_exists?sort_by("name") as department>
     	<#assign doctors=statByCollegeMap[department.id?string+'-doctor']?if_exists>
     	<#assign masters=statByCollegeMap[department.id?string+'-master']?if_exists>
     	<#assign maxrows><#if (doctors?size>=masters?size)>${doctors?size?number}<#else>${masters?size?number}</#if></#assign>
     	<tr align="center">
     		<td rowspan="${maxrows?number}" >${department.name}<br><#if (doctors?size>0)>${doctors?size}博</#if><#if (masters?size>0)>${masters?size}硕</#if></td>
     		<#if doctors[0]?exists>
     			<td>${doctors[0].speciality.name?if_exists}</td>
     			<td><#if doctors[0].isSpecial==true>专业学位<#elseif doctors[0].isSelfForDoctor?exists&&doctors[0].isSelfForDoctor==true>${doctors[0].dateForDoctor?string("yyyy")}自主设置<#else>目录内</#if></td>
     		<#else>
     			<td></td>
     			<td></td>
     		</#if>
     		<#if masters[0]?exists>
     			<td>${masters[0].speciality.name?if_exists}</td>
     			<td><#if masters[0].isSpecial==true>专业学位<#elseif masters[0].isSelfForMaster?exists&&masters[0].isSelfForMaster==true>${masters[0].dateForMaster?string("yyyy")}年自主设置<#else>目录内</#if></td>
     		<#else>
     			<td"></td>
     			<td"></td>
     		</#if>
     	</tr>
     	<#if (maxrows?number>=2)>
     	<#list 1..(maxrows?number-1) as i>
     		<tr align="center">
     		<#if doctors[i]?exists>
     			<td>${doctors[i].speciality.name?if_exists}</td>
     			<td><#if doctors[i].isSpecial==true>专业学位<#elseif doctors[i].isSelfForDoctor?exists&&doctors[i].isSelfForDoctor==true>${doctors[i].dateForDoctor?string("yyyy")}自主设置<#else>目录内</#if></td>
     		<#else>
     			<td></td>
     			<td></td>
     		</#if>
     		<#if masters[i]?exists>
     			<td>${masters[i].speciality.name?if_exists}</td>
     			<td><#if masters[i].isSpecial==true>专业学位<#elseif masters[i].isSelfForMaster?exists&&masters[i].isSelfForMaster==true>${masters[i].dateForMaster?string("yyyy")}年自主设置<#else>目录内</#if></td>
     		<#else>
     			<td></td>
     			<td></td>
     		</#if>
     		</tr>
     	</#list>
     	<#else>
     	</#if>
     </#list>
<table>