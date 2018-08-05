<#include "/templates/head.ftl"/>
<BODY>
<table id="backBar"></table>
  <table  width="100%" class="frameTable_title">
     <tr>
       <td class="infoTitle"><@bean.message key="info.searchForm"/></td>
       <td>|</td>
       <form name="searchForm" method="post" action="speciality2ndSignUpStudent.do?method=index" onsubmit="return false;">
       <td class="infoTitle">
	     <select name="signUpStd.setting.id" onchange="search()" style="width:200px">
	        <#list settings as setting>
	         <option value="${setting.id}" <#if RequestParameters['signUpStd.setting.id']?default("")==setting.id?string> <#assign signUpSetting=setting>selected</#if>>${setting.name}</option>
	        </#list>
	     </select>
	     <#if signUpSetting?exists><#elseif settings?size!=0><#assign signUpSetting=settings?first></#if>
	   </td>  
       <#include "/pages/course/calendar.ftl"/>
   </tr>
  </table>
  <table class="frameTable" height="85%">
    <tr>
     <td class="frameTable_view" style="width:160px">
     <#include "searchForm.ftl"/>
     </td>
     </form>
     <td valign="top">
	     <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
  </tr>
 <script>
   var bar = new ToolBar('backBar','辅修专业报名管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if (settings?size>0)>
   bar.addItem("报名录取统计","specialitySettingList()",null,"统计各个专业的报名和录取信息");
   bar.addItem("自动录取","autoMatriculate()");
   bar.addItem("自动分班","autoAssignClass()");
   </#if>
   bar.addHelp("<@msg.message key="action.help"/>");
   
   var action="speciality2ndSignUpStudent.do";
   var form=document.searchForm;
   
   function search(){
       <#if (settings?size>0)>
	    form.action=action+"?method=search&orderBy=signUpStd.std.code asc";
  	    form.target="contentListFrame";
   	    form.submit();
   	    <#else>
        alert("没有报名设置,不能查询");
        </#if>
    }
   search();
   
   function autoMatriculate(){
      form.action=action+"?method=matriculateSetting";
      form.submit();
   }
   function autoAssignClass(){
      form.action=action+"?method=assignClassSetting";
      form.submit();
   }
   function specialitySettingList(){
      form.action=action+"?method=specialitySettingList&orderBy=[0].limit desc";
      form.submit();
   }
 </script>
</body>
<#include "/templates/foot.ftl"/> 