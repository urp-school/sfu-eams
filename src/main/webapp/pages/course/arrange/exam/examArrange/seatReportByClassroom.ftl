<#include "/templates/head.ftl"/>
<#include "/templates/print.ftl"/>
<object id="factory2" style="display:none" viewastext classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="css/smsx.cab#Version=6,2,433,14"></object> 
<style>
.printTableStyle {
	border-collapse: collapse;
    border:solid;
	border-width:2px;
    border-color:#006CB2;
  	vertical-align: middle;
  	font-style: normal; 
	font-size: 10pt; 
}
table.printTableStyle td{
	border:solid;
	border-width:0px;
	border-right-width:2;
	border-bottom-width:2;
	border-color:#006CB2;
        height:26px;
}
</style>
<body  onload="SetPrintSettings()">
<#macro emptyTd count>
     <#list 1..count as i>
     <td></td>
     </#list>
</#macro>
   <#assign tableNum=0>
    <#list activities as activity>
     <#if tableNum!=0>
     <div style='PAGE-BREAK-BEFORE: always'></div>
     </#if>
   <#assign pagePrintRow = 30/>
     <table width="100%" align="center">
	   <tr>
	    <td align="center" colspan="4" style="font-size:17pt">
	     <B><@i18nName systemConfig.school/>学生考试座位表</B>
	     <br>
	    </td>
	   </tr>
	   <tr><td colspan="3">&nbsp;</td></tr>
	   <tr class="infoTitle">
		   <td>考试安排： (20${(activity.time.firstDay)?string("yy-MM-dd")} 日--${activity.time.timeZone})</td>
		   <td>考试地点:<@i18nName activity.room?if_exists/></td>
		   <td>考试人数:${activity.student?size}</td>
	   </tr>
	 </table>
	 <#assign pageNos=(activity.student?size/(pagePrintRow*2))?int />
	 <#if ((activity.student?size)>(pageNos*(pagePrintRow*2)))>
	 <#assign pageNos=pageNos+1 />
	 </#if>
	 <#list 0..pageNos-1 as pageNo>
	 <#assign passNo=pageNo*pagePrintRow*2 />
	 
	 <#assign examTakes = activity.student?sort_by(['std','code'])/>
	 <table class="printTableStyle" width="100%">
	   <tr class="darkColumn" align="center">
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="20%">备注</td>
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="20%">备注</td>
	   </tr>
	   <#list 0..pagePrintRow-1 as i>
	   <tr class="brightStyle" >
         <#if examTakes[i+passNo]?exists>
	     <td>${i+1+passNo}</td>
	     <td>${examTakes[i+passNo]?if_exists.std?if_exists.code}</td>
	     <td><@i18nName examTakes[i+passNo]?if_exists.std?if_exists/></td>
	     <td>${examTakes[i+passNo]?if_exists.task?if_exists.course?if_exists.name}</td>
	     <#else>
	     <@emptyTd count=4/>
         </#if>
	     
         <#if examTakes[i+pagePrintRow+passNo]?exists>
	     <td>${i+pagePrintRow+1+passNo}</td>
	     <td>${examTakes[i+pagePrintRow+passNo]?if_exists.std?if_exists.code}</td>
	     <td><@i18nName examTakes[i+pagePrintRow+passNo]?if_exists.std?if_exists/></td>
         <td>${examTakes[i+pagePrintRow+passNo]?if_exists.task?if_exists.course?if_exists.name}</td>
         <#else>
	     <@emptyTd count=4/>
         </#if>
	     
	   </tr>
	   </#list>
     </table>
     <#if pageNo_has_next>
 	 <div style='PAGE-BREAK-AFTER: always'></div>
 	 </#if> 
     </#list>
     <#assign tableNum=tableNum+1/>
   </#list>
   <table width="90%" align="center" class="ToolBar">
	   <tr>
		   <td align="center">
		   <input class="buttonStyle" type="button" value="<@msg.message key="action.print"/>" onclick="print()"/>
		  </td>
	  </tr>
  </table>
 </body>
<#include "/templates/foot.ftl"/>