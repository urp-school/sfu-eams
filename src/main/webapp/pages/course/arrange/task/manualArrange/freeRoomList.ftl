<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
<BODY>
<table id="freeRoomBar"></table>
<table class="listTable" width="100%" id="roomListTable">
<form name="roomListForm"  method="post" action="" onsubmit="return false;">
    <tr bgcolor="#ffffff" onKeyDown="javascript:enterQuery()">
      <td align="center" >
        <img src="${static_base}/images/action/search.gif"  align="top" onClick="displayRoomList()" alt="<@bean.message key="info.filterInResult" />"/>
      </td>
      <td><input type="text" id="classroom.code" value="${RequestParameters['classroom.code']?if_exists}" style="width:100%" maxlength="32"></td>
      <#assign classroomName><@localAttrName "classroom"/></#assign>
      <td><input type="text" id="${classroomName}" value="${RequestParameters[classroomName]?if_exists}" style="width:100%" maxlength="20"/></td>
      <td><input type="text" id="classroom.capacityOfCourse" value="${RequestParameters["classroom.capacityOfCourse"]?if_exists}" style="width:100%" maxlength="7"/></td>
      <td>
          <select id="classroom.configType.id" style="width:100%">
          <option value="">请选择..</option>
          <#list configTypeList as configType>
             <option value="${configType.id}"<#if configType.id?string==RequestParameters['classroom.configType.id']> selected</#if>><@i18nName configType/></option>
          </#list>
          </select>
      </td>
    </tr>
    <tr align="center" class="darkColumn">
      <td width="2%" align="center">&nbsp;</td>
      <td width="15%"><@bean.message key="attr.code"/></td>
      <td width="35%"><@bean.message key="attr.infoname"/></td>
      <td width="10%">人数</td>
      <td width="25%">教室设备配置</td>
    </tr>
    <#list result.classroomList.items?if_exists as room>
    <tr title="<@bean.message key="info.classroomSelector"/>" class="brightStyle" 
     onclick="selectRoom(this,event)" align="center"><td class="select"><input type="radio" name="freeRoomId" ><input type="hidden" name="classroomId" value="${room.id}"><input type="hidden" name="classroomName" value="<@i18nName room/>"></td>
       <td>${room.code}</td>
       <td><@i18nName room/></td>
       <td>${room.capacityOfCourse}</td>
       <td><@i18nName room.configType?if_exists/></td>
    </tr>
    </#list>
	<#assign paginationName="classroomList" />
	<#include "/templates/pageBar.ftl"/>
    </table>
	</form>
	<script language="javascript">
	<#assign detectCollision = RequestParameters["detectCollision"]?default('1')/>
   var bar = new ToolBar('freeRoomBar','<#if detectCollision='0'>教室列表（<font color="red">含非空闲</font>）<#else><@bean.message key="info.freeRoom.list"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');
   //bar.addItem("显示公共教室","javascript:displayRoomList(true,true)",'list.gif');
   bar.addItem("查询空闲",'displayRoomList(false, true)','search.gif');
   bar.addItem("查询所有","javascript:displayRoomList(false,false)",'list.gif',"不检测教室时间冲突");
    
    function displayRoomList(displayPublic,detectCollision,pageNo,pageSize){
      parent.document.weekTeacherRoomForm['classroom.code'].value=document.roomListForm['classroom.code'].value;
      parent.document.weekTeacherRoomForm['<@localAttrName "classroom"/>'].value=document.roomListForm['<@localAttrName "classroom"/>'].value;
      parent.document.weekTeacherRoomForm['classroom.capacityOfCourse'].value=document.roomListForm['classroom.capacityOfCourse'].value;
      parent.document.weekTeacherRoomForm['classroom.configType.id'].value=document.roomListForm['classroom.configType.id'].value;
      if(pageNo==null){
        pageNo="1";
      }

      if(pageSize==null){
        pageSize="20";
      }
      self.parent.listFreeRoom(displayPublic,detectCollision,pageNo,pageSize);
    }
	function selectRoom(obj,event){
	    ele = getEventTarget(event);
	    while(null!=ele&&ele.tagName!="TD"){
	        if(ele.parentNode!=null)
	           ele=ele.parentNode;
	        else ele=null;
	    }
	    if(null!=ele){
	      // 怪了，去掉一行还真不行
	      ele.parentNode.firstChild.firstChild.checked = true;
   	      ele.parentNode.firstChild.firstChild.checked = true;
	    }
	    setRoom();
	}
    function setRoom(){
        var inputId = document.roomListForm["freeRoomId"];
        if (!inputId[0]) {
        	if (!inputId.checked) {
        		alert("<@bean.message key="common.selector"/>");
        		return;
        	}
        	self.parent.setRoom(inputId.parentNode.childNodes[1].value, inputId.parentNode.childNodes[2].value);
        } else {
            var selected=-1;
        	for (var i=0; i<inputId.length; i++) {
        		if (inputId[i].checked) {
        			selected=i;
        			break;
        		}
        	}
        	if (-1 == selected) {
        		alert("<@bean.message key="common.selector"/>");
        		return;
        	}
        	self.parent.setRoom(inputId[selected].parentNode.childNodes[1].value, inputId[selected].parentNode.childNodes[2].value);
        }
    }
    function enterQuery() {
      if (getEvent().keyCode == 13) displayRoomList();
    }
    function pageGoWithSize(pageNo,pageSize){
       displayRoomList(null,null,pageNo,pageSize);
    }
  </script>
</body>
<#include "/templates/foot.ftl"/>