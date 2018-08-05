<#include "/templates/head.ftl"/>
<body>
<table id="backBar" width="100%"</table>
<script>
   var bar = new ToolBar('backBar','学位学科文档管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>
<table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="17%" style="font-size:10pt">
   	  <form name="listForm" method="post" target="documentListFrame" action="" onsubmit="return false;">
      <table  width="100%" id ="viewTables" style="font-size:10pt">
	   <tr><td colspan="2" class="infoTitle" align="left" valign="bottom">
			<img src="${static_base}/images/action/info.gif" align="top"/>
				 <B>文档查询</B>
		   </td>
	   </tr>
        <tr>
          <td colspan="2" style="font-size:0px">
              <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
          </td>
        </tr>
       <tr>
         <td width="40%">文档名称</td>
         <td><input type="text" name="degreeDocument.name" maxlength="30" value="" style="width:100%"></td>
       </tr>
       <tr>
         <td>用户名称</td>
         <td><input type="text" name="degreeDocument.uploaded.name" value="" style="width:100%"></td>
       </tr>
       <tr height="50px">
       	   <td colspan="2" align="center">
       	   		<button name="button" onclick="search(1)" style="buttonStyle">查询</button>
       	   </td>
       </tr>
	  </table>
	  </form>
	<td>
	<td valign="top">
     	<iframe name="documentListFrame" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<script>
  var form = document.listForm;
  function search(pageNo,pageSize,orderBy){
	   form.action="thesisDocument.do?method=search";
	   goToPage(form,pageNo,pageSize,orderBy);
  }
  search(1);
</script>
<#include "/templates/foot.ftl"/>