<#include "/templates/head.ftl"/>
<body >
  <table id="examActivityBar" width="100%"></table>
  <@table.table  id="listTable" sortable="true" width="100%">
    <@table.thead>
      <@table.selectAllTd id="examTakeId"/>
      <@table.sortTd id="take.std.code" name="attr.stdNo" width="10%"/>
      <@table.sortTd id="take.std.name" name="attr.personName" width="8%"/>
      <@table.sortTd id="take.task.course.code" width="8%" name="attr.courseNo"/>
      <@table.sortTd id="take.task.seqNo" width="8%" name="attr.taskNo"/>
      <@table.td width="8%" name="entity.teacher"/>
      <td id="take.courseTake.task.course.name" class="tableHeaderSort" width="20%"><@bean.message key="attr.courseName"/></td>
      <td width="12%">考试日期</td>
      <td id="take.activity.room.name" class="tableHeaderSort" width="8%">考试地点</td>
      <td id="take.examStatus.name" class="tableHeaderSort" width="8%">考试情况</td>
      <td id="take.delayReason.id" class="tableHeaderSort" width="8%">缓考原因</td>
    </@>
    <@table.tbody datas=takes;take>
      <@table.selectTd id="examTakeId" value="${take.id}" type="checkbox"/>
      <td>${take.std.code}</td>
      <td><@i18nName take.std/></td>
      <td>${take.task.course.code}</td>
      <td>${take.task.seqNo?if_exists}</td>
      <td><@getTeacherNames take.task.arrangeInfo.teachers/></td>
      <td><@i18nName take.task.course/></td>
      <#if take.activity?exists>
      <td>${(take.activity.time.firstDay)?string("yy-MM-dd")} ${take.activity.digest(Session["org.apache.struts.action.LOCALE"],Request["org.apache.struts.action.MESSAGE"],":time")}</td>
      <#else><td></td></#if>
      <td><@i18nName (take.activity.room)?if_exists/></td>
      <td><@i18nName take.examStatus/></td>
      <td><@i18nName take.delayReason?if_exists/></td>
    </@>
   </@>
	<form name="actionForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
        <input type="hidden" name="take.examType.id" value="${RequestParameters['take.examType.id']}"/>
        <input type="hidden" name="examTakeIds" value=""/>
	</form>
    <script>
     var bar=new ToolBar('examActivityBar','排考结果列表',null,true,true);
     bar.setMessage('<@getMessage/>');
     bar.addItem("查询导出","exportData()");
     var mm =bar.addMenu("设置考试情况为..",null);
     <#list examStatuses as examStatus>
     mm.addItem("<@i18nName examStatus/>","editExamStatus(${examStatus.id})");
     </#list>
     <#if RequestParameters['take.examStatus.id']?default("")=='4'>
     bar.addItem("改为正常考试","multiIdAction('&method=checkApplies&agree=0')");
     bar.addItem("通过申请","multiIdAction('&method=checkApplies&agree=1')");
     </#if>
     bar.addItem("增加考试记录","add()");
     bar.addItem("<@msg.message key="action.delete"/>","multiIdAction('&method=remove')");
    
    var form =document.actionForm;
    resetHidden();
    function resetHidden() {
        form["examTakeIds"].value = "";
    }
    
    function exportData(){
        resetHidden();
       if(${totalSize}>10000){alert("记录数超过一万,系统暂时不能导出");return;}
       if(!confirm("是否导出全部${totalSize}条记录?"))return;
       var form=parent.document.takeSearchForm;
       addInput(form,"keys","std.code,std.name,task.course.code,task.seqNo,task.course.name,examType.name,task.arrangeInfo.teachDepart.name,activity.time.firstDay,activity.time.timeZone,activity.room.name,task.arrangeInfo.teacherCodes,task.arrangeInfo.teacherNames,task.teachClass.name");
       addInput(form,"titles","<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,<@msg.message key="attr.courseNo"/>,<@msg.message key="attr.taskNo"/>,<@msg.message key="attr.courseName"/>,考试类型,开课院系,考试日期,考试时间,考试地点,教师职工号,授课教师,教学班名称");
       form.action="examTake.do?method=export";
       form.submit();
    }
	function multiIdAction(extra){
       var takeIds = getSelectIds("examTakeId");
       if(""==takeIds) {alert("请选择学生");return;}
       if(confirm("确认该操作?")){
	       var params=getInputParams(parent.document.takeSearchForm,null,false);
	       addParamsInput(form,params);
	       form.action = "examTake.do?" + extra;
	       form["examTakeIds"].value = takeIds;
	       form.submit();
       }
    }
    function editExamStatus(examStatusId){
        resetHidden();
        multiIdAction("&method=editExamStatus&examTake.examStatus.id="+examStatusId);
    }
    function add(){
        resetHidden();
        form.action="examTake.do?method=edit";
        form.submit();
	}
	</script>
</body> 
<#include "/templates/foot.ftl"/> 