package com.ropotdaniel.full_blog.domainobject

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.multipart.MultipartFile
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "article")
class Article(@Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
              @Column(nullable = false) var title: String,
              @Lob @Column(nullable = false) var content: String,
              @Lob @Column var bannerImage: MultipartFile
){
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    var dateCreated = ZonedDateTime.now()
}
