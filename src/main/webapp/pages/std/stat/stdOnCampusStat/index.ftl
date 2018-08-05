<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','在校学生统计(学籍有效,在校)',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
      <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>统计项目</B>      
	      </td>
	  <tr>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
       <tr>
         <td class="padding" id="defaultItem" onclick="stat(this,'StdType')" onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif" align="bottom" ><@msg.message key="entity.studentType"/>
         </td>
       </tr>
       <tr>
         <td class="padding"  onclick="stat(this,'Depart')"  onmouseover="MouseOver(event)" onmouseout="MouseOut(event)" title="点击按该项目进行统计">
         &nbsp;&nbsp;<image src="${static_base}/images/action/list.gif"><@msg.message key="entity.department"/>
         </td>
       </tr>
       <tr>
         <td>
          <br>
           &nbsp;&nbsp;说明:各个年份中的-1或者-2表示招生入学的月份
         </td>
       </tr>
	  </table>
	<td>
	<td valign="top">
     	<iframe name="statFrame" src="#" width="100%" frameborder="0" scrolling="auto">
     	</iframe>
	</td>
</table>
<form name="statForm" method="post" target="statFrame" action="" onsubmit="return false;"></form>
<script>
   document.getElementById("defaultItem").onclick();
   
   function stat(td,kind){
      clearSelected(viewTables,td);
      setSelectedRow(viewTables,td);
      var form = document.statForm;
      form.action="stdOnCampusStat.do?method=statBy"+kind;
      form.submit();
   }
</script>
</body>
<#include "/templates/foot.ftl"/>