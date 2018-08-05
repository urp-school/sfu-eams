<#include "/templates/head.ftl"/>
<body > 
<table id="bar1"></table>
<table width="100%" align="center" class="infoTable">
	<tr>
		<td class="title" style="width:25%">学生类别 ：</td>
		<td>${schedule?if_exists.studentType?if_exists.name?if_exists}</td>
		<td class="title" style="width:25%">所在年级 ：</td>
		<td>${schedule?if_exists.enrollYear?if_exists}</td>
	</tr>
   <tr>
     <td class="title">学制：</td>
     <td>${schedule?if_exists.studyLength?if_exists}年 </td>
     <td class="title"></td>
     <td></td>
   </tr>
   </table>
   <table id="bar2"></table>
<@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="tacheSettingId"/>
      <@table.td  text="步骤名称"/>
      <@table.td  text="计划完成时间" />
      <@table.td  text="是否需要导师确认" />
      <@table.td  text="步骤备注"/>
    </@>
    <@table.tbody datas=schedule.tacheSettings?sort_by(["tache","code"]);tacheSetting>
      <@table.selectTd id="tacheSettingId" value="${tacheSetting.id}"/>
  		<td>${(tacheSetting.tache.name)?if_exists}</td>
  		<td>${tacheSetting.planedTimeOn?default("")?string("yyyy-MM-dd")}</td>
  		<td>${tacheSetting.isTutorAffirm?string("是","否")}</td>
  		<td>${tacheSetting.settingRemark?if_exists}</td>
      </@>
 </@>
 <form name="actionForm" method="post" action="" onsubmit="return false;">
   <input name="scheduleId" value="${(schedule.id)?if_exists}" type="hidden"/>
 </form>
 <script>
    var bar = new ToolBar("bar1","进度基本信息",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addPrint("<@msg.message key="action.print"/>");
    bar.addItem("<@msg.message key="action.back"/>","backList()","backward.gif");
    var tacheBar = new ToolBar("bar2","进度步骤信息维护",null,true,true);
    tacheBar.addItem("<@msg.message key="action.add"/>","addTacheInfo()");
    tacheBar.addItem("<@msg.message key="action.edit"/>","editTacheInfo()");
    tacheBar.addItem("批量修改","batchEditTacheInfo()");
    tacheBar.addItem("<@msg.message key="action.delete"/>","deleteTacheInfo()");
    var action="thesisSchedule.do";
    var form=document.actionForm;
    function addTacheInfo(){
    	form.action =action+"?method=editTache";
    	form.submit();
    }
    function editTacheInfo(){
       	form.action =action+"?method=editTache";
       	submitId(form,"tacheSettingId",false);
    }
    function batchEditTacheInfo(){
    	form.action =action+"?method=batchEditTache";
    	addInput(form,"batch","load");
       	submitId(form,"tacheSettingId",true);
    }
    function deleteTacheInfo(){
    	form.action=action+"?method=removeTache";
       	submitId(form,"tacheSettingId",true,null,"确认该操作？");
    }
    function backList(){
    	parent.search(1);
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>