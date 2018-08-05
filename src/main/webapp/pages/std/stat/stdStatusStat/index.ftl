<#include "/templates/head.ftl"/>
<body>
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','学籍统计',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>");
</script>     
 <table class="frameTable_title">
      <tr>
       <td id="viewTD0" class="transfer"  onclick="javascript:changeView1('view0',event);statByHomeplace();" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" valign="top" >按生源地统计</font>
       </td>
       <td id="viewTD1" class="padding" onclick="javascript:changeView1('view1',event);statByDegree();" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" align="bottom" >按学位统计</font> 
       </td>
       <td id="viewTD2" class="padding" onclick="javascript:changeView1('view2',event);statByAbroad();" onmouseover="viewMouseOver(event)" onmouseout="viewMouseOut(event)">
          <font color="blue"><image src="${static_base}/images/action/list.gif" align="bottom" >按留学生统计</font> 
       </td>
       <td width="60%"></td>
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
       $("view"+i).style.display = "none";
     }
     document.getElementById(id).style.display = "block";
   }
   
	 var action="stdStatusStat.do";
 	 function statByHomeplace(){
 	    var form =document.homeplaceForm;
 	 	form.action=action+"?method=statByHomeplace";
 		form.submit();
 	}
	 statByHomeplace();
    function statByAbroad(){
         var form =document.abroadForm;
 	 	form.action=action+"?method=statByAbroad";
 		form.submit();
 	}
    function statByDegree(){
        var form =document.degreeForm;
 		form.action=action+"?method=statByDegree";
 		form.submit();
 	}
</script>
<script language="JavaScript" type="text/JavaScript" src="scripts/viewSelect.js"></script> 
</body>
<#include "/templates/foot.ftl"/>