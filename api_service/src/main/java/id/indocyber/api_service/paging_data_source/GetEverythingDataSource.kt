package id.indocyber.api_service.paging_data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.indocyber.api_service.service.GetEverythingService
import id.indocyber.common.entity.everything.Article

class GetEverythingDataSource(
    private val getEverythingService: GetEverythingService,
    private val sources: String,
    private val q: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        try {
            val result = getEverythingService.getEverything(
                sources = sources,
                q = q,
                page = page,
                pageSize = pageSize
            )
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (
                result.body()?.articles?.isEmpty() == true ||
                result.body()?.let { page * pageSize >= 100 } == true ||
                result.body()?.let { page * pageSize >= it.totalResults } == true
            ) null else page + 1
            return result.body()?.let {
                LoadResult.Page(
                    data = it.articles,
                    prevKey,
                    nextKey
                )
            } ?: LoadResult.Error(Exception("invalid data"))
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}