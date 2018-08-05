<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
<body>
  <table id="campusTimeBar"></table>
  <script>
　　　var bar = new ToolBar("campusTimeBar",'${studentType.name} 学生在校日历列表',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem('<@bean.message key="action.add"/>',"javascript:operation('editOnCampusTime',false)",'new.gif');
     bar.addItem('<@bean.message key="action.modify"/>',"javascript:operation('editOnCampusTime',true)",'update.gif');
     bar.addItem('<@bean.message key="action.delete"/>',"javascript:operation('removeOnCampusTime',true)",'delete.gif');
     bar.addBack("<@msg.message key="action.back"/>");
  </script>
  <@table.table width="100%" >
    <@table.thead>
     <@table.td text=""  width="2%"/>
       <td width="10%"><@bean.message key="entity.studentType"/></td>
       <td width="10%"><@bean.message key="attr.enrollTurn"/></td>
       <td width="20%">入学学期</td>
       <td width="25%">毕业学期</td>
     </@>
     <@table.tbody datas=onCampusTimes;onCampusTime>
       <@table.selectTd   type="radio" id="onCampusTimeId" value="${onCampusTime.id}"/>
      <td>&nbsp;<@i18nName onCampusTime.stdType/></td>
      <td>&nbsp;${onCampusTime.enrollTurn}</td>
      <td>&nbsp;${onCampusTime.enrollCalendar.year}&nbsp;${onCampusTime.enrollCalendar.term}</td>
      <td>&nbsp;${onCampusTime.graduateCalendar?if_exists.year?if_exists}&nbsp;${onCampusTime.graduateCalendar?if_exists.term?if_exists}</td>
     </@>
  </@>
     <form name="onCampusTimeListForm" method="post" action="calendar.do" onsubmit="return false;">
     <input type="hidden" name="studentType.id" value="${RequestParameters['studentType.id']}"/>
     </form>
  <script>
  function operation(method,needId){
    var onCampusTimeId ="";
    if(needId){
       onCampusTimeId = getRadioValue(document.getElementsByName('onCampusTimeId'));
       if(onCampusTimeId=="") {alert("请选择一个");return;}
    }
    document.onCampusTimeListForm.action+="?method=" + method+ "&onCampusTime.id=" + onCampusTimeId;
    if(method.indexOf('remove')!=-1 && !confirm("删除学生在校日历，确保没有其他基于此日历的数据，否则将不能删除！确认删除")) return;
    document.onCampusTimeListForm.submit();
  }
  function noNeedCreate(){
   <#if superStdType?exists>
     alert("没有制定学生的在校日历!\n如果不制定，将按照[${superType.name}]的各个所在年级依次使用。");
   </#if>
  }
  noNeedCreate();
  </script>
</body>
<#include "/templates/foot.ftl"/>