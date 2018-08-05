<#include "/templates/head.ftl"/>
<body>
	<table id="teacherAvailBar"></table>    
	
	<form name="teacherForm" method="post" action="" onsubmit="return false;">
		<#include "/pages/course/arrange/availTime/availTimeTable.ftl" />
	    <input type="hidden" name="availTime" value=""/>
	    <input type="hidden" name="teacher.id" value="${teacher.id}"/>
	    <input type="hidden" name="availTime.id" value="${availTime.id}"/>
	</form>
	<script>
	   	var bar = new ToolBar('teacherAvailBar','<@bean.message key="action.modify" /><@bean.message key="info.availTimeOfTeacher" arg0="${teacher.name}"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("<@bean.message key="action.clear"/>",clearAll,'action.gif');
	   	bar.addItem("<@bean.message key="action.inverse"/>",inverse,'action.gif');
	   	bar.addItem("<@bean.message key="action.save"/>",save,'save.gif');
	   	bar.addBack("<@bean.message key="action.back"/>"); 
	   	
		var form = document.teacherForm;
	    function save() {
	     	if (checkTextLength(form['availTime.remark'].value, "文字说明")) {
	     		form.action = "teacherAvailTime.do?method=saveTeacherAvailTime";
	     		teacherForm.availTime.value = getAvailTime();
	        	form.submit();
	     	}
	    }
	</script> 
</body>
<#include "/templates/foot.ftl"/>