function isMultiId(str)
{
	return str.indexOf(",")>0;
}

function isSingleId(str)
{
	return str != "" && !isMultiId(str);
}

function appendQuery(url, query)
{
	if (url.indexOf("?") >= 0)
		return url + "&" + query;
	else
		return url + "?" + query;
}

function gotoWithLessParam(url, params)
{
	var ids = getIds();
	if (ids == "")
	{
		location = url;
	} else if(isSingleId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
		alert("Please select one or none!Å");
    }
}

function gotoWithSingleParam(url, params)
{   
	var ids = getIds();
	if(isSingleId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
		alert("Please select one!");
    }
}

function gotoWithMultiParam(url, params)
{
	var ids = getIds();
	if (isMultiId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
        alert("Please select more than one!Å");
    }
}

function gotoWithParam(url, params)
{
	var ids = getIds();
	if(ids == "")
	{
	  alert("Please select!");
    }else
	{
        location = appendQuery(url, params + "=" + ids);
    }
}

function confirmWithParam(url, params)
{
   if(confirm("Are you sure!ü")){
       gotoWithParam(url, params);
   }
}

function confirmWithSingleParam(url, params)
{
   if(confirm("Are you sure!")){
       gotoWithSingleParam(url, params);
   }
}

function confirmForm(url, form)
{   
	if(confirm("Are you sure!ü"))
	{
		submitForm(url, form)
	}
}

function confirmFormWithParam(url, form)
{
	if(getIds() == "")
	{
        alert("Please select!");
        return;
    }
   
    if(confirm("Are you sure!"))
	{
       submitForm(url, form)
	}
}

function submitForm(url, form)
{
     form.action=url;
     form.submit();
}

function submitFormWithParam(url, form)
{
	if(getIds() == "")
	{
        alert("Please select!Å");
        return;
     }
      submitForm(url, form)
}
function submitFormWithSingleParam(url, form)
{
    if(!isSingleId(getIds()))
	{
        alert("Please select one!Å");
        return;
    }
    submitForm(url, form)
}
