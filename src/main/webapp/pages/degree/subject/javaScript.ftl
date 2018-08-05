  <script language="javascript">    
    var form = document.pageGoForm;	
	function modifySubjectClass(subjectClassId){
	  form.action="subjectClass.do?method=modifySubjectClass&subjectClassId="+subjectClassId;
	  form.submit();		
	}
	
	function modifyFirstSubject(firstSubjectId){
	  form.action="firstSubject.do?method=modifyFirstSubject&firstSubjectId="+firstSubjectId;
	  form.submit();
	}
	function modifySecondSubject(secondSubjectId){
      form.action = "secondSubject.do?method=modifySecondSubject&secondSubjectId="+secondSubjectId;
      form.submit();
	}
    function pageGo(pageNo){ 
       form.pageNo.value=pageNo;
       form.submit();
    }
  </script>
  
  