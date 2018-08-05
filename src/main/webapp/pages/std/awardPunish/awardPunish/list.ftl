<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
<#if RequestParameters['isAward']=="1">
	<#include "../awardListTable.ftl"/>
	<#assign entityName="award"/>
<#else>
	<#include "../punishListTable.ftl"/>
	<#assign entityName="punish"/>
</#if>
<@htm.actionForm name="actionForm" action="awardPunish.do" entity=entityName>
  <input name="isAward" type="hidden" value="${RequestParameters['isAward']}"/>
</@>
</body>
<script>
  	var bar =new ToolBar("bar","<#if RequestParameters['isAward']=="1">奖励列表<#else>处分列表</#if>",null,true,true);
  	bar.setMessage('<@getMessage/>');
  	bar.addItem("<@msg.message key="action.new"/>","add()");
  	bar.addItem("<@msg.message key="action.edit"/>","edit()");
  	bar.addItem("<@msg.message key="action.delete"/>","remove()");
  	bar.addItem("<@msg.message key="action.export"/>", "exportData()");
  	bar.addPrint("<@msg.message key="action.print"/>");
  
  	function exportData(){
	    if (!confirm("是否导出查询条件内的所有数据?")) {
	    	return;
	    }
	    
	    <#if entityName?exists && entityName == "award">
			var titles = "学号,姓名,院系所,获奖名称,获奖级别,颁发时间,颁发部门,是否有效";
		    var keys = "std.code,std.name,std.department.name,name,type.name,presentOn,depart,isValid";
	    <#elseif entityName?exists && entityName == "punish">
			var titles = "学号,姓名,院系所,处分名称,处分类别,处分时间,部门,是否有效";
		    var keys = "std.code,std.name,std.department.name,name,type.name,presentOn,depart,isValid";
	    </#if>
	    
	    addInput(form, "keys", keys);
	    addInput(form, "titles", titles);
	    exportList();
  	}
</script>
<#include "/templates/foot.ftl"/>