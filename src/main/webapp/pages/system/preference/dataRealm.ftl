<#include "/templates/head.ftl"/>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <link href="${static_base}/css/tableTree.css" rel="stylesheet" type="text/css">
 <script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script> 
 <script language="JavaScript" type="text/JavaScript" src="scripts/tableTree.js"></script>
 <script> defaultColumn=1;</script>

 <body>
 <table id="dataRealmBar" width="100%"></table>
 <script> 
   var bar = new ToolBar('dataRealmBar','<@bean.message key="info.dataRealm.module" arg0="${resource.code}"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
 </script> 
  <#if ((departList?size==0)&&(stdTypeList?size==0))>
<@bean.message key="info.dataRealm.null"/>
  <#else>
  <table width="100%" border="0" >
  <tr>
   <td style="width:250px" valign="top">
   <div class="dynamic-tab-pane-control tab-pane" id="tabPane1">
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
     <#if (departList?size>0)>
     <div style="display: block;" class="tab-page" id="tabPage3">
     <h2 class="tab"><a href="#"><@bean.message key="entity.college"/>(<font id="COMMENT_COUNTS" color="blue">${departList?size}</font>)</a></h2>
     <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage3" ) );</script>
      
      <table width="100%" align="left" class="listTable">
	   <tr  class="darkColumn">
         <td><@bean.message key="attr.id"/></td>
	     <td><@bean.message key="attr.name"/></td>
	   </tr>
       <#list departList?sort_by("code") as depart>
	   <#if depart_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if depart_index%2==0 ><#assign class="brightStyle" ></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)">
	    <td width="30%"  >${depart.code}</td>
        <td ><@i18nName depart/></td>        
	   </tr>
	   </#list>
      </table>
     </div>
     </#if>
     
     <#if (stdTypeList?size>0)>     
     <div style="display: block;" class="tab-page" id="tabPage4">
     <h2 class="tab"><@bean.message key="entity.studentType"/>(${stdTypeList?size})</h2>
     <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage4" ) );</script>   
      <table width="100%" align="left" class="listTable">
	   <tr  class="darkColumn">
         <td><@bean.message key="attr.id"/></td>
	     <td><@bean.message key="attr.name"/></td>
	   </tr>
       <#list stdTypeList?sort_by("code") as stdType>   
	   <#if stdType_index%2==1 ><#assign class="grayStyle" ></#if>
	   <#if stdType_index%2==0 ><#assign  class="brightStyle"></#if>
	   <tr class="${class}" onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" >
	    <td width="30%"  >${stdType.code}</td>
        <td ><@i18nName stdType/></td>
	   </tr>
	   </#list>
      </table>
     </div>   
     </#if>
     </div>
     <script type="text/javascript">setupAllTabs();</script>
     </td>
    </tr>
    <table>
    </#if>
</body>
<#include "/templates/foot.ftl"/> 