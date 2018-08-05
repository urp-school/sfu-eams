<#include "/templates/head.ftl"/>
 <script>
    var detailArray = {};
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(parent)">
     <table id="myBar"></table>
     <@table.table width="100%" id="listTable" sortable="true" headIndex="1" onkeypress="DWRUtil.onReturn(event,query)">
       <form name="actionForm" method="post" action="systemMessage.do?method=teacherList" onsubmit="return false;">
       <tr>
           <td><img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/></td>
           <td><input style="width:100%" type="text" name="teacher.code" maxlength="32" value="${RequestParameters['teacher.code']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="teacher.name" maxlength="50" value="${RequestParameters['teacher.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="teacher.department.name" maxlength="50" value="${RequestParameters['teacher.department.name']?if_exists}"></td>
           <td><input style="width:100%" type="text" name="teacher.teacherType.name" maxlength="50" value="${RequestParameters['teacher.teacherType.name']?if_exists}"></td>
       </tr>
       </form>
       <@table.thead>
         <@table.selectAllTd id="teacherId"/>
	     <@table.sortTd name="teacher.code" id="teacher.code"/>
	     <@table.sortTd name="attr.personName" id="teacher.name"/>
	     <@table.sortTd name="entity.department" id="teacher.department.name"/>
	     <@table.sortTd text="教职工类别" id="teacher.teacherType.id"/>
	   </@>
	   <@table.tbody datas=teachers;teacher>
	      <@table.selectTd id="code" value="${teacher.code?if_exists}"/>
	      <td>${teacher.code?if_exists}</td>
	      <td>${teacher.name}</td>
	      <td>${teacher.department?if_exists.name?if_exists}</td>
	      <td><@i18nName teacher.teacherType?if_exists/></td>
	   </@>
     </@>
 </body>
 <script language="JavaScript" type="text/JavaScript" src="scripts/system/SendMessage.js"></script>
 <script>
   function getIds(){
       return(getCheckBoxValue(document.getElementsByName("code")));
    }
    <#list teachers as teacher>
    <#if teacher.code?exists>
    detailArray['${teacher.code}'] = {'name':'${teacher.name}'};
    </#if>
	</#list>
    var bar= new ToolBar("myBar","全校教师列表",null,true,true);
    bar.addItem("选中添加","addSelected()");
</script>
<#include "/templates/foot.ftl"/>