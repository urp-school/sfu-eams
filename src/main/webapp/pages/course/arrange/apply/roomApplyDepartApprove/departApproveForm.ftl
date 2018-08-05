<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="formTable" width="100%" style="text-align:center">
		<tr>
			<td class="darkColumn"><b>归口审核</b></td>
		</tr>
		<tr>
			<td>
				<button accesskey="S" onclick=""><@msg.message key="action.submit"/>(<u>S</u>)</button>&nbsp;
				<button accesskey="R" onclick=""><@msg.message key="action.reset"/>(<u>R</u>)</button>
			</td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "归口审核", null, true, true);
		bar.addItem("审核", "alert(1)");
        bar.addItem("<@msg.message key="action.back"/>", "home()", "backward.gif");
    
    	var form = document.actionForm;
	    function home() {
	        form.action = "roomApplyDepartApprove.do?method=index";
	        form.submit();
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>
