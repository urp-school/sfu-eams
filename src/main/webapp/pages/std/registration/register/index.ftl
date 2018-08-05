<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Selector.js"></script>
<script language="JavaScript" src="<@bean.message key="menu.js.url"/>"></script>
 <body >  
 <table id="gradeBar"></table>
    <table class="frameTable_title">
      <tr>
      	<td  id="viewTD0" class="transfer"  onclick="javascript:changeView1('view0',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue">有注册数据</font>
       </td>
       <td  id="viewTD1" class="padding" onclick="javascript:changeView1('view1',event)" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue">无注册数据</font> 
       </td>
       <td class="separator">|</td>
     <form name="stdSearch" method="post" action="register.do?method=index" onsubmit="return false;">
      <input type="hidden" name="register.calendar.id" value="${calendar.id}" />
      <#include "/pages/course/calendar.ftl"/>
     </tr>
   </table>
  <table width="100%" border="0" height="100%" class="frameTable">
  <tr>
   <td style="width:20%" valign="top" class="frameTable_view">
        <#assign stdTypeList=calendarStdTypes>
		<#include "/pages/components/initAspectSelectData.ftl"/>
		<#include "searchForm.ftl"/>
	  </form>
     </td>
     <td valign="top">
	     <iframe src="#" id="contentFrame" name="contentFrame" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" height="100%" width="100%">
	     </iframe>
     </td>
    </tr>
  <table>
<script>
    var action="register.do";
    var form = document.stdSearch;
    function search(pageNo,pageSize,orderBy){
	   form.action = action+"?method=search";
	   form.target="contentFrame";
       goToPage(form,pageNo,pageSize,orderBy);
    }
    var bar=new ToolBar("gradeBar","注册信息管理",null,true,true);
    bar.addItem("注册管理","addReg()");
    bar.setMessage('<@getMessage/>');
    bar.addHelp("<@msg.message key="action.help"/>");
    search();
    function notPassed(){
       setSelected(document.getElementById("isPass"),3);
       search();
    }
    
    function addReg(){
  	 var url = "register.do?method=add&year=${calendar.year}&calendarId=${calendar.id}&term=${calendar.term}";
   	window.open(url, '', 'scrollbars=yes,left=0,top=0,width=600,height=400,status=yes');
  	 }
    
    viewNum=2;
   	function changeView1(id,event){
     	 changeView(getEventTarget(event));
	     if(id=="view1"){	
	     	$("button1").style.display="none";
	     	$("button2").style.display="";
	     	$("isFinishedRegisterTitle").style.display="none";       
	     	$("isFinishedRegister").style.display="none";       
	         form.action = "register.do?method=unregisterList";
			 form.target="contentFrame";
		     form.submit();
	     }else{
	     	$("button1").style.display="";
	     	$("button2").style.display="none";
	     	$("isFinishedRegisterTitle").style.display="";       
	     	$("isFinishedRegister").style.display=""; 
	         form.action = "register.do?method=search";
			 form.target="contentFrame";
		     form.submit();
     }
   }
   
   $("button2").style.display="none";
</script> 
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
 </body>   
<#include "/templates/foot.ftl"/> 