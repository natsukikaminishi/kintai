## 会社の勤怠システムのAndroidアプリバージョン作ってみました
(でも勤怠はクラウドにするらしい。)</br>

## 作ったきっかけ
コーディングしなさすぎで、したかったｗ</br>

## このアプリを見るおすすめの人
* 初心者だけど、とにかく何か動くものが作りたい人
  * html,js不要で、コード量が圧倒的に少ない
* BootStrap、Gradleを使ってみたい。スマホアプリ作ってみたい人
  * BootStrapは、超有名なcssフレームワークです
  * Gradleは、たぶん一番流行りのbuildシステムです
* 環境構築すらも苦手な人
  * MySQLなどpre installも不要で、Android Stadioさえinstallすれば開発できます
  * SQLiteによるDBアクセスは、内臓されたAndroid SDKで可能です

## 開発環境
Android Studio (+ Androidの実機があれば尚可)</br>

## 主な機能
* text入力/計算/保存</br>
  * SQLite:SQLiteOpenHelperを継承することで簡単にDBアクセスができます
  * Preference:簡易な設定などは、個々のアプリ単位で、残しておくことが可能です
  * TextWatcher:フォーカスアウトや戻った時に、自動でTextを残します
* 送信用textの作成
  * 特にAndroidらしさはないFileInputStreamやBufferedReaderなどを使用しています
* FTP送信
  * Apach commons-net.jarを使うと簡易に実装できます
* メーラー起動</br>
  * Intentで、簡易に画面遷移できますが、メーラーにも同じIntentで、簡易に遷移できます
* Material Calendar view</br>
  * デフォルトのCalendar viewは、ちょっとださいので、上記を使用しました
  * etc

## サンプルgif
<p>
  <img src="https://github.com/natsukikaminishi/kintai/blob/master/kintai_app.gif" width=60%>  
</p>　
<!--
MD形式を忘れないために Remember me
# This is an <h1> tag
## This is an <h2> tag
###### This is an <h6> tag
*This text will be italic*
_This will also be italic_

**This text will be bold**
__This will also be bold__

_You **can** combine them_

* Item 1
* Item 2
  * Item 2a
  * Item 2b
1. Item 1
1. Item 2
1. Item 3
   1. Item 3a
   1. Item 3b
   
   I think you should use an
`<addr>` element here instead.

- [x] @mentions, #refs, [links](), **formatting**, and <del>tags</del> supported
- [x] list syntax required (any unordered or ordered list supported)
- [x] this is a complete item
- [ ] this is an incomplete item

First Header | Second Header
------------ | -------------
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column
-->
