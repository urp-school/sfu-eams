   function validateCreditAndHour(){
         var form = document.courseGroupForm;
         var errors="";
         if(!/^\d+$/.test(form['courseGroup.creditHour'].value))
            errors+="å­¦æ¶æ ¼å¼ä¸æ­£ç¡®ï¼åºä¸ºæ­£æ´æ°";
         if(!/^\d*\.?\d*$/.test(form['courseGroup.credit'].value))
            errors+="å­¦åæ ¼å¼ä¸æ­£ç¡®ï¼åºä¸ºæ­£å°æ°";
         if(errors!=""){alert(errors);return false;}
         else return true;
     }
   /**
    * æ¾ç¤ºéæ©åè¡¨
    */
   function displaySelectList(kind){
         selectedKind=kind;
         if(kind=="planCourse.teachDepart"){
            selectListFrame.location=
               "collegeSelector.do?method=search&multi=false&pageNo=1&pageSize=10";
         }else if(kind=="planCourse.substitution"||kind=="course"){
            selectListFrame.location=
              "courseSelector.do?method=search&multi=false&pageNo=1&pageSize=10";
         }else if(kind=="preCourses"){
            selectListFrame.location=
              "courseSelector.do?method=search&multi=true&pageNo=1&pageSize=10";
         }else
          alert("unsupported list kind");
    }
    /**
     * æ¸é¤å·²ç»éæ©çå¼
     */
    function resetSelectList(kind){
	    planCourseForm[kind+".id"].value="";
	    planCourseForm[kind+".name"].value="";
    }
	/**
	 * ååºå³ä¾§çéæ©
	 */
    function select(ids,names,ext1,ext2,ext3){
        if(ids==""){alert("è¯·éæ©ä¸ä¸ª");return;}
        if(selectedKind=="course") {
            planCourseForm['planCourse.id'].value="";
            planCourseForm['planCourse.course.id'].value=ids;
            planCourseForm['planCourse.course.name'].value=names;
            planCourseForm['planCourse.course.credits'].value=ext1;
            planCourseForm['planCourse.course.extInfo.period'].value=ext2;
            planCourseForm['planCourse.course.weekHour'].value=ext3;
        }
        else if(selectedKind=="preCourses"){
            selectedIds = ids.split(",");
            selectedNames=names.split(",");
	        for(var i=0;i<selectedIds.length;i++){
   	           if(planCourseForm['preCourses.id'].value.indexOf(selectedIds[i])==-1){
   	                if(planCourseForm['preCourses.id'].value!="")
   	                   planCourseForm['preCourses.id'].value+=","
   	                 planCourseForm['preCourses.id'].value+=selectedIds[i];
   	                 if(planCourseForm['preCourses.name'].value!="")
   	                     planCourseForm['preCourses.name'].value+=","
   	                 planCourseForm['preCourses.name'].value+=selectedNames[i];        
   	            }
	         }
	    }
	    else if(selectedKind=="planCourse.teachDepart"||selectedKind=="planCourse.substitution"){
  	        planCourseForm[selectedKind+".id"].value=ids;
	        planCourseForm[selectedKind+".name"].value=names;
        }else{
            alert("unsurported ");
        }
    }
	/**
	 * éªè¯å¹å»è®¡åçè¯¾ç¨
	 */
    function validatePlanCourse(){
 	   var form = document.planCourseForm;
	   var wrongFormat="";
	   var terms = form['planCourse.termSeq'].value;
       if(terms==''){alert("å­¦æä¸ºå¿å¡«é¡¹");setTermFocus();return false;}
       if(!/^[1-9,]{1,}$/.test(terms))
          {alert('å¼è¯¾å­¦æä¸ºæ°å­1-9å,ç»æç.');setTermFocus();return false;}
       else if(terms.indexOf(',')!=-1){
	       if(terms.indexOf(',')!=0)
	       terms=","+terms;
	       if(terms.lastIndexOf(',')!=terms.length-1)terms+=",";	      
	       var termsArray = terms.split(',');
	       for(var i=0;i<termsArray.length;i++){
	          if(termsArray[i]!=""&&termsArray[i]>termsCount){	           
	          	wrongFormat+=termsArray[i]+"å¤§äºè¯¥ç»çæå¤§å­¦ææ°\n";
	          }
	       }
	       form['planCourse.termSeq'].value=terms;
       }

       if(form['planCourse.teachDepart.id'].value=="")
           wrongFormat+="å¼è¯¾é¢ç³»ä¸è½ä¸ºç©º"+"\n";
       if(form['planCourse.course.id'].value=="")
           wrongFormat+="è¯¾ç¨ä¸è½ä¸ºç©º\n";
       if(!/^\d*\.?\d*$/.test(form['planCourse.course.credits'].value))
           wrongFormat+="å­¦åä¸ºæ­£å°æ°\n";
       if(!/^\d+$/.test(form['planCourse.course.extInfo.period'].value))
           wrongFormat+="å­¦æ¶ä¸ºæ­£æ´æ°\n";
       if(!/^\d+$/.test(form['planCourse.course.weekHour'].value))
           wrongFormat+="å¨è¯¾æ¶ä¸ºæ­£æ´æ°\n";
       if(form['planCourse.remark'].value.length>50)
           wrongFormat+="å¤æ³¨ä¸è½è¶è¿50ä¸ªå­ç¬¦\n";
       if(wrongFormat!="") {alert(wrongFormat);return false;}
       else return true;
    }

    function setTermFocus(){
       var termInput = document.getElementById("planCourse_termSeq");        
       termInput.select();
    }
    /**
     * ä¿å­å¹å»è®¡åä¸­çè¯¾ç¨
     */
    function savePlanCourse(form){
       if(!validatePlanCourse()) return;       
       if(form['preCourses.id'].value!=""){            
          var preCourseIds=form['preCourses.id'].value.split(",");
          var preCourseNames=form['preCourses.name'].value.split(",");
        
          for(var i=0;i<preCourseIds.length;i++){
             var input1 = document.createElement('input');
		     input1.name="planCourse.preCourses["+i+"].id";
	         input1.value=preCourseIds[i];
	         input1.type="hidden";
		     planCourseForm.appendChild(input1);
             var input2 = document.createElement('input');
		     input2.name="planCourse.preCourses["+i+"].name";
	         input2.value=preCourseNames[i];
	         input2.type="hidden";
		     planCourseForm.appendChild(input2);
         }
        if(preCourseIds.length>0){
	       var input = document.createElement('input');
		   input.name="preCourseCount";
	       input.value=preCourseIds.length;
	       input.type="hidden";
		   planCourseForm.appendChild(input);
       }
	   }else{
	       var input = document.createElement('input');
		   input.name="preCourseCount";
	       input.value="0";
	       input.type="hidden";
		   planCourseForm.appendChild(input);
	   }
	   form.submit();
	}