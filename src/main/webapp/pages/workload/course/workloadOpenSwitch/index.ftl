<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
<table id="myBar"></table>
  <table class="frameTable">
	  <tr>
	  <td class="frameTable_view" width="20%">
	   <table  width="100%">
	    <tr>
	      <td  class="infoTitle" align="left" valign="bottom">
	       <img src="${static_base}/images/action/info.gif" align="top"/>
	          <B>学生类别列表</B>
	      </td>
	    <tr>
	      <td  colspan="8" style="font-size:0px">
	          <img src="${static_base}/images/action/keyline.gif" height="2" width="100%" align="top">
	      </td>
	   </tr>
	  </table>	  
	  <#assign stdTypes=stdTypes?sort_by("code")/>
    <table width="100%" align="left" class="listTable" id="stdTypeListTable">
      <form name="stdTypeListForm" method="post" target="contentListFrame" action="" onsubmit="return false;">
      <input type="hidden" name="switch.teachCalendar.studentType.id" value="${(stdTypes?first.id)?default("")}"/>
      <input type="hidden" name="orderBy" value="switch.teachCalendar.start desc"/>
	   <tr  class="darkColumn">
         <td><@bean.message key="attr.id"/></td>
	     <td><@bean.message key="attr.name"/></td>
	   </tr>
       <#list stdTypes as stdType>
		   <tr>
		     <td align="left" >${stdType.code}</td>
	        <td class="padding" <#if stdType_index=0>id="defaultStdType"</#if>
	        onclick="javascript:setSelectedRow(stdTypeListTable,this);getById('${stdType.id}')" 
	        onmouseover="MouseOver(event)" onmouseout="MouseOut(event)"><@i18nName stdType/></td>
		   </tr>
	   </#list>
	   </form>
      </table>
	  </td>
	  <td valign="top">
		  <iframe  src="#" 
		           id="contentListFrame" name="contentListFrame" 
	      marginwidth="0" marginheight="0"
	      scrolling="no" 	 frameborder="0"  height="440" width="100%">
	     </iframe>
	  </td>
	  </tr>
  </table>
  <script>
  function getById(studentTypeId){
      var form = document.stdTypeListForm;
      form['switch.teachCalendar.studentType.id'].value=studentTypeId;
      form.action="?method=switchList";
      form.submit();
  }
  getById(${(stdTypes?first.id)?default("")});
   var bar = new ToolBar('myBar','工作量查询开关',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@bean.message key="action.help"/>");   
  </script>
  </body>