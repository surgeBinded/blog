package com.ropotdaniel.full_blog.datatransferobject.response

import com.ropotdaniel.full_blog.datatransferobject.article.ArticleDTO

data class ArticleResponse(var content: List<ArticleDTO>,
                           var pageNo: Int,
                           var pageSize: Int,
                           var totalElements: Long,
                           var totalPages: Int,
                           var last: Boolean)