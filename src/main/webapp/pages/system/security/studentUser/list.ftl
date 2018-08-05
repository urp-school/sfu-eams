<#include "/templates/head.ftl"/>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
 <form name="stdListForm" method="post" onsubmit="return false;"></form>
 	<@getMessage/>
    <@table.table width="100%" align="left" id="sortTable" sortable="true">
	   <@table.thead>
	     <@table.selectAllTd id="stdCode"/>
	     <@table.sortTd width="10%" id="student.code" name="attr.stdNo"/>
	     <@table.sortTd width="8%" id="student.name" name="attr.personName"/>
	     <@table.sortTd width="5%" id="student.basicInfo.gender.id" name="entity.gender"/>
	     <@table.sortTd width="8%" id="student.enrollYear" name="attr.enrollTurn"/>
	     <@table.sortTd width="15%"id="student.department.name" name="entity.college"/>
	     <@table.sortTd width="15%" id="student.firstMajor.name" name="entity.speciality"/>
   	     <@table.td width="8%" name="entity.acount"/>
	   </@>
	   <@table.tbody datas=stds;std>
	     <@table.selectTd id="stdCode" type="checkbox" value="${std.code}"/>
	    <td><#if std.userInfo?exists><A href="std.do?method=info&userId=${std.userInfo.id}">${std.code}</A><#else>${std.code}</#if></td>
        <td><A href="studentDetailByManager.do?method=detail&stdId=${std.id}" target="blank" >${std.name} </a></td>
        <td><@i18nName std.basicInfo?if_exists.gender?if_exists/></td>
        <td>${std.enrollYear}</td>
        <td><@i18nName std.department/></td>
        <td><@i18nName std.firstMajor?if_exists/></td>
        <#if stdUserMap[std.id?string]?exists>
         <td>
	      <#if stdUserMap[std.id?string].state ==1><@bean.message key="action.activate" /></#if>
	      <#if stdUserMap[std.id?string].state ==0><font color="red"><@bean.message key="action.freeze" /></font></#if>
	    </td>
	    <#else>
        <td>无帐户</td>
	    </#if>
	   </@>
     </@>
 </body>
<#include "/templates/foot.ftl"/>