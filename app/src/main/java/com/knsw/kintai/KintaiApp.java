package com.knsw.kintai;

/*
The MIT License (MIT)Copyright (c) 2013-2015 Bearded Hen

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.</td>
*/

/*
MITライセンス（MIT）Copyright（c）2013-2015 Bearded Hen

これにより、以下のコピーを入手した人に無料で許可が与えられます。
このソフトウェアおよび関連するドキュメントファイル（「ソフトウェア」）、
以下の権利を含むがこれらに限定されないソフトウェア。
のコピーの使用、コピー、変更、マージ、公開、配布、サブライセンス、および/または販売
ソフトウェア、およびソフトウェアの提供先に許可を与えるため、
次の条件が適用されます。

上記の著作権表示とこの許可通知は、すべてに含まれるものとします
ソフトウェアのコピーまたは大部分。

本ソフトウェアは、「現状有姿」で提供され、いかなる種類の保証も明示、または
黙示、含むが、商品性、適合性の保証に限定されない
特定の目的と非侵害のため。 いかなる場合においても著者または
著作権者は、いかなる請求、損害またはその他の責任についても責任を負います
契約、不法行為、その他の行為、またはその結果
本ソフトウェアまたは本ソフトウェアの使用またはその他の取引との接続
*/

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class KintaiApp extends Application {

    // 他クラスから参照するためのstatic変数
    private static Context context;
    private static SharedPreferences preferences;

    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
        KintaiApp.context = getApplicationContext();
        preferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    /*
     * 必要なgetter類
     */
    public static Context getAppContext() {
        return KintaiApp.context;
    }

    public static SharedPreferences getSharedPreferences() {
        return preferences;
    }
}
