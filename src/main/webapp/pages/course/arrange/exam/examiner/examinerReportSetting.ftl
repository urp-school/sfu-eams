<#include "/templates/head.ftl"/>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckeditor.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckTextArea.js"></script>
<#assign textAreaId = "noticeFCK"/>
<body>
<table id="settingBar"></table>
 <form name="actionForm" method="post" action="examiner.do?method=examinerReport" onsubmit="return false;">
 <input name="calendar.id" value='${RequestParameters['calendar.id']}' type="hidden"/>
 <input name="examActivityIds" value='${RequestParameters['examActivityIds']}' type="hidden"/>
 
 <table  width="100%" border="0" class="infotitle">
	 <tr valign="top">
	 	<td>请输入监考须知：</td>
	 	<td><textarea id="${textAreaId}" name="notice.notice"></textarea></td>
	 </tr>
	 <tr>
	 </tr>
	 <tr><td>补充说明标题</td><td><input name="notice.title" value="监考须知" type="text" maxlength="20"/></td></tr>
	 <tr><td>请输入打印日期</td><td><input name="notice.date" onfocus="calendar()" maxlength="10"/></td></tr>
	 <tr><td>请输入打印监考部门</td><td><input name="notice.department" maxlength="20"/></td></tr>
	 <tr><td colspan="2" align="middle"><button class="buttonStyle" onclick="this.form.submit();">打印预览</button>&nbsp;&nbsp;</td></tr>
 </table>
 </form>
 <script>
  var bar = new ToolBar("settingBar","监考通知模板设置",null,true,true);
  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  
  initFCK("${textAreaId}");
 </script>
</body>
<#include "/templates/foot.ftl"/>