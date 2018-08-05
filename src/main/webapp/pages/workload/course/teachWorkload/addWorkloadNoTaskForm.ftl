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
 	function addTeacher(){
       var teacher = document.getElementById('teacher');
       if(document.teachWorkloadForm['teacherIds'].value.length>0){
       		alert("只能选择单个老师");
       		return;
       }else{
       		document.teachWorkloadForm['teacherIds'].value+=teacher.value;
       		document.teachWorkloadForm['teachWorkload.teacherInfo.teacherName'].value+=DWRUtil.getText('teacher');
       }
   }
 </script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" onload="f_frameStyleResize(self,10)">
 <table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','添加无任务的教学任务',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addBack('<@msg.message key="action.back"/>');
</script>
  <table align="center" width="100%">
  <form name="teachWorkloadForm" method="post" action="" onsubmit="return false;">
	<tr>
	<td>
	<table width="100%" align="center" class="listTable">
		<form name="teachWorkloadForm" method="post" action="" onsubmit="return false;">
		<tr class="darkColumn" align="center">
			<td colspan="6">添加无任务的工作量
			</td>
		</tr>
		<tr>
			<td class="grayStyle" width="60px;">学年度学期</td>
			<td colspan="3">学生类别<select id="stdType" name="teachWorkload.studentType.id" style="width:100px;"></select>
				学年度<select id="year" name="year" style="width:100px;"></select>
				学期<select id="term" name="term" style="width:50px;"></select>
			</td>
		<td class="grayStyle" id="f_courseName">课程名称<font color="red">*</font>:</td>
		<td><input type="text" name="teachWorkload.courseName" width="100%"></td>
			<#include "/templates/calendarSelect.ftl"/>
		</tr>
		<tr>
			<td class="grayStyle" id="f_teacher">教师<font color="red">*</font>:</td>
			<td colspan="3">
				<input type="hidden" name="teacherIds" value="">
				<input type="text" name="teachWorkload.teacherInfo.teacherName" style="width:300px;" value="" readonly="true"><br>
	       		<select id="teachDepartment" name="teachDepartment" style="width:100px">
	       		</select>
   	       		<select id="teacher" name="teacherId" style="width:90px">
	       		</select>
               <input type="button"  class="buttonStyle" value="<@msg.message key="action.add"/>" onClick="addTeacher();"/>
   	       	   <input type="button"  class="buttonStyle" value="<@msg.message key="action.clear"/>" onClick="this.form['teacherIds'].value='';this.form['teachWorkload.teacherInfo.teacherName'].value='';"/> 
			</td>
			<td class="grayStyle">开课院系:</td>
			<td><@htm.i18nSelect datas=openColleges selected="" name="teachWorkload.college.id" style="width:150px;"></@></td>
		</tr>
		<tr>
			<td class="grayStyle">课程类别</td>
			<td width="80px;"><@htm.i18nSelect datas=courseTypes selected="" name="teachWorkload.courseType.id" style="width:150px"></@>
			</td>
			<td class="grayStyle" id="f_classNames">教学班名称<font color="red">*</font></td>
			<td><input type="text" name="teachWorkload.classNames"  style="width:150px;"></td>
			<td class="grayStyle" id="f_courseNO">课程序号:</td>
			<td><input type="text" name="teachWorkload.courseSeq" value=""></td>
		</tr>
	</table>
	</td>
	</tr>
	<tr>
		<td>
			<table width="100%" align="center" class="listTable">
				<tr>
					<td class="grayStyle"align="center" width="60px;" id="f_stdNum">
						<@msg.message key="workload.studentNumberInClass"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="stdNum" name="teachWorkload.studentNumber" value="" width="50px;" maxlength="7"/>
					</td>
					<td class="grayStyle"align="center" width="70px;" id="f_modulus">工作量系数<font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input type="hidden" id="workloadModulus" value="">
						<select id="teachModules" name="teachWorkload.teachModulus.id" style="width:180px;" onChange="doChange()">
							<option value="">请选择</option>
							<#list teachModulus?if_exists as modulu>
									<option value="${modulu.id}">${modulu.modulusValue?string("##0.0")}//${modulu.studentType.name?if_exists}//${modulu.courseCategory.name?if_exists}</option>
							</#list>
						</select>
					</td>
					<td class="grayStyle"align="center" width="60px;" id="f_payBill">支付报酬<font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<select name="teachWorkload.payReward" style="width:50px;">
							<option value="">请选择</option>
							<option value="true">已支付</option>
							<option value="false">未支付</option>
						</select>
					</td>
					<td class="grayStyle"align="center" id="f_calcWorkload">
						<@msg.message key="workload.calcWorkload"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<select name="teachWorkload.calcWorkload" style="width:100px;">
							<option value="">请选择</option>
							<option value="true">计</option>
							<option value="false">不计</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="grayStyle"align="center" id="f_weeks">
						<@msg.message key="workload.weeks"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="weeks" name="teachWorkload.weeks" value="" maxlength="5"/>
					</td>
					<td class="grayStyle"align="center" id="f_course">
						<@msg.message key="workload.totleCourse"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input id="totleCourses" type="text" name="teachWorkload.totleCourses" value="" maxlength="200">
					</td>
					<td class="grayStyle"align="center" id="f_weekCourse">
						<@msg.message key="workload.weekCourse"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="classNumberOfWeek" maxlength="5" name="teachWorkload.classNumberOfWeek" style="width:50px" value="">
					</td>
					<td class="grayStyle"align="center" id="f_totleWorkload">
						<@msg.message key="workload.taskWorkload"/><font color="red">*</font>:
					</td>
					<td class="brightStyle"align="left">
						<input type="text" id="totleWorkload" name="teachWorkload.totleWorkload" maxlength="10" style="width:100px" value="">
                        <input type="button" value="计算" name="button2" onclick="doCaculate()" class="buttonStyle"/>
					</td>
				</tr>
				<tr>
					<td class="grayStyle"align="center" id="f_remark">备注</td>
					<td colspan="8">
						<textarea name="teachWorkload.remark" style="width:100%" rows="2"></textarea>
					</td>
				</tr>
				<tr>
					<td class="darkColumn" align="center"  colspan="9">
					 	<button onClick="save()">提交</button></td>
				</tr>
				<script language="javascript">
				var form = document.teachWorkloadForm;
 			function save(){
 				var a_fields = {
 					'teachWorkload.courseName':{'l':'课程名称:', 'r':true,'t':'f_courseName','mx':'50'},
         			'teachWorkload.teacherInfo.teacherName':{'l':'教师', 'r':true,'t':'f_teacher'},
         			'teachWorkload.classNames':{'l':'教学班名称', 'r':true,'t':'f_classNames','mx':'100'},
         			'teachWorkload.teachModulus.id':{'l':'工作量系数', 'r':true,'t':'f_modulus'},
         			'teachWorkload.payReward':{'l':'支付报酬', 'r':true,'t':'f_payBill'},
         			'teachWorkload.calcWorkload':{'l':'<@msg.message key="workload.calcWorkload"/>', 'r':true,'t':'f_calcWorkload'},
         			'teachWorkload.studentNumber':{'l':'<@msg.message key="workload.studentNumberInClass"/>', 'r':true,'f':'unsigned','t':'f_stdNum','mx':'10'},
         			'teachWorkload.weeks':{'l':'<@msg.message key="workload.weeks"/>', 'r':true,'f':'real', 't':'f_weeks','mx':'10'},
         			'teachWorkload.totleCourses':{'l':'<@msg.message key="workload.totleCourse"/>','f':'real','r':true,'t':'f_course','mx':10},
         			'teachWorkload.classNumberOfWeek':{'l':'<@msg.message key="workload.weekCourse"/>','f':'real','r':true,'t':'f_weekCourse','mx':10},
         			'teachWorkload.totleWorkload':{'l':'<@msg.message key="workload.taskWorkload"/>','f':'real','r':true,'t':'f_totleWorkload','mx':10},
         			'teachWorkload.courseSeq':{'l':'课程序号','t':'f_courseNO','mx':10},
         			'teachWorkload.remark':{'l':'备注','t':'f_remark','mx':500}
     				};
     			var totleWorkload = document.getElementById("totleWorkload").value;
     			var v = new validator(form, a_fields, null);
 				var caculateWorkload=getTotleWorkload();
 					if(v.exec()&&validateStdNumillegele()&&confirm("计算出来的工作量是:"+caculateWorkload+"\n你确定提交吗?")){
 						form.action ="teachWorkload.do?method=saveWorkloadNoTask";
 						setSearchParams(parent.form,form);
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
		</table>
		</td>
	</tr>
	</form>
	<#include "/templates/departTeacher2Select.ftl"/>
 <table>
 </body>
<#include "/templates/foot.ftl"/>