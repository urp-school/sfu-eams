<#include "/templates/head.ftl"/>
<BODY>
<table id="myBar" width="100%"></table>
  <@table.table id="listTable" width="100%" sortable="true">
     <@table.thead>
       <@table.selectAllTd id="signUpStdId"/>
       <@table.sortTd name="attr.stdNo" id="signUpStd.std.code"/>
       <@table.sortTd width="10%" name="attr.personName" id="signUpStd.std.name"/>
       <@table.td text="第一专业班级"/>
       <@table.sortTd text="平均绩点" id="signUpStd.GPA"/>
       <@table.sortTd text="服从调剂" id="signUpStd.isAdjustable"/>
       <@table.td text="录取方向"/>
     </@>
     <@table.tbody datas=signUpStds;signUpStd>
       <@table.selectTd id="signUpStdId" value=signUpStd.id/>
       <td><a href="studentDetailByManager.do?method=detail&stdId=${signUpStd.std.id}" title="查看学生基本信息">${signUpStd.std.code}</A></td>
       <td><@i18nName signUpStd.std/></td>
       <td><@i18nName signUpStd.std.firstMajorClass?if_exists/></td>
       <td>${signUpStd.GPA}</td>
       <td>${signUpStd.isAdjustable?string("是","否")}</td>
       <td><@i18nName (signUpStd.matriculated.aspect)?if_exists/></td>
     </@>
  </@>
  <@htm.actionForm name="actionForm" action="speciality2ndSignUpStudent.do" entity="signUpStd">
    <input type="hidden" name="signUpStd.setting.id" value="${RequestParameters['signUpStd.setting.id']}"/>
  </@>  
  <script>
     var bar =new ToolBar("myBar","录取学生列表",null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("批量取消","batchCancelMatriculate()");
     bar.addPrint("<@msg.message key="action.print"/>");
     bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
     
     function batchCancelMatriculate(){
        multiAction('batchCancelMatriculate','是否取消录取选定的学生?');
     }
  </script>
</body>
<#include "/templates/foot.ftl"/> 