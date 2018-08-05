<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table>
<@table.table id="listTable" sortable="true" width="100%">
  <@table.thead>
    <@table.selectAllTd id="stdScopeId"/>
    <@table.sortTd name="attr.enrollTurn" id="stdScope.enrollTurn"/>
    <@table.sortTd name="entity.studentType" id="stdScope.stdType"/>
    <@table.sortTd name="entity.department" id="stdScope.department"/>
    <@table.sortTd name="entity.speciality" id="stdScope.speciality"/>
    <@table.sortTd name="entity.specialityAspect" id="stdScope.aspect"/>
  </@>
  <@table.tbody datas=stdScopes;scope>
    <@table.selectTd id="stdScopeId" value=scope.id/>
    <td>${scope.enrollTurn?default("")}</td>
    <td><@i18nName scope.stdType?if_exists/></td>
    <td><@i18nName scope.department?if_exists/></td>
    <td><@i18nName scope.speciality?if_exists/></td>
    <td><@i18nName scope.aspect?if_exists/></td>
  </@>
</@>
<@htm.actionForm name="actionForm" action="speciality2ndSignUpStdScope.do" entity="stdScope">
  <input type="hidden" name="stdScope.setting.id" value="${RequestParameters['stdScope.setting.id']}"/>
</@>
<script>
  var bar = new ToolBar("myBar","报名学生范围列表",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.new"/>","add()");
  bar.addItem("<@msg.message key="action.edit"/>","edit()");
  bar.addItem("<@msg.message key="action.delete"/>","remove()");
  bar.addItem("返回设置","parent.search()","backward.gif");
</script>
</body>
<#include "/templates/foot.ftl"/> 
