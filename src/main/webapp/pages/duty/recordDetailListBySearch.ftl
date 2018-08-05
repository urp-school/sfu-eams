<#include "/templates/head.ftl"/>
<#include "/pages/system/calendar/timeFunction.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body LEFTMARGIN="0" TOPMARGIN="0" style="overflow:auto;">
 <script>
<#--   window.self.resizeTo(400, ${(result.recordDetailList?size)*30+140}); -->
	<#if (result.recordDetailList?size)< 8 >
	window.self.resizeTo(400, ${(result.recordDetailList?size)*30+150});
	window.self.moveTo((screen.width-400)/3, (screen.height-${(result.recordDetailList?size)*30+150})/5);
	<#else>
	window.self.resizeTo(400, 8*30+150);
	window.self.moveTo((screen.width-400)/3, (screen.height-(8*30+150))/5);
	</#if>
 </script>
 <TABLE WIDTH="100%" BORDER="0" ALIGN="LEFT" CELLPADDING="0" CELLSPACING="0">
  <TR> 
   <TD HEIGHT="100%" VALIGN="TOP" BACKGROUND="images/loginForm/ifr_mainBg_0.gif">
	<TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR>
      <TD BACKGROUND="images/loginForm/leftItem_001.gif" STYLE="background-repeat:no-repeat ">              
       <TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
        <TR> 
         <TD WIDTH="15%" HEIGHT="42">&nbsp;</TD>
         <TD WIDTH="85%"><FONT COLOR="red"><B><@i18nName result.dutyRecord.teachTask.course?if_exists/>&nbsp;<@bean.message key="info.duty.dutyRecord"/></B></FONT></TD>
        </TR>
       </TABLE>
      </TD>
     </TR>
    </TABLE>
    <TABLE WIDTH="100%" BORDER="0" ALIGN="CENTER" CELLPADDING="0" CELLSPACING="0">
     <TR> 
      <TD HEIGHT="100%" VALIGN="TOP">       
       <table width="85%" align="center" class="listTable">
        <form name="commonForm" action="dutyRecordManager.do" method="post" target="main" onsubmit="return false;">
        <tr align="center" class="darkColumn">
	     <td width="25%"><@bean.message key="info.duty.checkDutyDate"/></td>
	     <td width="20%"><@bean.message key="info.duty.timeBeginHour"/></td>
	     <td width="20%"><@bean.message key="info.duty.timeEndHour"/></td>
	     <td width="35%"><@bean.message key="info.duty.dutyStatus"/></td>
	    </tr>
	    <#assign recordDetailIds = "," />
        <#list (result.recordDetailList?sort_by("dutyDate"))?if_exists as recordDetail>
        <#if recordDetail_index%2==1 ><#assign class="grayStyle" ></#if>
	    <#if recordDetail_index%2==0 ><#assign class="brightStyle" ></#if>
	    <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	     <td><#if recordDetail.dutyDate?exists>${recordDetail.dutyDate?string("yyyy-MM-dd")}</#if></td>
	     <td><@getTimeStr recordDetail.beginTime?if_exists/></td>
	     <td><@getTimeStr recordDetail.endTime?if_exists/></td>	     
	     <td>&nbsp;	<#if recordDetail.dutyStatus?exists >
	    			<@i18nName recordDetail.dutyStatus />
	    			<#else><@bean.message key="common.noRecord"/></#if></td>
        </tr>
        </#list>
        </form>
       </TABLE>
      </TD>
     </TR>          
    </TABLE>
   </TD>
  </TR>
 </TABLE>
</BODY>
<#include "/templates/foot.ftl"/>