package com.example.testexampleappbasic.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssResponse (
    @field:Element(name = "channel")
    var channel: Channel? = null
)
@Root(name = "channel", strict = false)
data class Channel (
    @field:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "link", required = false)
    var link: String? = null,

    @field:ElementList(name = "item", inline = true)
    var items: List<Item>? = null
)

@Root(name = "item", strict = false)
data class Item (
    @field:Element(name = "title")
    var title: String? = null,

    @field:Element(name = "link")
    var link: String? = null,

    @Namespace(reference = "http://search.yahoo.com/mrss/", prefix = "media")
    @field:Element(name = "thumbnail", required = false) // Correct annotation for media:thumbnail
    var mediaThumbnail: MediaThumbnail? = null,

)
@Root(name = "thumbnail", strict = false)
data class MediaThumbnail(
    @field:Attribute(name = "url")
    var url: String? = null,

    @field:Attribute(name = "width", required = false)
    var width: Int? = null,

    @field:Attribute(name = "height", required = false)
    var height: Int? = null
)