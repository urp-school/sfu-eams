<#include "/templates/head.ftl"/>
<body>
<table id="teachPlanConfirmBar"></table>
<script>
  var bar = new ToolBar("teachPlanConfirmBar",'<@msg.message key="teachPlan.confirm.title"/>',null,true,true);
  bar.setMessage('<@getMessage/>')
  bar.addItem("<@bean.message key="action.affirm"/>","updateConfirmState('1')");
  bar.addItem("<@bean.message key="action.affirm.cancle"/>","updateConfirmState('0')");
</script>
   <table class="frameTable">
   <tr>
    <td width="20%" class="frameTable_view"><#include "../planSearchForm.ftl"/></td>
    <td valign="top">
	 <iframe  src="#"
	     id="planListFrame" name="planListFrame"
	     marginwidth="0" marginheight="0" scrolling="no"
	     frameborder="0"  height="100%" width="100%" >
	  </iframe> 
    </td>
   </tr>
  </table>
 <script language="JavaScript" type="text/JavaScript" src="scripts/course/TeachPlan.js"></script>
 <script>
    multi=true;
    withAuthority=true;
    searchTeachPlan();
 </script>
</body>

<#include "/templates/foot.ftl"/>