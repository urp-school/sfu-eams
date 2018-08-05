<#include "/templates/head.ftl"/>
<body >
  <table id="myBar"></table>
  <@table.table id="listTable" sortable="true">
      <@table.thead>
          <@table.selectAllTd id="switchId"/>
          <@table.td name="attr.year2year" />
          <@table.td name="attr.term" />
          <@table.td text="开关" />
          <@table.td text="开放时间" />
      </@>
      <@table.tbody datas=switchs;switch>
          <@table.selectTd id="switchId" value=switch.id/>
          <td>${switch.teachCalendar.year}</td>
          <td>${switch.teachCalendar.term}</td>
          <td <#if !switch.isOpen> style="color:red"</#if>>${switch.isOpen?string("开放","关闭")}</td>
          <td><#if switch.isOpen>${(switch.openTime?string("yyyy-MM-dd"))?default("")}</#if></td>
          </@>
  </@>
  <@htm.actionForm name="actionForm" action="workloadOpenSwitch.do" entity="switch">
  </@>
  <script>
   function otherCalendar(){
      self.location="?method=noSwitchCalendarList&switch.teachCalendar.studentType.id=${RequestParameters['switch.teachCalendar.studentType.id']}&orderBy=calendar.start desc";
   }
   function openForCalendar(isOpen){
   addInput(document.actionForm,"isOpen",isOpen);
   multiAction("setStatus");
   }
   var bar = new ToolBar('myBar','工作量查询开关列表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("设置为开放","openForCalendar(1)");
   bar.addItem("设置为关闭","openForCalendar(0)");
   bar.addItem("其他学期",otherCalendar);   
  </script>
</body>
<#include "/templates/foot.ftl"/>