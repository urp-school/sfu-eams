<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0">
<table id="backBar" width="100%"></table>
<script>
   var bar = new ToolBar('backBar','学生网上评教',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table align="center" width="100%">
	<tr>
		<td>
			<table class="listTable" align="center" width="100%">
		<form name="statForm" method="post" target="displayFrame" action="" onsubmit="return false;">
		<tr>
			<td colspan="4" class="darkColumn" align="center">统计条件</td>
		</tr>
		<tr>
			<td class="grayStyle" id="f_start">起始分数<font color="red">*</font></td>
			<td><input type="text" name="start" value="70" style="width:100%" maxlength="5"/></td>
			<td class="grayStyle" id="f_span">分数跨度<font color="red">*</font></td>
			<td><input type="text" name="span" value="10" style="width:100%" maxlength="5"/></td>
		</tr>
		<tr>
			<td class="grayStyle" id="f_count">显示数目<font color="red">*</font></td>
			<td><input type="text" name="count" value="2" style="width:100%" maxlength="2"/></td>
			<td class="grayStyle" id="f_excellentMark">优良分数<font color="red">*</font></td>
			<td>>=<input type="text" name="excellentMark" value="80" style="width:80%" maxlength="5"/></td>
		</tr>
		<tr>
			<td class="grayStyle">学年度学期选择</td>
			<td colspan="3">
				学生类别:<select id="stdType" name="stdTypeId" style="width:100px;"></select><br>
				起始学年度学期:<select id="yearStart" name="yearStart" style="width:100px;"></select><select id="termStart" name="termStart" style="width:50px;"></select><br>
				结束学年度学期:<select id="yearEnd" name="yearEnd" style="width:100px;"></select><select id="termEnd" name="termEnd"  style="width:50px;"></select><br>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center" class="darkColumn"><button name="buttonName" style="buttonStyle" onclick="stat(this.form)">统计</button></td>
		</tr>
		</form>
		</table>
		</td>
	</tr>
	<tr>
		<td><iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe></td>
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
    			var dd1 = new CalendarSelect("stdType","yearStart","termStart",false,false,false);
    			dd1.init(stdTypeArray);
    			var dd2 = new CalendarSelect("stdType","yearEnd","termEnd",false,false,false);
    			dd2.init(stdTypeArray);
    		function stat(form){
    			var a_fields = {
    				'start':{'l':'起始分数', 'r':true, 't':'f_start', 'f':'integer'},
    				'span':{'l':'分数跨度', 'r':true, 't':'f_span', 'f':'integer'},
    				'count':{'l':'显示数目', 'r':true, 't':'f_count', 'f':'integer'},
    				'excellentMark':{'l':'优良分数', 'r':true, 't':'f_excellentMark', 'f':'integer'}
    			};
    		
    			var v = new validator(form, a_fields, null);
    			if (v.exec()) {
	    			form.action="teachQualityStatisticAction.do?method=statEvaluatePage"
	    			form.submit();
    			}
    		}
    </script>
    	
</body>
<#include "/templates/foot.ftl"/>
