<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="17%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>文档分类</B>      
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>    
       <tr>
         <td class="padding" id="defaultMenu" onclick="displayDocumentList(this,'std')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/inbox.gif">面向学生
         </td>
       </tr>
       <tr >
         <td class="padding" onclick="displayDocumentList(this,'teacher')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
          &nbsp;&nbsp;<image src="${static_base}/images/action/readedMessage.gif">面向教师
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="displayDocumentList(this,'manager')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
           &nbsp;&nbsp;<image src="${static_base}/images/action/sentbox.gif">面向管理人员
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="documentListFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<script>
   var bar = new ToolBar('backBar','系统文档',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
   function displayDocumentList(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      documentListFrame.location="document.do?method=list&kind="+kind;
   }

	$("defaultMenu").onclick();
</script>
</body>
<#include "/templates/foot.ftl"/>