<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<#assign labInfo>编辑代码生成规则</#assign>
<#include "/templates/back.ftl"/>
  <table width="90%" align="center" class="formTable">
    <form action="codeScript.do?method=save" name="actionForm" method="post" onsubmit="return false;">
    <input type="hidden"  name="codeScript.id" value="${codeScript.id?if_exists}"/>
    <@searchParams/>
    <tr class="darkColumn">
      <td colspan="4"><B>编辑代码生成规则</B></td>
    </tr>
    <tr>
      <td  id="f_codeName" class="title"><font color="red">*</font>编码对象:</td>
      <td><input id="codeValue" type="text"  name="codeScript.codeName" value="${codeScript.codeName?if_exists}" maxLength="32" style="width:80px;"></td>
      <td  id="f_attr" class="title"><font color="red">*</font>编码属性:</td>
      <td><input type="text" name="codeScript.attr" value="${codeScript.attr?if_exists}" maxLength="25"></td>
    </tr>
    <tr>
      <td  id="f_codeClassName" class="title"><font color="red">*</font>编码类型:</td>
      <td colspan="3"><input type="text" name="codeScript.codeClassName" style="width:400px"  value="${codeScript.codeClassName?if_exists}" maxLength="100"/></td>
    </tr>
    <tr>
      <td  id="f_description" class="title"><font color="red">*</font>简要描述:</td>
      <td colspan="3"><input type="text" name="codeScript.description" style="width:400px" value="${codeScript.description?if_exists}" maxLength="25"/></td>
    </tr>
    <tr>
       <td class="title">说明:</td> 
       <td colspan="3">此脚本采用的是<A href="http://www.beanshell.org" target="_blank">beanshell</a>语言.<br>
        生成脚本的程序中，可以使用预先定义的变量<br>1)entity表示要生成代码的实体类对象。<br>2)seq[4]表示生成代码段中的顺序增长部分,其中长度为4.
        <br>3)可是使用dao向数据库中查询其他数据。
       </td>
    </tr>
    <tr>
       <td  id="f_script" class="title"><font color="red">*</font>编码脚本:</td>
       <td colspan="3" ><textarea name="codeScript.script" style="font-size:11px" cols="70" rows="8">${codeScript.script?default("")}</textarea></td>
    </tr>
    <tr>
      <td class="title">  <@bean.message key="attr.createAt" />: </td>
      <td>${(codeScript.createAt?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</td>    
      <td class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${(codeScript.modifyAt?string("yyyy-MM-dd hh:mm:ss"))?if_exists}</td>
    </tr>
    <tr class="darkColumn" align="center" >
      <td colspan="5">
          <button onclick="save(this.form)"><@bean.message key="system.button.submit"/></button>&nbsp;
          <button onClick="test(this.form)">测试</button>&nbsp;
          <input type="button" onClick='reset()' value="<@bean.message key="system.button.reset"/>" class="buttonStyle"/>
      </td>
    </tr>
    </form> 
  </table>
  <#if testResult?exists><div width="80%" >测试结果</div></#if>
  <div  width="80%" style="">${testResult?default("")}</div>
  <script language="javascript" > 
     function reset(){
         document.codeScriptForm.reset();
     }
     function validatorScript(form){
	     var a_fields = {
	         'codeScript.codeName':{'l':'编码对象', 'r':true, 't':'f_codeName'},
	         'codeScript.attr':{'l':'编码属性', 'r':true, 't':'f_attr'},
	         'codeScript.codeClassName':{'l':'编码类型', 'r':true, 't':'f_codeClassName'},
	         'codeScript.description':{'l':'简要描述', 'r':true, 't':'f_description'},
	         'codeScript.script':{'l':'编码脚本', 'r':true, 't':'f_script','mx':1000}
	     };
	     var v = new validator(form , a_fields, null);
	     return v.exec();
     }
     function test(form){
        if (validatorScript(form)) {
           form.action="codeScript.do?method=test";
           form.submit();
        }
     }
     function save(form){
      if (validatorScript(form)) {
        form.action="codeScript.do?method=save";
        form.submit();
      }
   }
 </script>
 </body> 
</html>