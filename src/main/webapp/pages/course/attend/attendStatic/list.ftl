<#include "/templates/head.ftl"/>
<body>
<table id="taskBar" ></table>
<script>
     var bar = new ToolBar('taskBar', '学生考勤明细列表', null, true, false);
     bar.setMessage('<@getMessage/>');
     var menu1 = bar.addMenu('高级',null);
     menu1.addItem('销假','updateAttendtype()');
     menu1.addItem('批量销假','batchUpdate()');
     bar.addItem('<@bean.message key="action.add"/>', 'addAttendStatic()');
     bar.addItem('<@bean.message key="action.modify"/>', 'editSingle()');
     bar.addItem('<@bean.message key="action.delete"/>', 'removeAttendStatic()');
     bar.addItem('导入', 'importAttendStatic()');
    
</script>
  <@table.table width="100%" sortable="true" id="listTable" headIndex="1">
  <form name="taskListForm" id="taskListForm" action="" method="post" onsubmit="return false;" >
    <input type="hidden" name="method" value="search">
    
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery(event)">
      <td align="center" width="3%">
        <img src="${static_base}/images/action/search.png"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input style="width:100%" type="text" name="attendStatic.student.code" maxlength="32" value="${RequestParameters['attendStatic.student.code']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="attendStatic.student.name" maxlength="32" value="${RequestParameters['attendStatic.student.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="adminClass.name" maxlength="20" value="${RequestParameters['adminClass.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="attendStatic.student.firstMajor.name" maxlength="20" value="${RequestParameters['attendStatic.student.firstMajor.name']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="attendStatic.task.seqNo" maxlength="20" value="${RequestParameters['attendStatic.task.seqNo']?if_exists}"/></td>
      <td ><input style="width:100%" type="text" name="attendStatic.course.name" maxlength="20" value="${RequestParameters['attendStatic.course.name']?if_exists}"/></td>      
      <td><input style="width:100%" type="text" name="attendStatic.department.name" maxlength="50" value="${RequestParameters['attendStatic.department.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="teacher.name" maxlength="3" value="${RequestParameters['teacher.name']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="attenddate" maxlength="10" value="${RequestParameters['attenddate']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="attendStatic.attendtime" maxlength="10" value="${RequestParameters['attendStatic.attendtime']?if_exists}"/></td>
      <td><input style="width:100%" type="text" name="attendStatic.attendtype" maxlength="10" value=""/></td>
    </tr>
  	</form>
  	<@table.thead>
  	  <@table.selectAllTd id="attendStaticId" />
      <@table.sortTd id="attendStatic.student.code" text="学生学号"/>
      <@table.sortTd id="attendStatic.student.name" text="学生姓名"/>
      <@table.td id="adminClass.name" text="班级名称"/>
      <@table.sortTd id="attendStatic.student.firstMajor.name" text="专业名称"/>
      <@table.sortTd id="attendStatic.task.seqNo" text="课程序号"/>
      <@table.sortTd id="attendStatic.course.name" text="课程名称"/>
      <@table.sortTd id="attendStatic.department.name" text="上课院系"/>
      <@table.td id="teacher.name" text="上课教师"/>
      <@table.sortTd id="attendStatic.attenddate" text="考勤日期"/>
      <@table.sortTd id="attendStatic.attendtime" text="考勤时间"/>
      <@table.sortTd id="attendStatic.attendtype" text="考勤类型"/>
    </@>
    
    <@table.tbody datas=attendStatics;attendStatic>
      <@table.selectTd id="attendStaticId" value=attendStatic.id/>
      <td>${attendStatic.student.code!}</td>
      <td>${attendStatic.student.name!}</td>
      <td>
      <#assign adminClasses = attendStatic.student.adminClasses/>
      <#list adminClasses as adminClass>
      	${adminClass.name!}
      </#list>
      </td>
      <td>${attendStatic.student.firstMajor.name!}</td>
      <td>${(attendStatic.task.seqNo)!}</td>
      <td>${attendStatic.course.name!}</td>
      <td>${attendStatic.department.name!}</td>
      <td>
      <#assign teachers = attendStatic.task.arrangeInfo.teachers/>
      <#list teachers as teacher>
      	${teacher.name!}
      </#list>
      </td>
      <td>${attendStatic.attenddate?string('yyyy-MM-dd')}</td>
      <td>${attendStatic.attendtime!}</td>
      <td>
      <#if attendStatic.attendtype=='1'>
      	出勤
      <#elseif attendStatic.attendtype=='2'>
      	缺勤
      <#elseif attendStatic.attendtype=='3'>
       	 迟到
      <#elseif attendStatic.attendtype=='4'>
      	早退
      <#elseif attendStatic.attendtype=='5'>
      	请假
      </#if>
      </td>
    </@>
    </@>
</body>
    
    <script>
    
    var form = document.getElementById("taskListForm");
    function addAttendStatic(){
    	form.action = "?method=stuSeach";
        form.submit();
    }
    
    function editSingle(){
       id = getSelectIds("attendStaticId");
       if(id=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       if(isMultiId(id)) {alert("<@bean.message key="common.singleSelectPlease" />。");return;}
       form.action = "?method=editSingle&attendStatic.id=" + id;
       form.submit();
    }
    
    function removeAttendStatic(){
       ids = getSelectIds("attendStaticId");
       if(ids=="") {alert("<@bean.message key="prompt.task.selector" />");return;}
       if(confirm("将要删除选定的:"+countId(ids)+"个考勤详细信息")!=true)return;
       form.action="?method=remove&attendStaticIds=" + ids;
       form.submit();
    }
    
    function importAttendStatic(){
       form.action="?method=importForm&templateDocumentId=25";
	   addInput(form,"importTitle","考勤明细信息上传");
	   form.submit();
    }
    
    function updateAttendtype(){
    	id = getSelectIds("attendStaticId");
        if(id=="") {alert("请选择考勤信息");return;}
        if(isMultiId(id)) {alert("<@bean.message key="common.singleSelectPlease" />。");return;}
        if(confirm("确定销假？")!=true)return;
    	form.action = "?method=updateAttendtype&attendStatic.id=" + id;
        form.submit();
    }
    
    function batchUpdate(){
       ids = getSelectIds("attendStaticId");
       if(ids=="") {alert("请选择考勤信息");return;}
       if(confirm("将要批量销假选定的:"+countId(ids)+"个考勤详细信息")!=true)return;
       form.action = "?method=batchUpdate&attendStaticIds=" + ids;
       form.submit();
    }
    
    function enterQuery(event) {if (portableEvent(event).keyCode == 13)query();}
    function query(pageNo,pageSize,orderBy){
        form.action="?method=search";
        form.target = "_self";
        transferParams(parent.document.attendStaticForm,form,null,false);
        goToPage(form,pageNo,pageSize,orderBy);
    }
    </script>
<#include "/templates/foot.ftl"/> 