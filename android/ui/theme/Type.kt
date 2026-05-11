package com.stack.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.stack.app.R

/**
 * Google Fonts の動的読み込みプロバイダ。
 *
 * app/build.gradle.kts に依存関係を追加すること:
 *   implementation("androidx.compose.ui:ui-text-google-fonts:<version>")
 *
 * res/values/font_certs.xml に証明書を追加:
 *   <array name="com_google_android_gms_fonts_certs">
 *     <item>@array/com_google_android_gms_fonts_certs_dev</item>
 *     <item>@array/com_google_android_gms_fonts_certs_prod</item>
 *   </array>
 */
private val gFontProvider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

private val Bricolage = FontFamily(
    Font(GoogleFont("Bricolage Grotesque"), gFontProvider, FontWeight.Medium),
    Font(GoogleFont("Bricolage Grotesque"), gFontProvider, FontWeight.Bold),
    Font(GoogleFont("Bricolage Grotesque"), gFontProvider, FontWeight.ExtraBold),
)

private val Onest = FontFamily(
    Font(GoogleFont("Onest"), gFontProvider, FontWeight.Normal),
    Font(GoogleFont("Onest"), gFontProvider, FontWeight.Medium),
    Font(GoogleFont("Onest"), gFontProvider, FontWeight.SemiBold),
    Font(GoogleFont("Onest"), gFontProvider, FontWeight.Bold),
)

private val Mono = FontFamily(
    Font(GoogleFont("JetBrains Mono"), gFontProvider, FontWeight.Normal),
    Font(GoogleFont("JetBrains Mono"), gFontProvider, FontWeight.Medium),
)

/** 用途別に使い分ける拡張プロパティ */
object AppFonts {
    val Display = Bricolage  // 見出し
    val Body = Onest         // 本文 / UI
    val Mono = Mono          // メタ情報・日付など
}

val AppTypography = Typography(
    displayLarge = TextStyle(fontFamily = Bricolage, fontWeight = FontWeight.Bold, fontSize = 28.sp, letterSpacing = (-1).sp),
    headlineMedium = TextStyle(fontFamily = Bricolage, fontWeight = FontWeight.Bold, fontSize = 22.sp, letterSpacing = (-0.7).sp),
    titleLarge = TextStyle(fontFamily = Bricolage, fontWeight = FontWeight.Bold, fontSize = 17.sp, lineHeight = 22.sp, letterSpacing = (-0.3).sp),
    bodyLarge = TextStyle(fontFamily = Onest, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 22.sp),
    bodyMedium = TextStyle(fontFamily = Onest, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 20.sp),
    labelLarge = TextStyle(fontFamily = Onest, fontWeight = FontWeight.SemiBold, fontSize = 12.5f.sp),
    labelMedium = TextStyle(fontFamily = Onest, fontWeight = FontWeight.SemiBold, fontSize = 11.sp),
    labelSmall = TextStyle(fontFamily = Mono, fontWeight = FontWeight.Medium, fontSize = 10.sp, letterSpacing = 0.8.sp),
)
