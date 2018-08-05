<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<body >
 <#assign labInfo><@msg.message key="page.specialityForm.label"/></#assign>   
<#include "/templates/back.ftl"/>
  <table width="90%" align="center" class="formTable">
  <form action="speciality.do?method=edit" name="specialityForm" method="post" onsubmit="return false;">
  <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B><@msg.message key="page.specialityForm.label"/></B></td>
    </tr>  
    <tr>
      <td id="f_code" class="title"><font color="red">*</font><@msg.message key="attr.code"/>:</td>
      <td width="20%">
      <input id="codeValue" type="text" name="speciality.code" value="${speciality.code?if_exists}" maxLength="32" style="width:80px;">
      <input type="hidden" name="speciality.id" value="${speciality.id?if_exists}">
      </td>
      <td width="20%" id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
      <td><input type="text" name="speciality.name" value="${speciality.name?if_exists}" maxLength="25"></td>
    </tr>
    <tr>
      <td id="f_engName" class="title"><@msg.message key="attr.engName"/>:</td>
      <td><input type="text" name="speciality.engName" value="${speciality.engName?if_exists}" maxLength="100"/></td>
      <td id="f_abbreviation" class="title"><@msg.message key="common.abbreviation"/>:</td>
      <td><input type="text" name="speciality.abbreviation" value="${speciality.abbreviation?if_exists}" maxLength="25"/></td>
    </tr>
    <tr>
      <td id="f_deparment" class="title"><font color="red">*</font><@msg.message key="entity.department"/>:</td>
      <td><@htm.i18nSelect datas=departments selected=(speciality.department.id?string)?default("0") name="speciality.department.id" style="width:150px"/></td>
      <td id="f_stdType" class="title"><font color="red">*</font><@msg.message key="entity.studentType"/>:</td>
      <td><@htm.i18nSelect datas=stdTypes selected=(speciality.stdType.id?string)?default("0")  name="speciality.stdType.id" style="width:150px"/></td>
    </tr>
    <tr >
       <td id="f_maxPeople" class="title"><@msg.message key="speciality.maxPeople"/>:</td>
       <td><input type="text" name="speciality.maxPeople" size="5" value="${speciality.maxPeople?if_exists}" maxLength="3"/></td> 
       <td class="title">专业类别:</td>
       <td>
       	   <select name="speciality.majorType.id">
			  <#list majorTypes as majorType>
              <option value="${majorType.id}" <#if majorType.id==(speciality.majorType.id)?default(1)>selected</#if>>${majorType.name}</option>
              </#list>
           </select>
       </td>
    </tr>
    <tr>
      <td class="title">使用状态:</td>
      <td><@htm.radio2  name="speciality.state" value=speciality.state?default(true)/></td>
      <td class="title"><@msg.message key="attr.dateEstablished"/>:</td>
  	     <td>
  	        <input type="textarea" name="speciality.dateEstablished" onfocus='calendar()' id="dateEstablished"
  	         value="${(speciality.dateEstablished?string("yyyy-MM-dd"))?if_exists}"/>
  	     </td>
    </tr>
  
    <tr >
      <td class="title">学科门类</td>
      <td><@htm.i18nSelect datas=subjectCategories selected=(speciality.subjectCategory.id)?default("")?string  name="speciality.subjectCategory.id" style="width:150px"/></td>
      <td class="title"><@msg.message key="attr.createAt"/>: </td>
      <td>${(speciality.createAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr>
      <td id="f_remark" class="title"><@msg.message key="common.remark"/>:</td>
      <td><textarea name="speciality.remark" cols=15 rows="2">${speciality.remark?if_exists}</textarea></td>
      <td class="title"><@msg.message key="attr.modifyAt"/>:</td>
      <td>${(speciality.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr  class="darkColumn" align="center">
      <td colspan="4">
          <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  <script language="javascript" > 
     function reset(){         
         document.specialityForm.reset();
      }
     function save(form){
         var a_fields = {
         'speciality.code':{'l':'<@msg.message key="attr.code"/>', 'r':true, 't':'f_code'},
         'speciality.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'},
         'speciality.maxPeople':{'l':'<@msg.message key="speciality.maxPeople"/>','r':false,'t':'f_maxPeople','f':'unsigned','mx':4},
         'speciality.remark':{'l':'<@msg.message key="common.remark"/>','r':false,'t':'f_remark','mx':100}        
         };
        	var v = new validator(form , a_fields, null);
        	if (v.exec()){
        		form['speciality.code'].value = cleanSpaces(form['speciality.code'].value);
          		form.action = "speciality.do?method=save"
          		form.submit();
        	}
      }
</script>
</body> 
</html>