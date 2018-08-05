    var resourceMap = new Object();
    resourceMap['room']="classroom";
    resourceMap['class']="adminClass";
    resourceMap['teacher']="teacher";
    var resourceDepartMap=new Object();
    resourceDepartMap['room']="depart.id";
    resourceDepartMap['class']="adminClass.department.id";
    resourceDepartMap['teacher']="teacher.department.id";
    var resourceType="";
  	var viewNum=2;
  	function populateParams(prefix){
  	    var resourceForm =document.resourceForm;
	    var resourceSearchForm = document.resourceSearchForm;
        
	    var elems = resourceSearchForm.elements;
	    for(i =0;i <elems.length; i++){
	      // must be or condition for some params out of pojo
	      if(elems[i].name.indexOf(prefix)==0||elems[i].name.indexOf(".")>1)
		        resourceForm[elems[i].name].value= elems[i].value;
	    }
  	}
  	
    function getResourceOfDepart(departId){
        var resourceForm =document.resourceForm;
        var elems = resourceForm.elements;
        
        for(i =0;i <elems.length; i++){
	      // must be or condition for some params out of pojo
	      if(elems[i].name.indexOf(".")>1&&elems[i].name.indexOf("calendar")==-1){
		        resourceForm[elems[i].name].value="";
		  }
	    }
	    resourceForm[resourceDepartMap[resourceType]].value=departId;
	    resourceForm.action=resourceType +"Resource.do?method=search";
	    resourceForm.target="contentFrame";
        resourceForm.submit();
    }
    function searchResource(pageNo,pageSize,orderBy){
       var form = document.resourceForm;
       form.target="contentFrame";
       form.action=resourceType+ "Resource.do?method=search";
       goToPage(form,pageNo,pageSize,orderBy);
    }
     function getOccupyInfo(resourceId,isMerged){
       var form = document.resourceForm;
       if(""==resourceId||null==resourceId){
           var resourceId =  getCheckBoxValue(contentFrame.document.getElementsByName(resourceMap[resourceType]+"Id"));
           if(""==resourceId){alert("请选择一个");return;}
       }
       //alert(form['calendar.term'].value)
       form.target="contentFrame";
       form.action=resourceType + "Resource.do?method=occupyTables&" + resourceMap[resourceType] +"Ids=" + resourceId;
       if(null!=isMerged&&1==isMerged){
          form.action+="&isMerged=1";
       }
       form.submit();
     }
    function enterQuery(prefix) {
        if (window.event.keyCode == 13) {
                 populateParams(prefix); 
                 searchResource();
        }
    }
	function changeToView(id,event){
	     changeView(getEventTarget(event));
	     displayView(id);
	}
	function displayView(divId){
	    for(i=1;i<=viewNum;i++){
	      var viewDiv ="view"+i;
	      var div = document.getElementById(viewDiv);
	      if(null==div) return;
	      if(divId==viewDiv)
	         div.style.display = "block";
	      else 
	         div.style.display = "none";
	    }
	}
	
    function getStatisInfo(isOccupy){
       var form = document.resourceForm;
       form.target="contentFrame";
       addInput(form,"isOccupy",isOccupy,"hidden"); 
       var ids = getCheckBoxValue(contentFrame.document.getElementsByName(resourceMap[resourceType]+"Id"));
       if(ids=="") {alert("请至少选择一项！");return;}
   	   form.action = resourceType + "Resource.do?method=digestTime&" + resourceMap[resourceType] +"Ids=" + ids;
   	   form.submit();
    }
    function capitalize(str){
       return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    function changeCalendar(form){
     form.action=resourceType+'Resource.do?method=index';
     form.target="";
     form.submit();
    }