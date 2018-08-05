<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/itemSelect.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/course/CourseTake.js"></script>
<body>
	<table id="appointTutorBar" width="100%"></table>
	<@table.table width="100%" align="center" sortable="true" id="listTable">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.sortTd id="student.code" name="attr.stdNo"/>
			<@table.sortTd id="student.name" name="attr.personName"/>
			<@table.sortTd id="student.enrollYear" name="filed.enrollYearAndSequence"/>
			<@table.sortTd id="student.type.name" name="entity.studentType"/>
			<@table.sortTd id="student.department.name" name="entity.college"/>
			<@table.sortTd id="student.teacher.name" text="导师姓名"/>
			<td>班级名称</td>
		</@>
		<@table.tbody datas=stds; student>
			<@table.selectTd id="stdId" value="${student.id}"/>
			<td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">${student.code}</a></td>
			<td><@i18nName student?if_exists/></td>
			<td>${student.enrollYear}</td>
			<td><@i18nName student.type?if_exists/></td>
			<td><@i18nName student.department?if_exists/></td>
			<td><@i18nName student.teacher?if_exists/></td>
			<td><#list student.adminClasses?if_exists as adminClass>
				${adminClass.name}<#if adminClass_has_next>,</#if>
			</#list>
			</td>
		</@>
	</@>
    <@htm.actionForm name="listForm" method='post' entity="std" action="appointTutor.do"/>
	<script>
	    var bar = new ToolBar('appointTutorBar','选择学生',null,true,true);
	    bar.setMessage('<@getMessage/>');
        bar.addItem("<@msg.message key="action.export"/>","exportRecord()",'excel.png','指定导师表');
	    bar.addItem('选择导师','appointTutor()','detail.gif','选择导师');
	
	 	var form = document.listForm;
	    function getIds(){
	       return(getCheckBoxValue(document.getElementsByName("stdId")));
	    }
	    
	    function appointTutor(){
		    var idSeq=getIds();
		    if(""==idSeq){
		    	alert("请选择一些学生");
		    	return;
		    }
			form.action="appointTutor.do?method=listTutor";
			addInput(form,"stdIdSeq",idSeq);
			form.submit();
		}
		
		function exportRecord() {
		    if(${totalSize}>2000) {
                alert("记录大于2000，不能导出！");
                return;
            }
            
            addInput(form, "keys", "code,name,enrollYear,type.name,department.name,teacher.name", "hidden");
            addInput(form, "titles", "学号,姓名,所在年级,学生类别,院系,导师姓名", "hidden");
            exportList();
		}
	</script>
</body>   
<#include "/templates/foot.ftl"/>