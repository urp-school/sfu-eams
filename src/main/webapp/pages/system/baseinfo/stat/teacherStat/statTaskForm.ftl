<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','授课情况',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addPrint("<@msg.message key="action.print"/>");
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table align="center" width="100%" class="listTable">
	<form name="statForm" method="post" target="displayFrame" action="" onsubmit="return false;">
	<tr class="darkColumn">
		<td colspan="2">学生类别:
		<select id="stdType" name="studentType.id" style="width:100px;"></select>
	    从:<select id="startYear" name="startYear" style="width:100px;"></select><select id="startTerm" name="startTerm" style="width:60px;"></select>
	    到:<select id="endYear" name="endYear" style="width:100px;"></select><select id="endTerm" name="endTerm"  style="width:60px;"></select>
		<button name="buttonName" style="buttonStyle" onclick="stat(this.form)">统计</button>
	  </td>
	</tr>
	</form>
	<tr>
		<td colspan="2" ><iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
	</tr>
	</table>
	<script src='dwr/interface/calendarDAO.js'></script>
	<script src='scripts/common/CalendarSelect.js'></script>
	<script>
			var stdTypeArray = new Array();
    			<#list stdTypeList as stdType>
    				stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    			</#list>
    			DWREngine.setAsync(false);
    			var dd1 = new CalendarSelect("stdType","startYear","startTerm",false,false,false);
    			dd1.init(stdTypeArray);
    			var dd2 = new CalendarSelect("stdType","endYear","endTerm",false,false,false);
    			dd2.init(stdTypeArray);
    			
    		function stat(form){
    			form.action="teacherStat.do?method=statTask"
    			form.submit();
    		}
    		stat(document.statForm);
    </script>
    	
</body>
<#include "/templates/foot.ftl"/>
