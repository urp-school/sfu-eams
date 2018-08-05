<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>一级学科</#assign>  
<#include "/templates/back.ftl"/>
  <table width="80%" align="center" class="formTable">
    <form action="level1Subject.do?method=save" name="level1SubjectForm" method="post" onsubmit="return false;"> 
    <tr>
      <td align="center" id="f_code" class="grayStyle">学科代码:<font color="red">*</font></td>
      <td class="brightStyle" >
      <input id="codeValue" type="text"  name="level1Subject.code" maxlength="32" value="${level1Subject.code?if_exists}" maxLength="32" style="width:80px;">
      <#assign className="Level1Subject">
      <#include "/pages/system/checkCode.ftl"/>
      <input type="hidden"  name="level1Subject.id" value="${level1Subject.id?if_exists}">
      </td>
      <td align="center" id="f_name" class="grayStyle">学科名称:<font color="red">*</font></td>
      <td class="brightStyle" ><input type="text" name="level1Subject.name" maxlength="30" value="${level1Subject.name?if_exists}"  maxLength="25"></td>
    </tr>
    <tr>
      <td align="center" class="grayStyle" id="f_category">学科门类:<font color="red">*</font></td>
      <td class="brightStyle" >
          <select name="level1Subject.category.id">
              <option value="">请选择...</option>
	          <#list result.categoryList?if_exists?sort_by("name") as category>
				 <option value=${category?if_exists.id?if_exists} <#if category?if_exists.id?if_exists?string==level1Subject.category?if_exists.id?if_exists?string>selected</#if>>${category?if_exists.name?if_exists}</option>
		      </#list>
	      </select>
	  </td>    
      <td align="center" id="f_version" class="grayStyle">英文名称:</td>
	  <td class="brightStyle" ><input type="text" name="level1Subject.engName" maxlength="30" value="${level1Subject.engName?if_exists}" maxLength="25"/></td>        
     </tr>  
     <tr>
      <td align="center" class="grayStyle">博士学位授予权:</td>
      <td class="brightStyle" >
          <select name="level1Subject.forDoctor">
	      	 <option value=0 <#if "0"==level1Subject.forDoctor?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level1Subject.forDoctor?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
      </td>
      <td align="center" id="f_ISBN" class="grayStyle">硕士学位授予权:</td>
      <td class="brightStyle" >
          <select name="level1Subject.forMaster">
	      	 <option value=0 <#if "0"==level1Subject.forMaster?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level1Subject.forMaster?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
     </tr> 
     <tr>  
      <td align="center" class="grayStyle">批准时间:</td>
	  <td class="brightStyle"  colspan="3"><input type="text" name="level1Subject.ratifyTime" maxlength="10" onfocus="calendar()" value="${level1Subject.ratifyTime?if_exists}" maxLength="25"/></td>        
     </tr>  
    <tr align="center"  class="darkColumn">
      <td colspan="5">
          <input type="button" onClick='save(this.form)' value="提交" class="buttonStyle"/>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" onClick='reset()' value="重置" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  
  <script language="javascript" > 
  
     function reset(){
         document.level1SubjectForm.reset();
     }
     function cancel(){
           setDefaultDate(document.level1SubjectForm);
       	   document.level1SubjectForm.action="level1SubjectManage.do?method=index&hold=true";
       	   document.level1SubjectForm.submit();
       }
    function save(form){
	    var a_fields = {
	       	'level1Subject.code':{'l':'学科代码', 'r':true, 't':'f_code','mx':'32'},
	       	'level1Subject.name':{'l':'学科名称', 'r':true, 't':'f_name'},
	       	'level1Subject.category.id':{'l':'学科门类', 'r':true, 't':'f_category'}
		};
	  
	    var v = new validator(form, a_fields, null);
	    if (v.exec()) {
	       form.action = "level1Subject.do?method=save";
	       form.submit();
	    }
    }
 </script>
 </body> 
</html>