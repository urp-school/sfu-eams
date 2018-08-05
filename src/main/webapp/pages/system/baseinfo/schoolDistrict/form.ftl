<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
<body  >
<#assign labInfo><@msg.message key="page.schoolDistrictForm.label" /></#assign>  
<#include "/templates/back.ftl"/>
  <table width="70%" align="center" class="formTable">
    <form action="schoolDistrict.do?method=edit" name="districtForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B><@msg.message key="page.schoolDistrictForm.label" /></B></td>
    </tr>  
    <tr>
      <td id="f_code" class="title"><font color="red">*</font><@msg.message key="attr.code" />:</td>
      <td>
      <input id="codeValue" type="text" name="schoolDistrict.code" value="${schoolDistrict.code?if_exists}" maxLength="32" style="width:80px;">
      <input type="hidden" name="schoolDistrict.id" value="${schoolDistrict.id?if_exists}">
      </td>
      <td id="f_name" class="title"><font color="red">*</font><@msg.message key="attr.infoname" />:</td>
      <td><input type="text" name="schoolDistrict.name" value="${schoolDistrict.name?if_exists}" maxLength="25"></td>
    </tr>
    <tr>
      <td id="f_engName" class="title"><@msg.message key="attr.engName" />:</td>
      <td><input type="text" name="schoolDistrict.engName" value="${schoolDistrict.engName?if_exists}" maxLength="100"/></td>
      <td id="f_abbreviation" class="title"><@msg.message key="common.abbreviation" />:</td>
	  <td><input type="text" name="schoolDistrict.abbreviation" value="${schoolDistrict.abbreviation?if_exists}" maxLength="25"/></td>        
     </tr>    
     <tr>
       <td class="title">是否使用:</td>
       <td colspan="3"><@htm.radio2  name="schoolDistrict.state" value=schoolDistrict.state?default(true)/></td>       
     </tr>
    <tr>
      <td class="title">  <@msg.message key="attr.createAt" />: </td>
      <td>${(schoolDistrict.createAt?string("yyyy-MM-dd"))?if_exists}</td>    
      <td class="title">  <@msg.message key="attr.modifyAt" />:</td>
      <td>${(schoolDistrict.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
   
    <tr align="center" class="darkColumn">
      <td colspan="5">
          <input type="button" onClick='save(this.form)' value="<@msg.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@msg.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  
  <script language="javascript"> 
     function reset(){
         document.districtForm.reset();
     }
    function save(form){
         var a_fields = {
         'schoolDistrict.code':{'l':'<@msg.message key="attr.code" />', 'r':true, 't':'f_code'},
         'schoolDistrict.name':{'l':'<@msg.message key="attr.name" />', 'r':true, 't':'f_name'}
     };
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
     	form['schoolDistrict.code'].value = cleanSpaces(form['schoolDistrict.code'].value);
        form.action = "schoolDistrict.do?method=save"
        form.submit();
     }
   }
 </script>
 </body> 
</html>