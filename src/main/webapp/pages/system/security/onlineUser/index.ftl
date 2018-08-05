<#include "/templates/head.ftl"/>

<BODY>
 <table id="onlineUserBar"></table>
 <table width="100%" class="frameTable">
 <tr>
  <td width="20%" valign="top" class="frameTable_view">
   <table class="searchTable" width="100%" >
   <form name="onlineUserForm" method="post" onsubmit="return false;">
     <tr>
     <td colspan="2">
      <fieldSet  align=center> 
	  <legend>在线信息</legend>
	   在线: ${onlineCount}</br>
	   <#list categories?keys as categoryId>
        ${categories[categoryId].name}：${onlineMap[categoryId]?default(0)}</br>
       </#list>
      </fieldSet>
      </td>
     </tr>
      
     <tr>
      <td colspan="2">
      <br>
      <fieldSet  align=center> 
	  <legend>人数上限</legend>
	   上限：${max}</br>
	   <#list categories?keys as categoryId>
        ${categories[categoryId].name}：<input name="max_${categoryId}" style="width:50px"value="${maxMap[categoryId]?default(100)}"/></br>
       </#list>
      </fieldSet>
      </td>
      </tr>
      <tr>
         <td  align="center" ><button onclick="save(this.form);">提交</button></td>
      </tr>
     </form>
   </table>
  </td>  
  <td valign="top">
	 <@table.table width="100%" id="onLineUserTable" sortable="true">
	    <@table.thead>
	      <@table.selectAllTd id="sessionId"/>
	      <@table.sortTd width="10%" text="用户名" id="principal"/>
	      <@table.sortTd width="10%" text="姓名" id="details.userName"/>
	      <@table.sortTd width="15%" text="登陆时间" id="details.loginAt"/>
	      <@table.sortTd width="15%" text="最近访问时间" id="lastRequest"/>
	      <@table.sortTd width="10%" text="在线时间" id="onlineTime"/>
	      <@table.sortTd width="15%" text="地址" id="details.host"/>
	      <@table.sortTd width="10%" text="用户身份" id="details.category"/>
	      <@table.sortTd width="10%" text="状态" id="expired"/>
	   </@>
	   <@table.tbody datas=sessionInfos;sessionInfo>
	      <@table.selectTd  id="sessionId" value=sessionInfo.sessionId/>
	      <td>${sessionInfo.principal}</td>
	      <td>${sessionInfo.details.userName?default('')}</td>
	      <td>${sessionInfo.details.loginAt?string("yy-MM-dd HH:mm")}</td>
	      <td>${sessionInfo.lastRequest?string("yy-MM-dd HH:mm")}</td>
	      <td>${(sessionInfo.onlineTime)/1000/60}min</td>
	      <td>${sessionInfo.details.host?default('')}</td>
	      <td>${categories[sessionInfo.details.category?string].name}</td>
	      <td>${sessionInfo.expired?string("过期","在线")}</td>
	   </@>
	  </@>
  </td>
 </tr>
</table>
<script>
  var form=  document.onlineUserForm;
   function save(form){
      <#list categories?keys as categoryId>
      if(!(/^\d+$/.test(form['max_${categoryId}'].value))){alert("${categories[categoryId].name}最大用户数限制应为整数");return;}
      </#list>
      form.action="onlineUser.do?method=save";
      form.submit();
   }
   function query(){
       alert("暂无实现");
   }

   function invalidateSession(){      
      var names =getCheckBoxValue(document.getElementsByName("sessionId"));
      if(names =="") {
         window.alert('<@msg.message key="common.selectPlease"/>!');
         return;
      }
      if(!confirm("是否确定要结束选定用户的会话?")) return;
      form.action="onlineUser.do?method=invalidate&sessionIds=" +names;
      form.submit();      
   }
   function refresh(){
      form.action="onlineUser.do?method=index";
      form.submit();
   }
   function sendMessage(){
       var names =getCheckBoxValue(document.getElementsByName("name"));
       if(""==names){
           alert("请选择用户");return;
       }
       window.open("systemMessage.do?method=quickSend&receiptorIds="+names);
   }
   var bar = new ToolBar('onlineUserBar','在线用户',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addItem("发送消息","sendMessage()","inbox.gif");
   bar.addItem("刷新","javascript:refresh()",'refresh.gif');
   bar.addItem("结束会话","javascript:invalidateSession()",'delete.gif');
   bar.addBack("<@msg.message key="action.back"/>");    
</script>
</body>
<#include "/templates/foot.ftl"/>