<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>二级学科点信息</#assign>  
<#include "/templates/back.ftl"/>
  <table width="80%" align="center" class="formTable">
    <form action="level2Subject.do?method=save" name="level2SubjectForm" method="post" onsubmit="return false;">
     <input type="hidden" name="level2Subject.id" value="${level2Subject.id?if_exists}"> 
    <#if level2Subject.id?exists>
    <tr>
      <td align="center" class="title">学科点名称:</td>
      <td>${level2Subject.speciality.name}</td>
      <td align="center" class="title">学科点代码:<font color="red">*</font></td>
      <td>${level2Subject.speciality.code}</td>
    </tr>
    <#else>
    <tr>
      <td align="center" class="title">学科点名称:<font color="red">*</font></td>
      <td>
	      <select name="level2Subject.speciality.id" style="width:200px" onChange="setSpecialityCode(this.value);">
	      <#list specialityList?sort_by("name") as speciality>
	      	<option value="${speciality.id}">${speciality.name}</option>
	      </#list>
	      </select>
      </td>
      <td align="center" class="title">学科点代码:<font color="red">*</font></td>
      <td id="speciality.code"></td>
    </tr>
    </#if>
    <tr>
      <td align="center" class="title">一级学科:</td>
      <td class="brightStyle" >
          <select name="level2Subject.level1Subject.id">
	          <#list categoryList?if_exists?sort_by("name") as category>
				 <option value=${category?if_exists.id?if_exists} <#if category?if_exists.id?if_exists?string==level2Subject.level1Subject?if_exists.id?if_exists?string>selected</#if>>${category?if_exists.name?if_exists}</option>
		      </#list>
	      </select>
	  </td>
	  <td align="center" class="title">批准时间:</td>
	  <td class="brightStyle" ><input type="text" name="level2Subject.ratifyTime" onfocus="calendar()" value="${(level2Subject.ratifyTime?string("yyyy-MM-dd"))?if_exists}" maxLength="10"/></td>            
     </tr>  
     <tr>
      <td align="center" class="title">是否专业学位:</td>
      <td class="brightStyle" >
          <select name="level2Subject.isSpecial">
	      	 <option value=0 <#if "0"==level2Subject.isSpecial?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.isSpecial?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
      </td>
      <td align="center" id="f_ISBN" class="title">是否自主设置:</td>
      <td class="brightStyle" >
          <select name="level2Subject.isReserved">
	      	 <option value=0 <#if "0"==level2Subject.isReserved?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.isReserved?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
     </tr> 
     <tr>
      <td align="center" class="title">博士学位授予权:</td>
      <td class="brightStyle" >
          <select name="level2Subject.forDoctor">
	      	 <option value=0 <#if "0"==level2Subject.forDoctor?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.forDoctor?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
      </td>
      <td align="center" id="f_ISBN" class="title">硕士学位授予权:</td>
      <td class="brightStyle" >
          <select name="level2Subject.forMaster">
	      	 <option value=0 <#if "0"==level2Subject.forMaster?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.forMaster?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
     </tr>
     <tr>
      <td align="center" class="title">是否自主设置的博士点:</td>
      <td class="brightStyle" >
          <select name="level2Subject.isSelfForDoctor">
	      	 <option value=0 <#if "0"==level2Subject.isSelfForDoctor?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.isSelfForDoctor?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
      </td>
      <td align="center" id="f_ISBN" class="title">是否支主设置的硕士点:</td>
      <td class="brightStyle" >
          <select name="level2Subject.isSelfForMaster">
	      	 <option value=0 <#if "0"==level2Subject.isSelfForMaster?if_exists?string("1","0")>selected</#if>>无</option>
	      	 <option value=1 <#if "1"==level2Subject.isSelfForMaster?if_exists?string("1","0")>selected</#if>>有</option>
		  </select>
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
  
  <script language="javascript">
  	var form = document.level2SubjectForm;
    <#if level2Subject.id?exists><#else>
    var specialities=new Object();
    <#list specialityList?sort_by("name") as speciality>
      	specialities['${speciality.id}']='${speciality.code}';
    </#list>
    function setSpecialityCode(id){
        if(id!=""){
           $('speciality.code').innerHTML=specialities[id];           
        }else{
          	$('speciality.code').innerHTML="";
        }
    }
    // 当第一次进入页面时，触发动作
    setSpecialityCode(form["level2Subject.speciality.id"].value);
    </#if>
    function reset(){
		form.reset();
    }
    function save(form){    
		form.action="level2Subject.do?method=save";
		form.submit();	
    }
   
 </script>
 </body> 
</html>