<#include "/templates/head.ftl"/>
 <BODY>
 <table id="backBar" width="100%"></table>
    <@table.table   width="100%" id="listTable" sortable="true">
	<@table.thead>
      <@table.selectAllTd id="practiceId"/>
      <@table.sortTd  name="attr.stdNo" id="graduatePractice.student.code"/>
      <@table.sortTd  name="attr.personName" id="graduatePractice.student.name"/>
      <@table.sortTd  text="院系" id="graduatePractice.student.department.name"/>
      <@table.sortTd  text="专业" id="graduatePractice.student.firstMajor.name"/>
      <@table.sortTd  text="实习单位" id="graduatePractice.practiceCompany"/>
      <@table.sortTd  text="是否实习基地" id="graduatePractice.isPractictBase"/>
      <@table.td text="实习方式"/>
      <@table.sortTd text="指导老师姓名"  id="graduatePractice.practiceTeacher.name"/>
    </@>
    <@table.tbody datas=graduatePractices;graduatePractice>
      <@table.selectTd id="practiceId" value="${graduatePractice.id}"/>
        <td>${(graduatePractice.student.code)?if_exists}</td>
  		<td>${(graduatePractice.student.name)?if_exists}</td>
  		<td>${(graduatePractice.student.department.name)?if_exists}</td>
  		<td>${(graduatePractice.student.firstMajor.name)?if_exists}</td>
  		<td>${(graduatePractice.practiceCompany)?if_exists}</td>
  		<td>${graduatePractice.isPractictBase?string("是","否")}</td>
  		<td>${(graduatePractice.practiceSource.name)?if_exists}</td>
  		<td>${(graduatePractice.practiceTeacher.name)?if_exists}</td>
      </@>
    </@>
    <form name="actionForm" method="post" action="" onsubmit="return false;">
      	<input type="hidden" id="keys" name="keys" value="student.type.name,student.department.name,student.code,student.name,practiceCompany,practiceSource.name,isPractictBase,practiceTeacher.name,practiceDesc,teachCalendar.year,teachCalendar.term">
      	<input type="hidden" id="titles" name="titles" value="<@msg.message key="entity.studentType"/>,<@msg.message key="entity.department"/>,<@msg.message key="attr.stdNo"/>,<@msg.message key="attr.personName"/>,实习单位,实习方式,是否实习基地,指导教师,实习内容,学年度,学期">
      	<#list RequestParameters?keys as key>
      		<input type="hidden" name="${key}" value="${RequestParameters[key]}"/>
      	</#list>
    </form>
<script>
	var bar = new ToolBar('backBar','毕业实习列表列表',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addItem("<@msg.message key="action.add"/>","add()");
	bar.addItem("批量指定",'batchAdd()');
	bar.addItem("<@msg.message key="action.edit"/>","edit()");
	bar.addItem("<@msg.message key="action.delete"/>","remove()");
	var menu = bar.addMenu("高级..");
	menu.addItem("<@msg.message key="action.export"/>","exportObject()");
	menu.addItem("<@msg.message key="action.print"/>","print()");
	
	var form = document.actionForm;
	action="graduatePractice.do";
	
	function addParams() {
		addInput(form, "params", queryStr, "hidden");
	}
	
	function add(){
	   var calendarId = parent.document.searchForm['calendar.id'].value;
	   form.action=action+"?method=edit&calendarId="+calendarId;
	   form.submit();
	}
	function batchAdd(){
		addInput(form, "graduatePractice.calendar.id", "${RequestParameters['calendar.id']?default("")}");
		form.action = "graduatePractice.do?method=stdList";
		form.submit();
	}
	function edit(){
	   form.action=action+"?method=edit";
	   addParams();
	   submitId(form,"practiceId",false);
	}
	function remove(){
		form.action=action+"?method=remove";
		addParams();
		submitId(form,"practiceId",true,null,"你确定要删除吗?");
	}
	function exportObject(){
	   form.action =action+"?method=export";
	   form.submit();
	}
	orderBy =function(what){
		parent.search(1,${pageSize},what);
	}
	function pageGoWithSize(pageNo,pageSize){
       parent.search(pageNo,pageSize,'${RequestParameters['orderBy']?default('null')}');
    }
</script>
</body>
<#include "/templates/foot.ftl"/>
	