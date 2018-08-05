<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<@table.table width="100%" align="center">
		<@table.thead>
			<@table.selectAllTd id="stdId"/>
			<@table.td name="attr.stdNo" width="10%"/>
			<@table.td name="attr.personName" width="14%"/>
			<@table.td name="entity.college" width="20%"/>
			<@table.td name="entity.speciality"/>
			<@table.td name="filed.enrollYearAndSequence" width="15%"/>

		</@>
		<@table.tbody datas=studentList; student>
			<@table.selectTd id="stdId" value=student.id/>
		    <td><a href="studentDetailByManager.do?method=detail&stdId=${student.id}">${student.code}</a></td>
		    <td><a href="#" onclick="viewDetail(${student.id})"><@i18nName student?if_exists/></a></td>
		    <td><@i18nName student.department?if_exists/></td>
		    <td><@i18nName student.firstMajor?if_exists/></td>
		    <td align="center">${student.enrollYear}</td>
		</@>
	</@>
	<form method="post" action="" name="actionForm">
        <#assign filterKeys = ["method", "pageNo", "button1", "reset1", "button31", "searchFalg", "moduleName", "stdIds", "stdId", "params"]/>
        <#list RequestParameters?keys as key>
            <#if !filterKeys?seq_contains(key)>
        <input type="hidden" name="${key}" value="${RequestParameters[key]?if_exists}"/>
            </#if>
        </#list>
	</form>
	<script>
		var bar = new ToolBar("bar", "<@bean.message key="std.stdList"/>1", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addItem("<@msg.message key="action.export"/>", "exportData()");
		bar.addItem('查看明细', 'viewDetail()');
		
		var form = document.actionForm;
		
		function checkIds(){
	 		if (getSelectIds("stdId") == "") {
	 			alert("请选择学生！");
	 			return false;
	 		}
	 		return true;
	 	}
		function viewDetail(stdId) {
	 		form.action="stdCreditStat.do?method=viewDetail";
	 		form.target = "_self";
	 		addParamsInput(form, queryStr);
		    if (null == stdId) {
				if(!checkIds()) {
		 			return;
		 		}
		 		stdId = getSelectIds("stdId");
		 		submitId(form, 'stdId', false);
		    } else {
		        addInput(form, "stdId", stdId, "hidden");
                form.submit();
		    }
		}
		
	function exportData(){
       var stdIds = getSelectIds("stdId");
       form.action="stdCreditStat.do?method=export";
       addInput(form,"keys","std.code,std.name,std.type.name,totalCredit","hidden");
       addInput(form,"titles","学号,姓名,学生类别,总学分","hidden");
       if(""==stdIds){
          if(!confirm("未选中学生,系统将导出查询条件内的所有学生的上课记录?")) return;
          else{
             addInput(form,"stdIds",stdIds, "hidden");
             transferParams(parent.document.stdCreditStatForm,form,"student",false);
             form.submit();
          }
       }else{
          if(!confirm("系统将导出选中学生的上课记录?")) return;
          else{
             addInput(form,"stdIds",stdIds,"hidden");
             form.submit();
          }
       }
    }
	</script>
</body>
<#include "/templates/foot.ftl"/>