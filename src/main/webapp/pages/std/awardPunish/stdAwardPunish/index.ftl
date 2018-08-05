<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','<@msg.message key="std.awardsPunishs.personal.title"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>     
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B><@msg.message key="std.awardsPunishs.personal.infoCategories"/></B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="info(this,'award')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom"><@msg.message key="std.awardsPunishs.personal.rewards"/>
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="info(this,'punish')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="std.awardsPunishs.personal.punishments"/>
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<form name="infoForm" method="post" action="" onsubmit="return false;"></form>
<script>
   document.getElementById("defaultItem").onclick();   
   function info(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      var form = document.infoForm;
      form.action="stdAwardPunish.do?method="+kind+"List";
      form.target="statFrame";
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>