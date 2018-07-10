package wang.dreamland.www.common;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Mybatis+Mysql 分页工具类
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = {
				Connection.class}),
		@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {
				Statement.class})})
public class PageHelper implements Interceptor {
	private static final Logger logger = Logger.getLogger(PageHelper.class);

	public static final ThreadLocal<Page> localPage = new ThreadLocal<Page>();

	/**
	 * 开始分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 */
	public static void startPage(Integer pageNum, Integer pageSize) {
		System.out.println("pageNumc:" + pageNum);
		localPage.set(new Page(pageNum, pageSize));
	}

	/**
	 * 结束分页并返回结果，该方法必须被调用，否则localPage会一直保存下去，直到下一次startPage
	 * 
	 * @return
	 */
	public static Page endPage() {
		Page page = localPage.get();
		localPage.remove();
		return page;
	}

	//@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (localPage.get() == null) {
			return invocation.proceed();
		}
		if (invocation.getTarget() instanceof StatementHandler) {
			StatementHandler statementHandler = (StatementHandler) invocation
					.getTarget();
			MetaObject metaStatementHandler = SystemMetaObject
					.forObject(statementHandler);
			// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环
			// 可以分离出最原始的的目标类)
			while (metaStatementHandler.hasGetter("h")) {
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			// 分离最后一个代理对象的目标类
			while (metaStatementHandler.hasGetter("target")) {
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = SystemMetaObject.forObject(object);
			}
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
					.getValue("delegate.mappedStatement");
			// 分页信息if (localPage.get() != null) {
			Page page = localPage.get();
			BoundSql boundSql = (BoundSql) metaStatementHandler
					.getValue("delegate.boundSql");
			// 分页参数作为参数对象parameterObject的一个属性
			String sql = boundSql.getSql();

			Connection connection = (Connection) invocation.getArgs()[0];
			// 重设分页参数里的总页数等
			setPageParameter(sql, connection, mappedStatement, boundSql, page);

			System.out.println("total:" + page.getPages());

			// 如果当前页大于总页数，则等于总页数
			if (page.getPageNum() > page.getPages()) {
				page.setPageNum(page.getPages());
			}

			// 重写sql
			String pageSql = buildPageSql(sql, page);
			System.out.println("sql::::" + pageSql);

			// 重写分页sql
			metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);

			// 将执行权交给下一个拦截器
			return invocation.proceed();
		} else if (invocation.getTarget() instanceof ResultSetHandler) {
			Object result = invocation.proceed();
			Page page = localPage.get();
			page.setResult((List) result);
			return result;
		}
		return null;
	}

	/**
	 * 只拦截这两种类型的 <br>
	 * StatementHandler <br>
	 * ResultSetHandler
	 * 
	 * @param target
	 * @return
	 */
	//@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler
				|| target instanceof ResultSetHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	//@Override
	public void setProperties(Properties properties) {

	}

	/**
	 * 修改原SQL为分页SQL
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql, Page page) {
		StringBuilder pageSql = new StringBuilder(200);
		pageSql.append(sql);
		pageSql.append(" limit ").append(page.getStartRow());
		pageSql.append(" , ").append(page.getPageSize());
		return pageSql.toString();
	}

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 * @param page
	 */
	private void setPageParameter(String sql, Connection connection,
			MappedStatement mappedStatement, BoundSql boundSql, Page page) {
		// 记录总记录数
		String countSql = "select count(0) from (" + sql + ") as total";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
					countSql, boundSql.getParameterMappings(),
					boundSql.getParameterObject());

			//
			for (ParameterMapping parameterMapping : boundSql
					.getParameterMappings()) {
				String property = parameterMapping.getProperty();
				if (boundSql.hasAdditionalParameter(property)) {
					countBS.setAdditionalParameter(property,
							boundSql.getAdditionalParameter(property));
				}
			}
			//

			setParameters(countStmt, mappedStatement, countBS,
					boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			int totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			page.setTotal(totalCount);

		} catch (SQLException e) {
			logger.error("Ignore this exception", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
		}
	}

	/**
	 * 代入参数值
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,
			MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {

		ParameterHandler parameterHandler = new DefaultParameterHandler(
				mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	/**
	 * 分页对象，里面包括分页信息和数据结果
	 */
	public static class Page<E> implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private int pageNum;
		private int pageSize;
		private int startRow;
		private int endRow;
		private long total;
		private int pages;
		private List<E> result;

		public Page(Integer pageNum, Integer pageSize) {
			if (pageNum == null || pageNum < 1) {
				pageNum = 1;
			}
			if (pageSize == null || pageSize < 1) {
				pageSize = 7;
			}
			this.pageNum = pageNum;
			this.pageSize = pageSize;
		}

		public List<E> getResult() {
			return result;
		}

		public void setResult(List<E> result) {
			this.result = result;
		}

		public int getPages() {
			// 计算总页数
			long totalPage = this.getTotal() / this.getPageSize()
					+ ((this.getTotal() % this.getPageSize() == 0) ? 0 : 1);
			this.setPages((int) totalPage);
			return pages;
		}

		public void setPages(int pages) {
			this.pages = pages;
		}

		public int getEndRow() {
			this.endRow = pageNum * pageSize;
			return endRow;
		}

		public void setEndRow(int endRow) {
			this.endRow = endRow;
		}

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getStartRow() {
			this.startRow = this.pageNum > 0
					? (this.pageNum - 1) * this.pageSize
					: 0;
			return startRow;
		}

		public void setStartRow(int startRow) {
			this.startRow = startRow;
		}

		public long getTotal() {
			return total;
		}

		private int startPage;//开始页码（按钮上的数字）
		private int endPage;//结束页码（按钮上的数字）

		public int getStartPage() {
			return startPage;
		}

		public void setStartPage(int startPage) {
			this.startPage = startPage;
		}

		public int getEndPage() {
			return endPage;
		}

		public void setEndPage(int endPage) {
			this.endPage = endPage;
		}
		public void setTotal(long total) {
			//计算总页码数：
			int totalCount = Integer.parseInt(total+"");
			pages=(totalCount+pageSize-1)/pageSize;
			//计算页面的页码中“显示”的起始页码和结束页码
			//一般显示的页码较好的效果是最多显示10个页码
			//算法是前5后4，不足补10
			//计算显示的起始页码（根据当前页码计算）：当前页码-5
			startPage = pageNum - 5;
			if(startPage < 1){
				startPage = 1;//页码修复
			}

			//计算显示的结束页码（根据开始页码计算）：开始页码+9
			endPage = startPage + 9;
			if(endPage > pages){//页码修复
				endPage = pages;
			}

			//起始页面重新计算（根据结束页码计算）：结束页码-9
			startPage = endPage - 9;
			if(startPage < 1){
				startPage = 1;//页码修复
			}

			System.out.println(startPage +"和" +endPage);

			this.total = total;
		}

		@Override
		public String toString() {
			return "Page{" + "pageNum=" + pageNum + ", pageSize=" + pageSize
					+ ", startRow=" + startRow + ", endRow=" + endRow
					+ ", total=" + total + ", pages=" + pages + '}';
		}
	}
}