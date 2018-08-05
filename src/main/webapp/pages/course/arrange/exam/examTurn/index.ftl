<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="calendarFrameBar"></table>
  <table class="frameTable">
	  <tr>
	  <td class="frameTable_view" width="20%">
	   <table width="100%">
	    <tr>
	      <td class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>学生类别列表</B>
	      </td>
	    <tr>
	      <td colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top"/>
	      </td>
	   </tr>
	  </table>
    <table width="100%" align="left" class="listTable" id="stdTypeTable">
      <form name="stdTypeListForm" method="post" target="contentListFrame" action="" onsubmit="return false;">
      <input type="hidden" name="calendarType" value="calendar"/>
      <input type="hidden" name="studentType.id" value="${stdTypes?first.id}"/>
	   <tr align="center" class="darkColumn">
         <td><@bean.message key="attr.id"/></td>
	     <td><@bean.message key="attr.name"/></td>
	   </tr>
       <#list stdTypes as stdType>
	   <tr class="brightStyle" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" align="center">
	    <td >${stdType.code}</td>
        <td<#if stdType_index == 0> id="defaultSelect"</#if> onclick="setSelectedRow(stdTypeTable,this);get(${stdType.id})"><@i18nName stdType/></td>
	   </tr>
	   </#list>
	   </form>
      </table>
	  </td>
	  <td valign="top">
	  <iframe src="#" id="contentListFrame" name="contentListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="440" width="100%"></iframe>
	  </td>
	  </tr>
  </table>
  <script>
   var bar = new ToolBar('calendarFrameBar','考试场次管理',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("系统默认考试场次",'get("")');
   bar.addHelp("<@msg.message key="action.help"/>");
  function get(stdTypeId){
      var form = document.stdTypeListForm;
      form.action="examTurn.do?method=list&examTurn.stdType.id="+stdTypeId;
      form.submit();
  }
  defaultSelect.onclick();
  </script>
  </body>