package com.stack.app.model

import java.time.LocalDate

/** リンクの種類 */
enum class LinkType(val label: String) {
    NEWS("News"),
    SNS("X · SNS"),
    RSS("RSS"),
}

/** 保存されたリンク 1件 */
data class LinkItem(
    val id: String,
    val url: String,
    val title: String,
    val source: String,        // 発信者 (媒体名 / アカウント名)
    val publishedAt: LocalDate,
    val summary: String,
    val thumbnailUrl: String?,
    val readingMinutes: Int? = null,
    val type: LinkType,
    val tags: List<String> = emptyList(),
    val isRead: Boolean = false,
    val isFavorite: Boolean = false,
)

object SampleLinks {
    val items: List<LinkItem> = listOf(
        LinkItem(
            id = "1",
            url = "https://example.com/1",
            title = "大規模言語モデルの推論コストが1年で1/10に — 国内スタートアップの躍進が加速",
            source = "日本経済新聞",
            publishedAt = LocalDate.of(2026, 5, 10),
            summary = "推論専用チップの普及と量子化技術の進展により、商用LLMのトークンあたりコストが急速に低下。国内のAIスタートアップ各社が、これまで採算が合わなかった領域への展開を進めている。",
            thumbnailUrl = null,
            readingMinutes = 4,
            type = LinkType.NEWS,
            tags = listOf("AI", "経済"),
            isRead = false,
            isFavorite = true,
        ),
        LinkItem(
            id = "2",
            url = "https://x.com/takafumi_design/status/xxx",
            title = "UIデザインの「余白」は引き算ではなく、優先順位を表現する積極的な手段",
            source = "@takafumi_design",
            publishedAt = LocalDate.of(2026, 5, 10),
            summary = "余白を増やすほど要素同士の関係性が明確になる。ユーザーは「何を見ればいいか」を考えずに済む。詰め込むことは、判断をユーザーに丸投げすることと同じ。",
            thumbnailUrl = null,
            readingMinutes = null,
            type = LinkType.SNS,
            tags = listOf("デザイン", "UI"),
            isRead = false,
            isFavorite = false,
        ),
        LinkItem(
            id = "3",
            url = "https://tatsuya.example/notes/3",
            title = "個人開発で疲弊しないための、続けられるリズムの作り方",
            source = "Tatsuya's Notes",
            publishedAt = LocalDate.of(2026, 5, 9),
            summary = "3年続けた個人開発から学んだ、燃え尽きずに継続するためのリズム設計。タスクの粒度、休む基準、モチベーションが落ちたときの対処法までを実体験ベースで整理した。",
            thumbnailUrl = null,
            readingMinutes = 8,
            type = LinkType.RSS,
            tags = listOf("個人開発", "習慣"),
            isRead = true,
            isFavorite = true,
        ),
        LinkItem(
            id = "4",
            url = "https://asahi.com/article/xxx",
            title = "地方の図書館が「第三の場所」として再評価される理由",
            source = "朝日新聞デジタル",
            publishedAt = LocalDate.of(2026, 5, 8),
            summary = "人口減少時代の地方都市で、図書館がコミュニティハブとして機能し始めている。蔵書数ではなく、人と人がゆるやかに出会える設計が訪問者数を押し上げている。",
            thumbnailUrl = null,
            readingMinutes = 6,
            type = LinkType.NEWS,
            tags = listOf("社会"),
            isRead = true,
            isFavorite = false,
        ),
        LinkItem(
            id = "5",
            url = "https://producthunt.example/daily/xxx",
            title = "今週のローンチ: AI議事録ツール、ノーコードRPA、新世代タスク管理",
            source = "Product Hunt Daily",
            publishedAt = LocalDate.of(2026, 5, 7),
            summary = "今週Product Huntで注目を集めた3つのプロダクトを紹介。特にAI議事録ツールは発話者識別の精度が大きく改善され、既存ツールからの乗り換えが起きている。",
            thumbnailUrl = null,
            readingMinutes = null,
            type = LinkType.RSS,
            tags = listOf("プロダクト", "AI"),
            isRead = false,
            isFavorite = false,
        ),
    )
}
