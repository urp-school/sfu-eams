<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  	<div id="taskListDiv">
  	<table id="lastArrangeTable"></table>
  	<table width="100%" border="0" class="listTable" id="taskListTable">   
      	<form name="taskListForm" action="" method="post" onsubmit="re">
    		<tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
      			<td align="center" >
        			<img src="${static_base}/images/action/search.gif"  align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult"/>"/>
      			</td>
      			<td><input style="width:100%" type="text" name="input0" maxlength="7" value=""/></td>
      			<td><input style="width:100%" type="text" name="input1" maxlength="7" value=""/></td>
      			<td><input style="width:100%" type="text" name="input2" maxlength="7" value=""/></td>
      			<td><input style="width:100%" type="text" name="input3" maxlength="7" value=""/></td>
      			<td><input style="width:100%" type="text" name="input4" maxlength="7" value=""/></td>
      			<td><input style="width:100%" type="text" name="input5" maxlength="7" value=""/></td>
    		</tr>
    		<tr align="center" class="darkColumn">
	      		<td align="center" width="2%">
	        		<input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('taskId'),event);">
	      		</td>
	      		<td width="8%"><@bean.message key="attr.taskNo" /></td>
	      		<td width="8%"><@bean.message key="attr.courseNo"/></td>
	      		<td width="20%"><@bean.message key="attr.courseName"/></td>
	      		<td width="15%"><@bean.message key="entity.teachClass" /></td>
	      		<td width="10%"><@bean.message key="entity.courseType" /></td>
	      		<td width="20%">安排结果</td>
    		</tr>
  		</form>
	</table>
	<script>
	   	var bar = new ToolBar('lastArrangeTable','<@bean.message key="info.arrange.lastArrangeResult"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>');
	   	bar.addItem("<@bean.message key="action.export" />","exportData()");
	   	bar.addItem("<@bean.message key="action.deleteArrange" />","remove()");
	   	bar.addBack('<@bean.message key="action.back"/>');
	   	
	    var taskListData = new Array(${taskList?if_exists?size});
	    <#list taskList?if_exists as task>
	        taskListData[${task_index}]=new Array();
	        taskListData[${task_index}][0]="${task.id}";
	        taskListData[${task_index}][1]="${task.course.code}";
			taskListData[${task_index}][2]="${task.course.name}";
	        taskListData[${task_index}][3]="${task.teachClass.name}";
	        taskListData[${task_index}][4]="<@i18nName task.courseType/>";
	        taskListData[${task_index}][5]="${arrangeInfo[task.id?string]?if_exists}";
	    </#list>
	    
	    function enterQuery() {
            if (window.event.keyCode == 13) {
             	query();
           	}
	    }
	    
	    var cols=6;
		function query(){	
		    removeALLRow();
		    var form = document.taskListForm;
		    var example=new Array(cols);
	        for(var i=0;i<cols;i++)
	            example[i] = form["input"+i].value;
	        for(var i=0;i<taskListData.length;i++){
	            if(isMatch(example,taskListData[i]))
		            addRow(i);
	        }
		}
		
		function isMatch(example,target){
		    var match=true;
		    for(var i=0;i<cols;i++)
			    if(!example[i]==""&&target[i].indexOf(example[i])==-1){
				        match =false;
				        break;
			        }
			return match;
		}
		
		function remove(){
	        var taskIds = getCheckBoxValue(document.getElementsByName("taskId"));
	        if(taskIds=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
	        if(!confirm("<@bean.message key="prompt.deletion"/>"))return;
	        var form = document.taskListForm;
	        form.action="autoArrange.do?method=removeLastArrange&taskIds=" + taskIds;
	        form.submit();
	    }
	    
	    function removeALLRow(){
	      for(var i=taskListTable.rows.length-1;i>1;)
	          taskListTable.tBodies[0].removeChild(taskListTable.rows[i--]);
	    }
	    
	    function  addRow(index){    
		    var tr = document.createElement('tr');	    
		    tr.onclick=function() {onRowChange(event);}
		    if(index%2==1)
		       tr.className="grayStyle";
		    else
		       tr.className="brightStyle"
		    var td = document.createElement('td');
		    td.innerHTML ='<input type="checkbox" name="taskId" value="' + taskListData[index][0]+ '">';
		    //alert(td.innerHTML);
			tr.appendChild(td);
		    td.style.backgroundColor="#CBEAFF";
		    var idCol = document.createElement('td');
		    if(taskListData[index][9]=="<@bean.message key="common.yes" />")		
			    idCol.innerHTML ='<A href="courseTable.do?method=taskTable&task.id=' + taskListData[index][0]+ '">'+taskListData[index][0]+'</a>';
		    else 
		    	idCol.innerHTML=taskListData[index][0];
		    	    	
	        tr.appendChild(idCol);
			
			for(var i=1;i<taskListData[index].length;i++){
			    var oneTd = document.createElement('td');
			    if(i==2)
				     oneTd.innerHTML='<A href="teachTask.do?method=info&task.id='+ taskListData[index][0] + '">' +taskListData[index][i] +'</a>';
			    else oneTd.innerHTML=taskListData[index][i];
			    
				tr.appendChild(oneTd);
			}
			tr.className="brightStyle";
			tr.align="center";
			taskListTable.tBodies[0].appendChild(tr);   
			    
	    }
	    
	    function initData(){
	        for(var i=0;i<taskListData.length;i++)
	        addRow(i);
	    }
	    
	    initData();
	    
	    function exportData(){
	       var taskIds = getCheckBoxValue(document.getElementsByName("taskId"));
	       if(taskIds==""&&confirm("<@bean.message key="prompt.arrange.export.all"/>"));{
	           var rows = taskListTable.rows;
	           for(var i=0;i<rows.length;i++)
	               rows[i].firstChild.firstChild.checked=true;
	       }
	       return;
	       /** var form = document.taskListForm;
	       form.action="arrangeCourse.do?method=exportResult&taskIds="+taskIds;
	       alert(form.action);
	       form.submit();
	       **/
	    }
	</script>
</body>
<#include "/templates/foot.ftl"/>  