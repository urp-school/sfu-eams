<#include "/templates/head.ftl"/>
<BODY>
	<table id="toolBar" width="100%"></table>
    <@table.table width="100%" sortable="true" id="listTable">
       <@table.thead>
        <td></td>
        <@table.sortTd id="task.seqNo" name="attr.taskNo"/>
        <@table.sortTd id="task.course.code" name="attr.code"/>
        <@table.sortTd id="task.course.name" name="attr.courseName" width="20%"/>
        <td width="20%">实际安排</td>
        <td class="tableHeaderSort"  id="room.name" />教室名称</td>
        <@table.sortTd id="room.capacityOfCourse" name="attr.capacityOfCourse"/>
        <td class="tableHeaderSort" id="task.teachClass.stdCount" >人数</td>
        <td class="tableHeaderSort" id="task.teachClass.planStdCount">计划人数</td>
        <td class="tableHeaderSort" id="ratio">利用率</td>
       </@>
       <@table.tbody datas=utilizations;utilization>
         <#assign task=utilization[0]>
         <@table.selectTd id="taskId" type="radio" value="${task.id}"/>
         <td>${task.seqNo}</td>
         <td>${task.course.code}</td>
         <td><@i18nName task.course/></td>
         <td>${task.arrangeInfo.digest(task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],":day:units")}</td>
         <#assign room=utilization[1]>
         <td><@i18nName  room/></td>
         <td>${room.capacityOfCourse}</td>
         <td>${task.teachClass.stdCount}</td>
         <td>${task.teachClass.planStdCount}</td>
         <td>${(utilization[2]*100)?string("##.##")}%</td>
       </@>
    </@table.table>
  <form name="utilizationForm" method="post" action="manualArrange.do?method=roomUtilizations" onsubmit="return false;">
    <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}">
    <input type="hidden" name="task.calendar.id" value="${RequestParameters['calendar.id']}"> 
    <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}">
    <input type="hidden" name="pageNo" value="${pageNo}">
    <input type="hidden" name="pageSize" value="${pageSize}">
    <input type="hidden" name="ratio" value="${RequestParameters['ratio']}">
  </form>
 <script>
   var bar=new ToolBar("toolBar","教室利用率统计结果(不高于${RequestParameters['ratio']})",null,true,true);
   bar.addItem("重新设置利用率","setRatio()");
   bar.addItem("调整","adjust()");
   bar.addItem("刷新","refreshQuery()","refresh.gif");
   bar.addItem("<@msg.message key="action.print"/>","print()");
   bar.addBack("<@msg.message key="action.back"/>");
   function setRatio(){
      var form =document.utilizationForm;
      var ratio = prompt("请输入利用率的上限(小数形式,例如0.4标识40%)");
      if(!(/^\d*\.?\d*$/.test(ratio))){
         alert("输入格式不争取");
         return;
      }
      form['ratio'].value=ratio;
      form['pageNo'].value="1";
      form.submit();
   }
   //更换教室
    function adjust(){
      var taskId = getSelectId("taskId");
      if(""==taskId){alert("请选择一个");return;}
      var form = document.utilizationForm;
      transferParams(parent.document.taskForm,form,"task");
      var params =getInputParams(parent.document.taskForm);
      //alert(params)
      addInput(form,"params",params);
      
      form.action="manualArrange.do?method=manualArrange&task.id="+taskId;
      form.submit();
    }
 </script> 

</body>
<#include "/templates/foot.ftl"/> 
  