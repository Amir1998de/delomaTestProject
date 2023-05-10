package site.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import logic.dao.StoreRecDAO;
import logic.entity.StoreRecommendation;
import site.util.ShowcaseUtil;

public class LazyStoreRecDataModelDAO extends LazyDataModel<StoreRecommendation> {

	private static final long serialVersionUID = 9222784293154869519L;

	/*
	 * properties
	 */

	private final StoreRecDAO storeRecDAO;

	private List<StoreRecommendation> datasource;

	/*
	 * constructor
	 */
	public LazyStoreRecDataModelDAO(final StoreRecDAO dao) {
		this.storeRecDAO = dao;
	}

	/*
	 * count
	 *
	 * It divides the total number by the page size for example 40 / 4 = 10 ->
	 * (1 of 10 )
	 */
	@Override
	public int count(final Map<String, FilterMeta> filterBy) {

		final Map<String, Object> filters = ShowcaseUtil.convertFilters(filterBy);

		System.out.println("LazyStoreRecDataModelDAO//count : " + this.storeRecDAO.getRowCount(filters));
		return this.storeRecDAO.getRowCount(filters);

	}

	/*
	 * methods
	 */
	@Override
	public StoreRecommendation getRowData(final String rowKey) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRowKey(final StoreRecommendation storeRecommendation) {
		System.out.println("LazyStoreRecDataModelDAO//getRowKey : " + String.valueOf(storeRecommendation.getId()));
		return String.valueOf(storeRecommendation.getId());
	}
	

	/*
	 * load
	 *
	 * First is offset = 0 and pageSize = 4 so it will show object with Index
	 * 0,1,2,3 if I select next page in paginator, is offset = 4 and pageSize =
	 * 4 so it will show object with Index 4,5,6,7 And it continues...
	 */

	@Override
	public List<StoreRecommendation> load(final int offset, final int pageSize, final Map<String, SortMeta> sortBy,
			final Map<String, FilterMeta> filterBy) {

		final Map<String, Object> filters = ShowcaseUtil.convertFilters(filterBy);
		
		// TODO convert SortMeta - so that logic namespace does not know about PrimeFaces
		this.datasource = this.storeRecDAO.getList(offset, pageSize, filters, null);
		// datasource = this.storeRecDAO.getAll();

		System.out.println("LazyStoreRecDataModelDAO//load.size : " + this.datasource.size());
		System.out.println(
				"LazyStoreRecDataModelDAO//offset.load : " + offset + "storeRecommendation.pageSize: " + pageSize);
		System.out.println("LazyStoreRecDataModelDAO//filterBy.load: " + filterBy);
		System.out.println("LazyStoreRecDataModelDAO//sortBy.load : " + sortBy);

		return this.datasource;

	}

}
