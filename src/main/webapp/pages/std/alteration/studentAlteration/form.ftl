<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<body>
 <table id="bar"></table>
 <table width="90%" class="formTable" align="center" style="padding:0px;bolder-spacing:0px;">
	<form name="alterationForm" action="studentAlteration.do?method=save" method="post" onsubmit="return false;">
	<input name="alteration.id" type="hidden" value="${alteration.id?if_exists}"/>
	<@searchParams/>
	<tr> 
   	  <td align="center" class="darkColumn"><B>学籍变动详细信息</B></td>
  	</tr>
  	<tr>
  		<td style="text-align:center;padding:0px;bolder-spacing:0px">
			<table width="100%" class="formTable" align="center" style="border-bottom-width:0px">
				<tr>
					<td class="title" width="20%" id="f_stdId"><@msg.message key="attr.stdNo"/><font color="red">*</font></td>		
					<td width="30%" >
						<input name="alteration.std.id" id="stdId" value="${(alteration.std.id)?if_exists}" type="hidden"/>
						<#if (alteration.std.id)?exists>${alteration.std.code}<#else>
						<input type="text" style="width:50%" id="std.code" name="std.code" onchange="getStd()" maxlength="32"/>&nbsp;
						<button onclick="getStd();">查找学生</button>
						</#if>
					</td>
					<td class="title" width="20%"><@msg.message key="attr.personName"/></td>
					<td width="30%" id="name"><@i18nName (alteration.std)?if_exists/></td>
				</tr>
				<tr>
					<td class="title" id="f_alterDate" width="20%">学籍变动日期<font color="red">*</font></td>
					<td ><input type="text" style="width:100%;" name="alteration.alterBeginOn" onfocus="calendar()" size="10" maxlength="10" value='<#if alteration.alterBeginOn?exists>${alteration.alterBeginOn?string("yyyy-MM-dd")}</#if>'/></td>	
					<td class="title" width="20%">变动截止日期</td>
					<td ><input type="text" style="width:100%;" name="alteration.alterEndOn" onfocus="calendar()" size="10" maxlength="10" value='<#if alteration.alterEndOn?exists>${alteration.alterEndOn?string("yyyy-MM-dd")}</#if>'/></td>			
				</tr>
				<tr>
					<td class="title" id="f_alterationType">学籍变动种类<font color="red">*</font></td>
					<td >
						<@htm.i18nSelect id="f_mode" datas=modes name="alteration.mode.id" style="width:100%;" selected=(alteration.mode.id)?default("")?string/>
					</td>
					<td class="title" id="f_Reason">变更原因</td>
					<td>
					<@htm.i18nSelect name="alteration.reason.id" style="width:100%;" datas=reasons selected=(alteration.reason.id?string)?default("")/>
					</select>
					</td>
				</tr>
				<tr>
				    <td class="title" id="f_remark" style="border-bottom-width:2px">备注</td>
					<td colspan="3" style="border-bottom-width:2px">
					    <textarea cols='40' rows="2" name="alteration.remark">${alteration.remark?if_exists}</textarea>
			   		</td>
				</tr>
				<tr>
					<td class="darkColumn" colspan="4" style="border-bottom-width:2px;text-align:center;font-weight:bold">变更后学生信息</td>
				</tr>
				<tr>
					<td class="title">学生类别<font color="red">*</font></td>
					<td id="stdType">
						<select id="f_stdType" name="alteration.afterStatus.stdType.id" style="width:100%">
							<option value="${(alteration.afterStatus.stdType.id)?default("")}"><@bean.message key="filed.choose" />...</option>
						</select>
					</td>
			        <td class="title">学院<font color="red">*</font></td>
					<td id="department">
						<select id="f_department" name="alteration.afterStatus.department.id" style="width:100%">
							<option value="${(alteration.afterStatus.department.id)?default("")}"><@bean.message key="filed.choose" />...</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="title">专业</td>
					<td id="speciality">
						<select id="f_speciality" name="alteration.afterStatus.speciality.id" style="width:100%">
							<option value="${(alteration.afterStatus.speciality.id)?default("")}"><@bean.message key="filed.choose" />...</option>
						</select>
					</td>
					<td class="title">专业方向</td>
					<td>
						<select id="f_aspect" name="alteration.afterStatus.aspect.id" style="width:100%">
							<option value="${(alteration.afterStatus.aspect.id)?default("")}"><@bean.message key="filed.choose" />...</option>
						</select>
					</td>
				</tr>
				<tr style="border-bottom-width:0px">
					<td class="title"><@msg.message key="attr.enrollTurn"/><font color="red">*</font></td>
					<td id="f_enrollTurn"><input type="text" id="input_enrollTurn" name="alteration.afterStatus.enrollYear" value="${(alteration.afterStatus.enrollYear)?if_exists}" maxlength="7" style="width:100%"/></td>
					<td class="title">毕业时间</td>
					<td><input id="f_graduateOn" name="alteration.afterStatus.graduateOn" value="${(alteration.afterStatus.graduateOn?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()" maxlength="10" style="width:100%"/></td>
				</tr>
				<tr>
				  	<td class="title" id="f_status">学籍状态<font color="red">*</font></td>
				  	<td><@htm.i18nSelect id="select_status" name="alteration.afterStatus.state.id" style="width:100%;" datas=statuses selected=(alteration.afterStatus.state.id)?default("")?string><option value="">...</option></@></td>
				  	<td class="title">学籍是否有效</td>
				  	<td>
				  		<input id="r_isActive_1" type="radio" name="alteration.afterStatus.isActive" value="1" <#if (alteration.afterStatus.isActive)?default(false) == true>checked</#if>/>&nbsp;是
				  		<input id="r_isActive_0" type="radio" name="alteration.afterStatus.isActive" value="0" <#if (alteration.afterStatus.isActive)?default(false) == false>checked</#if>/>&nbsp;否
				  	</td>
				</tr>
				<tr>
					<td class="title">班级</td>
					<td><select name="alteration.afterStatus.adminClass.id" onmouseover="getAdminClasses(event)" style="width:100%"><option value="">...</option></select></td>
					<td class="title">是否在校</td>
				  	<td>
				  		<input id="r_isInSchool_1" type="radio" name="alteration.afterStatus.isInSchool" value="1" <#if (alteration.afterStatus.isInSchool)?default(false) == true>checked</#if>/>&nbsp;是
				  		<input id="r_isInSchool_0" type="radio" name="alteration.afterStatus.isInSchool" value="0" <#if (alteration.afterStatus.isInSchool)?default(false) == false>checked</#if>/>&nbsp;否
				  	</td>
				</tr>
				<tr>
					<td class="title">是否应用</td>
					<td><input type="checkbox" name="isApply" checked/></td>
					<td class="title"></td>
					<td></td>
				</tr>
			</table>
  		</td>
  	</tr>
  	<#include "/pages/components/initAspectSelectData.ftl"/>
	<tr>
		<td align="center" class="darkColumn">
		    <input type="button" onClick="save()" value="<@bean.message key="system.button.submit" />" name="button1" class="buttonStyle" />&nbsp;
		    <input type="reset" onClick="alterationForm.reset()" value="<@bean.message key="system.button.reset" />" name="reset1" class="buttonStyle" />
   		</td>
	</tr>
   </form>
 </table>

<script src='dwr/interface/calendarDAO.js'></script>
<script src='dwr/interface/adminClassService.js'></script>
<script src='scripts/common/CalendarSelect.js'></script>
<script language="javascript" >
    var bar=new ToolBar("bar","学籍变动管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.save"/>","save()");
    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
    
    form = document.alterationForm;
    
		<#--三联动-->
	var stdAlteration = new StdTypeDepart3Select("f_stdType", "f_department", "f_speciality", "f_aspect", false, false, true, true); 
	stdAlteration.init(stdTypeArray, departArray);
	function changeSepcialityType(event) {
		var select = getEventTarget(event);
		stdAlteration.firstSepciality = "${(alteration.afterStatus.stdType.id)?default("")}";
		stdAlteration.firstAspect = "${(alteration.afterStatus.aspect.id)?default("")}";
		fireChange($("f_department"));
	}
 // 查找学生
  function getStd(){
     var stdCode=form['std.code'].value;
     if(stdCode==""){ alert("请输入学号");clear(); }
     else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
  }
  // 清除设定的信息
  function clear(){
       $('name').innerHTML="";
       $('speciality').innerHTML="";
       $('stdType').innerHTML='';
       $('department').innerHTML="";
       $('stdId').value="";
  }
  
  var msg_outof_shool="该同学学籍无效!";
  var no_std="没有该学号对应的学生";
  
  function setStdInfo(data){
     if(null==data){
       clear();
       $('message').innerHTML=no_std;
     }else{
        $('name').innerHTML=data.name;
        $('stdId').value=data.id;
        $('f_department').value = data['department.id'];
        $('f_speciality').value = data['firstMajor.id'] == null ? "" : data['firstMajor.id'];
        $('f_aspect').value = data['firstAspect.id'] == null ? "" : data['firstAspect.id'];
        $('f_stdType').value = data['type.id'] == null ? "" : data['type.id'];
        $('input_enrollTurn').value = data['enrollYear'] == null ? "" : data['enrollYear'];
        getAdminClasses();
        if (data['isActive']) {
        	$("r_isActive_1").checked = "checked";
        } else {
        	$("r_isActive_0").checked = "checked";
        }
        if (data['isInSchool']) {
        	$("r_isInSchool_1").checked = "checked";
        } else {
        	$("r_isInSchool_0").checked = "checked";
        }
        $('select_status').value = data['state.id'] == null ? "" : data['state.id'];
        if(!data.state)$("message").innerHTML=msg_outof_shool;
        else{
           $("message").innerHTML='';
        }
     }
  }
  function save(){
	 if($("message").innerHTML!="") {alert("输入信息有误，详情见抬头");return;}
     if(form['alteration.std.id'].value==""){alert("没有学生，不能提交.");return;}
     var fields = {
         'alteration.alterBeginOn':{'l':'学籍变动日期', 'r':true, 't':'f_alterDate','f':'date'},
         'alteration.remark':{'l':'备注', 'r':false, 't':'f_remark','mx':200},
         'alteration.afterStatus.enrollYear':{'l':'所在年级', 'r':true, 't':'f_enrollTurn', 'f':'yearMonth'},
         'alteration.afterStatus.state.id':{'l':'学籍状态', 'r':true, 't':'f_status'}
     };
    
     var v = new validator(form, fields, null);
     if (v.exec()) {
        form.submit();
     }
  }
	
	var selectObj = form["alteration.afterStatus.adminClass.id"];
	var isInit = false;
	function reInit() {
		isInit = true;
	}
	
	var enrollYear0 = "";
	var stdTypeId0 = "";
	var departmentId0 = "";
	var specialityId0 = "";
	var aspectId0 = "";
	function getAdminClasses(event) {
		var enrollYear = form["alteration.afterStatus.enrollYear"].value;
		var stdTypeId = form["alteration.afterStatus.stdType.id"].value;
		var departmentId = form["alteration.afterStatus.department.id"].value;
		var specialityId = form["alteration.afterStatus.speciality.id"].value;
		var aspectId = form["alteration.afterStatus.aspect.id"].value;
		if (enrollYear != enrollYear0 || stdTypeId != stdTypeId0 || departmentId != departmentId0 || specialityId != specialityId0 || aspectId != aspectId0) {
			reInit();
			enrollYear0 = enrollYear;
			stdTypeId0 = stdTypeId;
			departmentId0 = departmentId;
			specialityId0 = specialityId;
			aspectId0 = aspectId;
		} else{
			isInit = false;
			return;
		}
		adminClassService.getAdminClasses(setAdminClasses, enrollYear, stdTypeId, departmentId, specialityId, aspectId);
	}
	
	function setAdminClasses(data) {
		if (isInit) {
			selectObj.options.length = 0;
			selectObj.options.add(new Option("...", ""));
			if (null == data) {
				return;
			}
			var dataLength = data.length;
			if (dataLength == 0) {
				return;
			}
			for (var i = 0; i < dataLength; i++) {
				selectObj.options.add(new Option(data[i].name, data[i].id));
			}
			selectObj.value = "${(alteration.std.firstMajorClass.id)?default("")}";
		}
		isInit = false;
	}
	
	setTimeout("getAdminClasses()", 1500);
</script>
</body>
<#include "/templates/foot.ftl"/>