<#include "/templates/head.ftl"/>
<BODY>
<table id="degreeInfo" width="100%"></table>
<table  width="100%" class="frameTable"> 
	<tr>
		<td width="18%" class="frameTable_view" valign="top">
			<table width="100%">
				<form name="searchForm" target="thesisTopicOpenFrame" method="post">
		        <tr>
		            <td align="left" valign="bottom" colspan="2"><img src="${static_base}/images/action/info.gif" align="top"/>&nbsp;<B><@msg.message key="filed.stdSearch"/></B></td>
		       </tr>
		        <tr>
		            <td colspan="2" style="font-size:0px"><img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/></td>
		        </tr>
				<tr><td width="40%"><@msg.message key="attr.stdNo"/>：</td>
					<td><input type="text" name="degreeApply.student.code" style="width:100px" maxlength="32"/></td>
				</tr>
				<tr><td><@msg.message key="attr.personName"/>：</td>
					<td><input type="text" name="degreeApply.student.name" style="width:100px" maxlength="20"/></td>
				</tr>
				<tr><td><@msg.message key="filed.enrollYearAndSequence"/>：</td>
					<td><input type="text" name="degreeApply.student.enrollYear" style="width:100px" maxlength="7"/></td>
				</tr>
				<tr><td><@msg.message key="entity.studentType"/>：</td>
					<td><select id="stdTypeOfSpeciality" name="degreeApply.student.type.id" style="width:100px;">
		         	  <option value=""><@msg.message key="common.selectPlease"/>...</option>
					</td>
				</tr>
				<tr><td ><@msg.message key="entity.college"/>：</td>
		     		<td><select id="department" name="degreeApply.student.department.id" style="width:100px;">
		         	  <option value=""><@msg.message key="common.selectPlease"/>...</option>
		           </select> 
		           </td>
		        </tr>
		        <tr>
		     		<td><@msg.message key="entity.speciality"/>：</td>
		     		<td ><select id="speciality" name="degreeApply.student.firstMajor.id" style="width:100px;">
		         	  <option value=""><@msg.message key="common.selectPlease"/>...</option>
		           </select>
		     		</td>
		     	</tr>
		     	<tr>
		     		<td><@msg.message key="entity.specialityAspect"/>：</td>
		     		<td >
		           		<select id="specialityAspect" name="degreeApply.student.firstAspect.id" style="width:100px;">
		         	 	 <option value=""><@msg.message key="common.selectPlease"/>...</option>
		           	</select>
		     		</td>
		     	</tr>
		     	<tr>
		     		<td>是否通过：</td>
		     		<td ><select name="degreeApply.isAgree" style="width:100px;">
		         	  <option value="false">不通过</option>
		         	  <option value="true">通过</option>
		           </select>
		     		</td>
		     	</tr>
		     	<tr>
		     		<td>起始时间：</td>
		     		<td ><input type="text" name="startTime" value="" onfocus="calendar()" style="width:100px;" maxlength="10"/></td>
		     	</tr>
		     	<tr>
		     		<td>结束时间：</td>
		     		<td ><input type="text" name="endTime" value="" onfocus="calendar()" style="width:100px;" maxlength="10"/></td>
		     	</tr>
		     	<tr height="50px">
		     		<td align="center" colspan="2"><button onClick="search()" class="buttonStyle"><@msg.message key="system.button.query"/></button>
		     		</td>
		     	</tr>
		     	</form>
		</table>
		<#assign stdTypeNullable=true>
		<#include "/templates/stdTypeDepart3Select.ftl"/>
	 </td>
	 <td valign="top">
		<iframe id="thesisTopicOpenFrame" name="thesisTopicOpenFrame" 
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0" height="100%" width="100%">
	    </iframe> 
		</td>
	</tr>
</table>
<script>
   var bar = new ToolBar('degreeInfo','学位申请管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("导出毕业报盘数据","expGraduateInfo()")
  var manageMenu = bar.addMenu('导出学位报盘数据',null);
  manageMenu.addItem("博士数据","dataOut('D')");
  manageMenu.addItem("同等学力数据","dataOut('E')");
  manageMenu.addItem("硕士数据","dataOut('M')");
  manageMenu.addItem("专业硕士","dataOut('P')");
  manageMenu.addItem("高校教师","dataOut('T')");
  bar.addHelp("<@msg.message key="action.help"/>");
    var form = document.searchForm;
	var action="degreeApply.do";
	function search(pageNo,pageSize,orderBy){
	    var startTime = form.startTime.value;
	    if(!(""==startTime||/^\d{4}\-\d{2}\-\d{2}/.test(startTime))){
	    	alert("开始时间格式为yyyy-MM-dd");
	    	return;
	    }
	    var endTime = form.endTime.value;
	    if(!(""==endTime||/^\d{4}\-\d{2}\-\d{2}/.test(endTime))){
	    	alert("结束时间格式为yyyy-MM-dd");
	    	return;
	    }
	    form.action=action+"?method=stdList";
	    goToPage(form,pageNo,pageSize,orderBy);
	}
    search(1);
    
    function doBeforeExp(){
    	var condition="";
    	if(form['degreeApply.student.code'].value!=""){
    		condition+="\n学号="+form['degreeApply.student.code'].value;
    	}
    	if(form['degreeApply.student.name'].value!=""){
    		condition+="\n姓名="+form['degreeApply.student.name'].value;
    	}
    	if(form['degreeApply.student.enrollYear'].value!=""){
    	   condition+="\n入学年份="+form['degreeApply.student.enrollYear'].value;
    	}
    	if(form['degreeApply.student.type.id'].value!=""){
    	   condition+="\n学生类别="+DWRUtil.getText("stdTypeOfSpeciality");
    	}
    	if(form['degreeApply.student.department.id'].value!=""){
    		condition+="\n院系="+DWRUtil.getText("department");
    	}
    	if(form['degreeApply.student.firstMajor.id'].value!=""){
    		condition+="\n专业="+DWRUtil.getText("speciality");
    	}
    	if(form['degreeApply.student.firstAspect.id'].value!=""){
    		condition+="\n专业方向="+DWRUtil.getText("specialityAspect");
    	}
    	if(""!=condition){
    	   condition="你查询的条件:"+condition;
    	}
    	return condition;
    }
    function  dataOut(value){
    	if(form['degreeApply.student.type.id'].value==""){
    		alert("请选择一个学生类别。");
    		return;
    	}
        var condition= doBeforeExp();
    	var temp=""
    	if(value=="D"){
    	  temp="博士";
    	}else if(value=="M"){
    	  temp="硕士";
    	}else if(value=="E"){
    	  temp="同等学力";
    	}
    	condition+="\n你选中的模板是"+temp+"模板,你确认提交吗?"
    	if(confirm(condition)){
    		addInput(form,"fileName",form['degreeApply.student.enrollYear'].value+DWRUtil.getText("stdTypeOfSpeciality"));
    	    addInput(form,"template","degreeTemplate_"+value+".xls");
    	    addInput(form,"templateType","degreeTemplate");
    		form.action="degreeApply.do?method=export";
    		form.submit();
    	}
    }
    function expGraduateInfo(){
    	var condition = doBeforeExp();
    	condition+="\n你要导出条件对应的学生毕业数据吗?"
    	if(confirm(condition)){
    		addInput(form,"fileName","毕业报盘数据");
    	    addInput(form,"template","graduateInfo.xls");
    	    addInput(form,"templateType","degreeTemplate");
    		form.action="degreeApply.do?method=export";
    		form.submit();
    	}
    }
</script>
</body>
<#include "/templates/foot.ftl"/>