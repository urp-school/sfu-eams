<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="timeSettingBar"></table>
  <table width="100%"  height="85%" class="frameTable">
    <tr>     
     <td style="width:20%" class="frameTable_view">
     <#include "settingList.ftl"/>
     </td>
     
     <td valign="top" width="80%">
     <iframe  src="#"
     id="contentFrame" name="contentFrame" 
     marginwidth="0" marginheight="0" scrolling="no"
     frameborder="0"  height="100%" width="100%">
     </iframe>
     </td>
    </tr>
  <table>
  <script>
      function loadSetting(settingId){
          contentFrame.window.location="timeSetting.do?method=info&timeSetting.id="+settingId;
      }
      <#if (settingList?size>0)>
         defaultSetting.onclick();
      </#if>
      function add(){
         contentFrame.window.location="timeSetting.do?method=edit";
     }
   var bar = new ToolBar('timeSettingBar','上课时间管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@bean.message key="action.new"/>",add,'new.gif');        
  </script>
<#include "/templates/foot.ftl"/>  