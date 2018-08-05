   var form =document.actionForm;
   var limitExportRecord = 10000;
   function sendMessage(){
     var courseTakeIds = getSelectIds("courseTakeId");
     if(""==courseTakeIds){alert("请选择上课学生");return;}
     window.open("courseTakeForTask.do?method=sendMessage&courseTakeIds="+courseTakeIds);
   }
    function withdraw(){
      addParamsInput(form,getInputParams(parent.document.courseTakeForm));
      submitId(form,'courseTakeId',true,'courseTake.do?method=withdraw&log=1',"确认退课操作吗?");
    }
    function editCourseTakeType(typeId){
      addParamsInput(form,getInputParams(parent.document.courseTakeForm));
      submitId(form,'courseTakeId',true,'courseTake.do?method=batchSetTakeType&courseTakeTypeId='+typeId,"确认设置操作吗?");
    }
    function exportData(action, listSize) {
       var takeIds = getSelectIds("courseTakeId");
       form.action = action + ".do?method=export";
       addInput(form, "keys", "task.seqNo,task.course.code,task.course.name,task.courseType.name,student.code,student.name,student.basicInfo.gender.name,student.firstMajorClass.name,student.department.name,student.firstMajor.name,student.firstAspect.name,courseTakeType.name,task.arrangeInfo.teacherNames,task.arrangeInfo.teacherDepartNames,task.course.credits,arrangeInfo","hidden");
       addInput(form, "titles", "课程序号,课程代码,课程名称,课程类别,学号,姓名,性别,班级,学院,专业,专业方向,修读类别,授课教师,授课教师院系,学分,课程安排","hidden");
       
       var toExportCount = listSize;
       if ("" == takeIds && toExportCount > limitExportRecord || "" != takeIds && (toExportCount = takeIds.match(new RegExp(",", "gi")).length + 1) > limitExportRecord) {
           alert("导出的记录不能超过" + limitExportRecord + "条。 ");
           return;
       }
       
       if ("" == takeIds) {
            if (!confirm("确定将导出下面列表中" + toExportCount + "条学生上课记录吗？")) {
                return;
            } else {
                 transferParams(parent.document.courseTakeForm,form,"courseTake",false);
            }
       } else {
            if (!confirm("确定将导出所选的" + toExportCount + "条学生上课记录吗？")) {
                return;
            } else {
                addInput(form,"courseTakeIds", takeIds, "hidden");
            }
       }
       addInput(form, "courseTakeIds", takeIds, "hidden");
       form.submit();
    }
