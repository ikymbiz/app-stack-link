# Stack — Android UI 実装

最初のUIを Jetpack Compose で実装。データは `SampleLinks` のハードコード。

## ファイル構成

```
com.stack.app/
├── MainActivity.kt
├── model/
│   └── LinkItem.kt          (データクラス + サンプルデータ)
└── ui/
    ├── theme/
    │   ├── Color.kt         (パレット)
    │   ├── Type.kt          (Google Fonts読み込み)
    │   └── Theme.kt         (MaterialTheme)
    ├── components/
    │   └── LinkCard.kt      (カードコンポーネント)
    └── screen/
        └── HomeScreen.kt    (ホーム画面 + ヘッダー/チップ/ナビ/FAB)
```

## `app/build.gradle.kts` に追加する依存

```kotlin
dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.10.00")
    implementation(composeBom)

    // Compose core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Activity / Lifecycle
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Google Fonts (Bricolage / Onest / JetBrains Mono)
    implementation("androidx.compose.ui:ui-text-google-fonts")

    // 画像読み込み (サムネ用、今は未使用だが追加しておくとよい)
    implementation("io.coil-kt:coil-compose:2.7.0")
}

android {
    buildFeatures { compose = true }
}
```

## Google Fonts 用の証明書

`app/src/main/res/values/font_certs.xml` を新規作成:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <array name="com_google_android_gms_fonts_certs">
        <item>@array/com_google_android_gms_fonts_certs_dev</item>
        <item>@array/com_google_android_gms_fonts_certs_prod</item>
    </array>
    <string-array name="com_google_android_gms_fonts_certs_dev">
        <item>
            MIIEqDCCA5CgAwIBAgIJANWFuGx90071MA0GCSqGSIb3DQEBBAUAMIGUMQswCQYD
            <!-- 省略: AndroidX Compose Samplesから完全な証明書をコピー -->
        </item>
    </string-array>
    <string-array name="com_google_android_gms_fonts_certs_prod">
        <item>
            MIIEQzCCAyugAwIBAgIJAMLgh0ZkSjCNMA0GCSqGSIb3DQEBBAUAMHQxCzAJBgNV
            <!-- 省略: AndroidX Compose Samplesから完全な証明書をコピー -->
        </item>
    </string-array>
</resources>
```

> 完全な証明書文字列は AndroidX のサンプルリポジトリにあります:
> https://github.com/android/compose-samples → `font_certs.xml`

## まだ実装していないTODO

- [ ] ViewModel + StateFlow (まずは静的UI完成優先)
- [ ] Room DB
- [ ] OGP取得 (Jsoup or okhttp + HTMLパーサ)
- [ ] LLM要約 (Claude/GPT/Gemini/Grok切替)
- [ ] 共有シート対応 (`<intent-filter>` で SEND/SEND_MULTIPLE 受け)
- [ ] RSS購読 (rome等のFeedパーサ + WorkManagerで定期巡回)
- [ ] 詳細画面 / 追加画面 / RSS購読管理 / 設定 画面
- [ ] サムネ画像実読み込み (Coil AsyncImage)
- [ ] アイコンを `Icons.Outlined.*` などプロパーなアイコンに置換 (現状は絵文字/文字)

## 次のステップ候補

1. **状態管理を切り出す** → `HomeViewModel` + `UiState`
2. **追加画面** (URLペースト + 解析プログレス) を作る
3. **詳細画面** (要約全文 + 元リンク + タグ編集) を作る
4. **共有シート連携** で実機から動かしてみる
