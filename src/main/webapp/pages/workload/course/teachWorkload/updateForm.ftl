<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <script>
 	var arrModulus = new Array();
 	<#list teachModulus?if_exists as modulu>
 		arrModulus[${modulu_index}]=new Array();
 		arrModulus[${modulu_index}][0]='${modulu.id}';
 		arrModulus[${modulu_index}][1]='${modulu.modulusValue?if_exists?string("##0.0")}';
 		arrModulus[${modulu_index}][2]=new Number(${modulu.minPeople?default(0)?string});
 		arrModulus[${modulu_index}][3]=new Number(${modulu.maxPeople?default(0)?string});
 	</#list>
 	function doChange(){
 		var moduluValue = document.getElementById("teachModules").value;
 		var tempmodulus="";
 		for(var i=0;i<arrModulus.length;i++){
 			if(arrModulus[i][0]==moduluValue){
 				tempmodulus=arrModulus[i][1];
 				break;
 			}
 		}
 		var modulu =document.getElementById("workloadModulus");
 		modulu.value=tempmodulus;
 	}
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0">
 	<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','修改授课工作量',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@msg.message key="action.back"/>');
</script>
	<table width="100%" align="center" class="listTable">
		 <form name="teachWorkloadForm" method="post" action="" onsubmit="return false;">
		<tr class="darkColumn" align="center">
			<td colspan="7"><B>修改授课工作量</B>
			</td>
		</tr>
		<tr>
		    <td rowspan="2" class="grayStyle" align="center">
				<@msg.message key="workload.teacherInfo"/>
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherName"/>:
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.teacherName}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.gender"/>:
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.gender?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherTitle"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.teacherTitle?if_exists.name?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherAge"/>:
			</td>
			<td class="brightStyle" align="left">
				${(teachWorkload.teacherInfo.teacherAge)?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.edudegree"/>:
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.eduDegree?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherType"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.teacherType?if_exists.name?if_exists}
			</td>
		</tr>
	</table>
			<table width="100%" align="center" class="listTable">
			<tr >
			<td class="grayStyle" align="center">
				<@msg.message key="workload.teacherCollege"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teacherInfo.teachDepart?if_exists.name?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="attr.year2year"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teachCalendar?if_exists.year?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.courseName"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.courseName?if_exists}
			</td>
			<td class="grayStyle" align="center">
				<@msg.message key="attr.term"/>
			</td>
			<td class="brightStyle" align="left">
				${teachWorkload.teachCalendar?if_exists.term?if_exists}
			</td>
		</tr>
		<tr>
			<td class="grayStyle" align="center">
				<@msg.message key="workload.college"/>
			</td>
			<td class="brightStyle" align="left">
				<@htm.i18nSelect datas=departmentList selected="${teachWorkload.college.id}" name="teachWorkload.college.id" style="width:150px;"/>
			</td>
			<td class="grayStyle" align="center">
				数据进入方式：
			</td>
			<td class="brightStyle" align="left">
				<#if true==teachWorkload.isHandInput?exists>手工录入<#else>系统统计</#if>
			</td>
		</tr>	
		</table>
			<table width="100%" align="center" class="listTable">
				<tr>
					<td rowspan="3" class="grayStyle"align="center" width="30px">
						<@msg.message key="workload.taskWorkload"/><@msg.message key="postfix.basInfo"/>
					</td>
					<td class="grayStyle"align="center" id="f_stdNum">
						<@msg.message key="workload.studentNumberInClass"/>
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="stdNum" name="teachWorkload.studentNumber" maxlength="5" value="${teachWorkload.studentNumber}">
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="entity.studentType"/>
					</td>
					<td class="brightStyle"align="left">
						${teachWorkload.studentType?if_exists.name?if_exists}
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="entity.courseCategory"/>
					</td>
					<td class="brightStyle"align="left">
						${teachWorkload.courseCategory?if_exists.name?if_exists}
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="workload.workloadModulus"/>
					</td>
					<td class="brightStyle"align="left">
						<input type="hidden" id="workloadModulus" value="<#if teachWorkload.teachModulus?exists>${teachWorkload.teachModulus.modulusValue?string("##0.0")}</#if>">
						<select id="teachModules" name="teachWorkload.teachModulus.id" style="width:180px;" onChange="doChange()">
							<#list teachModulus?if_exists as modulu>
								<#if (teachWorkload.teachModulus.id)?exists && teachWorkload.teachModulus.id?string==modulu.id?string>
									<option value="${modulu.id}" selected>${modulu.modulusValue?string("##0.0")}//${modulu.studentType.name?if_exists}//${modulu.courseCategory.name?if_exists}</option>
								<#else>
									<option value="${modulu.id}">${modulu.modulusValue?string("##0.0")}//${modulu.studentType.name?if_exists}//${modulu.courseCategory.name?if_exists}</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<td class="grayStyle"align="center" id="f_weeks">
						<@msg.message key="workload.weeks"/>
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="weeks" maxlength="3" name="teachWorkload.weeks" style="width:100px;" value="${teachWorkload.weeks}"/>
					</td>
					<td class="grayStyle"align="center" id="f_course">
						<@msg.message key="workload.totleCourse"/>
					</td>
					<td class="brightStyle"align="left" >
						<input id="totleCourses" type="text" name="teachWorkload.totleCourses" maxlength="200" style="width:100px" value="${teachWorkload.totleCourses?if_exists}"/>
					</td>
					<td class="grayStyle"align="center" id="f_weekCourse">
						<@msg.message key="workload.weekCourse"/>
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="classNumberOfWeek" name="teachWorkload.classNumberOfWeek" maxlength="3" style="width:100px" value="${teachWorkload.classNumberOfWeek?string("##0.#")}"/>
					</td>
					<td class="grayStyle"align="center" id="f_totleWorkload">
						<@msg.message key="workload.taskWorkload"/>
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="totleWorkload" name="teachWorkload.totleWorkload" maxlength="5" style="width:100px" value="${teachWorkload.totleWorkload?string("##0.0")}"/>
                        <input type="button" value="计算" name="button2" onclick="doCaculate()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="grayStyle"align="center">
						<@msg.message key="workload.payBill"/>
					</td>
					<td class="brightStyle"align="left">
						<select name="teachWorkload.payReward" style="width:100%">
							<option value="true" <#if teachWorkload.payReward==true>selected</#if>>确认</option>
							<option value="false" <#if teachWorkload.payReward==false>selected</#if>>未确认</option>
						</select>
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="workload.calcWorkload"/>
					</td>
					<td class="brightStyle"align="left">
						<select name="teachWorkload.calcWorkload" style="width:100%">
							<option value="true" <#if teachWorkload.calcWorkload==true>selected</#if>>确认</option>
							<option value="false" <#if teachWorkload.calcWorkload==false>selected</#if>>未确认</option>
						</select>
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="workload.teacherAffirm"/>
					</td>
					<td class="brightStyle"align="left">
						<#if teachWorkload.teacherAffirm==true><@msg.message key="workload.affirm1"/>
						<#else><@msg.message key="workload.affirm0"/>
						</#if>
					</td>
					<td class="grayStyle"align="center">
						<@msg.message key="workload.collegeAffirm"/>
					</td>
					<td class="brightStyle"align="left">
						<#if teachWorkload.collegeAffirm==true><@msg.message key="workload.affirm1"/>
						<#else><@msg.message key="workload.affirm0"/>
						</#if>
					</td>
				</tr>
				<tr>
					<td class="grayStyle"align="center" id="f_remark">备注</td>
					<td colspan="8">
						<textarea name="teachWorkload.remark" style="width:100%" rows="2">${teachWorkload.remark?if_exists}</textarea>
					</td>
				</tr>
				<tr>
					<td class="darkColumn" align="center"  colspan="9">
						<input type="hidden" name="teachWorkloadId" value="${teachWorkload.id}">
					 	<input type="button" value="<@msg.message key="action.update"/>" name="button1" onClick="update()" class="buttonStyle" />
					 	<input type="reset"  name="reset1" value="<@msg.message key="system.button.reset"/>" class="buttonStyle" />
					</td>
				</tr>
		</table>
			</form>
				<script language="javascript">
			var form = document.teachWorkloadForm
 			function update(){
 				var totleWorkload = document.getElementById("totleWorkload").value;
 				var a_fields = {
         			'teachWorkload.studentNumber':{'l':'<@msg.message key="workload.studentNumberInClass"/>', 'r':true,'f':'unsigned','t':'f_stdNum','mx':'10'},
         			'teachWorkload.weeks':{'l':'<@msg.message key="workload.weeks"/>', 'r':true,'f':'unsigned', 't':'f_weeks','mx':'10'},
         			'teachWorkload.totleCourses':{'l':'<@msg.message key="workload.totleCourse"/>','f':'unsigned','r':true,'t':'f_course','mx':10},
         			'teachWorkload.classNumberOfWeek':{'l':'<@msg.message key="workload.weekCourse"/>','f':'real','r':true,'t':'f_weekCourse','mx':10},
         			'teachWorkload.totleWorkload':{'l':'<@msg.message key="workload.taskWorkload"/>','f':'real','r':true,'t':'f_totleWorkload','mx':10},
         			'teachWorkload.remark':{'l':'备注','t':'f_remark', 'mx':100}
     				};
     			var v = new validator(form, a_fields, null);
 				var caculateWorkload = getTotleWorkload();
 					if (v.exec() && validateStdNumillegele() && confirm ("你现在的工作量是:${teachWorkload.totleWorkload?string("##0.0")}\n修改计算出来的工作量是:" + caculateWorkload + "\n你确定提交吗?")){
 						form.action ="teachWorkload.do?method=update";
 						setSearchParams(parent.form,form)
 						form.submit();
 					}
 				}
 			function doCaculate(){
 				var totleWorkload = document.getElementById("totleWorkload");
 				var classOfWeeks = document.getElementById("classNumberOfWeek");
 				totleWorkload.value=getTotleWorkload()
 				classOfWeeks.value =getClassesOfWeek()
 			}
 			function getTotleWorkload(){
 				var totleCourses = document.getElementById("totleCourses").value;
 				var modulusId =document.getElementById("workloadModulus").value;
 				var totleWorload =0;
 				if(""!=modulusId){
 					totleWorload=((modulusId-0)*(totleCourses-0)).toFixed(1);
 				}
 				return totleWorload;
 			}
 			function getClassesOfWeek(){
 				var totleCourses = document.getElementById("totleCourses").value;
 				var weeks = document.getElementById("weeks").value;
 				var classOfWeeks=0;
 				if(""==weeks&&"0"==weeks){
 					classOfWeeks=(totleCourses-0).toFixed(2);
 				}else{
 					classOfWeeks=((totleCourses-0)/(weeks-0)).toFixed(1);
 				}
 				return classOfWeeks;
 			}
 			function validateStdNumillegele(){
 				var stdNum = document.getElementById("stdNum").value;
 				var tempNum = new Number(stdNum);
 				var moduluId = document.getElementById("teachModules").value;
 				var flag=false;
 				for(var i=0;i<arrModulus.length;i++){
 					if(arrModulus[i][0]==moduluId){
 						if(tempNum>=arrModulus[i][2]&&tempNum<arrModulus[i][3]){
 							flag=true;
 						}else{
 							if(confirm("你填写的上课学生数目是"+tempNum+"选定的系数人数为["+arrModulus[i][2]+","+arrModulus[i][3]+"]不一样,你确定要提交吗？")){
 								flag=true;
 							}
 						}
 						break;
 					}
 				}
 				return flag;
 			}
 			</script>
		
 </body>
<#include "/templates/foot.ftl"/>