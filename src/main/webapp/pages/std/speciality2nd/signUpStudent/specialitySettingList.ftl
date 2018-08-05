<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0" >
<table id="myBar" width="100%"></table>
<@table.table id="listTable" sortable="true" width="100%">
  <@table.thead>
    <@table.selectAllTd id="specialitySettingId"/>
    <@table.sortTd name="entity.specialityAspect" id="[0].aspect.name"/>
    <#list 1..signUpSetting.choiceCount as i>
    <td>第${i}志愿</td>
    </#list>
    <@table.sortTd text="报名" id="total"/>
    <@table.td text="录取" id="matriculated"/>
    <@table.td text="调剂"/>
    <@table.sortTd text="上限" id="[0].limit"/>
  </@>
  <@table.tbody datas=settings;setting>
    <@table.selectTd id="specialitySettingId" value=setting[0].id/>
    <td><@i18nName setting[0].aspect?if_exists/></td>
    <#list 1..signUpSetting.choiceCount as i>
    <td>${setting[i*2-1]}/${setting[i*2]}</td>
    </#list>
    <td>${(setting[(signUpSetting.choiceCount+1)*2-1])?default('')}</td>
    <td>${(setting[(signUpSetting.choiceCount+1)*2])?default('')}</td>
    <td>${(setting[(signUpSetting.choiceCount+1)*2+1])?default('')}</td>
    <td>${setting[0].limit}</td>
  </@>
</@>
<@htm.actionForm name="actionForm" action="speciality2ndSignUpStudent.do" entity="specialitySetting">
  <input type="hidden" name="signUpStd.setting.id" value="${RequestParameters['signUpStd.setting.id']}"/>
</@>
<script>
  var bar = new ToolBar("myBar","专业设置列表(报名人数/录取人数)",null,true,true);
  bar.setMessage('<@getMessage/>');
  bar.addItem("录取名单","multiAction('matriculatedList')");
  bar.addPrint("<@msg.message key="action.print"/>");
  bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
  
      function beforSubmmit1(method) {
	 	var ids = getSelectIds("specialitySettingId");
	 	if (ids == null || ids == "") {
	 		alert("你没有选择要操作的记录！");
	 		return false;
	 	}
	 	
	    form.action = "speciality2ndSignUpStudent.do?method=" + method;
		addParamsInput(form,resultQueryStr);
	    return true;
	 }
	 
  function matriculatedList(){
     if (beforSubmmit1(method)) {
		    if(null!=confirmMsg){
		       if(!confirm(confirmMsg))return;
		    }
		    submitId(form,"specialitySettingId",multiId);
	    }
  }
</script>
</body>
<#include "/templates/foot.ftl"/> 
