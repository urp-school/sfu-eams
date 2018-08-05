<#include "/templates/head.ftl"/>
<script language="JavaScript" src="scripts/Menu.js"></script>
<script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
<body >
 <script language="javascript" >
    function doAction(form){
    	var a_fields={
    			'preAnswer.isTutorAffirm':{'l':'是否同意预答辩', 'r':true, 't':'f_isAffirm'},
    			<#list 0..4 as i>
    			'answerTeam${i}.name':{'l':'<@msg.message key="attr.personName"/>', 'r':true, 't':'f_name','mx':100},
    			'answerTeam${i}.specialityAspect':{'l':'<@msg.message key="entity.specialityAspect"/>', 'r':true, 't':'f_specialityAspect','mx':100},
    			'answerTeam${i}.depart':{'l':'院系所(单位)', 'r':true, 't':'f_depart','mx':100},
    			</#list>
    			'preAnswer.advice':{'l':'专家意见和建议', 'r':true, 't':'f_advice','mx':1000},
    			'preAnswer.isPassed':{'l':'预答辩结果', 'r':true, 't':'f_isPassed'}
          	}
         var errors = "";
         var answerNum = getIds("preAnswer.answerNum");
         if(answerNum.indexOf(",")>-1){
         	errors+="次数只能选择一个\n";
         }
         var isTutorAffirm = getIds("preAnswer.isTutorAffirm");
         if(isTutorAffirm.indexOf(",")>-1){
         	errors+="是否同意预答辩只能选择一个\n";
         }
         var isPassed = getIds("preAnswer.isPassed");
         if(isPassed.indexOf(",")>-1){
         	errors+="预答辩结果只能选择一个\n";
         }
         if(""!=errors){
         	alert(errors);
         	return;
         }
         var v = new validator(form,a_fields, null);
         if(v.exec()){
         	form.action="preAnswer.do?method=doWriteBook&methodFlag=doWriteBook&preAnswerId="+${preAnswerId?if_exists};
         	form.submit();
         }
   }
   function getIds(name){
       return(getCheckBoxValue(document.getElementsByName(name)));
    }   
 </script>
<form name="listForm" action="#" method="post" onSubmit="return false;">
  <table id="backBar" width="100%"></table>
	<script>
   var bar = new ToolBar('backBar','博士研究生学位论文预答辩申请及备案情况表',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@bean.message key="action.back"/>');
</script>
  <#include "book.ftl"/>
  <tr>
	<td colspan="5" align="center" class="darkColumn">
	  <input type="hidden" name="thesisManageId">
	  <input type="hidden" name="parameters" value="${parameters?if_exists}">	    
	  <input type="button" value="提交" name="button9" onClick="doAction(this.form)" class="buttonStyle"/>&nbsp;    
	</td>
  </tr>
</table>
</form>
</body>
<#include "/templates/foot.ftl"/>