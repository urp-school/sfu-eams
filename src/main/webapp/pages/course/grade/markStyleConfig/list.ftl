<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table width="100%" sortable="true" id="defaultConfig">
       	<@table.thead>
         	<@table.selectAllTd id="defaultConfigId"/>
         	<@table.sortTd text="代码" id="defaultConfig.markStyle.code"/>
         	<@table.sortTd text="名称" id="defaultConfig.markStyle.name"/>
	     	<@table.sortTd text="及格线" id="defaultConfig.markStyle.passScore"/>
	   	</@>
	   	<@table.tbody datas=defaultConfigs;defaultConfig>
         	<@table.selectTd id="defaultConfigId" value=defaultConfig.id/>
	    	<td>${defaultConfig.markStyle.code}</td>
	    	<td><A href="markStyleConfig.do?method=info&defaultConfigId=${defaultConfig.id}"><@i18nName (defaultConfig.markStyle)?if_exists/></A></td>
	    	<td>${defaultConfig.markStyle.passScore}</td>
	   	</@>
	</@>
	<@htm.actionForm name="actionForm" action="markStyleConfig.do" entity="defaultConfig" onsubmit="return false;"></@>
	<script>
		var bar = new ToolBar("bar", "成绩记录方式设置结果", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("添加设置", "addConfig()");
		bar.addItem("取消设置", "remove()");
		bar.addItem("详细配置", "singleAction('setting')");
		bar.addItem("查看配置", "info()", "detail.gif");
		
		form.target = "_self";
		function addConfig() {
			form.action = "markStyleConfig.do?method=addConfig";
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>