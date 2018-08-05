<div id="addStdFormDiv" style="display:none">
<table id="addStdBar" >
    <form name="addStdForm" method="post" action="" onsubmit="return false;">
</table>  
  <table width="100%" border="0" class="listTable">
	   <tr class="darkColumn">
	   <td colspan="2">
	     <input name="taskId" type="hidden" value="${task.id}">
	     直接输入学号添加(重复或无效者被忽略)多个学号，以逗号相隔:
	    </td>
	   </tr>
	   <tr>
	   	<td><textarea name="stdNos" style="width:100%"></textarea></td>
	   </tr>
	   <tr class="grayStyle">
	   <td colspan="2" width="80%">
	   <iframe src="#"
        id="stdListFrame" name="stdListFrame"
        marginwidth="0" marginheight="0" scrolling="no"
        frameborder="0" height="100%" width="100%"></iframe>
	   </td>
	   </tr>
	  </form>
	</table>
	<script>
	   	function addStd(){
	        var form =document.addStdForm;
	   	    if (form['stdNos'].value == "") {
	   	       if (confirm("没有输入任何学生学号,放弃添加？")) {
	     	       displayDiv('teachClassStdListDiv');
	     	   }
	     	   return;
	   	    }
	     	form.action = "courseTakeForTask.do?method=add";
	      	form.submit();
	   	}
	   	function addStdNos(stdNos) {
	   	   if ("" == stdNos) {
	   	   	return;
	   	   }
	   	   var stdNoArray = stdNos.split(",");
	   	   var existedStdNos = document.addStdForm['stdNos'].value;
	   	   for(var i=0;i<stdNoArray.length;i++){
	   	       if(existedStdNos.indexOf(stdNoArray[i])==-1)
	   	       existedStdNos+=","+stdNoArray[i];
	   	   }
	   	   document.addStdForm['stdNos'].value=existedStdNos;
	   	}
	   var bar = new ToolBar('addStdBar','添加学生，<@msg.message key="attr.taskNo"/>${task.seqNo}',null,true,true);
	   bar.setMessage('<@getMessage/>');
	   bar.addItem("<@msg.message key="action.add"/>","addStd()","new.gif");
	   bar.addItem("<@msg.message key="action.back"/>","javascript:displayDiv('teachClassStdListDiv')","backward.gif");
	</script>
</div>