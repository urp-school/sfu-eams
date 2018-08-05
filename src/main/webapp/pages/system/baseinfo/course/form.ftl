<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo><@msg.message key="page.courseForm.label"/></#assign>  
<#include "/templates/back.ftl"/> 
  <table width="90%" align="center" class="formTable">
    <form action="course.do?method=edit" name="courseForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B><@msg.message key="page.courseForm.label"/></B></td>
    </tr>  
    <tr>
      <td id="f_code" class="title" width="20%"><font color="red">*</font><@msg.message key="attr.code"/>:</td>
      <td>
      <input id="codeValue" type="text" name="course.code" value="${course.code?if_exists}" maxLength="32" style="width:80px;">
      <#assign className="Course">
      <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden" name="course.id" value="${course.id?if_exists}">
      </td>
      <td id="f_name" class="title" width="20%"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
      <td><input type="text" name="course.name" value="${course.name?if_exists}" maxLength="50"></td>
    </tr>
    <tr>
      <td id="f_engName" class="title"><@msg.message key="attr.engName"/>:</td>
      <td><input type="text" name="course.engName" value="${course.engName?if_exists}" maxLength="100"/></td>
      <td id="f_abbreviation" class="title"><@msg.message key="common.abbreviation"/>:</td>
      <td><input type="text" name="course.abbreviation" maxLength="50" value="${course.abbreviation?if_exists}"/></td>
    </tr>
    <tr>
      <td id="f_studentType" class="title"><font color="red">*</font><@msg.message key="course.stdType"/>:</td>
      <td>
          <@htm.i18nSelect datas=stdTypes name="course.stdType.id" selected=(course.stdType.id?string)?default("") style="width:155px"/>
      </td>
      <td class="title" id="f_weekHour"><font color="red">*</font><@msg.message key="attr.weekHour"/>:</td>
      <td><input type="text" name="course.weekHour" size="5" value="${course.weekHour?if_exists}" maxLength="3"/></td>
    </tr>
    <tr>
      <td class="title" id="f_credit"><font color="red">*</font><@msg.message key="common.grade"/>:</td>
      <td><input type="text" name="course.credits" size="5" value="${course.credits?if_exists}" maxLength="3"/></td>
      <td class="title" id="f_courseLength"><font color="red">*</font>学时:</td>
      <td><input type="text" name="course.extInfo.period" size="5" value="${(course.extInfo.period)?if_exists}" maxLength="3"/></td>
    </tr>
    <tr>
      <td class="title">要求语言熟练能力:</td>
  	  <td>
   	        <@htm.i18nSelect datas=languangeAblities name="course.languageAbility.id" selected="${(course.languageAbility.id)?default(0)}" style="width:155px">
   	        <option value="">请选择..</option>
   	        </@>
  	  </td>
      <td class="title">课程种类:</td>
  	  <td>
   	        <@htm.i18nSelect datas=courseCategories name="course.category.id" selected="${(course.category.id)?default(0)}" style="width:155px">
   	        <option value="">请选择..</option>
   	        </@>
  	  </td>
    </tr>
    <tr >
      <td  class="title">建议课程类别:</td>
      <td>
   	      <@htm.i18nSelect datas=courseTypes name="course.extInfo.courseType.id" selected="${(course.extInfo.courseType.id)?default(0)}" style="width:155px">
   	      <option value="">请选择..</option>
   	      </@>
  	  </td>
  	  <td  class="title">所属院系:</td>
		<td>
   	        <@htm.i18nSelect datas=departments name="course.extInfo.department.id" selected="${(course.extInfo.department.id)?default(0)}" style="width:155px">
   	        <option value="">请选择...</option>
   	        </@>
  	 	 </td>
    </tr>
    <tr >
      <td id="f_requirement" class="title"><@msg.message key="course.requirement"/>:</td>
      <td colspan="3" >
        <textarea name="course.extInfo.requirement"cols="40" rows="2">${(course.extInfo.requirement)?if_exists}</textarea>
      </td>
    </tr>
    <tr >
      <td id="f_description" class="title"><@msg.message key="attr.description"/>:</td>
      <td colspan="3" ><textarea name="course.extInfo.description"  cols="40" rows="2">${(course.extInfo.description)?if_exists}</textarea></td>
    </tr>
    <tr>
      <td class="title"><@msg.message key="attr.dateEstablished"/>:</td>
  	  <td>
  	        <input type="text" name="course.extInfo.establishOn" id="dateEstablished" maxlength="10" onfocus="calendar()" onload=''
  	         value="${(course.extInfo.establishOn?string("yyyy-MM-dd"))?if_exists}"/>
  	  </td>
      <td class="title">是否使用：</td>
      <td><@htm.radio2  name="course.state" value=course.state?default(true)/></td>
    </tr>
    <tr>
      <td class="title">  <@msg.message key="attr.createAt"/>: </td>
      <td>${(course.createAt?string("yyyy-MM-dd"))?if_exists}</td>
      <td rowspan="2" id="f_remark" class="title">&nbsp;&nbsp;<@msg.message key="common.remark"/>&nbsp;&nbsp;:</td>
      <td rowspan="2" >
        <textarea name="course.remark" cols="15" rows="2">${course.remark?if_exists}</textarea>        
      </td>
    </tr>
    <tr>
      <td class="title">  <@msg.message key="attr.modifyAt"/>:</td>
      <td>${(course.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr class="darkColumn" align="center">
      <td colspan="4">
          <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>
          <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并添加下一个" class="buttonStyle"/>
          <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  <script language="javascript" > 
    function reset(){
       	   document.courseForm.reset();
    }
    function save(form,params){
         var a_fields = {
         'course.code':{'l':'<@msg.message key="attr.code"/>', 'r':true, 't':'f_code','mx':32},
         'course.name':{'l':'<@msg.message key="attr.infoname"/>', 'r':true, 't':'f_name','mx':50},
         'course.extInfo.period':{'l':'学时','r':true,'t':'f_courseLength','f':'unsigned','mx':3},
         'course.credits':{'l':'<@msg.message key="common.grade"/>','r':true,'t':'f_credit','f':'unsignedReal','mx':3},
         'course.weekHour':{'l':'<@msg.message key="attr.weekHour"/>','r':true,'t':'f_weekHour','f':'unsigned','mx':3},
         'course.stdType.id':{'l':'<@msg.message key="entity.studentType"/>','r':true,'t':'f_studentType','f':'unsigned'},
         'course.engName':{'l':'<@msg.message key="attr.engName"/>', 'r':false, 't':'f_engName','mx':100},
         'course.abbreviation':{'l':'<@msg.message key="common.abbreviation"/>','r':false,'t':'f_abbreviation','mx':50},
         'course.extInfo.description':{'l':'<@msg.message key="attr.description"/>','r':false,'t':'f_description','mx':1000},
         'course.remark':{'l':'<@msg.message key="common.remark"/>','r':false,'t':'f_remark','mx':100}        
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form['course.code'].value = cleanSpaces(form['course.code'].value);
        form.action="course.do?method=save";
        if(null!=params)
          form.action+=params;
        form.submit();
     }
   }
 </script>
 </body>
</html>
