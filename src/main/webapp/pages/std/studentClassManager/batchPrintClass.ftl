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
<#assign pagePrintRow = 30/>
<body onload="SetPrintSettings()">
	<table id="bar"></table>
   <#list adminClassList?if_exists as adminClass >
   <#assign boycount=0/>
   <#assign girlcount=0/>
    <#assign stds=stdMap[adminClass.id?string]>
    <br>
     <table width="100%" align="center"  >
	   <tr>
	    <td align="center" colspan="10" style="font-size:17pt">
	     <B><@i18nName systemConfig.school/>学生名单</B>
	    </td>
	   </tr>
	   <tr><td>&nbsp;</td></tr>
	   <tr>
		   <td colSpan="10" style="font-size:12pt" id="class${adminClass.id}">班级：<@i18nName adminClass?if_exists/><#-->&nbsp;学籍有效的学生人数:${adminClass.stdCount?if_exists}--></td>
	   </tr>
	 </table>	 
	 <#assign pageNos=(stds?size/(pagePrintRow*2))?int/>
	 <#if ((stds?size)>(pageNos*(pagePrintRow*2)))>
	 <#assign pageNos=pageNos+1/>
	 </#if>
	 <#if (pageNos<1)>
	 	<#assign pageNos=1/>
	 </#if>
	 <#list 0..pageNos-1 as pageNo>
	 <#assign passNo=pageNo*pagePrintRow*2/>

	 <table class="printTableStyle"  width="100%"  >
	   <tr class="darkColumn" align="center">
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="6%"><@msg.message key="entity.gender"/></td>
	     <td width="10%">备注</td>
	     <td width="6%">序号</td>
	     <td width="10%"><@msg.message key="attr.stdNo"/></td>
	     <td width="15%"><@msg.message key="attr.personName"/></td>
	     <td width="6%"><@msg.message key="entity.gender"/></td>
	     <td width="10%">备注</td>
	   </tr>
	   <#assign stdCount = 0/>
	   <#list 0..pagePrintRow-1 as i>
	   <tr class="brightStyle">
         <#if stds[i+passNo]?exists>
	     <td>${i+1+passNo}</td>
	     <td>${stds[i+passNo].code}</td>
	     <td><@i18nName stds[i+passNo]/></td>
	     <td><@i18nName stds[i+passNo].basicInfo.gender/><#if stds[i+passNo].basicInfo.gender.id=1><#assign boycount=boycount+1/><#elseif stds[i+passNo].basicInfo.gender.id=2><#assign girlcount=girlcount+1/></#if></td>
         <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
         </#if>
	     <td></td>
             <#if stds[i+pagePrintRow+passNo]?exists>
	     <td>${i+pagePrintRow+1+passNo}</td>
	     <td>${stds[i+pagePrintRow+passNo]?if_exists.code?if_exists}</td>
	     <td><@i18nName stds[i+pagePrintRow+passNo]?if_exists/></td>
	     <td><@i18nName stds[i+pagePrintRow+passNo]?if_exists.basicInfo?if_exists.gender?if_exists/>
             <#if stds[i+pagePrintRow+passNo]?if_exists.basicInfo?if_exists.gender?if_exists.id?if_exists?string='1'>
                   <#assign boycount=boycount+1/> 
             <#elseif stds[i+pagePrintRow+passNo]?if_exists.basicInfo?if_exists.gender?if_exists.id?if_exists?string='2'>
                   <#assign girlcount=girlcount+1/>
             </#if>
             </td>
             <#else><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
             </#if>
	     <td></td>
	   </tr>
	   </#list>	   
     </table>
     <div style='PAGE-BREAK-AFTER: always'></div><br>
     </#list>
     <script>
	   		var titletd=document.getElementById("class${adminClass.id}");
	   		titletd.innerHTML=titletd.innerHTML+"&nbsp;&nbsp;人数：${stds?size}&nbsp;&nbsp;男:${boycount}&nbsp;&nbsp;女:${girlcount}";
	 </script>
   </#list>
   <table  width="90%" align="center" class="ToolBar">
	   <tr>
		   <td align="center">
		   <input class="buttonStyle"  type="button" value="<@msg.message key="action.print"/>" onclick="print()"/>
		  </td>
	  </tr>
  </table>
  <form method="post" action="" name="actionForm"></form>
  <script>
  	var bar = new ToolBar("bar", "班级学生名单打印", null, true, true);
  	bar.addItem("<@msg.message key="action.export"/>", "exportData()");
  	bar.addBack();
  	
  	var form = document.actionForm;
  	function exportData() {
  		 var ids = "${RequestParameters["ids"]?if_exists}";
  		 form.action = "adminClassManager.do?method=export&isOnCompus=1";
  		 addInput(form, "ids", ids, "hidden");
  		 addInput(form, "template", "adminClassStudent.xls", "hidden");
  		 form.submit();
  	}
  </script>
 </body>
<#include "/templates/foot.ftl"/>