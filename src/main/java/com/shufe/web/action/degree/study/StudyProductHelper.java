//$Id: StudyProductHelper.java,v 1.1 2007-3-6 下午11:39:23 chaostone Exp $
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
 * @author chaostone
 * 
 * MODIFICATION DESCRIPTION
 * 
 * Name           Date          Description 
 * ============         ============        ============
 *chaostone      2007-3-6         Created
 *  
 ********************************************************************************/

package com.shufe.web.action.degree.study;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ekingstar.commons.model.EntityUtils;
import com.ekingstar.commons.model.predicate.ValidEntityPredicate;
import com.ekingstar.commons.mvc.struts.misc.ForwardSupport;
import com.ekingstar.commons.transfer.exporter.Context;
import com.ekingstar.commons.utils.web.RequestUtils;
import com.ekingstar.commons.web.dispatch.Action;
import com.ekingstar.eams.system.basecode.industry.LiteratureType;
import com.ekingstar.eams.system.basecode.industry.MeetingType;
import com.ekingstar.eams.system.basecode.industry.ProjectType;
import com.ekingstar.eams.system.basecode.industry.PublicationLevel;
import com.ekingstar.eams.system.basecode.industry.ThesisIndexType;
import com.shufe.model.degree.study.StudyProduct;
import com.shufe.model.degree.study.StudyThesis;
import com.shufe.model.degree.study.ThesisIndex;
import com.shufe.model.std.Student;
import com.shufe.service.degree.study.StudyProductService;
import com.shufe.web.action.common.RestrictionSupportAction;

public class StudyProductHelper extends RestrictionSupportAction {
	public StudyProductService studyProductService;

	/**
	 * @param studyProductService The studyProductService to set.
	 */
	public void setStudyProductService(StudyProductService studyProductService) {
		this.studyProductService = studyProductService;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productType = request.getParameter("productType");
		Long productId = getLong(request, "studyProductId");
		StudyProduct product = null;
		if (null == productId) {
			product = StudyProduct.getStudyProduct(productType);
			populate(getParams(request, productType), product);
		} else {
			product = (StudyProduct) utilService.get(StudyProduct
					.getStudyProductType(productType), productId);
		}
		request.setAttribute("studyProduct", product);
		addCollection(request, "projectTypeList", baseCodeService
				.getCodes(ProjectType.class));
		addCollection(request, "literatureTypeList", baseCodeService
				.getCodes(LiteratureType.class));
		addCollection(request, "publicationLevelList", baseCodeService
				.getCodes(PublicationLevel.class));
		addCollection(request, "thesisIndexList", baseCodeService
				.getCodes(ThesisIndexType.class));
		addCollection(request, "meetingTypes", baseCodeService
				.getCodes(MeetingType.class));
		return forward(request, productType + "Form");
	}

	public ActionForward info(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productType = request.getParameter("productType");
		Long productId = getLong(request, "studyProductId");
		StudyProduct product = (StudyProduct) utilService.get(StudyProduct
				.getStudyProductType(productType), productId);
		request.setAttribute("studyProduct", product);
		return forward(request, "studyProductInfo");
	}

	/**
	 * @see com.shufe.web.action.common.DispatchBasicAction#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productType = request.getParameter("productType");
		Long productId = getLong(request, productType + ".id");
		StudyProduct product = null;
		if (null == productId) {
			product = StudyProduct.getStudyProduct(productType);
		} else {
			product = (StudyProduct) utilService.get(StudyProduct
					.getStudyProductType(productType), productId);
		}
		populate(getParams(request, productType), product);
		if (!product.isPO()) {
			EntityUtils.evictEmptyProperty(product);
		}
		//检查学生
		if (!ValidEntityPredicate.INSTANCE.evaluate(product.getStudent())) {
			String code = request.getParameter(productType + ".student.code");
			List stds = utilService.load(Student.class, "code", code);
			if (stds.size() != 1) {
				saveErrors(request.getSession(), ForwardSupport
						.buildMessages(new String[] { "error.model.notExist" }));
				return forward(request, new Action(this.clazz, "edit"));
			} else {
				product.setStudent((Student) stds.get(0));
			}
		}
		//保存论文的索引信息
		if (productType.equals("studyThesis")) {
			List indexTypes = baseCodeService.getCodes(ThesisIndexType.class);
			Set indexs = new HashSet();
			for (Iterator iter = indexTypes.iterator(); iter.hasNext();) {
				ThesisIndexType type = (ThesisIndexType) iter.next();
				String indexNo = request.getParameter("index" + type.getId());
				if (StringUtils.isNotBlank(indexNo)) {
					ThesisIndex thesisIndex = new ThesisIndex();
					thesisIndex.setThesisIndexNo(indexNo);
					thesisIndex.setThesisIndexType(type);
					indexs.add(thesisIndex);
				}
			}
			StudyThesis thesis = (StudyThesis) product;
			thesis.setIndexes(indexs);
		}
		utilService.saveOrUpdate(product);
		return redirect(request, "search", "info.save.success");
	}

	public ActionForward editAward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return forward(request, new Action(StudyAwardAction.class, "edit"));
	}

	/**
	 * @see com.shufe.web.action.common.DispatchBasicAction#configExportContext(javax.servlet.http.HttpServletRequest, com.ekingstar.commons.transfer.exporter.Context)
	 */
	protected void configExportContext(HttpServletRequest request,
			Context context) {
		String excelType = request.getParameter("excelType");
		if ("statisStudyProducts".equals(excelType)) {
			Map valueMap = RequestUtils.getParams(request, "studyProduct");
			context.put("resultMap", studyProductService
					.getStdPropertyOfProduct(valueMap,
							getDepartmentIdSeq(request),
							getStdTypeIdSeq(request)));
			context.put("publicTypes", utilService.load(
					PublicationLevel.class, "state", Boolean.TRUE));
			context.put("projectTypes", utilService.load(
					ProjectType.class, "state", Boolean.TRUE));
			context.put("literatureTypes", utilService.load(
					LiteratureType.class, "state", Boolean.TRUE));
			context.put("enrollYear", request.getParameter("enrollYear"));
		} else if ("studyInfoStatistic".equals(excelType)) {
			Long productId = getLong(request, "studyProductId");
			String productType = request.getParameter("productType");
			StudyProduct product = (StudyProduct) utilService.get(
					StudyProduct.getStudyProductType(productType), productId);
			Student student = product.getStudent();
			setExportDatas(request, context, student, null);
		}
	}
	
	public void setExportDatas(HttpServletRequest request, Context context,
			Student student, Boolean isPassed) {
		Map studyResults = studyProductService.getStudyProducts(
				student.getId(), Boolean.TRUE,isPassed);
		context.put("student", student);
		List thesiss = (List) studyResults.get("studyThesiss");
		context.put("studyThesiss", thesiss);
		context.put("thesisIndexs", getIndexs(thesiss));
		List literatures = (List) studyResults.get("literatures");
		context.put("literatures", literatures);
		context.put("literatureIndexs", getIndexs(literatures));
		List projects = (List) studyResults.get("projects");
		context.put("projects", projects);
		context.put("projectIndexs", getIndexs(projects));
		List studyMeetings = (List) studyResults.get("studyMeetings");
		context.put("studyMeetings", studyMeetings);
		context.put("meetingIndexs", getIndexs(studyMeetings));
		context.put("studyResults", studyResults);
		context.put("exportFile", student.getName() + "科研情况表");
	}
	
	/**
	 * 得到一个list所产生的索引列表
	 * @param list
	 * @return
	 */
	public List getIndexs(List list){
		List tempList = new ArrayList();
		for (int i = 0; i <list.size(); i++) {
			tempList.add(new Integer(i));
		}
		return tempList;
	}
}
