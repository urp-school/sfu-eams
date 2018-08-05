<#include "/templates/head.ftl"/>
<body  >
<table id="requirementBar"></table>
 <@table.table width="100%" border="0">
   <form name="preferenceListForm" method="post">
    <@table.thead>
      <@table.td text=""/>
      <@table.td width="10%" name="attr.courseNo"/>
      <@table.td width="20%" name="attr.courseName"/>
      <@table.td width="15%" name="attr.roomConfigOfTask"/>
      <@table.td name="entity.textbook"/>
      <@table.td width="5%" name="attr.teachLangType"/>
    </@>
    <@table.tbody datas=requirementPreferenceList;preference>
      <@table.selectTd  type="radio" id="preferenceId" value="${preference.id}"/>
      <td>&nbsp;${preference.course.code}</td>
      <td>&nbsp;${preference.course.name}</td>
      <td>${preference.require.roomConfigType.name}</td>
      <td><A href="#" onclick="editTextbook(${preference.id})"><#if preference.require.textbooks?size==0>添加教材<#else><@getBeanListNames preference.require.textbooks?if_exists/></#if></A></td>
      <td><@i18nName (preference.require.teachLangType)?if_exists/></td>       
    </@>
  </@>
  </form>
  <script>	
    function editRequire(){
        var form = document.preferenceListForm;
        var id = getRadioValue(form.preferenceId);
        if(id==""){alert("<@bean.message key="common.selectPlease" />");return;}
        if(id.indexOf(",")!=-1){alert("<@bean.message key="common.singleSelectPlease"/>");return;}        
        form.action="requirePrefer.do?method=edit&requirementType=inPreference&requirementPreference.id="+id;
        //alert(form.action);
        form.submit();
    }
   var bar = new ToolBar('requirementBar','<@bean.message key="entity.teachTask" /><@bean.message key="entity.taskRequirementPreference"/> <@bean.message key="common.list" />',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("选中修改课程要求","javascript:editRequire()",'update.gif'); 
   
   function editTextbook(requirePreferId){
        var form = document.preferenceListForm;
        addInput(form,"requirePrefer.id",requirePreferId,"hidden");
        form.action="requirePrefer.do?method=editTextbook&forward=preferList&requirementType=inPreference";
        form.submit();
    }   
    </script>
</body>    
<#include "/templates/foot.ftl"/>     