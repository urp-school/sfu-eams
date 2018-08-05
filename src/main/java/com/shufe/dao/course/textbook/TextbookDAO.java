package com.shufe.dao.course.textbook;

import java.util.List;

import com.ekingstar.commons.model.pojo.PojoExistException;
import com.shufe.dao.BasicDAO;
import com.shufe.model.course.textbook.Textbook;

/**
 * 教科书存取接口
 * 
 * @author chaostone
 * 
 */
public interface TextbookDAO extends BasicDAO {

	public List getTextbooks();

	public Textbook getTextbook(Long id);

	public void saveTextbook(Textbook textbook) throws PojoExistException;

	public List getTextbooksByName(String name);

}
