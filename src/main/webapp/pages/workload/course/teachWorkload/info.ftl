<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script>
 	function collegeAffirm(){
 		if("true"==${teachWorkload.collegeAffirm?string}){
 			if(!confirm("你已经确认过这个工作量,你还要确认一次吗??")){
 				return;
 			}
 		}
 		if(confirm("你确认要确认这个工作量吗?")){
 			document.teachWorkloadForm.action="teachWorkload.do?method=affirmSelect&affirmType=collegeAffirm&affirmEstate=true";
    		setSearchParams(parent.form,document.teachWorkloadForm);
    		document.teachWorkloadForm.submit();
    	}
 	}
 	function teacherAffirm(){
 		if("true"==${teachWorkload.teacherAffirm?string}){
 			if(!confirm("你已经确认过这个工作量,你还要确认一次吗??")){
 				return;
 			}
 		}
 		if(confirm("你确认要确认这个工作量吗?")){
    		document.teachWorkloadForm.action="teachWorkload.do?method=affirmSelect&affirmType=teacherAffirm&affirmEstate=true";
    		setSearchParams(parent.form,document.teachWorkloadForm);
    		document.teachWorkloadForm.submit();
    	}
 	}
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','授课工作量详细信息',null,true,true);
   bar.setMessage('<@getMessage/>');
   var affirmMenu = bar.addMenu('确认',null);
   affirmMenu.addItem("教师确认","teacherAffirm()");
   affirmMenu.addItem("院系确认","collegeAffirm()");
   bar.addBack('<@msg.message key="action.back"/>');
</script>
  <#include "../teachWorkloadSearch/detail.ftl"/>
 </body>
<#include "/templates/foot.ftl"/>