<#include "/templates/head.ftl"/>
<script type='text/javascript' src='dwr/interface/studentDAO.js'></script>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body > 
<script>
    function getStd(){
     var stdCode=document.conditionForm['studyMeeting.student.code'].value;
     if(stdCode==""){ alert("请输入学号"); }
     else{
       studentDAO.getBasicInfoName(setStdInfo,stdCode);
     }
    }
    function setStdInfo(data){
     if(null==data){
       document.getElementById("stdName").innerHTML="没有找到该学号所对应的学生";
     }else{
        $('stdName').innerHTML=data.name;
     }
  }
</script>
<#assign labInfo><#if studyProduct.id?exists>修改会议<#else>添加会议</#if></#assign>
<#include "/templates/back.ftl"/>
<table width="100%" align="center" class="formTable">
  <form name="conditionForm" method="post" action="" onsubmit="return false;">
	   <tr class="darkColumn" align="center">
	     <td colspan="2">会议详细信息</td>
	   </tr>
		<tr>
			<td class="title" width="25%" id="f_std"><@msg.message key="attr.stdNo"/><font color="red">*</font>:</td>
			<td>
			  <#if student?exists>
			  		<input type="hidden" name="studyMeeting.student.id" value="${student?if_exists.id?if_exists}">${student.code}
			  <#elseif (studyProduct.id)?exists>
			  		<input type="hidden" name="studyMeeting.student.id" value="${studyProduct.student?if_exists.id?if_exists}">${studyProduct.student.code}
			  <#else>
			   <input type="text" name="studyMeeting.student.code" maxlength="32" value="${studyProduct?if_exists.student?if_exists.code?if_exists}" onblur="getStd()"/>学生姓名:<span id="stdName"></span>
			  </#if>
	     	</td>
		</tr>
	   <tr>
	     <td class="title" width="25%" id="f_title">
	      会议名称<font color="red">*</font>：
	     </td>
	     <td>
	      <input type="text" name="studyMeeting.name" maxlength="20" size="20" value="${studyProduct?if_exists.name?if_exists}"/>
	     </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_topicName">
	      	获邀论文题目<font color="red">*</font>:
	     </td>
	     <td><input type="text" name="studyMeeting.topicName" maxlength="20" value="${studyProduct?if_exists.topicName?if_exists}">
	     </td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_meetingType">
	      会议类别<font color="red">*</font>：
	     </td>
	     <td><@htm.i18nSelect datas=meetingTypes selected="${(studyProduct.meetingType.id)?default('')?string}" name="studyMeeting.meetingType.id" style="width:150px;"/></td>
	   </tr>		   	
	   <tr>
	     <td class="title" id="f_meetingOn">
	      会议时间<font color="red">*</font>：
	     </td>
	     <td><input type="text" name="studyMeeting.meetingOn" maxlength="10" value="<#if studyProduct?if_exists.meetingOn?exists>${studyProduct?if_exists.meetingOn?string("yyyy-MM-dd")}</#if>" onfocus="calendar();f_frameStyleResize(self,0)" readonly="true"/>  
	     </td>
	   </tr>
	   
	    <tr>
	     <td class="title" width="25%" id="f_meetingAddress">
	      会议地点<font color="red">*</font>：
	     </td>
	     <td>
		    <input type="text" name="studyMeeting.meetingAddress" maxlength="100" value="${studyProduct?if_exists.meetingAddress?if_exists}"/>           
	     </td>
	   </tr>		   
	   <tr>  
	     <td class="title" width="25%" id="f_openDepart">
	      举办单位<font color="red">*</font>：
	     </td>
	     <td>
 		       <input type="text" name="studyMeeting.openDepart" maxlength="50" value="${studyProduct?if_exists.openDepart?if_exists}"/>  
	     </td>
	   </tr>
	   <tr>  
	     <td class="title" width="25%" id="f_remark">
	       备注：
	     </td>
	     <td>    
	     	  <textarea name="studyMeeting.remark">${studyProduct?if_exists.remark?if_exists}</textarea>
	     </td>
	   </tr>	   	   
	   <tr class="darkColumn" align="center">
	     <td colspan="2">
	       <input type="hidden" name="studyMeeting.id" value="${studyProduct?if_exists.id?if_exists}">
	       <input type="hidden" name="productType" value="${RequestParameters['productType']}"> 
		   <input type="button" value="<@bean.message key="system.button.submit"/>" name="button1" onClick="doAction(this.form)" class="buttonStyle"/>
	     </td>
	   </tr>
	   </form>
   </table>
 </body>
 <script language="javascript" >
     function doAction(form){
      var a_fields = {
      	 <#if !((studyProduct.id)?exists||student?exists)>
      	 	'studyMeeting.student.code':{'l':'<@msg.message key="attr.stdNo"/>', 'r':true, 't':'f_std'},
      	 </#if>
         'studyMeeting.name':{'l':'会议名称', 'r':true, 't':'f_title','mx':100},
         'studyMeeting.topicName':{'l':'获邀论文题目', 'r':true, 't':'f_topicName','mx':100},
         'studyMeeting.meetingType.id':{'l':'会议类别', 'r':true, 't':'f_meetingType'},
         'studyMeeting.meetingOn':{'l':'会议时间', 'r':true, 't':'f_meetingOn'},
         'studyMeeting.meetingAddress':{'l':'会议地点', 'r':true, 't':'f_meetingAddress','mx':100},     
         'studyMeeting.openDepart':{'l':'举办单位', 'r':true, 't':'f_openDepart','mx':100},     
         'studyMeeting.remark':{'l':'备注','t':'f_remark','mx':250}        
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form.action="<#if student?exists>studyProductStd.do<#else>studyProduct.do</#if>?method=save";
     	setSearchParams(parent.document.searchForm,form);
        form.submit();
     } 
    }
 </script>
<#include "/templates/foot.ftl"/>