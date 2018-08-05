<#include "/templates/head.ftl"/>
<script src="scripts/common/multiSelectChoice.js"></script>
<body> 
<#assign labInfo>教学任务生成选项</#assign>
<#include "/templates/back.ftl"/> 
   <table width="95%" align="center" class="formTable">
       <form name="taskGenParamsFrom" action="" method="post" onsubmit="return false;">
       <tr class="darkColumn">
         <td  colspan="2">教学任务生成选项</td>
       </tr>
       <input type="hidden" name="task.calendar.id" value="${RequestParameters['task.calendar.id']}"/>
       <input type="hidden" name="teachPlanIds" value="${RequestParameters['teachPlanIds']}"/>
       <input type="hidden" name="params.reGen" value="${RequestParameters['reGen']}"/>
       <#if RequestParameters['reGen']='1'>
       <tr>
         <td  class="title" width="15%">0.重新生成时:</td>
         <td  >
            <input name="params.removeGenerated" type="radio" checked value="1"/>删除已经生成的任务
            <input name="params.removeGenerated" type="radio" value="0"/>保留生成的任务
         </td>
       </tr>
        </#if>
       <tr>
         <td class="title" width="15%">1.生成学期</td>
         <td>
            <input name="params.forTerm" type="radio" value="0" onclick="displayTerm(this.form,false)" checked/>系统自动判断
            <input name="params.forTerm" id="assignTerm" value="0" type="radio" onclick="displayTerm(this.form,true)"/>手工指定
            <input name="term" id="termInput" maxlength="3" value="请输入手工指定的学期"  type="text" disabled/>
         </td>
       </tr>
       <tr>
         <td class="title">2.教室</td>
         <td>&nbsp;默认的教室要求：
           <select name="params.configType.id">
           <#list roomConfigList as config>
             <option value="${config.id}"<#if params.configType.id==config.id> selected</#if>><@i18nName config/></option>
           </#list>
           </select>
         </td>
       </tr>
       <tr>
         <td class="title">3.时间</td>
         <td id="f_courseUnits">默认一次课程的小节数：<input type="radio" name="params.unitStatus" value="1" onclick="unitGenSetting()"/>与学分一致&nbsp;<input type="radio" name="params.unitStatus" value="0" onclick="unitGenSetting()"/>自定义&nbsp;<input type="text" maxlength="3" style="width:50px" name="params.courseUnits" value="" disabled/>，<@msg.message key="attr.startWeek"/>：<input type="text" style="width:50px" name="params.weekStart" maxlength="3" value="1"/>，总周数:<input type="text" style="width:50px" maxlength="3" name="params.weeks" value="${params.weeks}"/>。</td>
       </tr>
       <tr>
         <td class="title">4.班级</td>
         <td>&nbsp;对应计划找不到班级时，是否生成该计划的任务：
          <select name="params.ignoreClass">
              <option value="0">不生成</option>
              <option value="1">依旧生成</option>
           </select>
         <br>&nbsp;教学任务生成,会根据每一个计划的所在年级,学生类别,院系,专业和方向等五个元素,严格查找对应的班级.选择不生成,则在查找不到班级时,不生成任务.
         </td>
       </tr>
       <tr>
         <td class="title">5.学期间隔</td>
         <td>&nbsp;在计算学期间隔时
          <select name="params.isOmitSmallTerm">
              <option value="1"> 忽略小学期</option>
              <option value="0"> 考虑小学期</option>
           </select>
         </td>
       </tr>
       <tr>
         <td class="title">6.忽略课程名称</td>
         <td>&nbsp;指定无需生成任务的课程名称(系统模糊匹配):<input name="params.ommitedCourseName" value="" maxlength="20"/></td>
       </tr>
       <tr>
         <td class="title">7.忽略课程代码</td>
         <td >&nbsp;指定无需生成任务的课程代码(多个代码以逗号相隔):<input name="params.ommitedCourseCodeSeq" value="" maxlength="200"/></td>
       </tr>
       <tr>
         <td class="title">8.指定课程代码</td>
         <td >&nbsp;指定要生成的课程代码(多个代码以逗号相隔):<input name="params.courseCodeSeq" value="" maxlength="200"/></td>
       </tr>
       <tr>
         <td class="title" rowspan="2">9.指定课程类别（性质）</td>
         <td>选择下面要指定的课程类别：</td>
       </tr>
       <tr>
         <td>
            <table cellpadding="0" cellspacing="0" style="text-align:center">
                <tr>
                    <td><@htm.i18nSelect datas=(courseTypes?sort_by("name"))?if_exists selected="" name="fromCourseTypeId" style="width:150px" size="10" MULTIPLE="MULTIPLE" onDblClick="selectRemoveAnyOne('fromCourseTypeId', 'toCourseTypeId')"/></td>
                    <td width="50px">
                        <input type="button" value="&lt;&lt;" onclick="selectRemoveAll('toCourseTypeId', 'fromCourseTypeId')" style="width:35px"/>
                        <br><br>
                        <input type="button" value="&lt;" onclick="selectRemoveAnyOne('toCourseTypeId', 'fromCourseTypeId')" style="width:35px"/>
                        <br><br>
                        <input type="button" value="&gt;" onclick="selectRemoveAnyOne('fromCourseTypeId', 'toCourseTypeId')" style="width:35px"/>
                        <br><br>
                        <input type="button" value="&gt;&gt;" onclick="selectRemoveAll('fromCourseTypeId', 'toCourseTypeId')" style="width:35px"/>
                    </td>
                    <td><select name="toCourseTypeId" style="width:150px" size="10" MULTIPLE="MULTIPLE" onDblClick="selectRemoveAnyOne('toCourseTypeId', 'fromCourseTypeId')"></select></td>
                    <input type="hidden" name="params.courseTypeIds" value=""/>
                </tr>
            </table>
         </td>
       </tr>
       <tr class="darkColumn">
         <td colspan="2" align="center">
          <input type="button" value="开始生成" onclick="javascript:save()" class="buttonStyle"/>
         </td>
       </tr>
       </form>
   </table>
   <script>
   var form = document.taskGenParamsFrom;
   function save(){
       form["params.courseTypeIds"].value = getAllOptionValue(form["toCourseTypeId"]);
       form.action="teachTaskGen.do?method=gen";
       if(form["params.unitStatus"][1].checked && !/^\d+$/.test(form['params.courseUnits'].value)){
          alert("节次数字格式不正确");return;
       }
       if(!/^\d+$/.test(form['params.weekStart'].value)){
          alert("起始周数字格式不正确");return;
       }
       if(!/^\d+$/.test(form['params.weeks'].value)){
          alert("总周数数字格式不正确");return;
       }
       if(!form['term'].disabled){
          if(!/^\d+$/.test(form['term'].value)){
             alert("指定学期数字格式不正确");return;
          }else{
             $('assignTerm').value=form['term'].value;
          }
       }
       form.submit();
   }
   function displayTerm(form,display){
      if(display){
          form['term'].disabled=false;
          form['term'].value="";
          form['term'].focus();
      }else{
          form['term'].disabled=true;
          form['term'].value="请输入手工指定的学期";
      }
   }
   
   function unitGenSetting() {
    if (form["params.unitStatus"][0].checked) {
        form["params.courseUnits"].disabled = "disabled";
        form["params.courseUnits"].value = "";
    } else {
        form["params.courseUnits"].disabled = "";
        form["params.courseUnits"].value = "${params.courseUnits}";
    }
   }
   
   form["params.unitStatus"][0].click();
   </script>
</body>
<#include "/templates/foot.ftl"/>