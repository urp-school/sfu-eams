<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
 <script language="JavaScript" type="text/JavaScript" src="scripts/tabpane.js"></script>
 <link href="${static_base}/css/tab.css" rel="stylesheet" type="text/css">
 <body>
  <table cellpadding="0" cellspacing="0" width="100%" border="0">
   <form name="commonForm" action="auditStandardOperation.do" method="post" action="" onsubmit="return false;">
   <tr>
    <td align="center" colspan="4" class="contentTableTitleTextStyle" bgcolor="#ffffff">
     <B><@bean.message key="attr.graduate.updateGraduateAudit"/></B>
    </td>
   </tr>  
   <tr>
    <td align="center">
     <div class="dynamic-tab-pane-control tab-pane" id="tabPane1" >
     <script type="text/javascript">tp1 = new WebFXTabPane( document.getElementById( "tabPane1") ,false );</script>
    
     <div style="display: block;" class="tab-page" id="tabPage1">
      <h2 class="tab"><a href="#"><@bean.message key="attr.graduate.graduateAuditBaseStandard"/></a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage1" ) );</script>
       <table width="100%" align="center" height="100%">
	   <tr>
	     <td align="center" colspan="2">
      		<iframe  src="auditStandardOperation.do?method=updateForm&auditStandardId=${result.auditStandard.id}"
	     		id="auditStandardOperationFrame" name="auditStandardOperationFrame" 
	     		marginwidth="0" marginheight="0" scrolling="auto"
	     		frameborder="0"  height="100%" width="100%">
     		</iframe>
     	</td>
	  </tr>
     </table>
     </div>
     
   <div style="display: block;" class="tab-page" id="tabPage2">
    <h2 class="tab"><a href="#" ><@bean.message key="attr.graduate.outsideExam"/></a></h2>
      <script type="text/javascript">tp1.addTabPage( document.getElementById( "tabPage2" ) );</script>
      <table width="100%" align="center" height="100%">
	   <tr>
	     <td align="center" colspan="2">
	     	<iframe  src="outerExamAuditStandardManager.do?method=manager&auditStandardId=${result.auditStandard.id}"
	     		id="outerExamAuditStandardManagerFrame" name="outerExamAuditStandardManagerFrame" 
	    		marginwidth="0" marginheight="0" scrolling="auto"
	     		frameborder="0"  height="100%" width="100%">
	     	</iframe>
	     </td>
	  </tr>
	  </table>
   </div>
   
  </div>
  </td>
  </tr>
  </table>
 </body>
 <script language="javascript" >
    function doAction(form){   

     var a_fields = {
         'auditStandard.auditType.id':{'l':'<@bean.message key="attr.graduate.auditType"/>', 'r':true, 't':'f_auditType'},
         'auditStandard.studentType.id':{'l':'<@bean.message key="entity.studentType"/>', 'r':true, 't':'f_studentType'},
         'auditStandard.averageScore':{'l':'<@bean.message key="filed.averageScoreNod"/>', 'r':true, 't':'f_averageScore', 'f':'unsignedReal'},
         'auditStandard.isTeachPlanCompleted':{'l':'<@bean.message key="entity.cultivatePlan"/>', 'r':true, 't':'f_isCultivateSchemeCompleted'},
         'auditStandard.discourseScore':{'l':'<@bean.message key="attr.graduate.discourseScore"/>', 'r':true, 't':'f_discourseScore', 'f':'unsignedReal'},
         'auditStandard.integrationScore':{'l':'<@bean.message key="attr.graduate.integrativeScore"/>', 'r':false, 't':'f_integrationScore', 'f':'unsignedReal'},
         'auditStandard.dutyRation':{'l':'<@bean.message key="attr.graduate.attendClassRate"/>', 'r':false, 't':'f_dutyRation', 'f':'unsignedReal'},
         'auditStandard.repeatCreditHour':{'l':'<@bean.message key="attr.graduate.totleCreditReStudy"/>', 'r':false, 't':'f_repeatCreditHour', 'f':'unsignedReal'},
         'auditStandard.publishedDiscourseCount':{'l':'<@bean.message key="attr.graduate.articleNumber"/>', 'r':false, 't':'f_publishedDiscourseCount', 'f':'unsigned'},
         'auditStandard.remark':{'l':'<@bean.message key="attr.remark"/>', 'r':false, 't':'f_remark', 'mx':200}
     };
     
     var v = new validator(form, a_fields, null);
     if (v.exec())) {
        form.submit();
     }
   }
 </script>
<#include "/templates/foot.ftl"/>