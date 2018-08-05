<#include "/templates/head.ftl"/>
<body>
	<table id="myBar" width="100%" ></table>
	<#include "basicInfoContents.ftl"/>
	<script>
	    var bar =new ToolBar("myBar","<@msg.message key="std.baseInfo.title"/>",null,true,true);
	    bar.setMessage('<@getMessage/>');
	    //bar.addItem("<@msg.message key="action.edit"/>","edit()");    
	    bar.addPrint("<@msg.message key="action.print"/>");
	    bar.addBack("<@msg.message key="action.back"/>");
	    
	    function edit(){
	       self.location="stdDetail.do?method=editBasicInfo";
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>