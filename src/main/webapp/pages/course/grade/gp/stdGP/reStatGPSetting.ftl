<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="gradeListBar" width="100%"></table>
    <@table.table width="100%" id="listTable">
     <@table.thead>
       <@table.selectAllTd id="calendarId"/>
       <@table.td width="15%" name="entity.studentType"/>
       <@table.td width="10%" name="attr.year2year"/>
       <@table.td width="10%" name="attr.term"/>
       <@table.td width="15%" text="起始日期" />
       <@table.td width="15%" text="结束日期"/>
      </@>
    <@table.tbody datas=calendars;calendar>
     <@table.selectTd id="calendarId" type="checkbox" value="${calendar.id}"/>
      <td>&nbsp;<@i18nName calendar.studentType/></td>
      <td>&nbsp;${calendar.year}</td>
       <td>&nbsp;${calendar.term}</td>
       <td>&nbsp;${calendar.start?string("yyyy-MM-dd")}</td>
       <td>&nbsp;${calendar.finish?string("yyyy-MM-dd")}</td>
     </@>
     </@>
  <form name="actionForm" method="post" action="" onsubmit="return false;">
     <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['std.type.id']}"/>
  </form>
  <script>
    var bar = new ToolBar("gradeListBar","当前学期和历史学期",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("进行绩点重新计算","reStatGP()");
    bar.addPrint("<@msg.message key="action.print"/>");
    
  function reStatGP(){
    var form=document.actionForm;
    form.action="stdGP.do?method=reStatGP";
    form.target="_parent";
    submitId(form,"calendarId",true);
  }
  </script>
</body>
<#include "/templates/foot.ftl"/>
