<#include "/templates/head.ftl"/>
<body>
 <table id="gradePointRuleInfoBar" width="100%"></table>
 <table class="infoTable">
   <form name="gradePointRuleForm" method="post"  onsubmit="return false;">
    <input type="hidden" name="gradePointRule.id" value="${gradePointRule.id?if_exists}"/>
   <tr>
       <td class="title" width="15%">名称<font color="red">*</font></td>
       <td width="15%"><input name="gradePointRule.name" value="${gradePointRule.name?if_exists}" maxLength="20"/></td>
       <td class="title" width="15%"><@msg.message key="entity.studentType"/><!--<font color="red">*</font>--></td>
       <td width="15%">
         <#if (gradePointRule.id?string)?default("")!="0">
         <@htm.i18nSelect datas=stdTypes selected=(gradePointRule.stdType.id?string)?default("") 
           name="gradePointRule.stdType.id" style="width:100px">
          <option value=""></option>
         </@>
         </#if>
       </td>
       <td class="title" width="15%">成绩记录方式</td>
       <td width="15%">
          <@htm.i18nSelect datas=markStyles selected=(gradePointRule.markStyle.id?string)?default("")
           name="gradePointRule.markStyle.id" style="width:100px"/>
       </td>
   </tr>
   </form>
 </table>
 <#if gradePointRule.id?exists>
 <#--百分制与其它等级制的填写样式-->
 <#macro majorTypeHTML name config value>
    <#if config.converters?size == 0>
        <input type="text" class="text" name="${name}" value="${value}" maxlength="5"/>
    <#else>
	    <select name="${name}" style="width:100px">
	       <#list config.converters as configItem>
            <option value="${configItem.defaultScore}"<#if (value?is_number && value >= configItem.minScore && value <= configItem.maxScore || value == configItem.grade)> selected</#if>>${configItem.grade}</option>
	       </#list>
	    </select>
    </#if>
 </#macro>
 <table id="myBar" width="100%"></table>
  <@table.table width="100%">
   <form name="batchUpdateForm" method="post"  onsubmit="return false;">
    <input type="hidden" name="params" value="&gradePointRuleId=${gradePointRule.id?if_exists}">
    <input type="hidden" name="gradePointRuleId" value="${gradePointRule.id?if_exists}">
   <@table.thead>
       <@table.selectAllTd id="GPMappingId"/>
       <td width="10%">下限(包含)</td>
       <td width="10%">上限(包含)</td>
       <td width="15%">对应绩点</td>
     </@>
     <@table.tbody datas=gradePointRule.GPMappings?sort_by("maxScore");GPMapping>
      <@table.selectTd id="GPMappingId" value="${GPMapping.id}" type="checkbox"/>
      <td><input type="text" class="text" name="minScore${GPMapping.id}" value="${GPMapping.minScore}" maxlength="5"/></td>
      <td><input type="text" class="text" name="maxScore${GPMapping.id}" value="${GPMapping.maxScore}" maxlength="5"/></td>
      <td><input type="text" class="text" name="gp${GPMapping.id}" value="${GPMapping.gp?string("#.##")}" maxlength="5"></td>
     </@>
     </form>
  </@>
  </#if>
 <div id="reportSetting" style="display:none;width:200px;height:100px;position:absolute;top:125px;right:50px;border:solid;border-width:1px;background-color:white ">
  <form name="actionForm" method="post" action="gradePointRule.do" onsubmit="return false;">
    <input type="hidden" name="params" value="&gradePointRuleId=${gradePointRule.id?if_exists}">
    <input type="hidden" name="gradePointRuleId" value="${gradePointRule.id?if_exists}">
   <table class="settingTable" width="100%" height="100%">
   <tr>
	   <td>&nbsp;&nbsp;下限：</td>
	   <td><input type="text" name="GPMapping.minScore" style="width:60px" maxlength="5"/></td>
   </tr>
   <tr>
	   <td  style="width:40%">&nbsp;&nbsp;上限</td>
	   <td><input type="text" name="GPMapping.maxScore" style="width:60px" maxlength="5"/></td>
   </tr>
   <tr>
	   <td>&nbsp;&nbsp;绩点：</td>
	   <td><input type="text" name="GPMapping.gp" style="width:60px" maxlength="3"/></td>
   </tr>
   <tr align="center">
      <td colspan="2"><button onclick="saveGPMapping();" class="buttonStyle" accesskey="S"><@msg.message key="action.save"/>(<U>S</U>)</button>
         &nbsp;<button accesskey="c" class="buttonStyle" onclick="closeSetting();"><@msg.message key="action.close"/>(<U>C</U>)</button></td>
   </tr>
 </form>
 </table> 
  <script>
    function displaySetting(){
       if(reportSetting.style.display=="block"){
           reportSetting.style.display='none';
       }else{
	       reportSetting.style.display="block";
	       document.actionForm['GPMapping.minScore'].focus();
	       f_frameStyleResize(self);
       }
    }
    function closeSetting(){
       reportSetting.style.display='none';
    }
  </script>
 </div>
 
  <script>
   var action="gradePointRule.do";
   function save(){
        var form = document.gradePointRuleForm;
        errorInfo="";
        if(form['gradePointRule.name'].value==""){
          errorInfo+="请给绩点映射起个名字!\n";
        }
        //if(null!=form['gradePointRule.stdType.id']){
        //   if(form['gradePointRule.stdType.id'].value==""){
        //     errorInfo+="请选择学生类别!";
        //   }
        //}
        if(""!=errorInfo){
           alert(errorInfo);return;
        } else {
	        form.action=action+"?method=save";
	        form.target="_parent";
	        form.submit();
        }
   }
   function saveGPMappings(){
      var form =document.batchUpdateForm;
      var errors="";
      <#list gradePointRule.GPMappings as gpmapping>
       errors+=checkSingleMapping(form,'${gpmapping.id}');
      </#list>
      if(errors!="") {alert(errors);return;}
      errors="";
      var datas=new Array();
      <#list gradePointRule.GPMappings?sort_by("maxScore") as gpmapping>
         datas.push(parseFloat(form['minScore${gpmapping.id}'].value));
         datas.push(parseFloat(form['maxScore${gpmapping.id}'].value));
      </#list>
      for(var i=0;i<datas.length;i+=2){
        if(datas[i+1]<datas[i]){
           errors+=datas[i+1]+":"+datas[i]+" 上下限范围错误\n";
        }
        if(i!=0&&datas[i-1]>=datas[i]){
           errors+=datas[i-1]+":"+datas[i]+" 上下限范围错误\n";
        }
      }
      if(errors!=""){alert(errors);return;}
      form.action="gradePointRule.do?method=batchSaveGPMapping";
      form.submit();
   }
   function checkSingleMapping(form,gpMappingId){
       var errorInfo="";
       if(!(/^\d+\.?\d*$/.test(form['gp'+gpMappingId].value))){
         errorInfo+=form['gp'+gpMappingId].value+" 格式不对\n";
       }
       if(!(/^\d+\.?\d*$/.test(form['minScore'+gpMappingId].value))){
         errorInfo+=(form['minScore'+gpMappingId].value+" 格式不对\n");
       }
       if(!(/^\d+\.?\d*$/.test(form['maxScore'+gpMappingId].value))){
         errorInfo+=(form['maxScore'+gpMappingId].value+" 格式不对\n");
       }
       return errorInfo;
   }
   function removeGPMappings(){
      submitId(document.batchUpdateForm,"GPMappingId",true,"gradePointRule.do?method=removeGPMapping","确定删除绩点规则?");      
   }
   function saveGPMapping(){
       var form =document.actionForm;
       var errorInfo="";
       var minScore = form['GPMapping.minScore'].value;
       var maxScore = form['GPMapping.maxScore'].value;
       if(!(/^\d+\.?\d*$/.test(minScore))){
          errorInfo+=form['GPMapping.minScore'].value+" 格式不对\n";
       }
       if(!(/^\d+\.?\d*$/.test(maxScore))){
          errorInfo+=form['GPMapping.maxScore'].value+" 格式不对\n";
       }
       if(!(/^\d+\.?\d*$/.test(form['GPMapping.gp'].value))){
          errorInfo+=form['GPMapping.gp'].value+" 格式不对";
       }
       if (minScore != "" && maxScore != "" && parseFloat(minScore) > parseFloat(maxScore)) {
       		errorInfo += "上下限范围错误\n";
       }
       
       if(errorInfo!=""){
         alert(errorInfo);return;
       } else {
       	  
	      form.action="gradePointRule.do?method=saveGPMapping";
	      form.submit();
      }
   }

   var bar = new ToolBar('gradePointRuleInfoBar','绩点映射规则   ${gradePointRule.name?if_exists}',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem('<@msg.message key="action.save"/>',"save()","save.gif");
   bar.addBack();
   <#if gradePointRule.id?exists>
   var bar1 =new ToolBar('myBar','绩点对照表',null,true,true);
   bar1.addItem("<@msg.message key="action.new"/>","displaySetting()","new.gif");
   bar1.addItem('<@msg.message key="action.save"/>',"saveGPMappings()","save.gif");
   bar1.addItem('<@msg.message key="action.delete"/>',"removeGPMappings()");
   </#if>
  </script>
</body>
<#include "/templates/foot.ftl"/>