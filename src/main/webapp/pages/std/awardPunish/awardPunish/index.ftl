<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="bar"></table>
   <table class="frameTable">
   <tr>
    <td  style="width:160px"  class="frameTable_view">
      <form name="actionForm" action="awardPunish.do?method=search" method="post" target="contentFrame" onsubmit="return false;">
      <#include "../awardPunishSearch/searchForm.ftl"/>
      </form>
      </td>
    <td valign="top">
     <iframe src="#" id="contentFrame" name="contentFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe>
	</td>
   </tr>
  </table>
<script language="javascript">
   var form =document.actionForm; 
   function search(type,pageNo,pageSize,orderBy){ 
   	  form.action="awardPunish.do?method=search";
      goToPage(form,pageNo,pageSize,orderBy);
   }
   search("award",1,null,null);
   var bar=new ToolBar('bar','学生奖惩查询',null,true,true);
   bar.setMessage('<@getMessage/>');
   menu = bar.addMenu('导入', 'alert("请从下拉列表中选择导入数据类别进行导入!")');
   menu.addItem('导入奖励信息', 'importData("award")');
   menu.addItem('导入处分信息', 'importData("punish")');
   bar.addItem("下载数据模板","downloadTemplate()",'download.gif');
   bar.addHelp("<@bean.message key="action.help"/>");
   
   function downloadTemplate(){
	   self.location="dataTemplate.do?method=download&document.id=16";
   }
   function importData(kind){
	   form.action="awardPunish.do?method=importForm&templateDocumentId=16&kind=" + kind;
	   addInput(form,"importTitle","学生奖惩上传")
	   form.submit();
   }
 </script>
 <#--
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
-->
</body>
<#include "/templates/foot.ftl"/>