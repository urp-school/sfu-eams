<#include "/templates/head.ftl"/>
<BODY topmargin=0 leftmargin=0>	
  <table id="backBar" width="100%"></table>
	<script>
	   var bar = new ToolBar('backBar','毕业实习管理',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("实习单位统计","practiceDepartStat()");
	   bar.addItem("带队老师统计","practiceTeacherStat()");
	   bar.addItem("导入","importData()");
	   bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
	   bar.addHelp("<@msg.message key="action.help"/>");
   </script>
	<table   width="100%"  class="frameTable_title">
  <form name="searchForm" method="post" target="displayFrame" action="graduatePractice.do?method=index" onsubmit="return false;">
  <input type="hidden" name="graduatePractice.teachCalendar.id" value="${calendar.id}"/>
  		<tr class="frameTable_view" align="right">
  		  <td></td>
  		<#include "/pages/course/calendar.ftl"/>
		</tr>
	</table>
	<table width="100%" class="frameTable">
		<tr>
			<td style="width:160px" class="frameTable_view" valign="top">
				<table width="100%" class="searchTable">
				    <tr>
				      <td colspan="2" class="infoTitle" align="left" valign="bottom">
				       <img src="${static_base}/images/action/info.gif" align="top"/>
				          <B>毕业实习查询</B>
				      </td>
				    <tr>
				      <td colspan="2" style="font-size:0px">
				          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
				      </td>
				    </tr>
					<tr>
						<td style="width:50%"><@msg.message key="attr.stdNo"/></td>
						<td><input name="graduatePractice.student.code" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td style="width:50%"><@msg.message key="attr.personName"/></td>
						<td><input name="graduatePractice.student.name" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td style="width:50%"><@msg.message key="attr.enrollTurn"/></td>
						<td><input name="graduatePractice.student.enrollYear" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td><@msg.message key="entity.department"/></td>
						<td><@htm.i18nSelect datas=departmentList selected="" name="graduatePractice.student.department.id" style="width:100px">
						     <option value=""><@msg.message key="common.all"/></option>
						     </@>
						</td>
					</tr>
					<tr>
						<td style="width:50%">教师工号</td>
						<td><input name="graduatePractice.practiceteacher.code" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td style="width:50%">教师姓名</td>
						<td><input name="graduatePractice.practiceTeacher.name" value="" style="width:80px"/></td>
					</tr>
					<tr>
						<td>教师部门</td>
						<td><@htm.i18nSelect datas=departmentList selected="" name="graduatePractice.practiceTeacher.department.id" style="width:100px">
						     <option value=""><@msg.message key="common.all"/></option>
						     </@>
						</td>
					</tr>
					<tr>
						<td>专业类别:</td>
						<td>
							<select name="graduatePractice.majorType.id" onchange="changeSpecialityType(event)" style="width:100px">
						        <option value="1">第一专业</option>
						        <option value="2">第二专业</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<button onClick="search(1)"><@msg.message key="system.button.query"/></button>
						</td>
					</tr>
				</table>
				</form>
			</td>
			<td valign="top">
				<iframe name="displayFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<script language="javascript">
		var form = document.searchForm;
		var action="graduatePractice.do";
		var stdTypeId="";
		var year ="";
		var term="";
		function search(pageNo,pageSize,orderBy){
		    form.action=action+"?method=search";
		    goToPage(form,pageNo,pageSize,orderBy);
		}
		function practiceDepartStat(){
			var errors =validateCalendar(true,true,true);
			if(""!=errors&&!confirm(errors)){
				return;
			}
			form.action=action+"?method=practiceDepartStat";
			form.submit();
		}
		function practiceTeacherStat(){
			var errors =validateCalendar(true,true,true);
			if(""!=errors&&!confirm(errors)){
				return;
			}
			form.action=action+"?method=practiceTeacherStat";
			form.submit();
		}
		function validateCalendar(stdTypeNull,yearNull,termNull){
				var errors="";
				if(stdTypeNull){
					var stdType = document.getElementById("stdType");
					if(""==stdType.value){
						errors+="学生类别没有选择\n";
					}else{
						errors+="学生类别:"+DWRUtil.getText("stdType")+"\n";
					}
					stdTypeId=stdType.value;
				}
				if(yearNull){
					var year = document.getElementById("year");
					if(""==year.value){
						errors+="学年度没有选择\n";
					}else{
						errors+="学年度:"+DWRUtil.getText("year")+"\n";
					}
					year=year.value;
				}
				if(termNull){
					var term = document.getElementById("term");
					if(""==term.value){
						errors+="学期没有选择\n";
					}else{
						errors+="学期:"+DWRUtil.getText("term")+"\n";
					}
					term = term.value;
				}
				if(""!=errors){
					errors+="条件在左边的选择框中";
				}
				return errors;
			}
	    search(1);
	
	    function downloadTemplate(){
	      self.location="dataTemplate.do?method=download&document.id=10";
	    }
	    function importData(){
	       form.action="graduatePractice.do?method=importForm&templateDocumentId=10";
	       addInput(form,"importTitle","毕业实习数据上传")
	       form.submit();
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>
