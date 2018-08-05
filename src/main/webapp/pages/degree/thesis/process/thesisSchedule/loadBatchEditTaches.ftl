<#include "/templates/head.ftl"/>
<body>
<table id="bar2" width="100%"></table>
<form name="actionForm" method="post" action="" onSubmit="return false;">
<@table.table   width="100%" id="listTable">
	<@table.thead>
      <@table.td  text="步骤名称"/>
      <@table.td  text="计划完成时间" />
      <@table.td  text="是否需要导师确认" />
    </@>
    <#list tacheSettings?sort_by(["tache","code"]) as tacheSetting>
	  <#if tacheSetting_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if tacheSetting_index%2==0 ><#assign class="brightStyle" ></#if>
	  <tr class="${class}">
  		<td>${(tacheSetting.tache.name)?if_exists}</td>
  		<td><input name="planTime${tacheSetting.id}" type="text" value="<#if tacheSetting.planedTimeOn?exists>${tacheSetting.planedTimeOn?string("yyyy-MM-dd")}</#if>" style="width:100%" onfocus="calendar();f_frameStyleResize(self)"></td>
  		<td>
  			<select name="isTutorAffirm${tacheSetting.id}" style="width:100%">
  				<option value="true" <#if tacheSetting.isTutorAffirm?exists&&tacheSetting.isTutorAffirm?string=="true">selected</#if>>是</option>
  				<option value="false" <#if tacheSetting.isTutorAffirm?exists&&tacheSetting.isTutorAffirm?string=="false">selected</#if>>否</option>
  			</select>
  		</td>
  	  </tr>
  	  </#list>
      <tr>
      	<td colspan="3" align="center" class="darkColumn"><button name="buttonName9"  class="buttonStyle" onclick="doSubmit()">提交</button></td>
      </tr>
 </@>
   <input name="scheduleId" value="${scheduleId}" type="hidden"/>
   <input name="tacheSettingIds" type="hidden" value="${tacheSettingIds}"/>
   <input name="batch" value="batch" type="hidden"/>
 </form>
 <script>
    var tacheBar = new ToolBar("bar2","批量修改步骤信息",null,true,true);
    tacheBar.addItem("提交","doSubmit()");
    tacheBar.addBack();
    var action="thesisSchedule.do";
    var form=document.actionForm;
    function doSubmit(){
    	if(confirm("你确定要先在提交你修改的信息吗?")){
    		form.action = action+"?method=batchEditTache";
    		form.submit();
    	}
    }
 </script>
</body>
<#include "/templates/foot.ftl"/>