<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table width="100%" sortable="true" id="markStyle">
       	<@table.thead>
         	<@table.selectAllTd id="markStyleId"/>
         	<@table.sortTd text="代码" id="markStyle.code"/>
         	<@table.sortTd text="名称" id="markStyle.name"/>
	     	<@table.sortTd text="及格线" id="markStyle.passScore"/>
	   	</@>
	   	<@table.tbody datas=markStyles;markStyle>
         	<@table.selectTd id="markStyleId" value=markStyle.id/>
	    	<td>${markStyle.code}</td>
	    	<td><@i18nName (markStyle)?if_exists/></td>
	    	<td>${markStyle.passScore}</td>
	   	</@>
	</@>
	<#if !markStyles?exists || markStyles?size == 0><p>当没有可以添加的成绩记录方式，或已经全部设置了。</p></#if>
	<@htm.actionForm name="actionForm" action="markStyleConfig.do" entity="markStyle" onsubmit="return false;"></@>
	<script>
		var bar = new ToolBar("bar", "添加成绩记录方式设置", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("全部添加", "addAll()");
		bar.addItem("选中添加", "addChoice()");
		bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
		
		function addAll() {
			$("markStyleIdBox").click();
			addChoice();
		}
		
		function addChoice() {
			submitId(form, "markStyleId", true, "markStyleConfig.do?method=save");
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>