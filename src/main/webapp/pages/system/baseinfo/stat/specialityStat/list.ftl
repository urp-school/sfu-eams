<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table>
  <@table.table width="100%"  id="listTable" sortable="true">
    <@table.thead>
       <@table.td width="5%" text="序号"/>
       <@table.sortTd width="20%" name="attr.infoname" id="speciality.name"/>
       <@table.sortTd width="10%" name="attr.code" id="speciality.code"/>
       <@table.td width="15%" text="所属学科门类" />
       <@table.td width="10%" text="设置时间" />
    </@>
    <@table.tbody datas=specialities;speciality,speciality_index>
       <td>${speciality_index + 1}</td>
       <td><a href="speciality.do?method=info&speciality.id=${speciality.id}"><@i18nName speciality/></a></td>
       <td>${speciality.code}</td>
       <td><@i18nName (speciality.subjectCategory)?if_exists/></td>
       <td><#if speciality.dateEstablished?exists>${speciality.dateEstablished?string("yyyy")}</#if></td>
    </@>
  </@>
 <script>
    var bar = new ToolBar("myBar","专业设置列表",null,true,true);
    bar.addPrint("<@msg.message key="action.print"/>");
    orderBy=function(what){
       parent.list(what);
    }
 </script>  
  </body>
<#include "/templates/foot.ftl"/>