<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/Menu.js"></script>
<body  LEFTMARGIN="0" TOPMARGIN="0">  
  <table id="creditConstraintBar"></table>
  <#if RequestParameters['isMajorConstraint']="1">
  	<#include "creditConstraintListOfMajor.ftl"/>
  <#else>
  	<#include "creditConstraintListOfStd.ftl"/>
  </#if>
  <@htm.actionForm name="actionForm"  action="creditConstraint.do" entity="creditConstraint">
    <input type="hidden" name="specialityCreditConstraintIds" value=""/>
    <input type="hidden" name="stdContraintIdSeq" value=""/>
    <input type="hidden" name="isUpdateStdCascade" value=""/>
    <input type="hidden" name="constraintIds" value=""/>
    <input type="hidden" name="isMajorConstraint" value=""/>
    <input type="hidden" name="isMaxConstraint" value=""/>
    <input type="hidden" name="creditValue" value=""/>
  </@>
  <script>
      addHiddens(form,resultQueryStr);
	  addParamsInput(form,resultQueryStr);
      function setParams(){
           alert(form['params'].value);
      }
      function setCredit(isMaxCredit){
         var creditConstraintIds = getSelectIds("creditConstraintId");
         var isUpdateStdCascade = null;
         if (creditConstraintIds == "") {
         	alert("<@bean.message key="common.selectPlease"/>");
         	return;
     	 } else {
            var value=null;
            var promptStr="";
            var isMaxConstraint="1";
            if(isMaxCredit) {
               	promptStr = "<@bean.message key="prompt.credit.max"/>";
            } else {
               promptStr = "<@bean.message key="prompt.credit.min"/>";
               isMaxConstraint="0";
            }
          	value = prompt(promptStr,"0");
          	if(null==value)return;
            while(!/^\d*\.?\d*$/.test(value) || parseFloat(value) > 100) {
            	if (!/^\d*\.?\d*$/.test(value)) {
                	errStr = value + "<@bean.message key="error.digitForm"/>";
                } else if (parseFloat(value) > 100) {
                	errStr = "上下限不能超过100。";
                }
                value = prompt(promptStr+errStr,"0");
                if(value==null) {
                	return;
                }
            }
            var isMajorConstraint = parent.document.creditForm['isMajorConstraint'].value;
            if(isMajorConstraint=="1") {
	            if (confirm("<@bean.message key="prompt.credit.updateStdCascade"/>")) {
	            	isUpdateStdCascade="1";
	            } else {
	            	isUpdateStdCascade = "0";
	            }
    	    }
			<#--得到所选择的checkbox，返回数组-->
            var selectedIds = selectedCheckBoxContents("creditConstraintId");
        	for (var i = 0; i < selectedIds.length; i++) {
        		if(isMaxCredit) {
            		if (parseFloat($("min" + selectedIds[i]).innerHTML) > parseFloat(value)) {
            			alert("上限不能低于下限！");
            			return;
            		}
        		} else {
        			if (parseFloat($("max" + selectedIds[i]).innerHTML) < parseFloat(value)) {
            			alert("下限不能超过上限！");
            			return;
            		}
       			}
        	}
            	
	        form.action="creditConstraint.do?method=update";
          	if (isUpdateStdCascade!=null) {
                form["isUpdateStdCascade"].value = isUpdateStdCascade;
          	}
            form["constraintIds"].value = creditConstraintIds;
            form["isMajorConstraint"].value = isMajorConstraint;
            form["isMaxConstraint"].value = isMaxConstraint;
            form["creditValue"].value = value;
          	form.submit();
         }
      }
      function removeCredits(){
         multiAction('remove',"确定删除操作?");
      }
      //专业学分上限
      function reInitConstraintCredit(){
          var creditConstraintIds= getCheckBoxValue(document.getElementsByName("creditConstraintId"))
          if(creditConstraintIds=="") {
            alert("<@bean.message key="common.selectPlease"/>");
           	return;
          }
          if(!confirm("初始化学分将专业和个人的学分都将按照培养计划重新设置。是否确定要重新初始化学分?")) {
           	return;
          }
          form.action = "creditConstraint.do?method=reInitCreditConstraint";
          form["specialityCreditConstraintIds"].value = creditConstraintIds;
          form.submit();
      }
      //学生学分上限
      function statCreditConstraint(){
           var stdContraintIdSeq= getSelectIds("creditConstraintId");
           if(stdContraintIdSeq=="") {
             if(!confirm("没有选择学生，系统将按照查询条件进行初始化学生的已选学分和奖励学分，继续请点击[确定]"))
               return;
           }
           else {
              if(!confirm("初始化选中学生的已选学分和奖励学分，继续请点击[确定]")) {
              	return;
              }
           }
           form.action= "creditConstraint.do?method=statStdCreditPrompt";
           form["stdContraintIdSeq"].value = stdContraintIdSeq;
           form.submit();
      }
      
      function noCreditStdList(){
           form.action= "creditConstraint.do?method=noCreditStdList";
           form.submit();
      }
      
      function editConstraint(needId){
           var stdContraintId= "";
           if(needId) {
              stdContraintId=getCheckBoxValue(document.getElementsByName("creditConstraintId"));
              if(""==stdContraintId){alert("请选择一个学生的学分，进行修改");return;}
           }
           form.action= "creditConstraint.do?method=editStdCreditConstraint&stdCreditConstraint.id="+stdContraintId;
           form.submit();
     }
     
   var bar = new ToolBar('creditConstraintBar','<#if RequestParameters['isMajorConstraint']="1"><@bean.message key="info.credit.specialityConstraintList"/><#else><@bean.message key="info.credit.stdConstraintList"/></#if>',null,true,true);
   bar.setMessage('<@getMessage/>');

  <#if RequestParameters['isMajorConstraint']!="1">
   bar.addItem("<@msg.message key="action.add"/>","javascript:editConstraint(false)");
   var menu = bar.addMenu("<@msg.message key="action.edit"/>","javascript:editConstraint(true)");
   menu.addItem("<@bean.message key="action.setMax"/>","javascript:setCredit(true)");
   menu.addItem("<@bean.message key="action.setMin"/>","javascript:setCredit(false)");
   bar.addItem("统计学分/绩点","javascript:statCreditConstraint()");
   bar.addItem("无学分的学生","noCreditStdList()",'update.gif');
   <#else>
   bar.addItem("重新初始化","javascript:reInitConstraintCredit()");
   bar.addItem("<@bean.message key="action.setMax"/>","javascript:setCredit(true)");
   bar.addItem("<@bean.message key="action.setMin"/>","javascript:setCredit(false)");
   </#if>
   bar.addItem("<@msg.message key="action.delete"/>","removeCredits()",'delete.gif');
  </script>
</body> 
<#include "/templates/foot.ftl"/> 