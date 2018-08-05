<#include "/templates/head.ftl"/>
<body >
  <table id="myBar"></table>
  <@table.table id="listTable" sortable="true">
      <@table.thead>
          <@table.selectAllTd id="calendarId"/>
          <@table.td name="attr.year2year" />
          <@table.td name="attr.term" />
      </@>
      <@table.tbody datas=calendars;calendar>
          <@table.selectTd id="calendarId" value=calendar.id/>
          <td>${calendar.year}</td>
          <td>${calendar.term}</td>
          </@>
  </@>
  <form name="actionForm" action="workloadOpenSwitch.do?method=setStatus" method="post">
       <input name="params" type="hidden" value="&orderBy=switch.teachCalendar.start desc&switch.teachCalendar.studentType.id=${RequestParameters['switch.teachCalendar.studentType.id']}"/>
  </form>
  <script>
    function openForCalendar(isOpen){
       var form =document.actionForm;
       addInput(form,"isOpen",isOpen);
       submitId(form,"calendarId",true);
    }
   var bar = new ToolBar('myBar','没有设置开关的学年学期',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("设置为开放","openForCalendar(1)");
   bar.addItem("设置为关闭","openForCalendar(0)");
  </script>
</body>
<#include "/templates/foot.ftl"/>