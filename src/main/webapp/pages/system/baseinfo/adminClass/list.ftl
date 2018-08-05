<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<script language="JavaScript" type="text/JavaScript" src="scripts/system/BaseInfo.js"></script>
  <@getMessage/>
  <@table.table  width="100%" sortable="true" id="listTable" >
    <@table.thead>
	    <@table.selectAllTd id="adminClassId"/>
	    <@table.sortTd name="attr.code" id="adminClass.code"/>
	    <@table.sortTd name="attr.infoname" id="adminClass.name"/>
	    <@table.sortTd name="entity.speciality" id="adminClass.speciality.name"/>
	    <@table.sortTd name="entity.specialityAspect" id="adminClass.aspect.name"/>
	  	<@table.sortTd name="entity.studentType" id="adminClass.stdType.name" />
	  	<@table.sortTd text="在校" id="adminClass.actualStdCount"/>
	  	<@table.sortTd text="学籍有效" id="adminClass.stdCount"/>
    </@>
    <@table.tbody datas=adminClasses;adminClass>
        <@table.selectTd id="adminClassId" value=adminClass.id/>
        <td>${adminClass.code}</td>
        <td><a href="adminClass.do?method=info&adminClass.id=${adminClass.id}">${adminClass.name}</a></td>
        <td><@i18nName adminClass.speciality?if_exists/></td>
        <td><@i18nName adminClass.aspect?if_exists/></td>
        <td><@i18nName adminClass.stdType/></td>
        <td>${adminClass.actualStdCount!}</td>
        <td>${adminClass.stdCount!}</td>
    </@>
  </@>
  <script language="javascript">
   type="adminClass";
  </script>
  </body>
<#include "/templates/foot.ftl"/>