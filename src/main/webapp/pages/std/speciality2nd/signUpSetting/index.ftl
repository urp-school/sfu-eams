<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="myBar" width="100%"></table>
    <table class="frameTable_title">
     <tr>
      <td class="infoTitle"></td>
      <form name="settingForm" method="post" action="speciality2ndSignUpSetting.do?method=index" onsubmit="return false;">
      <input type="hidden" name="signUpSetting.calendar.id" value="${calendar.id}"/>
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </form>
  </table>
  <table class="frameTable" height="85%">
    <tr>
     <td class="frameTable_content">
	     <iframe src="speciality2ndSignUpSetting.do?method=search&signUpSetting.calendar.id=${calendar.id}" id="settingListFrame" name="settingListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"></iframe>
     </td>
  </tr>
</table>
<script>
  var bar = new ToolBar("myBar","辅修专业报名设置",null,true,true);
  bar.addItem("级差设定","gpaSetting()");
  bar.addHelp("<@msg.message key="action.help"/>");
  
  function search(){
     settingListFrame.location="speciality2ndSignUpSetting.do?method=search&signUpSetting.calendar.id=${calendar.id}";
  }
  function gpaSetting(){
     settingListFrame.location='speciality2ndSignUpGPASetting.do?method=search';
  }
</script>
</body>
<#include "/templates/foot.ftl"/>