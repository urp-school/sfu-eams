<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/gradeSeg.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/grade/grade.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckeditor.js"></script>
<script language="javascript" type="text/javascript" src="${static_base}/scripts/fckeditor/fckTextArea.js"></script>
<#assign textAreaId = "content"/>
<body>
	<table id="bar"></table>
	<table align="center">
		<form method="post" action="" name="actionForm">
		<input type="hidden" name="taskIds" value="${RequestParameters['taskIds']?default('')}"/>
		<tr>
			<td style="font-weight:bold">综合分析内容：</td>
		</tr>
		<tr>
			<td><textarea id="${textAreaId}" name="contentValue" rows="10" cols="50">${(RequestParameters["contentValue"]?html)?default("")}</textarea></td>
		</tr>
		<tr>
			<td align="center"><button onclick="finish()">提交</button></td>
		</tr>
		</form>
	</table>
	<script>
		var bar = new ToolBar("bar", "编辑打印内容", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addBack("<@msg.message key="action.back"/>");
		
	    initFCK("${textAreaId}");

		var form = document.actionForm;
	    
	    function finish() {
	    	form.action = "teacherGrade.do?method=reportContent";
		    for (var i = 0; i < seg.length; i++) {
				var segAttr = "segStat.scoreSegments[" + i + "]";
		        addInput(form, segAttr + ".min", seg[i].min, "hidden");
		        addInput(form, segAttr + ".max", seg[i].max, "hidden");
		    }
	    	form.submit();
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>