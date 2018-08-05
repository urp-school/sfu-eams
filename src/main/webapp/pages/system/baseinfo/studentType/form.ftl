<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
 <#assign labInfo><@bean.message key="page.studentTypeForm.label" /></#assign>     
<#include "/templates/back.ftl"/>

  <table width="90%"  align="center" class="formTable">
    <form action="studentType.do?method=edit" name="studentTypeForm" method="post" onsubmit="return false;">
    <@searchParams/>
    <tr class="darkColumn">
      <td align="left" colspan="4"><B><@bean.message key="page.studentTypeForm.label" /></B></td>
    </tr>
    <tr>
      <td  id="f_code" class="title"><font color="red">*</font><@bean.message key="attr.code" />:</td>
      <td>
      <#if studentType.code?exists>
      <input id="codeValue" type="text" name="studentType.code" value="${studentType.code}" maxLength="32" style="width:80px;">
      <#else>
      <input id="codeValue" type="text" name="studentType.code" value="${RequestParameters['superCode']?if_exists}" maxLength="32" style="width:80px;">
      </#if>
      <input type="hidden" name="studentType.id" value="${studentType.id?if_exists}">
      </td>
      <td  id="f_name" class="title"><font color="red">*</font><@bean.message key="attr.infoname" />:</td>
      <td><input type="text" name="studentType.name" value="${studentType.name?if_exists}" maxLength="20"></td>
    </tr>
    <tr>
      <td  id="f_engName" class="title"><@bean.message key="attr.engName" />:</td>
      <td><input type="text" name="studentType.engName"  value="${studentType.engName?if_exists}" maxLength="100"/></td>
      <td  id="f_abbreviation" class="title"><@bean.message key="common.abbreviation" />:</td>
      <td><input type="text" name="studentType.abbreviation"  value="${studentType.abbreviation?if_exists}" maxLength="25"/></td>
    </tr>
    <tr >
      <td  class="title" ><@bean.message key="attr.dateEstablished" />:</td>
  	  <td colspan="3">
  	     <input type="text" name="studentType.dateEstablished" id="dateEstablished" maxlength="10" onfocus="calendar()" onload=''
  	         value="${(studentType.dateEstablished?string("yyyy-MM-dd"))?if_exists}" />
  	  </td>
    </tr>
    <tr>
      <td  class="title" ><@bean.message key="attr.parentStudentType" />:</td>
      <td><@htm.i18nSelect datas=superStdTypes selected="${studentType.superType?if_exists.id?if_exists}" name="studentType.superType.id" style="width:150px" type="hidden" value="">
          <option value="">...</option>
          </@>
      </td>
      <td  class="title" >是否使用:</td>
      <td><@htm.radio2  name="studentType.state" value=studentType.state?default(true)/></td>
    </tr>
    <#--
    <tr>
	      <td  class="title">关联院系部</td>
	      <td colspan="3">
	       <table width="100%" class="infoTitle">
	         <tr>
   	          <td>
	        可选范围：<br>
            <select name="Departs" MULTIPLE size="5" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Departs'], this.form['SelectedDepart'])" >
             <#list departs?sort_by("code") as depart>
              <option value="${depart.id}"><@i18nName depart/></option>
             </#list>
            </select>
           </td>
           <td  valign="middle">
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['Departs'], this.form['SelectedDepart'])" type="button" value="&gt;"> 
            <br><br>
            <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedDepart'], this.form['Departs'])" type="button" value="&lt;"> 
            <br>
           </td>
           <td>
            已关联:<br>
            <select name="SelectedDepart" MULTIPLE size="5" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedDepart'], this.form['Departs'])">
             <#list studentType.departs?sort_by("name")  as depart>
              <option value="${depart.id}"><@i18nName depart/></option>
             </#list>
            </select>
            </td>
           </tr>
          </table>
	      </td>
	</tr>
    -->
    <tr>
      <td  class="title" ><@bean.message key="attr.isBaseStudentType" />:</td>
      <td>
          <input type="radio" value="1" name="studentType.isBase" <#if isBase?if_exists == true>checked</#if> /><@bean.message key="common.yes" />
          <input type="radio" value="0" name="studentType.isBase" <#if isBase?if_exists == false>checked</#if> /><@bean.message key="common.no" />
      </td>
      <td  id="f_remark" class="title"><@bean.message key="common.remark" />:</td>
      <td ><input name="studentType.remark" value="${studentType.remark?if_exists}" maxlength="30"/></td>
    </tr>
    
    <tr>
      <td   class="title">  <@bean.message key="attr.createAt" />: </td>
      <td>${(studentType.createAt?string("yyyy-MM-dd"))?if_exists}</td>
      <td   class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${(studentType.modifyAt?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
    
    <tr  class="darkColumn" align="center">
      <td colspan="5">
          <input type="hidden" name="departIdSeq" value=""/>
          <input type="button" onClick='save(this.form)' value="<@bean.message key="system.button.submit"/>" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  
  <script language="javascript" > 
     function reset(){
         document.studentTypeForm.reset();
     }
    function save(form){
      //form.departIdSeq.value = getAllOptionValue(form.SelectedDepart);  
      var a_fields = {
         'studentType.code':{'l':'<@bean.message key="attr.code" />', 'r':true, 't':'f_code'},
         'studentType.name':{'l':'<@bean.message key="attr.name" />', 'r':true, 't':'f_name'}
      };
      var v = new validator(form , a_fields, null);
      if (v.exec()) {
      	form['studentType.code'].value = cleanSpaces(form['studentType.code'].value);
        form.action="studentType.do?method=save"
        form.submit();
      }
   }
 </script>
 </body> 
</html>