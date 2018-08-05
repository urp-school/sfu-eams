package com.shufe.web;

public interface Constants {

	public final static String RANK_KEY = "rank.key";
	public final static String DATA_AUTHORITY_DEPARTMENT_KEY = "department";
	public final static String DATA_AUTHORITY_STUDENTTYPE_KEY = "studentType";
	
	public final static String PREVIOUS_PAGENUM = "previousNumber";
	public final static String CURRENT_PAGENUM = "pageNo";
	public final static String NEXT_PAGNUM = "nextNumber";
	public final static String MAX_PAGENUM = "maxNumber";

	public final static String DETAIL_RESULT = "result";
	public final static String MODULES = "modules";

	/*----------add by squirrrel-----------*/
	// it's used Encrypt the password or oher need,this is the Encrypt key
	public final static String PASSOWRD_KEYWORD = "AeF8lp2Vg";

	public final static int STATE_OK = 0;

	public final static int STATE_FAIL = -1;
	/*---------end squirrel-----------*/

	public final static String SUCCESS = "success";
	public final static String ERROR = "error";
	public final static String FAILURE = "failure";
	public final static String INPUT = "input";
}
