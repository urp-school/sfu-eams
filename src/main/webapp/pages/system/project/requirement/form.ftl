<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">
<#assign labInfo>系统需求详细信息</#assign>
<#include "/templates/back.ftl"/>
<#include "code.ftl"/>
  <table width="100%"  class="formTable" align="center">
    <form action="require.do?method=edit" name="requireForm" method="post" onsubmit="return false;">
    <input name="params" type="hidden" value="${RequestParameters['params']?default("")}">
    <tr class="darkColumn">
      <td align="left" colspan="4"><B>系统需求详细信息</B></td>
    </tr>
    <tr>
      <td  id="f_module" class="title" ><font color="red">*</font>模块名称:</td>
      <td>
      <input id="codeValue" type="text"  name="require.module" value="${require.module?if_exists}" maxLength="32" style="width:80px;">
      <input type="hidden"  name="require.id" value="${require.id?if_exists}"/>
      </td>
      <td  id="f_developers" class="title"><font color="red">*</font>负责人:</td>
      <td><input type="text" name="require.developers" value="${require.developers?if_exists}" maxLength="25"></td>
    </tr>
    <tr>
      <td  id="f_fromUser" class="title"><font color="red">*</font>建议人:</td>
      <td><input type="text" name="require.fromUser"  value="${require.fromUser?if_exists}" maxLength="20"/></td>
      <td  id="f_stackHolders" class="title">其他相关人:</td>
      <td><input type="text" name="require.stackHolders"  value="${require.stackHolders?if_exists}" maxLength="25"/></td>
     </tr>
    <tr>
      <td  class="title">优先级:</td>
      <td>
         <select name="require.priority" style="width:100px">
            <#list priorityMap?keys as priority>
            <option value="${priority}" <#if priority=require.priority?default(2)?string> selected</#if>>${priorityMap[priority]}</option>
            </#list>
         </select>
      </td>
      <td   class="title">类型:</td>
      <td>
      
         <select  name="require.type" style="width:100px">
            <#list typeMap?keys as type>
            <option value="${type}" <#if type=require.type?default(1)?string> selected</#if>>${typeMap[type]}</option>
            </#list>
         </select>
      </td>
     </tr>
     <tr>
       <td  id="f_background" class="title">意见背景:</td>
       <td   colspan="3">
          <textarea name="require.background" cols="50">${require.background?if_exists}</textarea>
       </td>
     </tr>
     <tr>
       <td  id="f_content" class="title"><font color="red">*</font>意见内容:</td>
       <td   colspan="3">
          <textarea name="require.content" cols="50">${require.content?if_exists}</textarea>
       </td>    
     </tr>
     <tr>
       <td  id="f_suggestSolution" class="title">建议方案:</td>
       <td   colspan="3" cols="60">
          <textarea name="require.suggestSolution" cols="50">${require.suggestSolution?if_exists}</textarea>
       </td>    
     </tr>
    <tr >
      <td class="title">状态: </td>
      <td>
         <select  name="require.status" style="width:100px">
            <#list statusMap?keys as status>
            <option value="${status}" <#if status=require.status?default(1)?string> selected</#if>>${statusMap[status]}</option>
            </#list>
         </select>
      </td>    
      <td  class="title">估计工作量(人/日):</td>
      <td><input name="require.workload" value="${require.workload?if_exists}"></td>
    </tr>
    <tr >
      <td class="title" id="f_planCompleteOn"><font color="red">*</font>计划完成时间:</td>
      <td><input name="require.planCompleteOn" value="${(require.planCompleteOn?string("yyyy-MM-dd"))?if_exists}" onfocus="calendar()"></td>    
      <td  class="title">实际完成时间:</td>
      <td><input name="require.actualCompleteOn" value="${(require.actualCompleteOn?string("yyyy-MM-dd"))?if_exists}"  onfocus="calendar()"></td>
    </tr>
    <tr >
      <td class="title"><@bean.message key="attr.createAt" />: </td>
      <td>${(require.createdOn?string("yyyy-MM-dd"))?if_exists}</td>    
      <td  class="title">  <@bean.message key="attr.modifyAt" />:</td>
      <td>${(require.lastModifiedOn?string("yyyy-MM-dd"))?if_exists}</td>
    </tr>
  
    <tr   class="darkColumn" align="center">
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
         document.requireForm.reset();
     }
     function save(form,params){
         var a_fields = {
         'require.module':{'l':'模块名称', 'r':true, 't':'f_module'},
         'require.content':{'l':'意见内容', 'r':true, 't':'f_content','mx':200},
         'require.developers':{'l':'负责人', 'r':true, 't':'f_developers'},
         'require.fromUser':{'l':'建议人', 'r':true, 't':'f_fromUser'},
         'require.suggestSolution':{'l':'建议方案', 'r':false, 't':'f_suggestSolution','mx':200},
         'require.background':{'l':'建议背景', 'r':false, 't':'f_background','mx':200},
         'require.planCompleteOn':{'l':'计划完成时间', 'r':true, 't':'f_planCompleteOn'}
     };
     
     var v = new validator(form , a_fields, null);
     if (v.exec()) {
        form.action="requirement.do?method=save";
        if(null!=params){
           form.action+=params;
        }
        form.submit();
     }
   }
 </script>
 </body> 
</html>