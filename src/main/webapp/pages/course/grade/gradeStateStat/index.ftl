<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<body>
	<table id="bar"></table>
	<table class="frameTable_title" width="100%">
		<form method="post" action="" name="actionForm">
		<tr valign="top">
			<td><#include "/pages/course/calendar.ftl"/></td>
			<input type="hidden" name="gradeState.teachTask.calendar.studentType.id" value="${studentType.id}"/>
			<input type="hidden" name="gradeState.teachTask.calendar.year" value="${calendar.year}"/>
			<input type="hidden" name="gradeState.teachTask.calendar.term" value="${calendar.term}"/>
		</tr>
	</table>
	<table class="frameTable">
		<tr valign="top">
			<td style="width:20%" valign="top" class="frameTable_view"><#include "menuOption.ftl"/></td>
		</form>
			<td><iframe src="#" id="iframeId" name="pageIFrame" scrolling="no" marginwidth="0" marginheight="0" frameborder="0" height="100%" width="100%"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "录入成绩统计查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var selectDo = "${RequestParameters["indexPage"]?default('gradeStatus')}";
			    
	    var form = document.actionForm;

		<#--动态选择被执行的查询路径-->
     	function selectOnclick() {
    		if (selectDo == "gradeStatus") {
    			document.getElementById('gradeStateOption').onclick();
    		} else if (selectDo == "gradePublish") {
				document.getElementById('gradePublishOption').onclick();
    		} else if (selectDo == "gradePercent") {
				document.getElementById('gradePercentOption').onclick();
    		}
       	}

	    <#--当用户点击查询选择项(菜单项)的时候触发的动作-->
	    function selectFrame(td, indexPage) {
	  	  	$("error").innerHTML = "正在统计中，请稍后...";
	  	  	$("error").style.display = "block";
	  	  	
	    	clearSelected(menuTable, td);
		    setSelectedRow(menuTable, td);
		    
	    	addInput(form, "indexPage", indexPage, "hidden");
		    if (indexPage == "gradeStatus") {
		    	statusStat();
		    } else if (indexPage == "gradePublish") {
		    	publishStat();
		    } else if (indexPage == "gradePercent") {
		    	percentStat();
		    }
	    }
	    
	    selectOnclick(); 
	    
	    // 统计成绩状态
	    function statusStat() {
	    	form.action = "gradeStateStat.do?method=statusStat";
	    	form.target = "pageIFrame";
	    	form.submit();
	    }
	    
	    // 成绩发布统计
	    function publishStat() {
	    	form.action = "gradeStateStat.do?method=publishStat";
	    	form.target = "pageIFrame";
	    	form.submit();
	    }
	    
	    // 统计成绩百分比
	    function percentStat() {
	    	form.action = "gradeStateStat.do?method=percentStat";
	    	form.target = "pageIFrame";
	    	form.submit();
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>