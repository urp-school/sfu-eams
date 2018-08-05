<#include "/templates/head.ftl"/>
<body>
 	<table id="myBar" width="100%" ></table>
	<#include "abroadInfoContents.ftl"/>
	<script>
    	var bar =new ToolBar("myBar","<@msg.message key="std.certificateInfo.title"/>",null,true,true);
    	bar.addPrint("<@msg.message key="action.print"/>");
	    bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
 	</script>
</body>
<#include "/templates/foot.ftl"/>