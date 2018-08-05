<#include "/templates/head.ftl"/>
<body>
	<table id="bar"></table>
	<table class="frameTable" width="100%">
		<tr valign="top">
			<form method="post" action="" name="searchForm">
			<td class="frameTable_view" width="20%"><#include "/pages/components/classroomSearchTable.ftl"/></td>
			</form>
			<td><iframe name="pageFrame" src="#" width="100%" height="100%" frameborder="0" scrolling="no"></iframe></td>
		</tr>
	</table>
	<script>
		var bar = new ToolBar("bar", "教室基础信息查询", null, true, true);
		bar.setMessage('<@getMessage/>');
		bar.addHelp("<@msg.message key="action.help"/>");
		
		var form = document.searchForm;
		function searchClassroom() {
			form.action = "classRoomSearch.do?method=search";
			form.target = "pageFrame";
			form.submit();
		}
    	searchClassroom();
	</script>
	  <script language="JavaScript" type="text/JavaScript" src="<@msg.message key="js.baseinfo"/>"></script>
  <script language="javascript">
    type="classroom";
    keys="code,name,engName,abbreviation,description,createAt,modifyAt,remark,state,capacityOfCourse,capacityOfExam,floor,configType.name,building.name,schoolDistrict.name";
    titles="<@msg.message key="attr.code"/>,<@msg.message key="attr.name"/>,<@msg.message key="attr.engName"/>,<@msg.message key="attr.abbreviation"/>,<@msg.message key="attr.description"/>,<@msg.message key="attr.createAt"/>,<@msg.message key="attr.modifyAt"/>,<@msg.message key="attr.remark"/>,<@msg.message key="attr.state"/>,<@msg.message key="attr.capacityOfCourse"/>,<@msg.message key="attr.capacityOfExam"/>,<@msg.message key="attr.floor"/>,<@msg.message key="entity.classroomConfigType"/>,<@msg.message key="entity.building"/>,<@msg.message key="entity.schoolDistrict"/>";
    labelInfo="<@bean.message key="page.classroomListFrame.label" />";
    search();
    searchClassroom=search;
    initBaseInfoBar();
  </script>
<script language="JavaScript" type="text/JavaScript" src="scripts/CascadeSelect.js"></script> 
<#include "/templates/districtBuildingSelect.ftl"/>
</body>
<#include "/templates/foot.ftl"/>
