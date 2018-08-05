	 //打印分段统计
	 function printStatReport(form,kind){
	    form.target="_blank";
	    form.action=action+"?method=stat";
	    for(var i=0;i<seg.length;i++){
          var segAttr="segStat.scoreSegments["+i+"]";
          addInput(form,segAttr+".min",seg[i].min);
          addInput(form,segAttr+".max",seg[i].max);
        }
        if(null==kind){
           kind="task";
        }
        addInput(form,"kind",kind);
	    submitId(form,"taskId",true);
	    form.target="_self";
	 }
     //打印试卷分析
     function printExamReport(form){
        form.target="_blank";
        form.action="teacherGrade.do?method=reportForExam";
	    for(var i=0;i<seg.length;i++){
          var segAttr="segStat.scoreSegments["+i+"]";
          addInput(form,segAttr+".min",seg[i].min);
          addInput(form,segAttr+".max",seg[i].max);
        }
        submitId(form,"taskId",true);
        form.target="_self";
     }
	 //打印教学班成绩
	 function printTeachClassGrade(form){
        form.target="_blank";
        submitId(form,"taskId",true,action+"?method=report");
        form.target="_self";
	 }
	 //查看成绩信息
     function info(form){
        submitId(form,"taskId",false,action+"?method=info&orderBy=std.code asc");
     }
    function gradeStateInfo(form){
        submitId(form,"taskId",false,action+"?method=gradeStateInfo");
    }
     //成绩录入
     function inputGrade(){
       var taskId = getSelectIds("taskId");
       if(""==taskId || isMultiId(taskId)){
          alert("请仅选择一个教学任务.");
          return;
       }
       window.open("teacherGrade.do?method=input&taskId="+taskId);
     }
     //调整百分比
     function editGradeState(){
       var taskId = getSelectIds("taskId");
       if(""==taskId || isMultiId(taskId)){
          alert("请仅选择一个教学任务.");
          return;
       }
       window.open("teacherGrade.do?method=editGradeState&taskId="+taskId);
     }
     //批量调整成绩录入方式
     function batchUpdateGradeState(){
       var taskIds = getSelectIds("taskId");
       if(""==taskIds){
          alert("请选择教学任务.");
          return;
       }
       window.open("teacherGrade.do?method=batchUpdateGradeState&taskIds="+taskIds);
     }
     //批量更新成绩
     function batchUpdateGrade(){
       var taskIds = getSelectIds("taskId");
       if(""==taskIds){
          alert("请选择教学任务.");
          return;
       }
       window.open("teacherGrade.do?method=batchUpdateGrade&taskIds="+taskIds);
     }
     //删除考试成绩
     function removeGrade(gradeTypeId){
       var form =document.actionForm;
       setSearchParams();
       submitId(form,"taskId",false,action+"?method=removeGrade&gradeTypeId="+gradeTypeId,"确认删除成绩?");
     }
     
     //设置为绑定德育成绩--四六体系课程
     function setMoralGrade(){
       var form =document.actionForm;
       setSearchParams();
       submitId(form,"taskId",true,action+"?method=setMoralGrade&moralGradePercent=0.4","确认设置为四六体系课程?");
     }
     
     //取消绑定德育成绩--四六体系课程
     function cancelMoralGrade(){
       var form =document.actionForm;
       setSearchParams();
       submitId(form,"taskId",true,action+"?method=cancelMoralGrade","确认取消四六体系课程?");
     }
     
     //暂存查询参数
	 function setSearchParams(){
	    var params = getInputParams(parent.document.taskForm,null,false);
	    document.actionForm.params.value=params;
	 }
	 function editGradeStateInfo(form){
	    submitId(form,"taskId",false,"courseGradeDuplicate.do?method=editGradeState");
	 }
	 function publishGrade(form,gradeTypeId,isPublished,allGradeType){
	    setSearchParams();
	    //alert(form['params'].value);
	    if(isPublished==1) submitId(form,"taskId",true,"courseGradeDuplicate.do?method=publishGrade&isPublished="+isPublished+"&allGradeType="+allGradeType,"确定要发布成绩吗?");
	    if(isPublished==0) submitId(form,"taskId",true,"courseGradeDuplicate.do?method=publishGrade&isPublished="+isPublished+"&allGradeType="+allGradeType,"确定要取消发布吗?");
	 }