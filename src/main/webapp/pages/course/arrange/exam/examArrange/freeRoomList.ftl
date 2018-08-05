<#include "/templates/head.ftl"/>
<BODY>
<table id="freeRoomBar"></table>
<@table.table width="100%">
  <form name="roomListForm" action="" method="post" onsubmit="return false;">
    <input type="hidden" name="params" value="${RequestParameters['params']}"/>
    <input type="hidden" name="examActivity.id" value="${RequestParameters['examActivity.id']}"/>
    <input type="hidden" name="task.id" value="${RequestParameters['task.id']}"/>
    <input type="hidden" name="examType.id" value="${RequestParameters['examType.id']}"/>
    <tr bgcolor="#ffffff" onKeyDown="DWRUtil.onReturn(event, query)">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif" align="top" onClick="javascript:query()" alt="<@bean.message key="info.filterInResult"/>"/>
      </td>
      <td><input type="text" name="classroom.code" maxlength="32" value="${RequestParameters['classroom.code']?if_exists}" style="width:100%"/></td>
      <td><input type="text" name="<@localAttrName "classroom"/>" maxlength="20" value="" style="width:100%"/></td>
      <td></td>
      <td><input type="text" name="classroom.capacityOfExam" value="${RequestParameters['classroom.capacityOfExam']?default('')}" maxlength="5" style="width:100%"/></td>
      <td><input type="text" name="classroom.floor" value="${RequestParameters['classroom.floor']?default('')}" style="width:100%" maxlength="5"/></td>
      <td>
          <select name="classroom.configType.id" style="width:100%">
          <option value="">请选择..</option>
          <#list configTypeList as configType>
             <option value="${configType.id}" <#if configType.id?string==RequestParameters['classroom.configType.id']?if_exists> selected </#if>><@i18nName configType/></option>
          </#list>
          </select>
      </td>
    </tr>
   </form>
    <@table.thead>
      <@table.selectAllTd id="classroomId"/>
      <@table.td width="15%" name="attr.code"/>
      <@table.td width="25%" name="attr.infoname"/>
      <@table.td width="20%" name="entity.building"/>
      <@table.td width="10%" text="考试人数"/>
      <@table.td width="8%" text="楼层"/>
      <@table.td width="25%" text="教室设备配置"/>
    </@>
    <@table.tbody datas=rooms;room>
      <@table.selectTd id="classroomId" value=room.id/>
       <td>${room.code}</td>
       <td><@i18nName room/></td>
       <td><@i18nName room.building?if_exists/></td>
       <td>${room.capacityOfExam}</td>
       <td>${room.floor?if_exists}</td>
       <td><@i18nName room.configType?if_exists/></td>
    </@>
   </@>
	<script language="javascript">
	   	var bar = new ToolBar('freeRoomBar','<@bean.message key="info.freeRoom.list"/>',null,true,true);
	   	bar.setMessage('<@getMessage/>'); 
	   	bar.addItem("查询",'query()','search.png');
	   	bar.addItem("<@msg.message key="action.add"/>",'add()','new.gif');
	   	
	    function query(){
	        document.roomListForm.action="examArrange.do?method=freeRoomList";
	        document.roomListForm.submit();
	    }
	    function add(){
	       var roomIds = getSelectIds("classroomId");
	       if(""==roomIds){
	          alert("请选择一个或多个教室");
	          return;
	       }
	       var form =document.roomListForm;
	       form.action="examArrange.do?method=addRoom";
	       addInput(form,"roomIds",roomIds,"hidden");
	       form.submit();
	    }
  	</script>
</body>
<#include "/templates/foot.ftl"/>