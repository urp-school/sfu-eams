<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
	<table id="myBar" width="100%"></table>
    <#assign stdNameTitle><@msg.message key="action.info"/><@msg.message key="common.gradeTable"/></#assign>
    <#if RequestParameters['majorTypeId']=="1">
       <#include "/pages/components/stdList1stTable.ftl"/>
    <#else>
      <#include "/pages/components/stdList2ndTable.ftl"/>
    </#if>
  <form name="stdListForm" method="post" action="" onsubmit="return false;">
    <#include "reportSetting.ftl"/>
  </form>
  <script>
    var bar = new ToolBar("myBar","<@msg.message key="std.stdList"/>",null,true,true);
    bar.addItem("<@msg.message key="action.printSet"/>","displaySetting()",'setting.png');
    bar.addItem("<@msg.message key="action.preview"/>","printGrade()");
    
  	var form = document.stdListForm;
    function printGrade(stdId){
    	var a_fields = {
    		'reportSetting.pageSize':{'l':'<@msg.message key="common.maxRecodesEachPage"/>', 'r':true, 't':'f_pageSize', 'f':'unsigned'},
    		'reportSetting.fontSize':{'l':'<@msg.message key="common.foneSize"/>', 'r':true, 't':'f_fontSize', 'f':'unsigned'}
    	};
        var v = new validator(form, a_fields, null);
        if (v.exec()) {
	   		if (form['reportSetting.pageSize'].value % 2 != 0) {
				alert("<@msg.message key="info.maxRecodesEachPage"/>");
				return;
			}
	   		form.target="_blank";
	   		form.action="stdGradeReport.do?method=report";
	   		if(null!=stdId){
	   		    form.action+="&stdIds="+stdId;
	   		    form.submit();
	   		}else{
	   	    	submitId(form,"stdId",true);
	   		}
       	}
    }
    function stdIdAction(stdId){
      printGrade(stdId);
    }
  </script>
 </body>
<#include "/templates/foot.ftl"/>
