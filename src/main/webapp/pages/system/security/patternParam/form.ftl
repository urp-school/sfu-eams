<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
 <#assign labInfo><@msg.message key="info.moduleUpdate"/></#assign>  
<#include "/templates/back.ftl"/> 
   <form name="moduleForm" action="patternParam.do?method=save" method="post">
   <input type="hidden" name="patternParam.id" value="${(patternParam.id)?if_exists}" style="width:200px;" />
   <tr>
    <td>
     <table width="70%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2">数据限制参数</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_name"><@msg.message key="attr.name"/><font color="red">*</font>：</td>
	     <td>
          <input type="text" name="patternParam.name" value="${patternParam.name?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_type">类型<font color="red">*</font>：</td>
	     <td >
          <input type="text" name="patternParam.type" value="${patternParam.type?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description">标题<font color="red">*</font>：</td>
	     <td >
	        <input name="patternParam.description" value="${patternParam.description?if_exists}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_multiValue">是否多值<font color="red">*</font>：</td>
	     <td ><input type="radio" <#if (patternParam.multiValue)?default(true)>checked</#if> value="1" name="patternParam.multiValue">是
	          <input type="radio" <#if !(patternParam.multiValue)?default(true)>checked</#if> value="0" name="patternParam.multiValue">否
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_referenceType">引用类型：</td>
	     <td >
          <input type="text" name="patternParam.editor.source" value="${(patternParam.editor.source)?if_exists}" style="width:400px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_valueProperty">值属性：</td>
	     <td >
	        <input  name="patternParam.editor.idProperty" value="${(patternParam.editor.idProperty)?if_exists}"/>
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_titleProperty">显示属性：</td>
	     <td >
	        <input name="patternParam.editor.properties" value="${(patternParam.editor.properties)?if_exists}"/>
         </td>
	   </tr>
	   <tr class="darkColumn" align="center">
	     <td colspan="6"  >
	       <input type="button" value="<@msg.message key="action.submit"/>" name="button1" onClick="save(this.form)" class="buttonStyle" />
	       <input type="reset"  name="reset1" value="<@msg.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript" >
   function save(form){
     var a_fields = {
         'patternParam.name':{'l':'<@msg.message key="attr.name"/>', 'r':true,'t':'f_name'},
         'patternParam.description':{'l':'标题', 'r':true, 't':'f_description'},
         'patternParam.type':{'l':'类型', 'r':true, 't':'f_type'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>