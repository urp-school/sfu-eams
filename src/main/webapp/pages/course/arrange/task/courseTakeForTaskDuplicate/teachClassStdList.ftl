<#include "/templates/head.ftl"/>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<div id="teachClassStdListDiv" style="display:block">
<table id="teachClassStdBar"></table>
 <@table.table width="100%" id="listTable">
    <@table.thead>
      <@table.selectAllTd id="courseTakeId"/>
      <@table.td name="attr.stdNo" id="take.student.code" width="8%"/>
      <@table.td name="attr.personName" width="7%" id="take.student.name"/>
      <@table.td width="8%" name="entity.studentType" id="take.student.type.name"/>
      <@table.td width="4%" name="attr.credit" id="task.course.credits"/>
      <@table.td width="6%" text="修读类别" id="take.courseTakeType"/>
      <@table.td width="15%" name="entity.speciality" id="take.student.firstMajor.name"/>
      <@table.td width="20%" name="entity.specialityAspect" id="take.student.firstAspect.name"/>
    </@>
    <@table.tbody datas=task.teachClass.courseTakes?sort_by(["student","code"]);take>
      <@table.selectTd type="checkbox" id="courseTakeId" value="${take.id}"/>
      <td>${take.student.code}</td>
      <td><@i18nName take.student/></td>
      <td><@i18nName take.student.type/></td>
      <td>${task.course.credits}</td>
      <td><@i18nName take.courseTakeType/></td>
      <td><@i18nName take.student.firstMajor?if_exists/></td>
      <td><@i18nName take.student.firstAspect?if_exists/></td>
    </@>
    </@>
    <form name="stdListForm" action="" method="post" onsubmit="return false;">
      <input name="taskId" value="${task.id}" type="hidden"/>
    </form>
    <form name="tmpForm" method="post" action="courseTakeForTaskDuplicate.do?method=stdList&taskId=${task.id}&pageSize=70">
      <input name="adminClassName" type="hidden" value=""/>
    </form>
</div>
   <#include "addStdForm.ftl"/>
  <script>
   var bar = new ToolBar('teachClassStdBar','<@msg.message key="attr.taskNo"/>［${task.seqNo}］，学生名单（${task.teachClass.courseTakes?size}）人（实践）',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("发送消息","sendMessage()","inbox.gif");
   bar.addItem("打印点名册","printStdListForDuty();",'print.gif');
   bar.addItem("退课","withdraw()",'delete.gif');
   bar.addItem("<@msg.message key="action.add"/>","javascript:displayDiv('addStdFormDiv')",'new.gif');
   var m=bar.addMenu("选择...")
   m.addItem("单号","selectOddStdNo()");
   m.addItem("双号","selectEvenStdNo()");
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   
  var divs=new Array();
  divs[0]="addStdFormDiv";
  divs[1]="teachClassStdListDiv";
  function displayDiv(divId){
     for(var i=0;i<2;i++){
        if(divs[i]==divId){
           document.getElementById(divs[i]).style.display="block";
        }else{
           document.getElementById(divs[i]).style.display="none";
        }
     }
     if(stdListFrame.location=="about:blank"){
         <#assign adminClassName=(task.teachClass.adminClasses?first.name)?default('')>
         addInput(document.tmpForm,"adminClassName","${adminClassName}");
         addInput(document.tmpForm,"orderBy","std.code asc");
         document.tmpForm.target="stdListFrame";
         document.tmpForm.submit();
     }else{
         var targWin = parent.document.all[self.name];
         if(targWin != null) {
            var HeightValue =document.body.scrollHeight;
            targWin.style.pixelHeight = HeightValue;
         }
     }
  }
  function withdraw(){
        submitId(document.stdListForm, "courseTakeId", true, "courseTakeForTaskDuplicate.do?method=withdraw&log=1","确认你所的学生要退课？");
    }
  function printStdListForDuty(){
     window.open("teachTaskCollege.do?method=printStdListForDuty&teachTaskIds=${task.id}");
  }
  function sendMessage(){
     var courseTakeIds = getSelectIds("courseTakeId");
     if(""==courseTakeIds){alert("请选择上课学生");return;}
     window.open("courseTakeForTaskDuplicate.do?method=sendMessage&courseTakeIds="+courseTakeIds);
  }
  function selectOddStdNo(){
    t=document.getElementById("listTable");
   rows=t.rows;
   for(var i=1;i<rows.length;i++){
       if(parseInt(rows[i].cells[1].innerHTML)%2==1){
         rows[i].cells[0].childNodes[0].checked=true;
       }
      }
  }
  
  function selectEvenStdNo(){
    t=document.getElementById("listTable");
   rows=t.rows;
   for(var i=1;i<rows.length;i++){
       if(parseInt(rows[i].cells[1].innerHTML)%2==0){
         rows[i].cells[0].childNodes[0].checked=true;
       }
      }
  }
  </script>
</body> 
<#include "/templates/foot.ftl"/> 