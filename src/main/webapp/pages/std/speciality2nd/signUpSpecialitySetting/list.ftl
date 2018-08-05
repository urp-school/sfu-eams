<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table>
<@table.table id="listTable" sortable="true" width="100%">
  <@table.thead>
    <@table.selectAllTd id="specialitySettingId"/>
    <@table.sortTd name="entity.speciality" id="specialitySetting.speciality.name"/>
    <@table.sortTd name="entity.specialityAspect" id="specialitySetting.aspect.name"/>
    <@table.sortTd text="报名" id="specialitySetting.total"/>
    <@table.sortTd text="录取" id="specialitySetting.matriculated"/>
    <@table.sortTd text="上限" id="specialitySetting.limit"/>
  </@>
  <@table.tbody datas=specialitySettings;setting>
    <@table.selectTd id="specialitySettingId" value=setting.id/>
    <td><@i18nName setting.speciality/></td>
    <td><@i18nName setting.aspect?if_exists/></td>
    <td>${setting.total?default(0)}</td>
    <td>${setting.matriculated?default(0)}</td>
    <td>${setting.limit}</td>
  </@>
</@>
<@htm.actionForm name="actionForm" action="speciality2ndSignUpSpecialitySetting.do" entity="specialitySetting">
  <input type="hidden" name="specialitySetting.setting.id" value="${RequestParameters['specialitySetting.setting.id']}"/>
</@>
<script>
  var bar = new ToolBar("myBar","专业设置列表",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("<@msg.message key="action.new"/>","add()");
  bar.addItem("批量修改上限","multiAction('editLimits')");
  bar.addItem("<@msg.message key="action.delete"/>","remove()");
  bar.addItem("返回设置","parent.search()","backward.gif");
</script>
</body>
<#include "/templates/foot.ftl"/> 
