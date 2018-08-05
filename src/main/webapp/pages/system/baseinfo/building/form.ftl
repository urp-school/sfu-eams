<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<#assign labInfo><@bean.message key="page.buildingForm.label" /></#assign>
<#include "/templates/back.ftl"/>
  <table width="80%" align="center" class="formTable">
    <form action="building.do?method=edit" name="buildingForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td colspan="4"><B><@bean.message key="page.buildingForm.label" /></B></td>
    </tr>
    <tr>
      <td id="f_code" class="title"><font color="red">*</font><@bean.message key="attr.code" />:</td>
      <td>
      <input id="codeValue" type="text" name="building.code" value="${building.code?if_exists}" maxLength="32" style="width:80px;">
      <#assign className="Building">
      <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden"  name="building.id" value="${building.id?if_exists}"/>
      </td>
      <td id="f_name" class="title"><font color="red">*</font><@bean.message key="attr.infoname" />:</td>
      <td><input type="text" name="building.name" value="${building.name?if_exists}" maxLength="20"></td>
    </tr>
    <tr>
      <td id="f_engName" class="title"><@bean.message key="attr.engName" />:</td>
      <td><input type="text" name="building.engName" value="${building.engName?if_exists}" maxLength="100"/></td>
      <td id="f_abbreviation" class="title"><@bean.message key="common.abbreviation"/>:</td>
      <td><input type="text" name="building.abbreviation"  value="${building.abbreviation?if_exists}" maxLength="25"/></td>
     </tr>
     <tr>
       <td id="f_district" class="title"><@bean.message key="common.schoolDistrict" />:</td>
       <td>
          <#if (building.schoolDistrict.id)?exists>
          	<#assign districtId = building.schoolDistrict.id>
          <#else>
             <#assign districtId = 0>
          </#if>
          <select name="building.schoolDistrict.id"  style="width:155px">
          <#list districtList as district>
              <option value="${district.id}" <#if district.id==districtId>selected</#if>>${district.name}
          </#list>
       </td>
       <td class="title">是否使用:</td>
       <td><@htm.radio2  name="building.state" value=building.state?default(true)/></td>       
     </tr>
    <tr >
      <td class="title">  <@bean.message key="attr.createAt" />: </td>
      <td>${(building.createAt?string("yyyy-MM-dd"))?if_exists}</td>    
      <td class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${(building.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    <tr class="darkColumn" align="center">
      <td colspan="5">
          <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          <input type="button" onClick='save(this.form,"&addAnother=1")' value="保存并添加下一个" class="buttonStyle"/>
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  <script language="javascript" > 
     function reset(){
         document.buildingForm.reset();
     }
    function save(form,params){
         var a_fields = {
         'building.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code'},
         'building.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'}
         };
		 var v = new validator(form , a_fields, null);
		 if (v.exec()) {
		 	form['building.code'].value = cleanSpaces(form['building.code'].value);
		    form.action="building.do?method=save";
		    if(null!=params)
		       form.action+=params;
		    form.submit();
		 }
   }
 </script>
 </body> 
</html>