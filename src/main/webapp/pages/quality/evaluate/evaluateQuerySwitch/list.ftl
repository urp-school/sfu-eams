<#include "/templates/head.ftl"/>
 <BODY>	
 <table id="backBar" width="100%"></table>
 <@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="switchId"/>
      <@table.sortTd  text="学生类别" id="switch.teachCalendar.studentType.name"/>
      <@table.sortTd  text="学年度" id="switch.teachCalendar.year"/>
      <@table.sortTd  text="学期"  id="switch.teachCalendar.term"/>
      <@table.sortTd  text="开放状态"  id="switch.isOpen"/>
      <@table.sortTd  text="上次开放时间"  id="switch.openAt"/>
    </@>
    <@table.tbody datas=querySwitchs?if_exists; switch>
    	<@table.selectTd id="switchId" value=switch.id/>
    	<td>${switch.teachCalendar.studentType.name}</td>
    	<td>${switch.teachCalendar.year}</td>
    	<td>${switch.teachCalendar.term}</td>
    	<td>${switch.isOpen?string("开放","关闭")}</td>
    	<td><#if switch.openAt?exists>${switch.openAt?string("yyyy-MM-dd-HH:mm")}</#if></td>
    </@>
<form name="listForm" method="post" action="" onsubmit="return false;">
</form>
</@>
<script>
	var bar = new ToolBar('backBar','评教开关列表',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<#if "true"==isOpen>关闭<#else>开放</#if>","save(<#if "true"==isOpen>'false'<#else>'true'</#if>)");
	var form = document.listForm;
	function save(value){
	 addInput(form,"isOpen",value)
	 var flag = "select"
	 var idSeq = getSelectIds("switchId");
	 if(""==idSeq){
	 	if(confirm("你确定要开放你所管理的评教查询开关嘛?")){
	 		flag="all";
	 	}else{
	 	   return;
	 	}
	 }
	 if("select"==flag){
	 	addInput(form,"idSeq",idSeq);
	 }
	 addInput(form,"isAll",flag);
	 form.action="evaluateQuerySwitch.do?method=operate";
	 setSearchParams(parent.form,form,"switch");
	 if("all"==flag){
	 	transferParams(parent.form,form,"switch");
	 }
	 form.submit();
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