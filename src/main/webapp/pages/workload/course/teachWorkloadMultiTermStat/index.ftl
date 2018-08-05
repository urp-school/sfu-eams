 <#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/Form.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar" width="100%"></table>
<table width="100%"class="frameTable">
	<tr>
	<td width="18%" valign="top" class="frameTable_view">
	<table width="100%" class="searchTable">
		<form id="multiCalendarForm" name="multiCalendarForm" method="post" action="" onsubmit="return false;">
			<input type="hidden" name="doQuery" value="doQuery">
			<tr>
				<td colspan="4" align="center"><@bean.message key="textEvaluation.selectCondition"/></td>
			</tr>
			<tr>
				<td><@msg.message key="entity.studentType"/></td>
				<td colspan="3"><select id="stdType" name="stdTypeId" style="width:100px">
				</select>
				</td>
			</tr>
			<tr>
				<td>开课院系</td>
				<td colspan="3"><@htm.i18nSelect datas=departments selected="" name="teachWorkload.college.id" style="width:100px"><OPTION VALUE="">全部</OPTION></@></td>
		    </tr>
		    <tr>
		    	<td>教师姓名</td>
		    	<td><input type="text" name="teachWorkload.teacherInfo.teacherName" value="${RequestParameters["teacherName"]?if_exists}" maxlength="100" style="width:100px"/></td>
		    </tr>
		    <tr>
		    	<td>课程序号</td>
		    	<td><input type="text" name="teachWorkload.courseSeq" value="${RequestParameters["courseSeqNum"]?if_exists}" maxlength="20" style="width:100px"/></td>
		    </tr>
		    <tr>
		    	<td>课程名称</td>
		    	<td><input type="text" name="teachWorkload.courseName" value="${RequestParameters["courseName"]?if_exists}" maxlength="100" style="width:100px"/></td>
		    </tr>
			<tr>
			<td colspan="2">
			    <fieldSet align=left style="width:150px"> 
		 		 <legend style="font-weight:bold;font-size:12px">从</legend>
		 		 <table width="100%"><tr>
		 		   <td>学年度:</td><td><select id="yearStart" name="yearStart"></select></td>
		 		   </tr><tr>
				   <td>学 期:</td><td><select id="termStart" name="termStart" ></select></td>
				</tr></table>
				</fieldSet>
			  </td>
			</tr>
			<tr>
			<td colspan="2">
			    <fieldSet align=left style="width:150px"> 
		 		 <legend style="font-weight:bold;font-size:12px;">到</legend>
		 		 <table width="100%"><tr>
		 		   <td>学年度:</td><td><select id="yearEnd" name="yearEnd"></select></td>
		 		   </tr><tr>
				   <td>学 期:</td><td><select id="termEnd" name="termEnd" ></select></td>
				</tr></table>
				</fieldSet>
			  </td>
			</tr>
		<tr>
			<td colspan="4"  align="center">
				<button name="buttonSubmit" onClick="search()" class="buttonStyle">查询</button>
			</td>
		</tr>
		</table>
		<script src='dwr/interface/calendarDAO.js'></script>
		<script src='scripts/common/CalendarSelect.js'></script>
		<script>
			var stdTypeArray = new Array();
    			<#list stdTypes?sort_by("code") as stdType>
    				stdTypeArray[${stdType_index}]={'id':'${stdType.id?if_exists}','name':'<@i18nName stdType/>'};
    			</#list>
    			DWREngine.setAsync(false);
    			var dd1 = new CalendarSelect("stdType","yearStart","termStart",false,false,false);
    			dd1.init(stdTypeArray);
    			var dd2 = new CalendarSelect("stdType","yearEnd","termEnd",false,false,false);
    			dd2.init(stdTypeArray);
    	</script>
		</form>
		</td>
		<td valign="top">
			<iframe name="departmentAndStdType" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar('backBar','工作量跨学期查询',null,true,true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("工作量汇总", "doChange()");
		
		var form = document.multiCalendarForm;
		function search() {
			form.action = "teachWorkloadMultiTermStat.do?method=search";
			form.target="departmentAndStdType";
			form.submit();
		}
		search();
	
		function doChange(pageNo, pageSize) {
			url="teachWorkloadMultiTermStat.do?method=mutiCalendarStat";
			if(null!=pageNo){
				url+="&pageNo="+pageNo;
			}else{
				url+="&pageNo=1";
			}
			if(null!=pageSize){
				url+="&pageSize="+pageSize;
			}else{
				url+="&pageSize=20";
			}
			form.action=url;
			form.target="departmentAndStdType";
			form.submit();
		}
	</script>
</body>
<#include "/templates/foot.ftl"/>