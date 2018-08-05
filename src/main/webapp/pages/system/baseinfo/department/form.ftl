<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url"/>"></script>
<body  >
<#assign labInfo><@msg.message key="page.departmentForm.label"/></#assign>  
<#include "/templates/back.ftl"/>   
     <table width="90%" align="center"  class="formTable">
       <tr class="darkColumn">
         <td align="left" colspan="4"><B><@msg.message key="page.departmentForm.label"/></B></td>
       </tr>
	   <form action="department.do?method=edit" name="departmentForm" method="post" onsubmit="return false;">
	   <@searchParams/>
	   <tr>
	     <td id="f_code" class="title"><font color="red">*</font><@msg.message key="attr.code"/>:</td>
	     <td>
	     <input id="codeValue" type="text"  name="department.code" value="${department.code?if_exists}" maxLength="32" style="width:80px;"/>
         <input type="hidden"  name="department.id" value="${department.id?if_exists}"/>
        </td>
	     <td id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname"/>:</td>
	     <td><input type="text" name="department.name" value="${department.name?if_exists}" maxLength="20"></td>
	   </tr>
	   <tr>
	     <td id="f_engName" class="title"><@msg.message key="attr.engName"/>:</td>
	     <td><input type="text" name="department.engName" value="${department.engName?if_exists}" maxLength="100"/></td>
	     <td id="f_abbreviation" class="title"><@msg.message key="common.abbreviation"/>:</td>
	     <td><input type="text" name="department.abbreviation"  value="${department.abbreviation?if_exists}" maxLength="50"/></td>
	   </tr>
	   <tr>
	     <td class="title" ><@msg.message key="attr.dateEstablished"/>:</td>
   	     <td>
   	        <input type="textarea" name="department.dateEstablished" maxlength="10" id="dateEstablished" onfocus="calendar()" onload=''
   	         value="${(department.dateEstablished?string("yyyy-MM-dd"))?if_exists}"/>
   	     </td>
   	      <td class="title" ><@msg.message key="department.isTeaching"/>:</td>
	     <td><@htm.radio2 value=department.isTeaching?default(false) name="department.isTeaching"/></td>
	   </tr> 
	   <tr>
	     <td class="title">是否使用: </td>
	     <td><@htm.radio2  name="department.state" value=department.state?default(true)/></td>
         <td class="title"> <@msg.message key="department.isCollege"/>: </td>
	     <td>
	      	<input type="radio" value="1" name="department.isCollege" <#if department.isCollege?if_exists == true>checked</#if> /> <@msg.message key="common.yes"/>
	      	<input type="radio" value="0" name="department.isCollege" <#if department.isCollege?if_exists == false>checked</#if> /> <@msg.message key="common.no"/>
	     </td>	
	   </tr>
	   <tr>
	      <td class="title">关联学生类别</td>
	      <td colspan="3">
	       <table width="100%" class="infoTitle">
	         <tr>
   	          <td>
	        可选范围：<br>
            <select name="StdTypes" MULTIPLE size="5" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" >
             <#list stdTypes?sort_by("code") as stdType>
              <option value="${stdType.id}"><@i18nName stdType/></option>
             </#list>
            </select>
           </td>
           <td valign="middle">
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['StdTypes'], this.form['SelectedStdType'])" type="button" value="&gt;"> 
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])" type="button" value="&lt;"> 
            <br>
           </td> 
           <td>
            已关联:<br>
            <select name="SelectedStdType" MULTIPLE size="5" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedStdType'], this.form['StdTypes'])">
             <#list department.stdTypes?sort_by("code")  as stdType>
              <option value="${stdType.id}"><@i18nName stdType/></option>
             </#list>
            </select>
            </td>
           </tr>
          </table>
	      </td>	      
	   </tr>
	   <tr>
	     <td class="title">  <@msg.message key="attr.createAt"/>: </td>
	     <td>${(department.createAt?string("yyyy-MM-dd"))?if_exists}</td>
	     <td rowspan="2"  id="f_remark"  class="title"><@msg.message key="common.remark"/>:</td>
	     <td rowspan="2" ><textarea name="department.remark"  cols="20" rows="2">${department.remark?if_exists}</textarea></td>
	   </tr>
	   <tr>
	     <td class="title">  <@msg.message key="attr.modifyAt"/>:</td>
	     <td>${(department.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
	   </tr>
	   <tr>
	     <td colspan="4" align="center" class="darkColumn">
   	       <input type="hidden" name="stdTypeIdSeq" value=""/>
           <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>"  class="buttonStyle"/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>"  class="buttonStyle"/> 
	     </td>
	   </tr>
	   </form> 
     </table>
    </td>
   </tr>
  </table>
   <script language="javascript" > 
     function reset(){
       	   document.departmentForm.reset();
     }
     function save(form){
        form.stdTypeIdSeq.value = getAllOptionValue(form.SelectedStdType);  
         var a_fields = {
         'department.code':{'l':'<@msg.message key="attr.code"/>', 'r':true, 't':'f_code'},
         'department.name':{'l':'<@msg.message key="attr.name"/>', 'r':true, 't':'f_name'},
         'department.remark':{'l':'<@msg.message key="common.remark"/>','r':false,'t':'f_remark','mx':100}
      };
       	var v = new validator(form , a_fields, null);
       	if (v.exec()) {
       		form['department.code'].value = cleanSpaces(form['department.code'].value);
        	form.action = "department.do?method=save"
        	form.submit();
     	}
   }  
 </script>
 </body> 
</html>