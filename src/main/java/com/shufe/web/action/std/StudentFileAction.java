//$Id$
/*
 *
 * KINGSTAR MEDIA SOLUTIONS Co.,LTD. Copyright c 2005-2006. All rights reserved.
 * 
 * This source code is the property of KINGSTAR MEDIA SOLUTIONS LTD. It is intended 
 * only for the use of KINGSTAR MEDIA application development. Reengineering, reproduction
 * arose from modification of the original source, or other redistribution of this source 
 * is not permitted without written permission of the KINGSTAR MEDIA SOLUTIONS LTD.
 * 
 */
/********************************************************************************
 * @author yang
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name                 Date                Description 
 * ============         ============        ============
 * yang             	2006-09-08         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.std;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.ekingstar.commons.lang.SeqStringUtil;
import com.ekingstar.commons.query.Condition;
import com.ekingstar.commons.query.EntityQuery;
import com.ekingstar.commons.query.OrderUtils;
import com.ekingstar.commons.transfer.TransferResult;
import com.ekingstar.commons.transfer.exporter.DefaultPropertyExtractor;
import com.ekingstar.commons.transfer.exporter.PropertyExtractor;
import com.ekingstar.commons.transfer.importer.DefaultEntityImporter;
import com.ekingstar.commons.transfer.importer.DefaultItemImpoter;
import com.ekingstar.commons.transfer.importer.Importer;
import com.ekingstar.commons.transfer.importer.reader.ExcelItemReader;
import com.ekingstar.commons.utils.query.QueryRequestSupport;
import com.ekingstar.commons.utils.transfer.ImporterForeignerListener;
import com.shufe.model.duty.DutyRecord;
import com.shufe.model.std.Student;
import com.shufe.model.system.baseinfo.AdminClass;
import com.shufe.service.duty.DutyRecordPropertyExporter;
import com.shufe.service.std.StdImportListener;
import com.shufe.service.std.StudentClassImportListener;
import com.shufe.service.system.baseinfo.AdminClassService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class StudentFileAction extends RestrictionSupportAction {

	private AdminClassService adminClassService;

	// private static final String EXPORT_METHOD = "exportMethod";

	// private static final String TITLES = "titles";

	// private static final String KEYS = "keys";

	protected HashMap exportMethod = new HashMap();

	protected Class[] exportMethodTypes = { HttpServletRequest.class };

	public ActionForward importStudentExcelForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request);
	}

	/**
	 * 导入学生学籍信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		FormFile xlsFile = (FormFile) dynaForm.get("studentFile");
		InputStream is = xlsFile.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));

		Importer importer = new DefaultEntityImporter(Student.class);
		importer.setReader(new ExcelItemReader(wb, 1));
		if (null == importer) {
			return forward(request, "/pages/components/importData/error");
		}
		TransferResult tr = new TransferResult();
		importer.addListener(new ImporterForeignerListener(utilService)).addListener(
				new StdImportListener(utilService.getUtilDao()));
		importer.transfer(tr);
		request.setAttribute("importer", importer);
		request.setAttribute("importResult", tr);
		return forward(request, "/pages/components/importData/result");
	}

	public void exportStudentExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources message = getResources(request, "excelconfig");
		request.setAttribute("keys", message.getMessage("student.export.keys"));
		request.setAttribute("titles", message.getMessage("student.export.showkeys"));
		request.setAttribute("exportMethod", "getStudentExcelData");
		super.export(mapping, form, request, response);
	}

	public Collection getStudentExcelData(HttpServletRequest request) {
		Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
		List studentList = utilService.load(Student.class, "id", ids);
		return studentList;
	}

	public ActionForward importAdminClassStudentExcelForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return forward(request);
	}

	/**
	 * 导入班级学生关联信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importAdminClassStudentExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaActionForm dynaForm = (DynaActionForm) form;
		FormFile xlsFile = (FormFile) dynaForm.get("studentFile");
		InputStream is = xlsFile.getInputStream();
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));

		Importer importer = new DefaultItemImpoter();
		importer.setReader(new ExcelItemReader(wb, 0));
		if (null == importer) {
			return forward(request, "/pages/components/importData/error");
		}
		TransferResult tr = new TransferResult();
		StudentClassImportListener listener = new StudentClassImportListener();
		listener.setUtilService(utilService);
		listener.setAdminClassService(adminClassService);
		importer.addListener(listener);
		importer.transfer(tr);
		request.setAttribute("importer", importer);
		request.setAttribute("importResult", tr);
		return forward(request, "/pages/components/importData/result");

	}

	public void exportAdminClassStudentExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources message = getResources(request, "excelconfig");
		request.setAttribute("keys", message.getMessage("adminClassStudent.keys"));
		request.setAttribute("titles", message.getMessage("adminClassStudent.showkeys"));
		request.setAttribute("exportMethod", "getAdminClassStudentExcelData");
		super.export(mapping, form, request, response);
	}

	public Collection getAdminClassStudentExcelData(HttpServletRequest request) {
		Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
		EntityQuery entityQuery = new EntityQuery(AdminClass.class, "adminClass");
		entityQuery.add(new Condition("adminClass.id in (:ids)", ids));
		entityQuery.add(new Condition("adminClass.department.id in (:departmentIds)", SeqStringUtil
				.transformToLong(getDepartmentIdSeq(request))));
		entityQuery.add(new Condition("adminClass.stdType.id in (:stdTypeIds)", SeqStringUtil
				.transformToLong(getStdTypeIdSeq(request))));
		entityQuery.addOrder(OrderUtils.parser("adminClass.code"));
		List adminClassList = (List) utilService.search(entityQuery);
		List adminClassStudentList = new ArrayList();
		List adminClassKeyList = getKeyList(request, "adminClass.");
		List studentKeyList = getKeyList(request, "student.");
		// String[] classPropertyArray =
		for (Iterator iter = adminClassList.iterator(); iter.hasNext();) {
			AdminClass adminClass = (AdminClass) iter.next();
			Object[] classStudentArray = new Object[adminClassKeyList.size()
					+ studentKeyList.size()];
			addClassKeyArray(adminClassKeyList, classStudentArray, adminClass);
			if (CollectionUtils.isEmpty(adminClass.getStudents())) {
				adminClassStudentList.add(classStudentArray);
			} else {
				List studentList = new ArrayList(adminClass.getStudents());
				Collections.sort(studentList, new BeanComparator("code"));
				for (Iterator iterator = studentList.iterator(); iterator.hasNext();) {
					Object[] elementArray = ArrayUtils.clone(classStudentArray);
					Student student = (Student) iterator.next();
					int temp = adminClassKeyList.size();
					for (Iterator studentIter = studentKeyList.iterator(); studentIter.hasNext();) {
						String studentKey = (String) studentIter.next();
						try {
							elementArray[temp] = PropertyUtils.getProperty(student, studentKey);
						} catch (Exception e) {
							elementArray[temp] = null;
						}
						temp++;
					}
					adminClassStudentList.add(elementArray);
				}
			}
		}
		return adminClassStudentList;
	}

	/**
	 * @param adminClassKeyList
	 * @param adminClass
	 * @return
	 */
	private void addClassKeyArray(List adminClassKeyList, Object[] elementArray,
			AdminClass adminClass) {
		int classSize = 0;
		for (Iterator adminClassIter = adminClassKeyList.iterator(); adminClassIter.hasNext();) {
			String classKey = (String) adminClassIter.next();
			try {
				elementArray[classSize] = PropertyUtils.getProperty(adminClass, classKey);
			} catch (Exception e) {
				elementArray[classSize] = null;
				log.debug("adminClass." + classKey + "error!\n");
			}
			classSize++;
		}
	}

	/**
	 * @param request
	 * @param name
	 */
	private List getKeyList(HttpServletRequest request, String name) {
		List keyList = new ArrayList();
		String[] keyArray = StringUtils.split(getExportKeys(request), ",");
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (key.indexOf(name) == 0) {
				keyList.add(key.substring(name.length()));
			}
		}
		return keyList;
	}

	public void exportSecondSpecialityStudentExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		MessageResources message = getResources(request, "excelconfig");
		request.setAttribute("keys", message.getMessage("secondSpecialityStudent.keys"));
		request.setAttribute("titles", message.getMessage("secondSpecialityStudent.showkeys"));
		request.setAttribute("exportMethod", "getSecondSpecialityStudentExcelData");
		super.export(mapping, form, request, response);
	}

	public Collection getSecondSpecialityStudentExcelData(HttpServletRequest request) {
		Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
		EntityQuery entityQuery = new EntityQuery(Student.class, "student");
		if (ArrayUtils.isEmpty(ids)) {
			QueryRequestSupport.populateConditions(request, entityQuery);
			entityQuery
					.add(new Condition(
							"(student.firstMajor.department.id in (:firstMajor_department_id)) "
									+ "or"
									+ " (student.secondMajor.department.id in (:secondMajor_department_id))",
							SeqStringUtil.transformToLong(this.getDepartmentIdSeq(request)),
							SeqStringUtil.transformToLong(this.getDepartmentIdSeq(request))));

		} else {
			entityQuery.add(new Condition("student.id in (:stdIds)", ids));
		}
		StringBuffer select = new StringBuffer();
		select.append("select ");
		String[] keyArray = StringUtils.split(getExportKeys(request), ",");
		for (int i = 0; i < keyArray.length; i++) {
			String keyTemp = keyArray[i];
			select.append("student." + keyTemp);
			if (i != (keyArray.length - 1)) {
				select.append(", ");
			} else {
				select.append(" ");
			}
		}
		entityQuery.setSelect(select.toString());
		entityQuery
				.addOrder(OrderUtils
						.parser("student.code,student.secondMajor.department.code,student.secondMajor.code"));

		return utilService.search(entityQuery);
	}

	public void exportDutyRecordExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		MessageResources message = getResources(request, "excelconfig");
		request.setAttribute("keys", message.getMessage("dutyRecord.keys"));
		request.setAttribute("titles", message.getMessage("dutyRecord.showkeys"));
		request.setAttribute("exportMethod", "getDutyRecordExcelData");
		super.export(mapping, form, request, response);

		/*
		 * MessageResources message = getResources(request, "excelconfig");
		 * String keys = message.getMessage("dutyRecord.keys"); String titles =
		 * message.getMessage("dutyRecord.showkeys"); ExcelTools et = new
		 * ExcelTools(); DutyRecordPropertyExporter exporter = new
		 * DutyRecordPropertyExporter(); exporter.setLocale((Locale)
		 * request.getSession().getAttribute(Globals.LOCALE_KEY));
		 * exporter.setMessageResources(getResources(request)); HSSFWorkbook wb =
		 * et.object2Excel(dutyRecordList, keys, titles,exporter);
		 * response.setContentType("application/vnd.ms-excel;charset=GBK");
		 * response.setHeader("Content-Disposition", "attachment;filename=" +
		 * "dutyRecordList.xls"); ServletOutputStream sos =
		 * response.getOutputStream(); wb.write(sos); sos.close();
		 */
	}

	public Collection getDutyRecordExcelData(HttpServletRequest request) {
		Long[] ids = SeqStringUtil.transformToLong(request.getParameter("ids"));
		List dutyRecordList = utilService.load(DutyRecord.class, "teachTask.id", ids);
		final Boolean isContainsNull;
		String courseTakeTypeString = request.getParameter("courseTakeTypeString");
		if (StringUtils.contains(courseTakeTypeString, "null")) {
			isContainsNull = new Boolean(true);
			courseTakeTypeString = StringUtils.remove(courseTakeTypeString, "null");
		} else {
			isContainsNull = new Boolean(false);
		}
		final Long[] courseTakeTypeIdArray = SeqStringUtil.transformToLong(courseTakeTypeString);
		return CollectionUtils.select(dutyRecordList, new Predicate() {

			public boolean evaluate(Object object) {
				/*
				 * if(Boolean.TRUE.equals(isContainsNull)&&("null".equals(((DutyRecord)object).getCourseTakeType().getCode()))){
				 * return true; } if(ArrayUtils.contains(courseTakeTypeIdArray,
				 * ((DutyRecord)object).getCourseTakeType().getId())){ return
				 * true; } return false;
				 */
				return (Boolean.TRUE.equals(isContainsNull) && ("null".equals(((DutyRecord) object)
						.getCourseTakeType(Boolean.FALSE).getCode())))
						|| (ArrayUtils.contains(courseTakeTypeIdArray, ((DutyRecord) object)
								.getCourseTakeType(Boolean.FALSE).getId()));
			}
		});
	}

	/**
	 * 下载模版文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void downloadTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String file_name = request.getParameter("fileName");
		if (file_name == null) {
			return;
		}
		file_name = "/WEB-INF/template/upload/" + file_name.trim();
		InputStream inStream = null;
		byte[] b = new byte[100];
		int len = 0;
		file_name = getRealName(request, file_name);
		if (file_name == null) {
			response.getWriter()
					.print("not file correspond to " + request.getParameter("fileName"));
			return;
		}
		File file = new File(file_name);
		if (!file.exists()) {
			response.getWriter().write("without docuemnt path:[" + file_name + "]");
			return;
		}
		try {
			// response.reset();
			inStream = new FileInputStream(file_name);
			response.setContentType("application/x-msdownload");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ request.getParameter("fileName") + "\"");
			while ((len = inStream.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			inStream.close();
		} catch (Exception e) {
			// e.printStackTrace();
			log.warn("client abort download file:" + request.getParameter("fileName"));
		}
	}

	/**
	 * 得到文件下载的真实路径
	 * 
	 * @param request
	 * @param file_name
	 * @return
	 */
	private String getRealName(HttpServletRequest request, String file_name) {
		if (request == null || file_name == null)
			return null;
		file_name = file_name.trim();
		if (file_name.equals(""))
			return null;

		String file_path = request.getRealPath(file_name);
		if (file_path == null)
			return null;
		File file = new File(file_path);
		if (!file.exists())
			return null;
		return file_path;
	}

	/**
	 * @see List数据集导出生成Excel文件
	 * @param list
	 *            对象数据集
	 * @param propertyKeys
	 *            对象属性字符串，中间以","间隔
	 * @param propertyShowKeys
	 *            显示字段的名字字符串，中间以","间隔
	 * @return 返回一个HSSFWorkbook（excel）类型数据。
	 * @throws Exception
	 */
	public HSSFWorkbook object2Excel(List list, String propertyKeys, String propertyShowKeys,
			PropertyExtractor exporter) throws Exception {

		/** ************* new excel **************** */
		HSSFWorkbook wb = new HSSFWorkbook(); // 建立新HSSFWorkbook对象
		HSSFSheet sheet = wb.createSheet("test result"); // 建立新的sheet对象
		HSSFRow row = null;
		HSSFCell cell = null;
		Object cellVal = null;

		/** **************** 取得传入的list列名称和显示名称 ********** */
		String[] pKeys = Tokenizer2StringArray(propertyKeys, ",");
		String[] pShowKeys = Tokenizer2StringArray(propertyShowKeys, ",");
		/** *************** insert data to excel*************** */
		// 创建一行（标题)
		row = sheet.createRow(0); // 建立新行
		// 显示标题列名称
		for (int i = 0; i < pShowKeys.length; i++) {
			cell = row.createCell((short) i); // 建立新cell
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue(pShowKeys[i]);
		}
		// 逐行取数据
		int rowId = 1;// 数据行号（从第2行开始填充数据)
		for (Iterator iter = list.iterator(); iter.hasNext(); rowId++) {
			row = sheet.createRow(rowId); // 建立新行
			Object obj = iter.next();
			// 生成每一列
			for (int i = 0; i < pKeys.length; i++) {
				cell = row.createCell((short) i); // 建立新cell

				// cellVal = PropertyUtils.getProperty(obj, pKeys[i]);

				cellVal = exporter.getPropertyValue(obj, pKeys[i]);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);

				cell.setCellValue((cellVal == null) ? "" : cellVal.toString());
			}
		}
		return wb;
	}

	/**
	 * @see 将StringTokenizer类型数据转化生成字符串数组
	 * @param sourceStr
	 *            解析","间隔的字符串，变成字符串数组
	 * @param strDot
	 * @return
	 */
	private String[] Tokenizer2StringArray(String sourceStr, String strDot) {
		StringTokenizer st = new StringTokenizer(sourceStr, strDot);
		int size = st.countTokens();
		String[] strArray = new String[size];
		for (int i = 0; i < size; i++) {
			strArray[i] = st.nextToken();
		}

		return strArray;
	}

	protected String getExportKeys(HttpServletRequest request) {
		String keys = (String) request.getAttribute("keys");
		if (StringUtils.isBlank(keys)) {
			throw new RuntimeException("keys is empty!\n");
		}
		return SeqStringUtil.keepUnique(keys);
	}

	protected String getExportTitles(HttpServletRequest request) {
		String titles = (String) request.getAttribute("titles");
		if (StringUtils.isBlank(titles)) {
			throw new RuntimeException("titles is empty!\n");
		}
		return SeqStringUtil.keepUnique(titles);
	}

	protected PropertyExtractor getPropertyExtractor(HttpServletRequest request) {
		if ("getDutyRecordExcelData".equals(request.getAttribute("exportMethod"))) {
			return new DutyRecordPropertyExporter();
		}
		return new DefaultPropertyExtractor();
	}

	protected Collection getExportDatas(HttpServletRequest request) {
		String exportMethod = (String) request.getAttribute("exportMethod");
		if (StringUtils.isBlank(exportMethod)) {
			throw new RuntimeException("EXPORT_METHOD is empty!\n");
		}
		Method method = null;
		try {
			method = getExportMethod(exportMethod);

		} catch (NoSuchMethodException e) {
			log.error("EXPORT_METHOD can find with name " + exportMethod + " !\n");
			throw new RuntimeException(e);
		}
		try {
			return (Collection) method.invoke(this, new Object[] { request });
		} catch (Exception e) {
			return Collections.EMPTY_LIST;
		}

	}

	private Method getExportMethod(String name) throws NoSuchMethodException {

		synchronized (exportMethod) {
			Method method = (Method) exportMethod.get(name);
			if (method == null) {
				method = clazz.getMethod(name, exportMethodTypes);
				methods.put(exportMethod, method);
			}
			return (method);
		}

	}

	/**
	 * @param adminClassService
	 *            要设置的 adminClassService
	 */
	public void setAdminClassService(AdminClassService adminClassService) {
		this.adminClassService = adminClassService;
	}

}
