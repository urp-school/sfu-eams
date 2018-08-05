<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="validator.js.url" />"></script>
 <body>
 <#assign labInfo><@msg.message key="security.restrictionPattern.info"/></#assign>  
<#include "/templates/back.ftl"/> 
   <form name="moduleForm" action="restrictionPattern.do?method=save" method="post">
   <input type="hidden" name="restrictionPattern.id" value="${(restrictionPattern.id)?if_exists}" style="width:200px;" />
   <input type="hidden" name="paramIds" value=""/>
   <tr>
    <td>
     <table width="70%" class="formTable" align="center">
	   <tr class="darkColumn">
	     <td  colspan="2">数据限制模式</td>
	   </tr>
	   <tr>
	     <td class="title" id="f_name"><@msg.message key="attr.name"/><font color="red">*</font>：</td>
	     <td >
          <input type="text" name="restrictionPattern.name" value="${restrictionPattern.name?if_exists}" style="width:200px;" />     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_pattern">模式<font color="red">*</font>：</td>
	     <td >
          <textarea  style="width:500px;" rows="4" name="restrictionPattern.pattern" >${restrictionPattern.pattern?if_exists}</textarea>     
         </td>
	   </tr>
	   <tr>
	     <td class="title" id="f_description">描述<font color="red">*</font>：</td>
	     <td >
	        <input name="restrictionPattern.description" value="${restrictionPattern.description?if_exists}"/>
         </td>
	   </tr>
   <tr>
    <td class="title" id="f_params">参数：</td>
    <td >
     <table>
      <tr>
       <td>
        <select name="Params" MULTIPLE size="10" style="width:200px" onDblClick="JavaScript:moveSelectedOption(this.form['Params'], this.form['SelectedParam'])" >
         <#list patternParams as param>
          <option value="${param.id}">${param.name}</option>
         </#list>
        </select>
       </td>
       <td  valign="middle">
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['Params'], this.form['SelectedParam'])" type="button" value="&gt;"> 
        <br><br>
        <input OnClick="JavaScript:moveSelectedOption(this.form['SelectedParam'], this.form['Params'])" type="button" value="&lt;"> 
        <br>
       </td> 
       <td  class="normalTextStyle">
        <select name="SelectedParam" MULTIPLE size="10" style="width:200px;" onDblClick="JavaScript:moveSelectedOption(this.form['SelectedParam'], this.form['Params'])">
         <#list restrictionPattern.params?if_exists as param>
          <option value="${param.id}">${param.name}</option>
         </#list>
        </select>
       </td>             
      </tr>
     </table>
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
     form.paramIds.value = getAllOptionValue(form.SelectedParam);  
     var a_fields = {
         'restrictionPattern.name':{'l':'<@msg.message key="attr.name"/>', 'r':true,'t':'f_name'},
         'restrictionPattern.description':{'l':'标题', 'r':true, 't':'f_description'},
         'restrictionPattern.pattern':{'l':'模式', 'r':true, 't':'f_pattern'}
     };
     var v = new validator(form, a_fields, null);
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
 </body>
<#include "/templates/foot.ftl"/>