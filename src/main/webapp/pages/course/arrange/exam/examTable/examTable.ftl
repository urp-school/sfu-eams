<#include "/templates/head.ftl"/>
<body >
  
  <table id="examActivityBar" width="100%"></table>
  <script>
     var bar=new ToolBar('examActivityBar','排考结果列表',null,true,false);
     bar.setMessage('<@getMessage/>');
     bar.addItem("打印考试安排","print()");
     bar.addItem("打印缓考申请","print_delay()");
     bar.addItem("申请缓考","applyDelay()");
     bar.addItem("取消申请","cancelApply()");
     var params = "&calendar.id=${RequestParameters['calendar.id']}&examType.id=${examType.id}";
     function print_delay(){
        var takeIds = getSelectIds("examTakeId");
        if(""==takeIds){
           alert("请选择申请缓考的课程");
           return;
        }else{
           self.location="examTable.do?method=printDelayApply&examTakeIds="+takeIds+params;
        }
     }
     function applyDelay(){
        var takeIds = getSelectIds("examTakeId");
        if(""==takeIds){alert("请选择排考");return;}
        self.location="examTable.do?method=editApply&examTakeIds="+takeIds+params;
     }
     function cancelApply(){
        var takeIds = getSelectIds("examTakeId");
        if(""==takeIds){alert("请选择排考");return;}
        self.location="examTable.do?method=cancelApply&examTakeIds="+takeIds+params;
     }
     function setExamTake(courseTakeId,examTakeId){
        document.getElementById(courseTakeId).innerHTML='<input type="checkBox" name="examTakeId" value="'+examTakeId+'">'
     }
</script>

 <table width="100%" class="listTable" id="listTable">
    <tr align="center" class="darkColumn">
      <td align="center" class="select"></td>
      <td width="8%"><@bean.message key="attr.taskNo"/></td>
      <td width="15%"><@bean.message key="attr.courseName"/></td>
      <td width="10%">考试日期</td>
      <td width="25%">考试安排</td>
      <td width="8%">考试地点</td>
      <td width="8%">主考老师</td>
      <td width="8%">考试情况</td>
      <td width="8%">其他说明</td>
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
      <td colspan="5">考试情况尚未发布</td>
      <#else>
        
        <#if take.getExamTake(examType)?exists>
          <#assign examTake =take.getExamTake(examType)>
        </#if>
        
        <#if examTake!="null">
      <td>${examTake.activity.time.firstDay?string("yyyy-MM-dd")}</td>
      <td>${examTake.activity.task.arrangeInfo.digestExam(take.task.calendar,Request["org.apache.struts.action.MESSAGE"],Session["org.apache.struts.action.LOCALE"],examTake.activity.examType.id,"第:weeks周 :day :time")}</td>
      <td><@i18nName examTake.activity.room?if_exists/></td>
      <td><@i18nName examTake.activity.examMonitor.examiner?if_exists/></td>
      <td><@i18nName examTake.examStatus/></td>
         <#else>
      <td colspan="5">没有应考记录</td>
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
	<pre>
	     1.考试情况一栏,指缓考还是正常考试.如果为"申请缓考"则意味着申请尚未批准.
	     2.打印缓考申请,请选中一门或多门课程,点击"打印缓考申请单"按钮
	</pre>
</body> 
<#include "/templates/foot.ftl"/> 