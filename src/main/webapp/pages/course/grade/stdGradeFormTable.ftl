	 <#assign gradeStatus={'0':'新添加','1':'录入确认','2':'已发布'}>
<table class="formTable" width="100%">
   <tr>
     <td class="title"><@msg.message key="attr.stdNo"/></td>
     <td class="content">${courseGrade.std.code}</td>
     <td class="title"><@msg.message key="attr.taskNo"/></td>
     <td class="content">${courseGrade.taskSeqNo?if_exists}</td>
     <td class="title">是否通过</td>
     <td class="content"><#if courseGrade.isPass>是<#else><font color="red">否</#if></td>
  </tr>
  <tr>
     <td class="title"><@msg.message key="attr.personName"/></td>
     <td class="content">${courseGrade.std.name}</td>
     <td class="title"><@msg.message key="attr.courseNo"/></td>
     <td class="content">${courseGrade.course.code}</td>
     <td class="title"><@msg.message key="attr.courseName"/></td>
     <td class="content"><@i18nName courseGrade.course/></td>
  </tr>
  <tr>
     <td class="title">总评成绩</td>
     <td class="content">${(courseGrade.getScoreDisplay(7))?if_exists}</td>
     <td class="title"><@msg.message key="attr.credit"/></td>
     <td class="content">${(courseGrade.credit)?if_exists}</td>
     <td class="title">绩点</td>
     <td class="content">${(courseGrade.GP?string("#.##"))?if_exists}</td>
  </tr>
  <tr>
     <td class="title">最终成绩</td>
     <td class="content" id="finalScoreTd"></td>
     <td class="title"><@msg.message key="entity.courseType"/></td>
     <td class="content">
       <select name="courseGrade.courseType.id" style="width:100px">
       <#list courseTypes  as courseType>
        <option value="${courseType.id}" <#if courseType.id?string=courseGrade.courseType.id?string>selected</#if>><@i18nName courseType/></option>
       </#list>
       </select>
     </td>
     <td class="title">修读类别</td>
     <td class="content">
       <select name="courseGrade.courseTakeType.id">
       <#list courseTakeTypes as courseTakeType>
        <option value="${courseTakeType.id}" <#if courseTakeType.id?string=(courseGrade.courseTakeType.id?string)?default("")>selected</#if>><@i18nName courseTakeType/></option>
       </#list>
       </select>
    </td>
  </tr>
  <tr>
  	<td class="title"><@msg.message key="entity.markStyle"/></td>
  	<td><@htm.i18nSelect datas=markStyles selected=courseGrade.markStyle.id?string name="courseGrade.markStyle.id"></@></td>
  	<td class="title"></td>
  	<td></td>
  	<td class="title"></td>
  	<td></td>
  </tr>
 </table>
 <#if (courseGrade.task.gradeState)?exists>
   <#assign gradeState=courseGrade.task.gradeState>
 </#if>
<#assign gradeConverterConfig = (converter.getConfig(courseGrade.markStyle))?if_exists/>
<#assign configs = (gradeConverterConfig.converters)?if_exists/>
 <@table.table width="100%">
    <@table.thead>
     <@table.td text="成绩种类"/>
     <@table.td text="考试情况"/>
     <@table.td text="得分" width="20%"/>
     <@table.td text="百分比"/>
     <@table.td text="是否通过"/>
     <@table.td text="状态"/>
    </@>
    <#if (courseGrade.examGrades?size!=0)>
    <@table.tbody datas=courseGrade.examGrades;examGrade>
     <td><@i18nName examGrade.gradeType/></td>
     <#--<td><@i18nName examGrade.examStatus/></td>-->
     <td>
        <select name="examStatus${examGrade.id}">
            <#list examStatus as examState>
            <option value="${examState.id}" <#if examState.id==examGrade.examStatus.id> selected</#if>>${examState.name}</option>
            </#list>
        </select>
     </td>
     <td>
		<#if !gradeConverterConfig?exists || configs?size == 0>
			<input name="examGrade${examGrade.id}" value="${(examGrade.score?string("#.##"))?if_exists}"/>
		<#else>
			<select name="examGrade${examGrade.id}" style="width:120px">
           		<option>...</option>
				<#list configs as converter>
	           		<option value="${converter.defaultScore}"<#if ((examGrade.score)?default(0) >= converter.minScore && (examGrade.score)?default(0) <= converter.maxScore)> selected</#if>>${converter.grade}</option>
	           	</#list>
			</select>
			<script>
				document.getElementsByName("examGrade${examGrade.id}")[0].value = "${(examGrade.score)?if_exists}";
			</script>
		</#if>
     </td>
     <td><#if gradeState?exists>${(gradeState.getPercent(examGrade.gradeType)?string.percent)?if_exists}</#if></td>
     <td><#if examGrade.isPass>是<#else><font color="red">否</font></#if></td>
     <td>${gradeStatus[examGrade.status?string]}</td>
    </@>
    </#if>
    <#if addedGradeTypes?size!=0>
    <@table.tbody datas=addedGradeTypes;gradeType>
     <td><@i18nName gradeType/></td>
     <td></td>
     <td>
		<#if !gradeConverterConfig?exists || configs?size == 0>
			<input name="addGradeTypeId${gradeType.id}" value=""/>
		<#else>
			<select name="addGradeTypeId${gradeType.id}" style="width:120px">
           		<option>...</option>
				<#list configs as converter>
	           		<option value="${converter.defaultScore}">${converter.grade}</option>
	           	</#list>
			</select>
			<script>
				document.getElementsByName("addGradeTypeId${gradeType.id}")[0].value = "${courseGrade.getPublishedScoreDisplay(gradeType)}";
			</script>
		</#if>
	 </td>
     <td><#if gradeState?exists>${(gradeState.getPercent(gradeType)?string.percent)?if_exists}</#if></td>
     <td></td>
     <td></td>
    </@>
    </#if>
 </@>
 <input name="addGradeTypeIds" type="hidden" value="<#list addedGradeTypes as type>${type.id},</#list>"/>
 
 <#assign autoComputeObj>
<input type="checkbox"<#if courseGrade.scoreIsGA> checked</#if> name="autoCalcFinal"/>自动计算
</#assign>
<#assign style_text_innerHTML>
<input type="text" name="courseGrade.score" style="width:50px" value="${(courseGrade.score?string("#.##"))?if_exists}">
</#assign>
<#assign style_hidden_innerHTML>
<input type="hidden" name="courseGrade.score" style="width:50px" value="${(courseGrade.score)?if_exists}"/>
</#assign>
<script>
    <#assign scoreSelectName = "courseGradeScore"/>
    var scoreSelectObj = {
        <#list defaultConfigs as defaultConfig>
            <#assign selectObj>
                <select name="courseGrade.score">
                    <option value="">无值/无效值</option>
                    <#list defaultConfig.converters as configItem>
                    <option value="${configItem.defaultScore}">${configItem.grade}</option>
                    </#list>
                </select>
            </#assign>
        'markStyle${defaultConfig.markStyle.id}':'${selectObj?js_string}\r\n${autoComputeObj?js_string}\r\n${style_hidden_innerHTML?js_string}'<#if defaultConfig_has_next>,</#if>
        </#list>
    };
    
    initFinalScore();
    function initFinalScore() {
        var courseGradeScoreValue = null;
        if (null == courseGradeForm["courseGrade.score"]) {
            courseGradeScoreValue = "${(courseGrade.score?string("#.##"))?if_exists}";
        } else {
            courseGradeScoreValue = courseGradeForm["courseGrade.score"].value;
        }
        var autoCalcFinalValue = null;
        if (null == courseGradeForm["autoCalcFinal"]) {
            autoCalcFinalValue = "<#if courseGrade.scoreIsGA>checked</#if>";
        } else {
            autoCalcFinalValue = courseGradeForm["autoCalcFinal"].checked;
        }
        <#list defaultConfigs as defaultConfig>if (courseGradeForm["courseGrade.markStyle.id"].value == ${defaultConfig.markStyle.id}) {<#if defaultConfig.converters?size == 0>
            $("finalScoreTd").innerHTML = "${style_text_innerHTML?js_string}\r\n${autoComputeObj?js_string}";
            courseGradeForm["courseGrade.score"].value = courseGradeScoreValue;</#if><#list defaultConfig.converters as configItem><#if configItem_index == 0>
            </#if>if (courseGradeScoreValue >= ${configItem.minScore} && courseGradeScoreValue <= ${configItem.maxScore}) {
                $("finalScoreTd").innerHTML = scoreSelectObj['markStyle${defaultConfig.markStyle.id}'];
                courseGradeForm["courseGradeScore"].value = courseGradeForm["courseGrade.score"].value = ${configItem.defaultScore};
            }<#if configItem_has_next> else </#if></#list><#if (defaultConfig.converters?size > 0)> else {
                $("finalScoreTd").innerHTML = scoreSelectObj['markStyle${defaultConfig.markStyle.id}'];
                courseGradeForm["courseGradeScore"].value = courseGradeForm["courseGrade.score"].value = "";
            }</#if>
        }<#if defaultConfig_has_next> else </#if></#list>
        
        courseGradeForm["autoCalcFinal"].checked = autoCalcFinalValue;
    }
</script>