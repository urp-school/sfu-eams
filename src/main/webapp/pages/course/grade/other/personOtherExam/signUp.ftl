<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table>	 
    <table width="85%" align="center" class="formTable">
      <form name="actionForm" action="" method="post" onSubmit="return false;">
       <input type="hidden" name="calendar.id" value="${calendar.id}">
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="filed.sogType.signupNow"/></td>
	   </tr>
	   <tr>
	    <td class="title" width="15%">&nbsp;<@msg.message key="attr.personName"/>：</td>
		<td width="35%"><@i18nName std?if_exists/>&nbsp;<font color='red'><br><@msg.message key="info.uncommonWordsTip"/></font></td>
	    <td class="title" width="17%">&nbsp;<@msg.message key="attr.stdNo"/>：</td>
		<td>${std.code?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title" >&nbsp;<@msg.message key="entity.department"/>：</td>
		<td><@i18nName (std.department)?if_exists/></td>
	    <td class="title" >&nbsp;<@msg.message key="attr.year2year"/>：</td>
		<td>${calendar.year?if_exists}</td>
	   </tr>	
	   <tr>
	    <td class="title" >&nbsp;<@msg.message key="entity.speciality"/>：</td>
		<td><@i18nName (std.firstMajor)?if_exists/></td>
	    <td class="title">&nbsp;<@msg.message key="attr.term"/>：</td>
		<td>${calendar.term?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title" >&nbsp;<@msg.message key="entity.specialityAspect"/>：</td>
		<td><@i18nName (std.firstAspect)?if_exists/></td>
	    <td class="title">&nbsp;<@msg.message key="entity.eduDegree"/>：</td>
		<td><@i18nName (std.type.eduDegree)?if_exists/></td>
	   </tr>
	   <tr>
	    <td class="title" >&nbsp;<@msg.message key="entity.adminClass"/>：</td>
		<td><@getBeanListNames std.adminClasses/>
	    </td>
	    <td class="title">&nbsp;<@msg.message key="std.studyingYear"/>：</td>
		<td>${std.enrollYear?if_exists}</td>
	   </tr>
	   <tr>
	    <td class="title" >&nbsp;<font color="red">*</font><@msg.message key="std.idCard"/>：</td>
		<td>${std.basicInfo.idCard?if_exists}
			<font color='red'><br>请仔细核对，如有问题予教务处处理！</font>
	    </td>
	    <td class="title">&nbsp;<@msg.message key="attr.eduLength"/>：</td>
		<td>${std.schoolingLength?if_exists}</td>
	   </tr>
  	</table>
    <@table.table width="85%" align="center">
       <@table.thead>
	     <td><@msg.message key="exam.type"/></td>
	     <td><@msg.message key="std.SignUpTime"/></td>
	     <td><@msg.message key="std.signUpConditions"/></td>
	     <td align="center"><@msg.message key="action.operation"/></td>
	   </@>
	   <@table.tbody datas=settings;setting>
	     <td align="left">&nbsp;<@i18nName setting.examCategory/></td>
	     <td align="left">&nbsp;[${setting.startAt?string("yyyy-MM-dd")},${setting.endAt?string("yyyy-MM-dd")}]</td>
	     <td align="left">&nbsp;<#if setting.superCategory?exists><font color='blue'><@i18nName setting.superCategory/>&nbsp;<@msg.message key="attr.graduate.pass"/></font></#if></td>
	     <td align="center"><#if hasSignUpMap[setting.examCategory.id?string]><button onclick="cancelSignUp(${setting.id})"><font color="red"><@msg.message key="std.cancleSignUp"/></font></button><#else><button onclick="signUp(${setting.id})"><@msg.message key="std.signUp"/></button></#if>
	   </@>
	</@>
  </form>
  <pre>
  <p>&nbsp;&nbsp;<@msg.message key="info.personalInfoModifyTip"/></p>
  </pre>
<script>
    var bar = new ToolBar("myBar", "<@msg.message key="std.examSignUp"/>", null, true, true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.back"/>", "back()", 'backward.gif');
    
    var form = document.actionForm;
    action = "personOtherExam.do";
    
    function getIds(){
       return(getCheckBoxValue(document.getElementsByName("soeId")));
    }
    
    function signUp(settingId){
       if(confirm("<@msg.message key="info.sureExamType"/>")){
		   form.action=action+"?method=submitSignUp&settingId="+settingId;
		   form.submit();
       }
    }
    
    function cancelSignUp(settingId){
       if(confirm("<@msg.message key="info.sureCancleExamType"/>")){
	       form.action = action + "?method=cancelSignUp&settingId=" + settingId;
	       form.submit();
       }
    }
    
    function back(){
       self.location = action + "?method=index";
    }
</script>
</body>
<#include "/templates/foot.ftl"/>