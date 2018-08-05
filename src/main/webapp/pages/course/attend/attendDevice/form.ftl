<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ieemu.js"></script>
<body>
<#assign labInfo>考勤监控信息</#assign>
<#include "/templates/back.ftl"/>
 <table width="100%" align="center" class="formTable">
  <form action="attendDevice.do?method=edit" name="classroomForm" method="post" onsubmit="return false;">
  <@searchParams/>
   <tr class="darkColumn">
     <td align="left" colspan="2"><B>考勤监控信息</B></td>
   </tr>
   
  <tr>
     <td class="title" width="40%"><font color="red">*</font>考勤机ID:</td>
     <td><input id="devid" type="text" name="attendDevice.devid" value="${(attendDevice.devid)!}" maxLength="20"/></td>
   </tr>
  <tr>
     <td class="title"><font color="red">*</font>教室:</td>
     <td>
        <select id="jsidId" name="attendDevice.jsid.id" style="width:155px;">
        <option></option>
        <#list jsidList as jsid>
        	<option value="${jsid.id}" <#if (attendDevice.jsid.id)?exists><#if jsid.id == attendDevice.jsid.id>selected </#if></#if>><@i18nName jsid/></option>
        </#list>
        </select>
     </td>
  </tr>
  <tr>
	     <td class="title">
	      <font color="red">*</font>考勤状态:
	     </td>
	     <td>
	      <input type="radio" name="attendDevice.kqjzt" value="true" <#if (attendDevice.kqjzt)?default(false)>checked</#if>>正常
	      <input type="radio" name="attendDevice.kqjzt" value="false" <#if !(attendDevice.kqjzt)?default(true)>checked</#if>>出错
	     </td>
  </tr>
  <tr>
     <td class="title"><font color="red">*</font>IP地址:</td>
     <td><input type="text" id="ip" name="attendDevice.ip" value="${(attendDevice.ip)!}"/></td>
  </tr>
  <tr>
     <td class="title"><font color="red">*</font>签到时间:</td>
     <td><input type="text" id="qdsj" name="attendDevice.qdsj" value="${(attendDevice.qdsj?string("yyyy-MM-dd HH:mm:ss"))?if_exists}" maxLength="19"/>&nbsp;(如：2013-08-23 11:28:26)</td>
  </tr>   
   
   <tr class="darkColumn" align="center">
     <td colspan="2">
       <input type="hidden" name="attendDevice.olddevid" value="${(attendDevice.devid)!}"/>
       <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       <#--
       <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并增加下一个" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
       -->
       <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>&nbsp;&nbsp;&nbsp;
     </td>
   </tr>
   </form>
 </table>
 <script language="JavaScript" type="text/JavaScript" src="scripts/course/attendDevice/DateCheck.js"></script>
  <script language="javascript"> 
     function reset(){
		document.classroomForm.reset();
     }
     function trimStr(str){ 
　　           return str.replace(/(^\s*)|(\s*$)/g, "");  
　　   }
     function save(form,params){
     	var devid=trimStr(document.getElementById("devid").value);
     	var jsidId=document.getElementById("jsidId").value;
     	var qdsj=trimStr(document.getElementById("qdsj").value);
     	var kqjzt=getRadioValue(document.getElementsByName("attendDevice.kqjzt"));
     	var ip=trimStr(document.getElementById("ip").value);
     	if(devid==null || devid==""){
     		alert("请填写考勤机ID");
	       	return;
     	}else if(isNaN(devid)){
     		alert("考勤机ID必须为数字");
	       	return;
     	}
     	if(jsidId==null || jsidId==""){
     		alert("请选择教室");
	       	return;
     	}
     	if(kqjzt==null || kqjzt==""){
     		alert("请选择考勤状态");
	       	return;
     	}
     	if(ip==null || ip==""){
     		alert("请填写IP地址");
	       	return;
     	}
     	if(qdsj==null || qdsj==""){
     		alert("请填写签到时间");
	       	return;
     	}else if(!IsDateTime(qdsj)){
     		alert("签到时间格式不正确");
	       	return;
     	}     	
     	form.action = "attendDevice.do?method=save";
     	form.submit();
     }
 </script>
</body> 
<#include "/templates/foot.ftl"/>