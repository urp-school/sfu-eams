<#include "/templates/simpleHead.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="scripts/common/ToolBar.js"></script>
<link href="${static_base}/css/toolBar.css" rel="stylesheet" type="text/css">
<body>
<table id="promptBar" width="100%"></table>
<script>
  	var bar = new ToolBar("promptBar","<@msg.message key="course.std.weclomeInterface"/>",null,true,true);  
   	bar.addHelp("&nbsp;<@msg.message key="action.help"/>");
</script>
 	<table width="80%" align="center" class="listTable">
	<#if electState?exists>
	<#assign  electParams = electState.params>
	<#assign  isEvaluated = electState.isEvaluated?default(false)>
	</#if>
	   <tr class="darkColumn">
	     <td align="center" colspan="4"><@i18nName electParams.calendar.studentType/> <@msg.message key="attr.year2year"/>：${electParams.calendar.year}<@msg.message key="attr.term"/>：${electParams.calendar.term} <@msg.message key="course.noticeItemOfElective"/></td>
	   </tr>
	   <tr>
	     <td class="grayStyle" width="25%" id="f_turn">&nbsp;<@bean.message key="attr.electTurn"/>：</td>
	     <td class="brightStyle">${electParams.turn?if_exists}</td>
	     <td class="grayStyle" width="25%" id="f_turn">&nbsp;<@msg.message key="common.switch"/>：</td>
	     <td class="brightStyle"><#if electParams.isOpenElection><@msg.message key="common.opened"/><#else><@msg.message key="common.closed"/></#if></td>
	   </tr>
       <tr>
	   	 <td class="darkColumn" width="25%" colspan="4">&nbsp;<@bean.message key="entity.electDateTime"/>：</td>
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%" id="f_startDate">&nbsp;<@bean.message key="attr.startDate"/>：</td>
	     <td class="brightStyle"> ${electParams.startDate?if_exists}</td>
	   	 <td class="grayStyle" width="25%" id="f_startTime">&nbsp;<@bean.message key="attr.startTime"/>：</td>
	     <td class="brightStyle"> ${electParams.startTime?if_exists}  </td>
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%" id="f_finishDate">&nbsp;<@bean.message key="attr.finishDate"/>：</td>
	     <td class="brightStyle"> ${electParams.finishDate?if_exists}</td>
	   	 <td class="grayStyle" width="25%" id="f_finishTime">&nbsp;<@bean.message key="attr.finishTime"/>：</td>
	     <td class="brightStyle"> ${electParams.finishTime?if_exists}  </td>
       </tr>
       <tr>
	   	 <td class="darkColumn" width="25%"  colspan="4">&nbsp;<@bean.message key="entity.electMode"/>：</td>
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%" >&nbsp;<@bean.message key="attr.isOverMaxAllowed"/>：</td>	
	     <td class="brightStyle"><#if electParams.isOverMaxAllowed?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
	   	 <td class="grayStyle" width="25%" >&nbsp;<@bean.message key="attr.isRestudyAllowed"/>：</td>
	     <td class="brightStyle"><#if electParams.isRestudyAllowed?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>	     
       </tr> 
       <tr>
         <td class="grayStyle" width="25%" >&nbsp;<@msg.message key="course.elect.withdrawMinimumPeople"/>：</td>
	     <td class="brightStyle"><#if electParams.isUnderMinAllowed?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>
         <td class="grayStyle" width="25%" >&nbsp;重修时是否限制选课对象：</td>
	     <td class="brightStyle"><#if electParams.isCheckScopeForReSturdy?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>	     
       </tr>
       <tr>
         <td class="grayStyle" width="25%" >&nbsp;<@bean.message key="attr.isCancelAnyTime"/>：</td>
	     <td class="brightStyle"><#if electParams.isCancelAnyTime?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>	     
	   	 <td class="grayStyle" width="25%">&nbsp;<@msg.message key="course.elect.thinkToAwardCredit"/>：</td>	
	     <td class="brightStyle"><#if electParams.isAwardCreditConsider?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>      
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.isCheckEvaluation"/>：</td>	
	     <td class="brightStyle"><#if electParams.isCheckEvaluation?if_exists==true><@bean.message key="common.yes"/><#else><@bean.message key="common.no"/></#if></td>         
	   	 <td class="grayStyle" width="25%">&nbsp;<@bean.message key="attr.floatCredit"/>：</td>
	     <td class="brightStyle">${electParams.floatCredit?if_exists}</td> 	
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%">&nbsp;是否限制校区：</td>	
	     <td class="brightStyle">${electParams.isSchoolDistrictRestrict?string("是","否")}</td>
	   	 <td class="grayStyle" width="25%">&nbsp;是否计划内课程：</td>	
	     <td class="brightStyle">${electParams.isInPlanOfCourse?string("是","否")}</td>
       </tr>
       <tr>
	   	 <td class="grayStyle" width="25%" id="f_notice">&nbsp;<@bean.message key="attr.notice"/>：</td>	
	     <td class="brightStyle" colspan="3"> ${electParams.notice?if_exists}</td>
       </tr>
       <#if electParams.isCheckEvaluation>
       <tr>
	   	 <td class="grayStyle" width="25%">&nbsp;<@msg.message key="field.studentEvaluate.evaluate"/>：</td>	
	     <td class="brightStyle" colspan="3"><#if isEvaluated><@msg.message key="attr.finished"/><#else><@msg.message key="attr.unfinished"/></#if></td>
       </tr>
       </#if>
	   <tr class="darkColumn">
	     <td colspan="4" align="center">
	       <input type="button" class="INPUT_button" id="goButton" value="<@msg.message key="course.entryElective"/>" name="goElection" onClick="goElectHome()" class="buttonStyle"/>&nbsp;	       
	     </td>
	   </tr>
  </table>
  <script>
     var nowDate ='${now?string("yyyy-MM-dd")}';
     var nowTime ='${now?string("HH:mm")}';
     function  goElectHome(){
         document.getElementById("goButton").value="<@msg.message key="course.loadingSystem"/>";
         document.getElementById("goButton").disabled=true;
         self.window.location="electCourse.do?method=index&electParams.id=${electParams.id}";
     }
     function ElectState(){
       this.constraint=new Object();
       this.params=new Object();
       this.params.isCheckEvaluation=${electParams.isCheckEvaluation?string};
       <#if electParams.isCheckEvaluation>
       this.isEvaluated=${isEvaluated?if_exists?string};
       <#else>
       this.isEvaluated=true;
       </#if>
       this.params.startDate ='${electParams.startDate?string("yyyy-MM-dd")}';
       this.params.finishDate ='${electParams.finishDate?string("yyyy-MM-dd")}';
       this.params.startTime ='${electParams.startTime}';
       this.params.finishTime ='${electParams.finishTime}';
       this.params.isOpenElection =${electParams.isOpenElection?string};
     }
     function canEnterElectSystem(state){
        if(!state.params.isOpenElection) {
           alert("<@msg.message key="course.noOpenedElective"/>");
           return  false;
        }
        if(state.params.isCheckEvaluation&&!state.isEvaluated){
          alert("<@msg.message key="course.unfinishEvaluate"/>");
          return false;
         }
        if(checkDate(state)) {
            return true;
         }
        else{
          alert("<@msg.message key="course.notElectivetime"/>");
          return false;
        }
     }
     function checkDate(state){
         var startDateInfo = state.params.startDate.split("-");
         var startTimeInfo =state.params.startTime.split(":");
         var a = new Date(startDateInfo[0],startDateInfo[1]-1,startDateInfo[2],startTimeInfo[0],startTimeInfo[1],0,0);
         startTime=a.valueOf();
         
         var finishDateInfo = state.params.finishDate.split("-");
         var finishTimeInfo =state.params.finishTime.split(":");
         b =new Date(finishDateInfo[0],finishDateInfo[1]-1,finishDateInfo[2],finishTimeInfo[0],finishTimeInfo[1],0,0);
         finishTime=b.valueOf();
         
         var nowDateInfo = nowDate.split("-");
         var nowTimeInfo = nowTime.split(":");
         c =new Date(nowDateInfo[0],nowDateInfo[1]-1,nowDateInfo[2],nowTimeInfo[0],nowTimeInfo[1],0,0);
         nowTimeMills=c.valueOf();
         
         return (startTime<=nowTimeMills&&nowTimeMills<=finishTime);
     }
     function setElectEnable(state){
         if(canEnterElectSystem(state)){
           document.getElementById("goButton").disabled=false;
         }
         else{
           document.getElementById("goButton").disabled =true;
         }
     }
     setElectEnable(new ElectState());
  </script>
  <div align="center" style="color:red" ><h3>请同学们 <a href="http://xg.shfc.edu.cn:81/epstar/web/applications/SWMS/JBXX/GRJBXXWH/index.jsp?current.model.id=4si1o18-187f2k-g4kq2s9z-1-g4kr0v0q-8">点此链接</a> 即时维护学工系统中的个人基本信息，其中必填项和家庭信息是校内相关部门提供就业及资助服务的主要依据，请认真填写。也可直接登录学工系统，在综合服务——我的基本信息页面中完成该任务。</h3></div>
  </body>
<#include "/templates/foot.ftl"/>