<#include "/templates/head.ftl"/>
<body leftmargin="0" topmargin="0">
 <table id="bar"></table>
<@table.table width="100%" id="listTable" sortable="true">
   <@table.thead>
    <@table.selectAllTd id="alterationId"/>
    <@table.sortTd name="attr.stdNo" id="std.code"/>
    <@table.sortTd name="attr.personName" id="std.name"/>
    <@table.sortTd name="entity.studentType" id="std.type.name"/>
    <@table.sortTd name="entity.department" id="std.department.name"/>
    <@table.sortTd name="entity.speciality" id="alteration.std.firstMajor.name"/>
    <@table.sortTd id="std.state.name" text="学籍状态"/>
    <@table.sortTd id="alteration.mode.name" text="变动类型"/>
     <@table.sortTd text="变动日期" id="alteration.alterBeginOn"/>
   </@>
   <@table.tbody datas=alterations;alteration>
     <@table.selectTd type="checkbox" id="alterationId" value=alteration.id/>
     <td><a href="studentDetailByManager.do?method=detail&stdId=${alteration.std.id}" title="查看学生基本信息">${(alteration.std.code)?if_exists}</a></td>
     <td><A href="#" onclick="alterationInfo(${alteration.id})" title="查看该学生的学籍变动信息"><@i18nName alteration.std/></A></td>
     <td><@i18nName alteration.std.type/></td>
     <td><@i18nName alteration.std.department/></td>
     <td><@i18nName alteration.std.firstMajor?if_exists/></td>
     <td>${(alteration.beforeStatus.state.name)?default("?")}-${(alteration.afterStatus.state.name)?default("?")}</td>
     <td><@i18nName alteration.mode/></td>
     <td>${alteration.alterBeginOn?string("yyyy-MM-dd")}</td>
   </@>
</@>
<@htm.actionForm name="actionForm" action="studentAlteration.do" entity="alteration">
    <input type="hidden" name="keys" value="std.code,std.department.name,std.name,std.firstMajor.name,beforeStatus.adminClass.name,afterStatus.adminClass.name,mode.name,alterBeginOn,reason.name,remark"/>
    <input type="hidden" name="titles" value="学号,学院,姓名,专业,变动前班级,变动后班级,异动种类,异动日期,异动原因,异动备注"/>
</@>
<script>
    var bar=new ToolBar("bar","学籍变动管理",null,true,true);
    bar.setMessage('<@getMessage/>');
    bar.addItem("<@msg.message key="action.info"/>","alterationInfo()");
    bar.addItem("<@msg.message key="action.new"/>","add()");
    bar.addItem("<@msg.message key="action.edit"/>","edit()");
    bar.addItem("<@msg.message key="action.delete"/>","remove()");
    bar.addItem("相关处理", "processOthers()", "update.gif", "进一步处理学生的成绩、选课等信息。");
    bar.addItem("导出","exportList()");
    
    function alterationInfo(id) {
    	if (null == id || "" == id) {
    		info();
    	} else {
    		form.action = "studentAlteration.do?method=info";
    		addInput(form, "alterationId", id, "hidden");
    		form.submit();
    	}
    }
    function add(){
      form.action = "studentAlteration.do?method=searchStuNo";
      form.submit();
    }
    function processOthers() {
        //form.target = "_blank";
        submitId(form, "alterationId", false, "studentAlteration.do?method=processOthers");
    }
</script>
</body>
<#include "/templates/foot.ftl"/>