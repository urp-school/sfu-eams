<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<body>
<#assign labInfo><@msg.message key="page.adminClassForm.label"/></#assign>
<#include "/templates/back.ftl"/> 
  <table width="90%" align="center" class="formTable">
    <form action="adminClass.do" name="adminClassForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td colspan="4"><B><@msg.message key="page.adminClassForm.label"/></B></td>
    </tr>
    <tr>
      <td id="f_code" class="title"><@msg.message key="attr.code"/>:</td>
      <td>
        <input id="codeValue" type="text" name="adminClass.code" value="${adminClass.code?if_exists}"  maxLength="32" style="width:80px;"/>
        <#assign className="AdminClass">
        <#include "/pages/system/checkCode.ftl"/>
        <input type="hidden" name="adminClass.id" value="${adminClass.id?if_exists}"/>
      </td>
      <td id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
      <td><input type="text" name="adminClass.name" value="${adminClass.name?if_exists}" maxLength="25"></td>
    </tr>
    <tr>
      <td id="f_studentType" class="title"><font color="red">*</font><@msg.message key="entity.studentType"/>:</td>
      <td>   
          <#if adminClass.stdType?if_exists.id?exists>
          	<#assign stdTypeId =adminClass.stdType.id>
          <#else>
            <#assign stdTypeId =0>
          </#if>
          <select id="stdTypeOfSpeciality" name="adminClass.stdType.id" style="width:155px">
	          <option value="${stdTypeId}"></option>
          </select>
      </td>
   	  <td id="f_enrollYear" class="title"><font color="red">*</font><@msg.message key="adminClass.enrollYear"/>:</td>
      <td><input type="text" name="adminClass.enrollYear" value="${adminClass.enrollYear?if_exists}" maxLength="7"/></td>	     
    </tr>
    <tr>
      <td id="f_department" class="title"><font color="red">*</font><@msg.message key="entity.department"/>:</td>
      <td> 
        <select id="department" name="adminClass.department.id" style="width:155px;">
           <option value="${adminClass.department?if_exists.id?if_exists}"><@msg.message key="common.selectPlease"/></option>
        </select>
      </td>
      <td id="f_planStdCount" class="title"><font color="red">*</font><@msg.message key="adminClass.planStdCount"/>:</td>
      <td><input type="text" name="adminClass.planStdCount" value="${adminClass.planStdCount?if_exists}" maxLength="3"/></td>
    </tr>
    <tr>
      <td class="title"><@msg.message key="entity.speciality"/>&nbsp;:</td>
      <td id="f_speciality" >         
        <select id="speciality" name="adminClass.speciality.id" style="width:155px;">
           <option value="${adminClass.speciality?if_exists.id?if_exists}"><@msg.message key="common.selectPlease"/></option>
        </select>
      </td>     
      <td id="f_actualStdCount" class="title"><@msg.message key="adminClass.actualStdCount"/>:</td>
      <td>${adminClass.actualStdCount?if_exists}</td>
    </tr>
    <tr>
      <td class="title"><@msg.message key="entity.specialityAspect"/>&nbsp;:</td>
      <td id="f_specialityAspect" >             
        <select id="specialityAspect" name="adminClass.aspect.id"  style="width:200px;">        
         <option value="${adminClass.aspect?if_exists.id?if_exists}"><@msg.message key="common.selectPlease"/></option>
        </select>
      </td>
  	  <td class="title">人数:</td>
      <td >${adminClass.stdCount?if_exists}</td>
    </tr>
    <tr>
      <td class="title" id="f_eduLength"><font color="red">*</font>学制:</td>
  	  <td><input type="text" name="adminClass.eduLength" value="${adminClass.eduLength?if_exists}" maxLength="3"/></td>
  	  <td class="title" id="f_instructor"><@msg.message key="adminClass.instructor"/>:</td>
      <td>
            <select id="instructorDepartment" style="width:150px">
   	         	<option value="${(adminClass.instructor.department.id)?if_exists}"></option>
	       	</select>
   	       	<select id="instructor" name="adminClass.instructor.id" style="width:90px">
   	         	<option value="${(adminClass.instructor.id)?if_exists}"></option>
	       	</select>
	  </td>
    </tr> 
    <tr>
      <td class="title" id="f_dateEstablished"><@msg.message key="attr.dateEstablished"/>:</td>
  	  <td>
        <input type="text" name="adminClass.dateEstablished"  onfocus="calendar()" id="dateEstablished"
         value="${(adminClass.dateEstablished?string("yyyy-MM-dd"))?if_exists}"/>
  	  </td>
       <td class="title">是否使用:</td>
       <td><@htm.radio2  name="adminClass.state" value=adminClass.state?default(true)/></td>   
    </tr>
    <tr>
      <td class="title">  <@msg.message key="attr.createAt"/>: </td>
      <td>${(adminClass.createAt?string("yyyy-MM-dd"))?if_exists}</td> 
      <td rowspan="2"  id="f_remark" class="title"><@msg.message key="common.remark"/>:</td>
      <td rowspan="2" ><textarea name="adminClass.remark"  cols="20" rows="2" >${adminClass.remark?if_exists}</textarea></td>
    </tr>
    <tr>
      <td class="title">  <@msg.message key="attr.modifyAt"/>:</td>
      <td>${(adminClass.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>    
    </tr>
    
    <tr  class="darkColumn" align="center">
      <td colspan="4">
         <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>
         <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并添加下一个" class="buttonStyle"/>
         <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>           
      </td>
    </tr>
    </form> 
  </table>
 </body>
 <script src='dwr/interface/teacherDAO.js'></script>
 <script src='scripts/departTeacher2Select.js'></script>
 <script language="javascript" >
     action="adminClass.do";
     function reset(){
       	   document.adminClassForm.reset();
      }
    function save(form,params){
     var a_fields = {
         'adminClass.code':{'l':'<@msg.message key="attr.code"/>', 'r':true, 't':'f_code'},
         'adminClass.department.id':{'l':'<@msg.message key="entity.department"/>', 'r':true, 't':'f_department'},
         'adminClass.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'},
         'adminClass.planStdCount':{'l':'<@msg.message key="adminClass.planStdCount"/>','r':true,'t':'f_planStdCount','f':'unsigned'},
         'adminClass.eduLength':{'l':'学制','r':true,'t':'f_eduLength','f':'unsignedReal'},
         'adminClass.remark':{'l':'<@msg.message key="common.remark"/>','r':false,'t':'f_remark','mx':100},
         'adminClass.dateEstablished':{'l':'<@msg.message key="attr.dateEstablished"/>','r':false,'t':'f_dateEstablished','f':'date'},
         'adminClass.enrollYear':{'l':'<@msg.message key="adminClass.enrollYear"/>','r':true,'t':'f_enrollYear','f':'yearMonth'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form['adminClass.code'].value = cleanSpaces(form['adminClass.code'].value);
        form.action=action+"?method=save";
        if(null!=params)
           form.action+=params;
        form.submit();
     }     
   }

    var instructorDepartArray = new Array();
    <#list departmentList?sort_by("name")  as depart>
    instructorDepartArray[instructorDepartArray.length]={'id':'${depart.id?if_exists}','name':'<@i18nName depart/>'};
    </#list>
    var t1=new DepartTeacher2Select('instructorDepartment','instructor',false,true);
    t1.initTeachDepartSelect(instructorDepartArray);
    t1.initTeacherSelect();
 </script>
<#include "/templates/stdTypeDepart3Select.ftl"/>
<#include "/templates/foot.ftl"/>