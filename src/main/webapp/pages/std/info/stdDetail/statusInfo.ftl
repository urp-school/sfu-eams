<#include "/templates/head.ftl"/>
<body>
	<table id="myBar" width="100%" ></table>
	<#include "statusInfoContents.ftl">
	<script>
	    var bar =new ToolBar("myBar","<@msg.message key="std.statusInfo.title"/>",null,true,true);
	    <#if couldEdit?default(false)>
	    bar.addItem("<@msg.message key="action.edit"/>","edit()"); 
	    </#if>
	    function edit(){
	       self.location="stdDetail.do?method=editStatusInfo";
	    }
	    bar.addPrint("<@msg.message key="action.print"/>");
	    bar.addBack("<@msg.message key="action.back"/>");
	</script>
</body>
<#include "/templates/foot.ftl"/>