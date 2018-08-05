<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<#assign labInfo><@bean.message key="page.specialityAspectForm.label" /></#assign>   
<#include "/templates/back.ftl"/>
  <table width="90%" align="center"  class="formTable">
    <form action="specialityAspect.do?method=edit&unload" name="specialityAspectForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4" ><B><@bean.message key="page.specialityAspectForm.label" /></B></td>
    </tr>
    <tr>
      <td  id="f_code" class="title"><font color="red">*</font><@bean.message key="attr.code" />:</td>
      <td>
       <input id="codeValue" type="text"  name="specialityAspect.code" value="${specialityAspect.code?if_exists}" maxLength="32" style="width:80px;">
       <input type="hidden"  name="specialityAspect.id" value="${specialityAspect.id?if_exists}"/>      
      </td>
      <td  id="f_name" class="title"><font color="red">*</font><@bean.message key="attr.infoname" />:</td>
      <td><input type="text" name="specialityAspect.name" value="${specialityAspect.name?if_exists}" maxLength="50"></td>
    </tr>
    <tr>
      <td  id="f_engName" class="title"><@bean.message key="attr.engName" />:</td>
      <td><input type="text" name="specialityAspect.engName"  value="${specialityAspect.engName?if_exists}" maxLength="100"/></td>
      <td  id="f_abbreviation" class="title"><@bean.message key="common.abbreviation" />:</td>
      <td><input type="text" name="specialityAspect.abbreviation"  value="${specialityAspect.abbreviation?if_exists}" maxLength="25"/></td>
    </tr>
    <tr>
      <td  id="f_studentType" class="title"><font color="red">*</font><@bean.message key="entity.studentType" />:</td>
      <td>
        <select id="stdTypeOfSpeciality" name="specialityAspect.speciality.stdType.id" style="width:155px;" >
       	   <option value="${specialityAspect.speciality?if_exists.stdType?if_exists.id?if_exists}"></option>
        </select>
      </td>
      <td  class="title"><@bean.message key="attr.dateEstablished" />:</td>
  	  <td>
  	     <input type="textarea" name="specialityAspect.dateEstablished"  onfocus="calendar()" id="dateEstablished"
  	      value="${(specialityAspect.dateEstablished?string("yyyy-MM-dd"))?if_exists}" />
  	  </td>
    </tr>    
    <tr>
      <td  id="f_department" class="title"><font color="red">*</font><@bean.message key="entity.department" />:</td>
      <td>
        <select id="department" name="specialityAspect.speciality.department.id" style="width:155px;" >
        <option value="${specialityAspect.speciality?if_exists.department?if_exists.id?if_exists}"><@bean.message key="common.selectPlease" /></option>
        </select>
      </td>
  	  <td   id="f_maxPeople" class="title"><@bean.message key="specialityAspect.maxPeople" />:</td>
      <td align="left" ><input type="text" name="specialityAspect.maxPeople" value="${specialityAspect.maxPeople?if_exists}" maxLength="3"/></td> 
    </tr>
    <tr >
      <td  id="f_speciality" class="title"><font color="red">*</font><@bean.message key="entity.speciality"/>:  </td>
      <td align="left" >
        <select id="speciality" name="specialityAspect.speciality.id"  style="width:155px;">
        <option value="${specialityAspect.speciality?if_exists.id?if_exists}"><@bean.message key="common.selectPlease" /></option>
        </select>
      </td>
      <td class="title">使用状态：</td>
      <td><@htm.radio2  name="specialityAspect.state" value=specialityAspect.state?default(true)/></td>
    </tr>
    <tr>
      <td  class="title"><@bean.message key="attr.modifyAt"/>:</td>
      <td>       
        ${(specialityAspect.createAt?string("yyyy-MM-dd"))?if_exists}
      </td>
      <td rowspan="3"  id="f_remark" class="title"><@bean.message key="common.remark" />:</td>
      <td rowspan="3"  valign="top">
        <textarea name="specialityAspect.remark" cols="20" rows="1">${specialityAspect.remark?if_exists}</textarea>
       </td>
    </tr>
    <tr>
      <td class="title"><@bean.message key="attr.modifyAt" />:</td>
      <td>${(specialityAspect.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>	   
    
    <tr  class="darkColumn" align="center">
      <td colspan="4">
          <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
 </body>
 <script language="javascript" > 
     function reset(){ 
       	   document.specialityAspectForm.reset();
      }
    function save(form){
     var a_fields = {
         'specialityAspect.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code','mx':32},
         'specialityAspect.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name','mx':50},
         'specialityAspect.maxPeople':{'l':'<@bean.message key="specialityAspect.maxPeople" />','r':false,'t':'f_maxPeople','f':'unsigned','mx':3},
         'specialityAspect.speciality.department.id':{'l':'<@bean.message key="entity.department" />','r':true,'t':'f_department'},
         'specialityAspect.speciality.id':{'l':'<@bean.message key="entity.speciality" />','r':true,'t':'f_speciality'},
         'specialityAspect.remark':{'l':'<@bean.message key="common.remark" />','r':false,'t':'f_remark','mx':100}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form['specialityAspect.code'].value = cleanSpaces(form['specialityAspect.code'].value);
        form.action="specialityAspect.do?method=save"
        form.submit();
     }
   }
 </script>
<#include "/templates/stdTypeDepart2Select.ftl"/>
<script>
  specialityNotNull=true;
</script>
</html>