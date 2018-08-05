<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
 	<table id="bar"></table>
 	<table class="frameTable_title">
        <#-- 此form为公用form，不能随意改名，因为在其它页面中有引用 -->
 		<form method="post" action="" name="tuitionFeeForm" target="displayFrame" onsubmit="return false;">
 		<tr>
 			<td>&nbsp;</td>
 			<#include "/pages/course/calendar.ftl"/>
 			
 		</tr>
 	</table>
 	<#assign stdTypeList=calendarStdTypes/>
	<#include "/pages/components/initAspectSelectData.ftl"/>
 	<table class="frameTable">
 		<tr valign="top" height="90">
 			<td class="frameTable_view" width="20%">
 				<#include "searchForm.ftl"/>
	 		</td>
			<input type="hidden" name="tuition_exists" value=""/>
			<input type="hidden" name="remark" value=""/>
			<input type="hidden" name="tuitionIds" value=""/>
			<input type="hidden" name="stdIds" value=""/>
			<input type="hidden" name="fee" value=""/>
		</form>
 			<td>
 				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
 			</td>
	 	</tr>
 	</table>
	<script>
	    var bar=new ToolBar("bar", "学费初始化", null, true, true);
        bar.addItem("有学费查询", "resetSearch()");

        // 此form为公用form，不能随意改名，因为在其它页面中有引用
        var form = document.tuitionFeeForm;
        
        function search() {
        	form.action = "tuitionFee.do?method=search";
        	form.target = "displayFrame";
        	form.submit();
        }
        
        resetSearch();
        
        function resetSearch() {
        	form["tuition_exists"].value = "";
        	form["remark"].value = "";
        	form["tuitionIds"].value = "";
        	form["stdIds"].value = "";
        	form["fee"].value = "";
        	search();
        }
	</script> 
</body>   
<#include "/templates/foot.ftl"/> 