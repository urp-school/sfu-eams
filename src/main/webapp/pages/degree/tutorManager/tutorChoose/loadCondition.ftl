<#include "/templates/head.ftl"/>
<BODY>
    <table id="tutorChoose" width="100%"></table>
  <table width="100%" class="frameTable">
  <form name='tutorManagerForm' method="post" action="" onsubmit="return false;">
  	<input type="hidden" name="doMethod" value="list">
    <tr>
    	<td width="20%" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable">
					<tr><td align="center" colspan="2">查询条件</td>
					<tr>
						<td width="30%">教师工号</td>
						<td><input type="text" name="tutorApply.teacher.code" maxlength="32" style="width:100%"></td>
					</tr>
					<tr>
						<td>教师姓名</td>
						<td><input type="text" name="tutorApply.teacher.name" style="width:100%" maxlength="20"></td>
					</tr>
					<tr>
						<td><@msg.message key="entity.college"/></td>
						<td><@htm.i18nSelect datas=departments?sort_by(["code"]) selected="" name="tutorApply.teacher.department.id" style="width:100%"><option value=""><@msg.message key="common.all"/></option></@></td>
					</tr>
					<tr>
						<td><@msg.message key="entity.tutorType"/></td>
						<td><@htm.i18nSelect datas=tutorTypes?sort_by(["code"]) selected="" name="tutorApply.tutorType.id" style="width:100%"><option value=""><@msg.message key="common.all"/></option></@></td>
					</tr>
					<tr>
						<td colspan="2">
							<fieldSet align="left">
								<legend style="font-size:12px;font-weight:bold"><@msg.message key="attr.requisitionTime"/></legend>
								<table width="100%" cellpadding="0">
									<tr>
										<td><@msg.message key="attr.from"/>：</td>
										<td><input type="text" name="applyTimeFrom" value="" onfocus="calendar()" style="width:110px" maxlength="10"/></td>
									</tr>
									<tr>
										<td><@msg.message key="attr.to"/>：</td>
										<td><input type="text" name="applyTimeTo" value="" onfocus="calendar()" style="width:110px" maxlength="10"/></td>
									</tr>
								</table>
							</fieldSet>
						</td>
					</tr>
					<tr>
						<td>是否通过</td>
						<td>
							<select name="tutorApply.isPass" style="width:100%">
								<option value="">全部</option>
								<option value="1">通过</option>
								<option value="0">不通过</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2"><input type="button" name="button1" value="查询" class="buttonStyle" onclick="search()"></td>
					</tr>
					</form> 
				</table>
    	</td>
    	<td valign="top">
    		<iframe id="tutorChooseFrame" name="tutorChooseFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0"  height="100%" width="100%">
		  	</iframe>
    	</td>
    </tr>
  <table>
    <script>
    var bar = new ToolBar("tutorChoose","导师遴选",null,true,true);
    bar.setMessage('<@getMessage/>');
    
    var form = document.tutorManagerForm;
    function setData(){
   		var parames = getInputParams(form);
   		tutorChooseFrame.setData(parames);
   }
    function search(){
    	form.action="tutorChoose.do?method=search";
    	form.target="tutorChooseFrame";
    	form.submit();
    }
   	search();
    </script>
 </body>
<#include "/templates/foot.ftl"/>