package com.ropotdaniel.full_blog.service

import com.ropotdaniel.full_blog.domainobject.Article

interface ArticleService {

    fun getAllArticles(): MutableList<Article>

    fun createArticle(article: Article): Article

    fun updateArticle(id: Long, modifiedArticle: Article): Article

    fun deleteArticle(id: Long)
}