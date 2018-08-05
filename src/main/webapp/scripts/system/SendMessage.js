    var detailArray = {};
    var form=document.actionForm;
    function addSelected(){
       var idSeq= getIds();
       if(idSeq==""){alert("请选择");return;}
       var ids = idSeq.split(",");
       for(var i=0;i<ids.length;i++){
          if(ids[i]!=""){
             parent.addValue(ids[i],getName(ids[i]));
          }
       }
    }
    function getName(id){
       if (id != ""){
          return detailArray[id]['name'];
       }else{
          return "";
       }
    }
    function query(){
       form.submit();
    }