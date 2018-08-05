<#include "/templates/head.ftl"/>
<body >
  	<div align="left" style="color:red">1、补缓考考试时间是每学期开学后的第2个周末。</div>
	<div align="left" style="color:red">2、课程考试用时以试卷标明为准。</div>
	<div align="left" style="color:red">3、请参加考试的同学携带学生证或带有照片的一卡通。</div>
  <table id="examActivityBar" width="100%"></table>
  <script>
     var bar=new ToolBar('examActivityBar','<@msg.message key="exam.arrangeResultList"/>',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("<@msg.message key="exam.printArrange"/>","print()");
     //bar.addItem("<@msg.message key="exam.printDelayApplication"/>","print_delay()");
     bar.addItem("<@msg.message key="exam.applicationDelay"/>","applyDelay()");
     bar.addItem("<@msg.message key="exam.cancelApplication"/>","cancelApply()");
     var params = "&calendar.id=${RequestParameters['calendar.id']}&examType.id=${examType.id}";
     function print_delay(){
        var takeIds = getSelectIds("examTakeId");
        if(""==takeIds){
           alert("<@msg.message key="exam.tipToChoice"/>");
           return;
        }else{
           self.location="stdExamTable.do?method=printDelayApply&examTakeIds="+takeIds+params;
        }
     }
     function applyDelay(){
        var takeId = getSelectId("examTakeId");
        if(""==takeId){alert("<@msg.message key="exam.tipToChoiceArrange"/>");return;}
        self.location="stdExamTable.do?method=editApply&examTake.id="+takeId+params;
     }
     function cancelApply(){
        var takeId = getSelectId("examTakeId");
        if(""==takeId){alert("<@msg.message key="exam.tipToChoiceArrange"/>");return;}
        self.location="stdExamTable.do?method=cancelApply&examTakeIds="+takeId+params;
     }
     function setExamTake(courseTakeId,examTakeId){
        document.getElementById(courseTakeId).innerHTML='<input type="radio" name="examTakeId" value="'+examTakeId+'">'
     }
</script>

 <table width="100%" class="listTable" id="listTable">
    <tr align="center" class="darkColumn">
      <td align="center" class="select"></td>
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="15%"><@bean.message key="attr.courseName"/></td>
      <td width="10%"><@msg.message key="exam.date"/></td>
      <td width="25%"><@msg.message key="exam.arrange"/></td>
      <td width="8%"><@msg.message key="exam.address"/></td>
      <td width="8%">座位号</td>
      <td width="8%"><@msg.message key="exam.situation"/></td>
      <td width="8%"><@msg.message key="exam.otherExplanation"/></td>
    </tr>
    <#list takes as take>
   	  <#if take_index%2==1 ><#assign class="grayStyle" ></#if>
	  <#if take_index%2==0 ><#assign class="brightStyle" ></#if>
     <tr class="${class}" align="center" 
       onmouseover="swapOverTR(this,this.className)" onmouseout="swapOutTR(this)" >
      <td  class="select" id="${take.id}"></td>
      <td>${take.task.seqNo?if_exists}</td>
      <td><@i18nName take.task.course/></td>
      <#assign group>null</#assign>
      <#assign examTake="null">
      
      <#if take.task.arrangeInfo.getExamGroup(examType)?exists>
         <#assign group =take.task.arrangeInfo.getExamGroup(examType)>
      </#if>
      
      <#if group!="null"&&group.isPublish?default(false)=false>
      <td colspan="4"><@msg.message key="exam.noDeploySituation"/></td>
      <#else>
        <#if take.getExamTake(examType)?exists>
          <#assign examTake =take.getExamTake(examType)>
        </#if>
        <#if examTake!="null">
      <td><#if examTake.activity?exists>${examTake.activity.time.firstDay?string("yyyy-MM-dd")}</#if></td>
      <td><#if examTake.activity?exists>${examTake.activity.digest(calendar,Session["org.apache.struts.action.LOCALE"],Request["org.apache.struts.action.MESSAGE"],"第:weeks周 :day :time")}</#if></td>
      <td><@i18nName (examTake.activity.room)?if_exists/></td>
       <td>${examMap[examTake.id?string]?if_exists}</td>
      <td><@i18nName examTake.examStatus/></td>
         <#else>
      <td colspan="4"><@msg.message key="exam.noRecords"/></td>
         </#if>
      </#if>
      <td><#if group!="null">${group.name}</#if></td>     
      <script>
         <#if examTake!="null">
           setExamTake(${take.id},${examTake.id});
         </#if>
      </script>
    </tr>
	</#list>
	</table>
	
</body> 
<#include "/templates/foot.ftl"/> 