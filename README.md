## 会社の勤怠システムのAndroidアプリバージョン作ってみました
(でも勤怠はクラウドにするらしい。)</br>

## 作ったきっかけ
コーディングしなさすぎで、したかったｗ</br>

## このアプリを見るおすすめの人
・初心者だけど、とにかく何か動くものが作りたい人</br>
 　 　html,css,js不要で、簡単に動きます。</br>
 　 　MySQLなどpre installも不要で、Android Stadioさえinstallすれば開発できます。</br>
 　 　SQLiteによるDBアクセスは、内臓されたAndroid SDKで可能です。</br>

## 開発環境
Android Studio (+ Androidの実機があれば尚可)</br>

## 主な機能
・text入力/計算/保存</br>
 　 　SQLite:SQLiteOpenHelperを継承することで簡単にDBアクセスができます。</br>
 　 　Preference:簡易な設定などは、個々のアプリ単位で、残しておくことが可能です。</br>
 　 　TextWatcher:フォーカスアウトや戻った時に、自動でTextを残します。</br>
・送信用textの作成</br>
 　 　特にAndroidらしさはないFileInputStreamやBufferedReaderなどを使用しています。</br>
・FTP送信</br>
 　 　Apach commons-net.jarを使うと簡易に実装できます。</br>
・メーラー起動</br>
 　 　Intentで、簡易に画面遷移できますが、メーラーにも同じIntentで、簡易に遷移できます。</br>
・Material Calendar view</br>
 　 　デフォルトのCalendar viewは、ちょっとださいので、上記を使用しました。</br>
  etc</br>

## サンプルgif
![sample](https://github.com/natsukikaminishi/kintai/blob/master/kintai_app.gif)
