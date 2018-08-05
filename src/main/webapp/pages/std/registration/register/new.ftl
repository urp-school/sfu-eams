<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script type='text/javascript' src='dwr/interface/registerService.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>

 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="feeInfoBar"></table>
	<script>
	   var bar = new ToolBar('feeInfoBar','在校生注册',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addBack("<@bean.message key="action.back"/>");
	</script>
	<h4 align="center">${RequestParameters['year']}年第${RequestParameters['term']}学期</h4>
	<table width="80%" align="center" class="listTable" id="registerTable" >
    	<form name="registerForm" method="post" action="register.do?method=singleRegister" onsubmit="return check()" >
    	<input type="hidden" value="${RequestParameters['year']}" name="year"/>
    	<input type="hidden" value="${RequestParameters['term']}" name="term"/>
    	<input type="hidden" value="${RequestParameters['calendarId']}" name="calendarId"/>
    	<input type="hidden" value="" name="params"/>
		<tr><td>学号:</td><td><input name="register.std.code" ><button onclick="searchRegister()">查找</button></td></tr>
		<tr><td>姓名:</td><td><input name="register.std.name" disabled></td></tr>
		<tr><td>性别:</td><td><input name="register.std.sex" disabled></td></tr>
		<tr><td>专业:</td><td><input name="register.std.speciality" disabled></td></tr>
		<tr><td>注册状态:</td><td><select name="register.oldState.code" style="width:120px" disabled>
         <option value="">....</option>
         <#list registerStates?sort_by("code")?reverse as registerState>
         <option value="${registerState.code}">${registerState.name}</option>
         </#list>
         </select></td></tr>
		<tr><td>是否缴费完成:</td><td><select name = "register.isTuitionFeeCompleted" disabled><option value="1" isSelected>是</option><option value="0" isSelected>否</option></select></td></tr>
		<tr><td>是否注册完成:</td><td><select name = "register.isPassed" disabled><option value="1" isSelected>是</option><option value="0" isSelected>否</option></select></td></tr>	
		<tr><td align = "right"><select name="register.state.code" style="width:110px" >
        	<option value="37">注册</option>
        	<#list registerStates?sort_by("code")?reverse as registerState>
        	<option value="${registerState.id}">${registerState.name}</option>
        	</#list></select></td><td align="left"><input  type="submit" value="注册" ></td></tr>
        	<input type="hidden" name="stdCode"/>
		</form>
		</table>
	
<script language="javascript" >
	var form = document.registerForm;
	function searchRegister(){
		registerService.getRegister(setData,form['register.std.code'].value,"${RequestParameters['calendarId']}");
	}
	function setData(registerMap){
		if(registerMap.registerIsTuitionFeeCompleted){
			registerMap.registerIsTuitionFeeCompleted=1;
		}else{
			registerMap.registerIsTuitionFeeCompleted=0;
		}
		if(registerMap.registerIsPassed){
		registerMap.registerIsPassed=1;
		}else{
		registerMap.registerIsPassed=0;
		}
		if(null!=registerMap){
			form['stdCode'].value=form['register.std.code'].value;
	        form['register.std.name'].value=registerMap.stdName;
	        form['register.std.sex'].value=registerMap.sex;
	        form['register.std.speciality'].value=registerMap.speciality;
	        form['register.oldState.code'].value=registerMap.stateName;
	        form['register.isTuitionFeeCompleted'].value=registerMap.registerIsTuitionFeeCompleted;
	        form['register.isPassed'].value=registerMap.registerIsPassed;  
	     }else{
	     	form['register.std.name'].value="查无此人";
	     }
	}
	function check(){
	     if(form['register.std.code'].value==''){
	     	alert('学号必须输入！');
	     	return false;
	     }
	     if(form['register.std.name'].value==''){
	     	searchRegister();
	     	return false;
	     }
	      form['params'].value="&year="+form['year'].value +"&term="+form['term'].value+"&calendarId="+form['calendarId'].value;
	       return true;
	     }
 </script>
 </body>
 <#include "/templates/foot.ftl"/>