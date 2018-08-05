<#include "/templates/head.ftl"/>
<body>
	<table id="roomAailTimeBar"></table>
   	<form name="roomForm" method="post">
		<#include "/pages/course/arrange/availTime/availTimeTable.ftl" />
      	<input type="hidden" name="availTime" value=""/>
      	<input type="hidden" name="classroom.id" value="${classroom.id}"/>
      	<input type="hidden" name="availTime.id" value="${availTime.id}"/>
   	</form>
	<script>
	   	var bar = new ToolBar('roomAailTimeBar','<@bean.message key="action.modify" /><@bean.message key="info.availTimeOfRoom" arg0="${classroom.name}"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("<@bean.message key="action.clear"/>",clearAll,'action.gif');
	   	bar.addItem("<@bean.message key="action.inverse"/>",inverse,'action.gif');
	   	bar.addItem("<@bean.message key="action.save"/>",save,'save.gif');
	   	bar.addBack("<@bean.message key="action.back"/>");
	   	
		var form = document.roomForm;
	    function save(){
	     	if (checkTextLength(form['availTime.remark'].value, "文字说明")) {
		        form.action="roomAvailTime.do?method=saveRoomAvailTime";
		        form.availTime.value = getAvailTime();
		        form.submit();
	        }
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>