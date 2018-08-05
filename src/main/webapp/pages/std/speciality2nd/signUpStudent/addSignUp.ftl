<#include "/templates/head.ftl"/>
<script src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url"/>"></script>
<body>
  <table id="myBar" width="100%" border="0"></table>
  <table width="85%" align="center" class="formTable">
 	 <tr>
 	   <td width="20%" class="title">&nbsp;<@msg.message key="attr.stdNo"/></td>
 	   <td id="f_stdNo" width="30%"><input name="signUpStd.std.code" id="stdNo" maxlength="32" value="" style="width:80px"/><input type="button" value="查找.." onclick="checkStd(stdNo.value)"/></td>
 	   <td class="title" width="25%">&nbsp;<@msg.message key="attr.personName"/></td>
 	   <td width="25%" id="stdName"></td>
 	 </tr>
   </table>
     <table width="85%" align="center" class="formTable">
        <form name="commonForm" action="" method="post" onsubmit="return false;">
        <@searchParams/>
      <#assign specialitySettings=setting.specialitySettings?sort_by(["aspect","name"])>
      <#list 1..setting.choiceCount as i>
       <tr>
	     <td class="title" id="f_specialityAspect${i}">
	      &nbsp;<@bean.message key="info.secondSpecialitySignUpSetting.speciality" arg0="${i}"/><#if i==1><font color="red">*</font></#if>：
	     </td>
	     <td>
	      <select name="specialitySettingId${i}" id="specialitySettingId${i}" style="width:300px;">
	        <option></option>
	        <#list specialitySettings as specialitySetting>
	        <option value="${specialitySetting.id}" <#if Request["signUpRank"+"${i}"]?exists><#if (Request["signUpRank"+"${i}"].specialitySetting.aspect.id)?exists><#assign signUpRecordSpecialitySettingId = Request["signUpRank"+"${i}"].specialitySetting.aspect.id><#if specialitySetting.aspect.id == signUpRecordSpecialitySettingId?number>selected</#if></#if></#if>><@i18nName specialitySetting.aspect?if_exists/>&nbsp;<@bean.message key="info.secondSpecialitySignUpSetting.totalLimit"/>${specialitySetting.limit}</option>
	        </#list>
	      </select>
	     </td>
	   </tr>
	  </#list>
	  <tr>
	     <td class="title" width="30%" id="">&nbsp;<@bean.message key="attr.istemperable"/>：</td>
	     <td><@htm.radio2  name="signUpStd.isAdjustable" value=signUpStd.isAdjustable?default(true)/></td>
	  </tr>
	   <tr class="darkColumn">
	     <td colspan="2" align="center" >
  	       <input type="hidden" name="signUpStd.id" value=""/>
	       <input type="hidden" name="signUpStd.calendar.id" value="${setting.calendar.id}"/>
	       <input type="hidden" name="signUpStd.std.id" value=""/>
	       <input type="hidden" name="signUpStd.setting.id" value="${setting.id}"/>
	       <input type="hidden" name="count" value="${setting.choiceCount}"/>
	       <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;
	       <input type="reset" value="<@bean.message key="system.button.reset"/>" name="reset1"  class="buttonStyle"/>
	     </td>
	   </tr>
      </form>
     </table>
  <script language="javascript" >
   var bar =new ToolBar("myBar",'<@bean.message key="info.secondSpecialitySignUpSetting.signUp"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if signUpStd.id?exists>
   bar.addItem("取消报名","cancelSignUp(${signUpStd.id})");
   </#if>
   bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
   
    var form=document.commonForm;
    function doAction(form){
     var fields = {
         'specialitySettingId1':{'l':'<@bean.message key="info.secondSpecialitySignUpSetting.speciality" arg0="1"/>', 'r':true, 't':'f_specialityAspect1'}
     };
     var v = new validator(form, fields, null);
     
     if (v.exec()) {
        var nullFlag = false;//遇到没有填写的志愿
        for (var i=1; i<=${setting.choiceCount}; i++){
            var targetSelector = document.getElementById("specialitySettingId" + i.toString());
            if (targetSelector.value != ""){
            	if(nullFlag){
            		alert("<@bean.message key="error.secondSpecialitySignUpSetting.signUp1by1"/>");
            		return;
            	}
            	if (!check(targetSelector.value, i)){                
	                   alert("<@bean.message key="error.secondSpecialitySignUpSetting.speciality"/>!");
	                   return;
	            }
            }else{
            	nullFlag = true;
            }
        }
        if(form['signUpStd.std.id'].value==""){alert("学生不存在!");return;}
        if(confirm("是否提交你选选择的志愿?")){
           form.action="speciality2ndSignUpStudent.do?method=saveSignUp";
           form.submit();
        }
     }
   }
   
   function check(specialitySettingId, index){
      var flag = true;
      for (var i=1; i<=${setting.choiceCount}; i++){
          var targetSelector = document.all["specialitySettingId" + i.toString()];
          if (targetSelector.value != "" && i!=index && targetSelector.value ==specialitySettingId)
              flag = false;
      }
      return flag;
   }
   
   function cancelSignUp(signUpStdId){
      if(confirm("是否确定取消所有志愿的报名记录？")){
         form.action="speciality2ndSignUp.do?method=cancelSignUp&signUpStdId="+signUpStdId;
         form.submit();
      }
   }
   
   function checkStd(stdNo){
     if(""==stdNo){
       $("error").innerHTML="学号不应为空";
     }else{
       studentDAO.getBasicInfoName(setStdInfo,stdNo);
     }
  }
  function setStdInfo(data){
     if(null==data){
       $("error").innerHTML="该学生不存在";
       form['signUpStd.std.id'].value="";
       $("stdName").innerHTML="该学生不存在";
     }else{
        $("stdName").innerHTML=data.name;
        form['signUpStd.std.id'].value=data.id;
     }
  }
 </script>
</body>
<#include "/templates/foot.ftl"/>