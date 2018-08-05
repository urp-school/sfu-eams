<#include "/templates/head.ftl"/>
<body >
  <table id="examActivityBar" width="100%"></table>
  <script>
     var bar=new ToolBar('examActivityBar','考试冲突学生名单(${stds?size})',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="action.print"/>","print()");
 </script>

 <@table.table width="100%" id="listTable">
    <@table.thead>
      <@table.selectAllTd id="stdId"/>
      <@table.td width="8%" name="attr.stdNo"/>
      <@table.td width="10%" name="attr.personName"/>
      <@table.td width="8%" text="院系"/>
      <@table.td width="20%" text="专业"/>
    </@>
    <@table.tbody datas=stds;std>
      <@table.selectTd id="stdId" value="${std.id}"/>
      <td>${std.code}</td>
      <td><@i18nName std/></td>
      <td><@i18nName std.department/></td>
      <td><@i18nName std.firstMajor?if_exists/></td>
    </@>
  </@>
</body>
<#include "/templates/foot.ftl"/> 