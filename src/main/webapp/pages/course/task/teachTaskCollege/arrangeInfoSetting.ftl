<#include "/templates/head.ftl"/>
<script language="JavaScript" type="text/JavaScript" src="<@bean.message key="validator.js.url" />"></script>
<body>
<table id="arrangeInfoSettingBar"></table>
<script>
  var bar=new ToolBar("arrangeInfoSettingBar","批量设置安排信息",null,true,true);
  bar.addBack("<@msg.message key="action.back"/>");
  bar.addHelp("<@msg.message key="action.help"/>","teachTask/arrageInfoSetting");
</script>
   	 <#assign continuedWeek><@bean.message key="attr.continuedWeek"/></#assign>
   	 <#assign oddWeek><@bean.message key="attr.oddWeek"/></#assign>
   	 <#assign evenWeek><@bean.message key="attr.evenWeek"/></#assign>
   	 <#assign randomWeek><@bean.message key="attr.randomWeek"/></#assign>
     <#assign weekCycle={'1':'${continuedWeek}',
                         '2':'${oddWeek}',
                         '3':'${evenWeek}',
                         '4':'${randomWeek}'}>
     <table width="100%" align="center" class="formTable">
        <form name="arrangeInfoSettingForm" action="?method=batchUpdateArrangeInfo" method="post" onsubmit="return false;">
        <input type="hidden" name="params" value="${RequestParameters['params']?if_exists}">
	    <tr>
  	     <td class="darkColumn" width="20%"><input type="checkbox" onclick="changeState(this,'weekCycle')" >&nbsp;占用周：</td>
	     <td class="grayStyle" >
	         <select id="weekCycle" name="arrangeInfo.weekCycle" disabled>
	            <#list weekCycle?keys as cycle>
		         <option value="${cycle}">${weekCycle[cycle]}</option>
		        </#list>
             </select>
	     </td>
  	     <td class="darkColumn" width="20%"><input type="checkbox"  onclick="changeState(this,'weeks')">&nbsp;周数：</td>
	     <td class="grayStyle" >
	       <input id="weeks" maxlength="3" name="arrangeInfo.weeks" value="请输入周数(正整数)" onfocus="this.value=''" disabled>
	     </td>
	    </tr>
	    <tr>
  	     <td class="darkColumn" width="20%"><input type="checkbox" onclick="changeState(this,'weekUnits')">&nbsp;周课时：</td>
	     <td class="grayStyle" >
	       <input id="weekUnits" name="arrangeInfo.weekUnits" maxlength="3" value="请输入周课时(正整数)" onfocus="this.value=''" disabled/>
	     </td>
  	     <td class="darkColumn" width="20%"><input type="checkbox" onclick="changeState(this,'overallUnits')" >&nbsp;总课时：</td>
	     <td class="grayStyle" >
	       <input id="overallUnits" name="arrangeInfo.overallUnits" maxlength="3" value="请输入总课时(正整数)" onfocus="this.value=''" disabled>
	     </td>
	    </tr>
	    <tr>
  	     <td class="darkColumn" width="20%"><input type="checkbox" onclick="changeState(this,'courseUnits')">&nbsp;节次：</td>
	     <td class="grayStyle" >
	       <input id="courseUnits" maxlength="3" value="请输入节次(正整数)" onfocus="this.value=''" name="arrangeInfo.courseUnits" value="" disabled/>
	     </td>
  	     <td class="darkColumn" width="20%"><input type="checkbox" onclick="changeState(this,'weekStart')">&nbsp;<@msg.message key="attr.startWeek"/>：</td>
	     <td class="grayStyle" >
	       <input id="weekStart" maxlength="3" value="请输入起始周(正整数)" onfocus="this.value=''" name="arrangeInfo.weekStart" value="" disabled/>
	     </td>     
	    </tr>
	   <tr class="darkColumn">
	     <td colspan="6" align="center" >
	     <#if (taskList?size!=0)>有${taskList?size}个没有确认的任务可以修改
	     <#else><font color="red">没有未确认的任务可供修改</font>
	     </#if>	
	       <input type="button" value="<@bean.message key="action.submit"/>" <#if (taskList?size==0)>disabled</#if>
	        name="saveButton" onClick="save(this.form)" class="buttonStyle" />&nbsp;
	       <input type="reset"  name="resetButton" value="<@bean.message key="action.reset"/>" class="buttonStyle" />
	     </td>
	   </tr>
     </table>
     <table  class="listTable">
	 <tr align="center" class="darkColumn">
	      <td width="5%"><@bean.message key="attr.taskNo"/></td>
	      <td width="15%"><@bean.message key="attr.courseName"/></td>
	      <td width="15%"><@bean.message key="entity.teachClass"/></td>
	      <td width="10%"><@bean.message key="entity.teacher"/></td>
	      <td width="4%">占用周</td>
	      <td width="4%">周课时</td>
	      <td width="4%">周数</td>
	      <td width="4%">总课时</td>
	      <td width="4%">节次</td>
	    </tr>
	    <#list taskList as task>
	   	  <#if task_index%2==1 ><#assign class="grayStyle" ></#if>
		  <#if task_index%2==0 ><#assign class="brightStyle" ></#if>
	     <tr class="${class}" align="center">
	      <td><input type="hidden" name="taskId" value="${task.id}">${task.seqNo?if_exists}</td>
	      <td><@i18nName task.course/></td>      
	      <td>${task.teachClass.name?html}</td>
	      <td><@getTeacherNames task.arrangeInfo.teachers/></td>
	      <td>${weekCycle[task.arrangeInfo.weekCycle?string]}</td>
	      <td>${task.arrangeInfo.weekUnits}</td>
	      <td>${task.arrangeInfo.weeks}</td>
	      <td>${task.arrangeInfo.overallUnits}</td>
	      <td>${task.arrangeInfo.courseUnits}</td>
	    </tr>
		</#list>
     </table>
   </form>
     <script>
        var infos=new Object();
        infos['courseUnits']='请输入节次(正整数)';
        infos['overallUnits']='请输入总课时(正整数)';
        infos['weeks']='请输入周数(正整数)';
        infos['weekUnits']='请输入周课时(正整数)';
        infos['weekStart']='请输入起始周(正整数)';
        function changeState(check,name){ 
           form= document.arrangeInfoSettingForm;           
           $(name).disabled=!check.checked;
           if($(name).type=="text"){
              $(name).value=infos[name];
           }
        }
        
        function save(form){
           var errors="";
           var find=false;           
           for(var i in infos){
              if(!$(i).disabled){
                 find=true;
                 var value=$(i).value;
                 if(!(/^\d+$/.test(value))){
                    errors+=infos[i]+'\n';
                 }
              }
           }
           if(!find){
              alert("请选择相应的设置项");
              return;
           }
           if(""!=errors){
              alert(errors);
              return;
           }
           form.submit();
        }
     </script>
 </body>

<#include "/templates/foot.ftl"/>