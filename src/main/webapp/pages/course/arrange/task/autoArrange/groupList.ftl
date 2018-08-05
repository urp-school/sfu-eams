<#include "/templates/head.ftl"/>
<BODY LEFTMARGIN="0" TOPMARGIN="0">
 <form name="unArrangedGroupListForm" method="post" action="" onsubmit="return false;">  
 <input type="hidden" name="calendar.id" value="${RequestParameters['calendar.id']}"/>
 <input type="hidden" name="arrangeType" value="${RequestParameters['arrangeType']}"/>
 <input type="hidden" name="calendar.studentType.id" value="${RequestParameters['calendar.studentType.id']}"/>   
 <input type="hidden" name="task.arrangeInfo.isArrangeComplete" value="${RequestParameters['task.arrangeInfo.isArrangeComplete']}"/> 
 <#assign isCompleted=RequestParameters['task.arrangeInfo.isArrangeComplete']>
 <div id="taskListDiv">
 <table id="arrangeGroupBar"></table>
 <table width="100%" border="0" class="listTable">
    <tr bgcolor="#ffffff" onkeypress="DWRUtil.onReturn(event, query)">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td ></td>
      <td ><input style="width:100%"type="text" name="taskGroup.name"  value="${RequestParameters['taskGroup.name']?default("")}"/></td>
      <td ></td>
      <td ></td>
      <td ><input style="width:100%"type="text" name="taskGroup.priority" value="${RequestParameters['taskGroup.priority']?if_exists}"/></td>       
      <td width="15%">
        <select  name="taskGroup.isSameTime" style="width:100%">
          <@yesOrNoOptions RequestParameters['taskGroup.isSameTime']?if_exists/>
        </select>
       </td>   
     </tr>
    <tr align="center" class="darkColumn">
      <td align="center" width="2%" >
        <input type="checkbox" onClick="toggleCheckBox(document.getElementsByName('groupId'),event);">
      </td>
      <td width="10%"><@bean.message key="attr.index"/></td>
      <td width="20%"><@bean.message key="attr.groupName"/></td>
      <td width="10%">任务数</td>
      <td width="10%"><@bean.message key="attr.arrangeCount"/></td>
      <td width="10%"><@bean.message key="attr.priority"/></td>
      <td width="10%"><@bean.message key="attr.isSameTime"/></td> 
    </tr>
    <#list groupList as group>
   	  <#if group_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if group_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" 
        onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" 
        onclick="onRowChange(event)">
      <td class="select">
        <input type="checkBox" name="groupId" value="${group.id}">
      </td>
      <td>&nbsp;${group_index+1}</td>
      <td>
      <A href="taskGroup.do?method=info&taskGroup.id=${group.id}&notDisplayOperation=1">
       &nbsp;${group.name}</a>
       </td>
      <td>&nbsp;${group.directTasks?size}</td>
      <td>&nbsp;${group.arrangedTaskCount}</td>
      <td>&nbsp;${group.priority}</td>
      <td> <#if group.isSameTime == true><@bean.message key="common.yes"/> <#else> <@bean.message key="common.no"/> </#if></td>
    </tr>
	</#list>
	</form>
	<#assign paginationName="groupList"/>
	<#include "/templates/newPageBar.ftl"/>
	</table>
  </div>
  <form name="arrangeParamsForm" method="post" action="autoArrange.do?method=arrange">
  <input type="hidden" name="taskGroupIds" value=""/>
  <input type="hidden" name="roomIds" value=""/>  
  <input type="hidden" name="isMonitor" value="1"/>
  <input type="hidden" name="arrangeType" value="taskGroup"/>
  <#include "params.ftl"/>
  </form>  

 <script>	
	function query(pageNo,pageSize){
	    var form = document.unArrangedGroupListForm;
	    if(form['taskGroup.isSameTime'].value=="3") form['taskGroup.isSameTime'].name="temp1";
	    form. action="autoArrange.do?method=list";
	    if(null!=pageNo)
          form.action +="&pageNo=" + pageNo;
        if(null!=pageSize)
          form.action +="&pageSize=" + pageSize;
	    form.submit();
	}
    function pageGoWithSize(pageNo,pageSize){
        query(pageNo,pageSize);
    }
    function checkSelectIds(){
        var groupIds = getCheckBoxValue(document.getElementsByName("groupId"));
        if(groupIds=="") {alert("请选择排课组。");return false;}
        return true;
    }
    function arrange(){
        var groupIds = getCheckBoxValue(document.getElementsByName("groupId"));
        if(groupIds=="") {alert("请选择排课组任务。");return;}
        var allRoomIds=getAllOptionValue(parent.document.taskGroupForm['classroom']);
        if(allRoomIds=="") {if(!confirm("没有为本次排课选用教室，确定使用任务的建议教室。\n点击确定，进行排课。否则点击取消")) return;}
        
        document.arrangeParamsForm['roomIds'].value=allRoomIds;
        document.arrangeParamsForm.taskGroupIds.value=groupIds;
        document.arrangeParamsForm.submit();
    }
    function removeGroupArrangeResult(){
        var groupId = getCheckBoxValue(document.getElementsByName("groupId"));
        if(groupId=="") {alert("请选择排课组");return;}
        if(confirm("删除排课组的排课结果，信息不可恢复确认删除？")){
            var form =document.unArrangedGroupListForm;
            var params = getInputParams(form,"calendar");
            params+=getInputParams(form,"task");
            params+="&arrangeType=" + form['arrangeType'].value;
            addParamsInput(form,params);
            document.unArrangedGroupListForm.action="autoArrange.do?method=removeGroupArrangeResult&groupIds="+groupId;
            document.unArrangedGroupListForm.submit();
	    }
    }
   var bar = new ToolBar('arrangeGroupBar','<#if isCompleted=="0">\
          <@bean.message key="common.notArranged"/><#else>\
          <@bean.message key="common.alreadyArranged"/></#if>\
          <@bean.message key="entity.taskGroup"/>',null,true,true);
   bar.setMessage('<@getMessage/>');
   <#if isCompleted=="0">
   bar.addItem("<@bean.message key="info.arrange.paramsManagement"/>",setParams,'setting.png');
   <#else>
   bar.addItem("<@bean.message key="action.delete"/>",removeGroupArrangeResult,'delete.gif','删除排课结果');
   </#if>
    </script>
</body>
<#include "/templates/foot.ftl"/>