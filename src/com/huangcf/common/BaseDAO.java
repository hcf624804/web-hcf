/*
 * Copyright �����Ϣ�ɷ����޹�˾.
 */
package com.huangcf.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Base DAO Class, ҵ��ϵͳ��DAO��Ҫ�̳�BaseDAO
 * 
 * @author shaoyifeng
 * @author ($Date: 2011-07-02 10:53:15 +0800 (����, 02 ���� 2011) $ modification by $Author: Chenjm $)
 */
public abstract class BaseDAO extends JdbcDaoSupport {
	private JdbcTemplate template;
	/**
	 * ��ѯ������¼
	 * 
	 * @param <R>
	 * @param sql
	 * @param clazz
	 * @param arguments
	 * @return
	 * @throws DAOException
	 */
	public <R> R queryForObject(String sql, Class<R> clazz, Object... arguments) throws DataAccessException {

		List<R> dtos = queryForList(sql, clazz, arguments);

		if (dtos.size() == 0) {
			return null;
		}
		else if (dtos.size() > 1) {
			return dtos.get(0);
		}

		return dtos.get(0);

	}

	/**
	 * ��ѯ������¼
	 * 
	 * @param <R>
	 * @param sql
	 * @param clazz
	 * @param arguments
	 * @return
	 */
	public <R> List<R> queryForList(String sql, Class<R> clazz, Object... arguments) throws DataAccessException {

		List<R> result = getJdbcTemplate().query(sql, arguments, new UserRowMapper(clazz));

		return result == null ? new ArrayList<R>() : result;
	}

	/**
	 * ��ѯ��1����¼
	 * 
	 * @param <R>
	 * @param sql
	 * @param clazz
	 * @param arguments
	 * @return
	 */
	public <R> R getFirst(String sql, Class<R> clazz, Object... arguments) {

		List<R> dtos = queryForList(sql, clazz, arguments);

		if (dtos.size() == 0)
			return null;

		return dtos.get(0);
	}

//	/**
//	 * ��ҳ��ѯ ������DataPackage������
//	 * 
//	 * @param querySql ��ѯ��䣬������
//	 * @param countSql ��ѯ��¼��ͳ�����
//	 * @param target ��ݿ��¼��װ��
//	 * @param currentPage ���ǰҳ��
//	 * @param pageSize ���ÿҳ����
//	 * @return ����
//	 */
//	public DataPackage getPage(String querySql, String countSql, final Class target, int currentPage, int pageSize)
//			throws DataAccessException {
//		int iCurPage = adjustCurrentPage(currentPage);
//		final int iPageSize = adjustPageSize(pageSize);
//
//		if (countSql == null) {
//			countSql = "select count(*) " + querySql.substring(querySql.indexOf(" from "));
//		}
//
//		final int iTotalCount = getJdbcTemplate().queryForInt(countSql);
//		final int iStartRowNum = (iCurPage - 1) * iPageSize;
//		List result = getJdbcTemplate().query(querySql, new RowMapper() {
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				if (iStartRowNum <= rowNum && rowNum < iStartRowNum + iPageSize) {
//					return ResultSetUtil.getElement(target, rs);
//				}
//				else {
//					return null;
//				}
//			}
//		});
//		List thisPageResult = new ArrayList();
//		for (Iterator iter = result.iterator(); iter.hasNext();) {
//			Object element = iter.next();
//			if (element != null) {
//				thisPageResult.add(element);
//			}
//		}
//
//		return wrapperPageableResult(iTotalCount, thisPageResult, iCurPage, iPageSize);
//	}

//	/**
//	 * ��ҳ��ѯ��Ϊ����CuteFramework Widget Grid ʹ�ã�
//	 * 
//	 * @param querySql
//	 * @param countSql
//	 * @param target
//	 * @param currentPage
//	 * @param pageSize
//	 * @return
//	 * @throws DataAccessException
//	 */
//	public Page getPages(String querySql, String countSql, final Class target, int currentPage, int pageSize)
//			throws DataAccessException {
//
//		DataPackage datas = getPage(querySql, countSql, target, currentPage, pageSize);
//		Page page = new Page();
//
//		page.setCurrentPageNo(currentPage);
//		page.setCurrentPageSize(pageSize);
//		page.setResult((ArrayList) datas.getRowList());
//		page.setTotalSize(datas.getRowCount());
//
//		return page;
//	}
//
//	/**
//	 * ��ҳ��ѯ������HashMap������
//	 * 
//	 * @param querySql
//	 * @param countSql
//	 * @param target
//	 * @param currentPage
//	 * @param pageSize
//	 * @return
//	 * @throws DataAccessException
//	 */
//	public HashMap getPagedHashMap(String querySql, String countSql, final Class target, int currentPage, int pageSize)
//			throws DataAccessException {
//		int iCurPage = adjustCurrentPage(currentPage);
//		final int iPageSize = adjustPageSize(pageSize);
//
//		if (countSql == null) {
//			countSql = "select count(*) " + querySql.substring(querySql.indexOf(" from "));
//		}
//
//		final int iTotalCount = getJdbcTemplate().queryForInt(countSql);
//		final int iStartRowNum = (iCurPage - 1) * iPageSize;
//		List result = getJdbcTemplate().query(querySql, new RowMapper() {
//			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
//				if (iStartRowNum <= rowNum && rowNum < iStartRowNum + iPageSize) {
//					return ResultSetUtil.getElement(target, rs);
//				}
//				else {
//					return null;
//				}
//			}
//		});
//		List thisPageResult = new ArrayList();
//		for (Iterator iter = result.iterator(); iter.hasNext();) {
//			Object element = iter.next();
//			if (element != null) {
//				thisPageResult.add(element);
//			}
//		}
//
//		return wrapperHashMapResult(iTotalCount, thisPageResult, iCurPage, iPageSize);
//	}

	// -- Private Methods ----------------------------------------------------//

	private static int adjustCurrentPage(Integer currentPage) {
		int iCurPage = 1;
		if (currentPage != null && currentPage.intValue() > 0) {
			iCurPage = currentPage.intValue();
		}
		return iCurPage;
	}

	private static int adjustPageSize(Integer pageSize) {
		int iPageSize;

		if (pageSize != null && pageSize.intValue() > 0) {
			iPageSize = pageSize.intValue();
		}
		else {
			iPageSize = 10;
		}

		return iPageSize;
	}

//	/**
//	 * @param totalCount
//	 * @param result
//	 * @param curPage
//	 * @param pageSize
//	 * @return
//	 */
//	private DataPackage wrapperPageableResult(int iTotalCount, List result, int iCurPage, int iPageSize) {
//		int iResultCount = 0;
//		if (result != null) {
//			iResultCount = result.size();
//		}
//
//		int remainder = 0; // βҳ�ж�
//		if (iTotalCount % iPageSize != 0) {
//			remainder = 1;
//		}
//
//		DataPackage dp = new DataPackage();
//		dp.setPageLines(iPageSize);
//		dp.setCurrPage(iCurPage);
//		dp.setRowCount(iTotalCount);
//
//		if (iResultCount == 0) // �޽��
//		{
//			dp.setRowList(new ArrayList());
//		}
//		else {
//			dp.setRowList(result);
//
//		}
//
//		return dp;
//	}

	private int adjustPageSize(int pageSize) {
		int iPageSize;
		if (pageSize > 0) {
			iPageSize = pageSize;
		}
		else {
			iPageSize = 10;
		}
		return iPageSize;
	}

	private int adjustCurrentPage(int currentPage) {
		if (currentPage > 0) {
			return currentPage;
		}
		else {
			return 1;
		}
	}

	/**
	 * @param totalCount
	 * @param result
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	private HashMap wrapperHashMapResult(int iTotalCount, List result, int iCurPage, int iPageSize) {
		int iResultCount = 0;
		if (result != null) {
			iResultCount = result.size();
		}
		int remainder = 0; // βҳ�ж�
		if (iTotalCount % iPageSize != 0) {
			remainder = 1;
		}

		Integer countPage = new Integer(iTotalCount / iPageSize + remainder);
		Integer currentPageTemp = new Integer(iCurPage);
		Integer countPerPageTemp = new Integer(iPageSize);
		Integer totalCount = new Integer(iTotalCount);

		HashMap pageMap = new HashMap();
		pageMap.put("totalpage", countPage);
		pageMap.put("currentpage", currentPageTemp);
		pageMap.put("countperpage", countPerPageTemp);
		pageMap.put("totalcount", totalCount);

		if (iResultCount == 0) // �޽��
		{
			pageMap.put("RESULT", new ArrayList());
		}
		else {
			pageMap.put("RESULT", result);

		}
		/*
		 * if (logger.isDebugEnabled()) { logger.debug("DAO multi pages sql getHashCollection:");
		 * logger.debug("... totalpage=" + countPage); logger.debug("... currentpage=" + currentPageTemp);
		 * logger.debug("... countperpage=" + countPerPageTemp); logger.debug("... RESULT size=" + iResultCount); }
		 */
		return pageMap;
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
}
