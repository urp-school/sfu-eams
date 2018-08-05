<script type='text/javascript' src='dwr/interface/studentClassManager.js'></script>
<div id="addStdFormDiv" style="display:none">
<table width="90%" align="center" class="listTable" style="border-top-style:none">
<form name="addStdForm" method="post" >
	<tr class="darkColumn">
		<td colspan="2">
	     直接输入学号添加(重复或无效者被忽略)多个学号，以逗号相隔:
	    </td>	    
	   </tr>	   
	   <tr>
	   <td><textarea name="stdNoSeq" style="width:100%"></textarea></td>
	   </tr>
	   <tr class="grayStyle">
	   <td colspan="2" width="80%">
	   <iframe src="#" id="stdListFrame" name="stdListFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%"> </iframe>
	   </td>
	   </tr>
	  </form>
	</table>
	<script>
	var dwrFlag=true;
   	function addStd(){
        var stdNos=document.addStdForm['stdNoSeq'].value;
   	    if(stdNos=="") {
   	       if(confirm("没有输入任何学生学号,放弃添加？")){
     	       displayDiv('classStudent');     	    
     	   }
     	   return;
   	    }
   	    if(!dwrFlag){
   	    	alert("请稍候！");
   	    }
   	    dwrFlag=false;
   	    var params = {'record.student.code':document.addStdForm['stdNoSeq'].value};
   	    studentClassManager.getStdBeanMap(params,setStdForAdd);
   	}
   	function setStdForAdd(stdMap){
   		var ids=document.getElementsByName("ids");
   		if(ids&&ids[0]){
   			for(var key in stdMap){
   				if(checkId(ids,key)){
   					addStudent(stdMap[key]);
   				}
   			}
   		}else{
   			for(var key in stdMap){
				addStudent(stdMap[key]);
   			}
   		}
   		displayDiv('classStudent');
   		dwrFlag=true;
   	}
   	function checkId(ids,key){
   		var flag=true;
   		for(var i=0; i<ids.length; i++){
   			if(ids[i].value==key){
   				flag=false;
   				return flag;
   			}
   		}
   		return flag;
   	}
   	function addStdNos(stdNos){
   	   if(""==stdNos) return;
   	   var stdNoArray = stdNos.split(",");
   	   var existedStdNos = document.addStdForm['stdNoSeq'].value;
   	   for(var i=0;i<stdNoArray.length;i++){
   	       if(existedStdNos.indexOf(stdNoArray[i])==-1)
   	       existedStdNos+=","+stdNoArray[i];
   	   }
   	   document.addStdForm['stdNoSeq'].value=existedStdNos;
   	}
   var bar=new ToolBar('addStdFormBar','添加学生',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("<@msg.message key="action.add"/>",addStd);
   bar.addItem("<@msg.message key="action.back"/>","javascript:displayDiv('classStudent')");
	</script>
</div>