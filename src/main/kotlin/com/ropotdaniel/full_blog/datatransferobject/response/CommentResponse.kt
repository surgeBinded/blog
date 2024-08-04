package com.ropotdaniel.full_blog.datatransferobject.response

import com.ropotdaniel.full_blog.datatransferobject.comment.CommentDTO

data class CommentResponse(var content: List<CommentDTO>,
                           var pageNo: Int,
                           var pageSize: Int,
                           var totalElements: Long,
                           var totalPages: Int,
                           var last: Boolean)