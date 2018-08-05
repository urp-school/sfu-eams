<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
	<table id="addAssistant"></table>
	<script>
	   var bar = new ToolBar('addAssistant','助教信息',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addBack("<@msg.message key="action.back"/>");
	</script>

	<table width="100%" align="center" class="listTable" id="feeDetailTable" onkeyPress="onReturn.focus(event)">
    	<form name="assistantForm" method="post" action="" onSubmit="return false;">
    	<input type='hidden' name="assistant.id" value='${(assistant.id)?if_exists}'>
		<tr class="darkColumn">
    		<td colspan="4" align="center">助教信息管理</td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"  id="f_stdNo"><font color="red">*</font><@msg.message key="attr.stdNo"/>:</td>
  		    <td>
  		      <input type="hidden" id='assistantStdId' name="assistant.std.id" value="${(assistant.std.id)?if_exists}">
    		  <input type="text" maxlength="32" id='assistantStdNo' name="assistant.std.code" TABINDEX="1" value="${(assistant.std.code)?if_exists}" <#if assistant.id?exists>readOnly</#if> onchange="getStd()" style="width:90px"/>
    		</td>
            <td class="grayStyle" align="center"><@msg.message key="attr.personName"/>:</td>
  		    <td id="name" class="grayStyle">${(std.name)?if_exists}</td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.speciality"/>:</td>
  		    <td id="speciality" class="grayStyle"><@i18nName std?if_exists.firstMajor?if_exists/></td>
            <td class="grayStyle" align="center">班级:</td>
  		    <td id="adminClass" class="grayStyle"><@getBeanListNames std?if_exists.adminClasses?if_exists/></td>
  		</tr>
  		<tr>
            <td class="grayStyle" align="center"><@msg.message key="entity.studentType"/>:</td>
  		    <td id="feeDetailStdType" class="grayStyle"><@i18nName std?if_exists.type?if_exists/></td>
            <td class="grayStyle" align="center"><@msg.message key="entity.department"/>:</td>
  		    <td id="department" class="grayStyle"><@i18nName std?if_exists.department?if_exists/></td>
  		</tr>
		<tr>
			<td class="grayStyle" align="center" id="f_period"><font color="red">*</font>学时：</td>
			<td class="brightStyle" align="left">
				<input type='text' name='assistant.period' maxlength="3" value='${(assistant.period)?if_exists}'>
			</td>
			<td class="grayStyle" align="center" id="f_turor"><font color="red">*</font>导师工号：</td>
			<td class="brightStyle" align="left">
				<input type='text' name='tutorNo' maxlength="15" value='${(assistant.tutor.code)?if_exists}'>
			</td>
		</tr>  		
		<tr>
			<td class="grayStyle" align="center" id="f_startTime"><font color="red">*</font>开始时间：</td>
			<td class="brightStyle" align="left">
				<input type="text" name="assistant.startTime" maxlength="10" value='${(assistant.startTime)?if_exists}' style="width:100px" onfocus="calendar()">
			</td>
			<td class="grayStyle" align="center" id="f_endTime"><font color="red">*</font>结束时间：</td>
			<td class="brightStyle" align="left">
				<input type="text" name="assistant.endTime"maxlength="10" value='${(assistant.endTime)?if_exists}' style="width:100px" onfocus="calendar()">
			</td>
		</tr>		 
		<tr>
			<td class="grayStyle" align="center" id="f_job"><font color="red">*</font>所做工作：</td>
			<td class="brightStyle" align="left" colspan="3">
				<textarea name="assistant.job" cols="50" TABINDEX="12">${(assistant.job)?if_exists}</textarea>
			</td>
		</tr>
		<tr>
			<td class="darkColumn" colspan="4" align="center">				
                <button accesskey="S" onClick="save(this.form);"><@msg.message key="action.save"/>(S)</button>&nbsp;
			</td>
		</tr>
	</table>
	</form>
	<br><br><br><br><br><br><br><br>
	<script language="JavaScript" type="text/JavaScript" src="scripts/common/onReturn.js"></script>
	<script>
	   document.assistantForm['assistant.std.code'].focus();
	   if(document.assistantForm['assistant.std.code'].value!=""){
	      getStd();
	   }
	  function getStd(){    
	     var form =document.assistantForm;
	     var stdNo=form['assistant.std.code'].value;
	     if(stdNo==""){clear(); }
	     else{
	       studentDAO.getBasicInfoName(setStdInfo,stdNo);
	     }
	  }	 
	  function setStdInfo(data){ 
	     if(null==data){       
	       clear();
	       $('error').innerHTML='学号不存在!';
	     }else{
	        $('assistantStdId').value=data.id;	        
	        $('name').innerHTML=data.name;
	        $('feeDetailStdType').innerHTML=data['type.name'];
	        $('department').innerHTML=data['department.name'];
	        $('speciality').innerHTML=data['department.name'];
	        $('adminClass').innerHTML=data['adminClasses.adminClass.name'];
	     }
	  }
	  function clear(){
	       $('assistantStdNo').value="";
	       $('assistantStdId').value="";
	       $('name').innerHTML="";
	       $('speciality').innerHTML="";
	       $('adminClass').innerHTML="";
	       $('feeDetailStdType').innerHTML="";
	       $('department').innerHTML="";
	       $('error').innerHTML="";
	  }	
	  
	  var onReturn =new OnReturn(document.assistantForm);
	  onReturn.add('assistant.std.code');
	  onReturn.add('assistant.period');
	  onReturn.add('assistant.startTime');
	  onReturn.add('assistant.endTime');	  
	  onReturn.add('assistant.job'); 
   function save(form,extra){
		var a_fields = {
 		     'assistant.std.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_stdNo','f':'alphanum', 'mx':32},
			 'assistant.startTime':{'l':'开始时间', 'r':true, 't':'f_startTime','f':'date'},			 
			 'assistant.endTime':{'l':'结束时间','r':true,'t':'f_endTime','f':'date'},
             'assistant.period':{'l':'学时','t':'f_period','f':'unsignedReal','r':true},
             'tutorNo':{'l':'导师工号','t':'f_turor','r':true},
			 'assistant.job':{'l':'所做工作','t':'f_job','r':true,'mx':100}
	    };
		var v = new validator(form, a_fields, null);
		if (v.exec()) {
			form.action = "assistant.do?method=save";
			if(null!=extra){
			   form.action+=extra;
			}			
			if(confirm("确定保存？"))
			  form.submit();
		}
	}
	</script>
 </body>
 <#include "/templates/foot.ftl"/>