<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <body  >
   <table id="requireBar" width="100%"></table>
   <table width="90%" align="center" class="formTable">
      <form name="requireForm" action="requirePrefer.do?method=saveRequirement" method="post" onsubmit="return false;">
	    <input type="hidden" name="requirementType" value="${RequestParameters['requirementType']}"/>
	    <#if RequestParameters['requirementType']=="inTask">
	     <input type="hidden" name="task.id" value="${task.id}" />
	     <#assign require=task.requirement>
	     <#else>
	     <input type="hidden" name="requirementPreference.id" value="${requirementPreference.id}"/>
	     <input type="hidden" name="requirementPreference.teacher.id" value="${requirementPreference.teacher.id}"/>
	     <input type="hidden" name="requirementPreference.course.id" value="${requirementPreference.course.id}"/>                  
	     <#assign require=requirementPreference.require>
	     </#if>
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@bean.message key="entity.taskRequirement"/></td>
	   </tr>
	   <tr>
	     <td class="title" width="20%">&nbsp;教室要求：</td>	     
	     <td  >
	        <select name="require.roomConfigType.id" style="width:150px">
	        <#list configTypeList as config>
	        <option value=${config.id} <#if require.roomConfigType.id==config.id> selected</#if> ><@i18nName config/></option>
	        </#list>
	     </td>
	     <td class="title">&nbsp;<@bean.message key="attr.teachLangType"/>：</td>	     
	     <td><@htm.i18nSelect datas=teachLangTypes name="require.teachLangType.id" selected=(require.teachLangType.id)?default('')?string /></td>
	   </tr>
	   <tr>
 	     <td class="title">&nbsp;教材：</td> 	     
 	     <td  colspan="3">
 	     <#list require.textbooks as book>
 	       <@i18nName book/> <@i18nName book.press/><br>
         </#list>
          <button class="buttonStyle" onclick="editTextbook()">修改教材</button>
         </td>
       </tr>
	   <tr>
 	     <td class="title"><@bean.message key="attr.referenceBooks"/>：</td> 	     
 	     <td  colspan="3">
 	     <textarea name="require.referenceBooks" cols="40">${require.referenceBooks?if_exists}</textarea></td>
       </tr>
	   <tr>
 	     <td class="title"><@bean.message key="attr.cases"/>：</td> 	     
 	     <td  colspan="3">
 	     	<textarea name="require.cases" cols="40">${require.cases?if_exists}</textarea></td>
       </tr>
	   <tr>
 	     <td class="title">课程要求：</td> 	     
 	     <td  colspan="3">
 	     	<textarea name="require.requireRemark" cols="40">${require.requireRemark?if_exists}</textarea></td>
       </tr>
     </table>
    </td>
   </tr>
   </form>
  </table>
  <script language="javascript">
   function save(form,saveAsPreference){
      var cases = form['require.cases'].value;
      var referenceBooks = form['require.referenceBooks'].value;
      var prompt="";
      if(referenceBooks=="") prompt+="<@bean.message key="info.referencebook.null"/>!\n";
      if(cases=="")prompt+="<@bean.message key="info.cases.null"/>!\n";
      if(prompt!=""&&!confirm(prompt+"<@bean.message key="prompt.submit.comfirm"/>"))return;
      form.action="requirePrefer.do?method=saveRequirement";
      addInput(form,"saveAsPreference",saveAsPreference);
      form.submit();      
   }
   <#if RequestParameters['requirementType']=="inTask">
   function retrievePreference(){
       var form = document.requireForm;
       form.action="requirePrefer.do?method=retrievePreferenceForTask&requirementType=inTask";
       form.submit();
   }
  </#if>
  function editTextbook(){
    var form = document.requireForm;
    <#if RequestParameters['requirementType']=="inTask">
    form.action="requirePrefer.do?method=editTextbook&forward=taskList&requirementType=inTask&task.id=${task.id}";
    <#else>
    form.action="requirePrefer.do?method=editTextbook&forward=preferList&requirementType=inPreference&requirePrefer.id=${requirementPreference.id}";
    </#if>
    form.submit();
  }
  
  <#assign labInfo><#if RequestParameters['requirementType']=="inTask"><@bean.message key="info.taskRequirement.of" arg0="${task.course.name}"/><#else><@bean.message key="info.taskRequirement.preference.of" arg0="${requirementPreference.course.name}"/></#if></#assign>
  var bar = new ToolBar("requireBar","${labInfo}",null,true,true);
  bar.setMessage('<@getMessage/>');
  <#if RequestParameters['requirementType']=="inTask">
  bar.addItem("<@bean.message key="action.extractPreference"/>","retrievePreference()");
  bar.addItem("保存并设置为偏好","save(document.requireForm,1)","save.gif");
  </#if>
  bar.addItem("保存","save(document.requireForm,0)","save.gif");
  bar.addBack("<@msg.message key="action.back"/>");

  </script>
 </body>
<#include "/templates/foot.ftl"/>