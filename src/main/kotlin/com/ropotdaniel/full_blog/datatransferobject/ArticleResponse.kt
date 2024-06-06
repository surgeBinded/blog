package com.ropotdaniel.full_blog.datatransferobject

class ArticleResponse(var content: List<ArticleDTO> ,
                      var pageNo: Int,
                      var pageSize: Int,
                      var totalElements: Long,
                      var totalPages: Int,
                      var last: Boolean)