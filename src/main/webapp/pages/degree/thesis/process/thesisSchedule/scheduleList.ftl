<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
<@table.table width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="scheduleId"/>
      <@table.sortTd text="所在年级" id="schedule.enrollYear"/>
      <@table.sortTd text="学生类别" id="schedule.studentType.name"/>
      <@table.sortTd text="学制" id="schedule.studyLength"/>
      <@table.td text="步骤数"/>
      <@table.td text="进度备注"/>
    </@>
    <@table.tbody datas=schedules;schedule>
      <@table.selectTd id="scheduleId" value="${schedule.id}"/>
  		<td>${schedule.enrollYear?if_exists}</td>
  		<td>${schedule.studentType.name?if_exists}</td>
  		<td>${schedule.studyLength?if_exists}</td>
  		<td>${schedule.tacheSettings?size}</td>
  		<td>${(schedule.remark?html)?if_exists}</td>
      </@>
    </@>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
      <input type="hidden" id="keys" name="keys" value="schedule.studentType.name,schedule.enrollYear,schedule.studyLength,tache.name,planedTime,isTutorAffirm,settingRemark">
      <input type="hidden" id="titles" name="titles" value="学生类别,所在年级,学制,步骤名称,计划完成时间,是否需要导师确认,步骤备注说明">
    </form>
<script>
	   var bar = new ToolBar('backBar','进度列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("查看", "detailInfo()", "detail.gif");
	   bar.addItem("<@msg.message key="action.add"/>","add()");
	   bar.addItem("<@msg.message key="action.edit"/>","edit()");
	   bar.addItem("步骤维护","info()");
	   bar.addItem("复制","copySchedule()");
	   bar.addItem("<@msg.message key="action.delete"/>","multiIdAction('remove')");
	   var menu =bar.addMenu("高级..");
	   menu.addItem("<@msg.message key="action.export"/>","exportObject()");
	   menu.addItem("打印","print()");
	var form =document.actionForm;
	action="thesisSchedule.do";
	function info(){
	   form.action=action+"?method=info";
	   submitId(form,"scheduleId",false);
	}
	function add(){
	   form.action=action+"?method=edit";
	   form.submit();
	}
	function edit(){
	   form.action=action+"?method=edit";
	   submitId(form,"scheduleId",false);
	}
	function detailInfo(){
	  form.action=action+"?method=doDetailInfo";
	  submitId(form,"scheduleId",false);
	}
	function copySchedule(){
	   var ids= getSelectIds("scheduleId");
	   if(""==ids){alert("请选择一个多个进行复制");return;}
	   if(!isMultiId(ids)){
	      copy('single')
	   }else{
	      copy('batch');
	   }
	}
	function copy(copyType){
	  form.action=action+"?method=loadCopyPage";
	  addInput(form,"copyType",copyType,"hidden");
	  if("single"==copyType){
	  	submitId(form,"scheduleId",false);
	  }else{
	  	submitId(form,"scheduleId",true);
	  }
	}
	function exportObject(){
	   var scheduleIds =getSelectIds("scheduleId");
	   form.action =action+"?method=export&scheduleIds="+scheduleIds;
	   var temp ="";
	   if(""==scheduleIds){
	   	  temp+="所有";
	   }else{
	   	 temp+="所选择";
	   }
	   if(confirm("确认导出你"+temp+"的数据吗?")){
	   	  form.submit();
	   }
	}
	
    function multiIdAction(actionType){
       form.action=action+"?method="+actionType;
       setSearchParams(parent.document.searchForm,form);
       submitId(form,"scheduleId",true,null,"确认该操作？");
    }
	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
</script>
</body>
<#include "/templates/foot.ftl"/>
	