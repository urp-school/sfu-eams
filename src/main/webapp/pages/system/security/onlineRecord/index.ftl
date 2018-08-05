<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','用户登录统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message "action.help"/>");
</script>     
 <table  class="frameTable_title">
      <tr>
       <td  id="viewTD0" class="transfer"  onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" valign="top" >登录查询</font>
       </td>
       <td  id="viewTD1" class="padding" onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" align="bottom" >次数统计</font> 
       </td>
       <td  id="viewTD2" class="padding" onclick="javascript:changeView1('view2',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" align="bottom" >分段统计</font> 
       </td>
       <td width="70%"></td>
      </tr>
     </table>
   <table width="100%" height="93%" class="frameTable">
   <tr>
   <td valign="top" class="frameTable_view" width="20%" style="font-size:10pt">
     <#include "statForm.ftl"/>
	<td>
	<td valign="top">
     	<iframe name="statFrame" id="statFrame" src="#" width="100%" frameborder="0" scrolling="no"></iframe>
	</td>
</table>
<script>
   viewNum=3;
   function changeView1(id,event){
     changeView(getEventTarget(event));
     for(i=0;i<3;i++){
       document.getElementById("view"+i).style.display = "none";
     }
     document.getElementById(id).style.display = "block"; 
   }
   
	 var action="onlineRecord.do";
 	 function loginCountStat(){
 	    var form =document.roleForm;
 	 	form.action=action+"?method=loginCountStat";
 		form.submit();
 	}
    function timeIntervalStat(){
        var form =document.numForm;
 	 	form.action=action+"?method=timeIntervalStat";
 		form.submit();
 	}
    function search(){
        var form =document.userForm;
 		form.action=action+"?method=search";
 		form.submit();
 	}
 	//statUser();
</script>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ViewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/>