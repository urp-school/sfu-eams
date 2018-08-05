package com.shufe.service.course.textbook.impl;

import java.util.List;

import org.springframework.orm.ObjectRetrievalFailureException;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.course.textbook.TextbookDAO;
import com.shufe.model.course.textbook.Textbook;
import com.shufe.service.BasicService;
import com.shufe.service.course.textbook.TextbookService;
import com.shufe.service.system.codeGen.CodeGenerator;

public class TextbookServiceImpl extends BasicService implements
		TextbookService {
	private TextbookDAO textbookDAO;

	private CodeGenerator codeGenerator;

	public List getTextbooks() {
		return textbookDAO.getTextbooks();
	}

	public Textbook getTextbook(Long id) {
		if (null == id)
			return null;
		try {
			return textbookDAO.getTextbook(id);
		} catch (ObjectRetrievalFailureException e) {
			return null;
		}
	}

	public void saveTextbook(Textbook textbook) throws PojoExistException {
		if (!codeGenerator.isValidCode(textbook.getCode())) {
			String code = codeGenerator.gen(textbook, null);
			textbook.setCode(code);
		}
		textbookDAO.saveTextbook(textbook);
	}

	public void setTextbookDAO(TextbookDAO textbookDAO) {
		this.textbookDAO = textbookDAO;
	}

	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

}
