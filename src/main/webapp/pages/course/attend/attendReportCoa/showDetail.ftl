<#include "/templates/head.ftl"/>
<body>
<table id="taskBar"></table>
<script>
     var bar = new ToolBar('taskBar', '学生考勤明细列表', null, true, false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("销假", "resumption()");
    
    var attendtypeMap = {};
</script>
    <#assign attendtypes = {"1":"出勤", "2":"缺勤", "3":"迟到", "4":"早退", "5":"请假"}/>
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
          <td>${attendStatic.student.code}</td>
          <td>${attendStatic.student.name}</td>
          <td>
          <#assign adminClasses = attendStatic.student.adminClasses/>
          <#list adminClasses as adminClass>
            ${adminClass.name}
          </#list>
          </td>
          <td>${attendStatic.student.firstMajor.name}</td>
          <td>${(attendStatic.task.seqNo)!}</td>
          <td>${attendStatic.course.name}</td>
          <td>${attendStatic.department.name}</td>
          <td>
          <#assign teachers = attendStatic.task.arrangeInfo.teachers/>
          <#list teachers as teacher>
            ${teacher.name}
          </#list>
          </td>
          <td>${attendStatic.attenddate?string('yyyy-MM-dd')}</td>
          <td>${attendStatic.attendtime!}</td>
          <td>${attendtypes[attendStatic.attendtype]}<script>attendtypeMap["${attendStatic.id}"] = "${attendStatic.attendtype}";</script></td>
        </@>
    </@>
    <#assign filterKeys = ["method"]/>
    <form method="post" action="" name="actionForm" onsubmit="return false;">
        <input type="hidden" name="attendStaticIds" value=""/>
        <input type="hidden" name="params" value="<#list RequestParameters?keys as key><#if !filterKeys?seq_contains(key)>&${key}=${RequestParameters[key]!}</#if></#list>"/>
    </form>
    <script>
        var form = document.actionForm;
        
        function initData() {
            form["attendStaticIds"].value = "";
        }
        
        function resumption() {
            initData();
            var attendStaticIds_ = getSelectIds("attendStaticId");
            if (null == attendStaticIds_ || undefined == attendStaticIds_ || "" == attendStaticIds_) {
                alert("请选择要操作的记录。");
                return;
            }
            
            var attendStaticIds = attendStaticIds_.split(",");
            for (var i = 0; i < attendStaticIds.length; i++) {
                if (attendtypeMap[attendStaticIds[i]] == "5") {
                    alert("当前选择的记录中存在已经“销假”（即“考勤类型”为“请假”）\n的记录，请选择非“销假”的记录。");
                    return;
                }
            }
            
            if (confirm("确定要将当前所选择的 " + attendStaticIds.length + " 条记录进行销假，\n把 “考勤类型”置为“请假”吗？")) {
                form.action = "?method=resumption";
                form["attendStaticIds"].value = attendStaticIds_;
                form.target = "_self";
                form.submit();
            }
        }
        
        function enterQuery(event) {if (portableEvent(event).keyCode == 13)query();}
	    function query(pageNo,pageSize,orderBy){
	        var form2=taskListForm;
	        form2.action="?method=showDetail";
	        form2.target = "_self";
	        transferParams(parent.document.attendStaticForm,form2,null,false);
	        goToPage(form2,pageNo,pageSize,orderBy);
	    }
    </script>
</body>
    
<#include "/templates/foot.ftl"/> 