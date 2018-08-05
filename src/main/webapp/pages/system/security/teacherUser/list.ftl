<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<@getMessage/>
  <@table.table width="100%" id="listTable" sortable="true" > 
     <@table.thead>
       <@table.selectAllTd id="teacherNo"/>
       <@table.sortTd width="10%" name="teacher.code" id="teacher.code"/>
	   <@table.sortTd width="10%" name="attr.personName" id="teacher.name"/></td>
	   <@table.sortTd width="5%" name="common.gender" id="teacher.gender"/></td>
   	   <@table.sortTd width="15%" name="entity.college" id="teacher.department.name"/></td>   
	   <@table.td width="30%" name="attr.email" /></td>
  	   <@table.td width="15%" name="attr.modifyAt"/></td>
   	   <@table.td width="5%" name="entity.acount"/></td>
	 </@>
	 <@table.tbody datas=teachers;teacher>
	   <@table.selectTd id="teacherNo" value="${teacher.code?if_exists}"/>
        <td><#if teacher.userInfo?exists><A href="teacherUser.do?method=info&userId=${teacher.userInfo.id}">${teacher.code?if_exists}</A><#else>${teacher.code?if_exists}</#if></td>
	    <td><A href="teacher.do?method=info&teacher.id=${teacher.id}">${teacher.name}</a></td>
        <td>${teacher.gender.name}</td>
        <td >${teacher.department.name}</td>
        <#if teacherUserMap[teacher.id?string]?exists>
        <td >${teacherUserMap[teacher.id?string].email?if_exists}</td>           
        <td >${teacherUserMap[teacher.id?string].modifyAt?string("yyyy-MM-dd")?if_exists}</td>
	    <td >
	      <#if teacherUserMap[teacher.id?string].state ==1><@bean.message key="action.activate" /></#if>
	      <#if teacherUserMap[teacher.id?string].state ==0><font color="red"><@bean.message key="action.freeze" /></font></#if>
	    </td>
	    <#else>
	    <td width="30%">&nbsp;</td>
        <td width="15%">&nbsp;</td>
	    <td >无</td>
	    </#if>
	  </@>
	</@>
 </body>
<#include "/templates/foot.ftl"/>