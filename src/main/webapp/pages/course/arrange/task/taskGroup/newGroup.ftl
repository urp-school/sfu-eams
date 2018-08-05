<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<#assign labInfo><#if taskGroup.id?exists> <@bean.message key="action.modify" /><#else> <@bean.message key="action.new" /></#if><@bean.message key="entity.taskGroup" />(1)</#assign>
<#include "/templates/back.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
    <table width="60%" align="center" class="formTable">
       <form name="taskGroupForm" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="task.calendar.id" value="${RequestParameters['calendar.id']}"/>
        <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="entity.taskGroup"/> <@bean.message key="common.baseInfo"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="25%" id="f_name"><font color="red">*</font>&nbsp;<@bean.message key="attr.infoname"/>：</td>
	     <td><input name="taskGroup.name" maxlength="25" value="${taskGroup.name?if_exists}"/></td>
       </tr>
       <tr>
	     <td class="title" width="25%" id="f_priority"><font color="red">*</font>&nbsp;<@bean.message key="attr.priority"/>：</td>
	     <td >
	         <select name="taskGroup.priority" style="width:50px">
                   <#list 1..9 as i>
                   <option value="${i}"<#if i == taskGroup.priority> selected</#if>>${i}</option>
                   </#list>
               </select><@bean.message key="attr.priority" />[1-9] <@bean.message key="common.increase"/>
         </td>
       </tr>
       <tr>
	     <td class="title" width="30%" id="f_remark">&nbsp;<@bean.message key="attr.remark"/>：</td>
	     <td><input name="taskGroup.remark" value="${taskGroup.remark?if_exists}" maxlength="30"/></td>
       </tr>
       <tr>
	     <td class="title" width="25%" id="f_isSameTime"><font color="red">*</font>&nbsp;<@bean.message key="attr.isSameTime"/>：</td>
	     <td>
	         <input type="radio" name="taskGroup.isSameTime" value="1"/><@bean.message key="common.yes"/>
	         <input type="radio" name="taskGroup.isSameTime" checked value="0"/><@bean.message key="common.no"/>
	     </td>
       </tr>

	   <tr class="darkColumn">
	     <td colspan="6" align="center">
	       <input type="button" value="<@bean.message key="action.next"/>" name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset" name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle"/>
	     </td>
	   </tr>
	   </form>
</table>
  <script language="javascript">
   function save(form){
     var a_fields = {
         'taskGroup.name':{'l':'<@bean.message key="attr.infoname" />','r':true,'t':'f_name'},
         'taskGroup.priority':{'l':'<@bean.message key="attr.priority" />','r':true,'t':'f_priority','f':'unsigned'}
     };
     var v = new validator(form, a_fields, null);
     form.action="taskGroup.do?method=lonelyTaskList";
     if (v.exec()) {
        form.submit();
     }
   }
  </script>
</body>
<#include "/templates/foot.ftl"/>