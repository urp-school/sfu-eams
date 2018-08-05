<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
  <table id="adminClassBar" with="100%"></table>
  <script>
    var bar = new ToolBar("adminClassBar","班级列表",null,true,true);
    bar.addItem("选中添加","setAdminClasses()");
    bar.addItem("取消","cancelSetAdminClasses()");
  </script>
  <@table.table align="center" id="adminClassListTable">
  	<@table.thead>
  		<@table.selectAllTd id="adminClassId"/>
  		<@table.td name="attr.code"/>
  		<@table.td name="attr.infoname"/>
  		<@table.td name="attr.enrollTurn"/>
  	</@>
  	<@table.tbody datas=adminClassList?sort_by("name");adminClass>
  		<@table.selectTd id="adminClassId" value=adminClass.id/>
	  	<script>
	       detailArray['${adminClass.id}'] = {'name':'${adminClass.name?if_exists}'};
	  	</script>
      	<td>${adminClass.code?if_exists}</td>
      	<td>${adminClass.name?if_exists}</td>
      	<td>${adminClass.enrollYear}</td>
  	</@>
  </@>
  <#list 1..1 as i><br></#list>
  <script>
      function setAdminClasses(){
          var adminClassIds =getCheckBoxValue(document.getElementsByName("adminClassId"));
          var adminClassNames=getNames(getCheckBoxValue(document.getElementsByName("adminClassId")));
          
          if(adminClassIds==","){
             //alert("<@bean.message key="common.MultiSelectPlease"/>");return;
              self.parent.setAdminClasses("","");
          }
          else{
              self.parent.setAdminClasses(adminClassIds,adminClassNames);
          }
      }
      function resetAdminClass(){
      }
      function setAdminClassesChecked(){
          var rows  = adminClassListTable.tBodies[0].childNodes;
          var selectIds = "${RequestParameters['adminClassIds']}";
          
          if(selectIds=="")return;
          for(var i=1;i<rows.length;i++){
                if(selectIds.indexOf(rows[i].childNodes[0].firstChild.value)!=-1)
                   rows[i].childNodes[0].firstChild.checked = true;
          }
      }
      function cancelSetAdminClasses(){
          self.parent.cancelSetAdminClasses();
      }
      setAdminClassesChecked();
  </script>
  </body>
<#include "/templates/foot.ftl"/>