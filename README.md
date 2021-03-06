# android-edu
2/27~3/5일간 진행된 안드로이드 교육 내용 정리

## 1. 레이아웃 종류
#### 1. LinearLayout
- 사각형 박스 형태의 디스플레이 화면에 UI 요소들을 일렬로 배치할 수 있다.
- orientation: 방향성이 있음
- match_parent: 부모 영역의 공간을 차지하지만, 차지할 수 있는 영역까지만 차지
- gravity: 자신의 뷰에서 포함하고 있는 데이터를 정렬
- layout_gravity: LinearLayout에서만 동작한다. 자신을 포함하고 있는 부모 위젯 레이아웃에서 옵션값에 따라 정렬 (자기 자신의 외부에 관련된 속성은 layout이 붙는다. ex. android:layout-margin, android:padding)
- layout_weight: 남는 공간을 얼만큼의 비율로 나눠 갖는지 결정


#### 2. RelativeLayout
- 기준을 잡고 그 기준을 대상으로 배치하고, align 같은 속성들을 이용해서 정렬한다.
- android:layout_centerInParent: 화면 한 가운데에 배치
- android:layout_centerHorizontal : 수평 상 가운데 배치
- [RelativeLayout.LayoutParams 종류](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html)

## 2. Activity
- startActivity(Intent): 다른 액티비티를 실행시킨다
- startActivityForResult(Intent): 다른 액티비티를 실행시키고 결과값을 받는다. onActivityResult() 리스너를 이용하여 데이터를 처리할 수 있다.

```
@Override
 protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
 }
```

- getIntent(): 전달받은 인텐트를 가져 옴
- setResult(): 자신을 호출한 액티비티에 결과값을 보내거나 추가적인 데이터를 보낼 수 있다. 역시 인텐트를 이용해야 한다.
- Intent: 컴포넌트 간의 데이터를 주고받거나 액티비티를 호출할 때 사용
  - 명시적 인텐트: 어떤 인텐트를 실행할지 명시적으로 표기 (ex. new Intent(MainActivity.this, MyActivity.class); )
  - 암시적 인텐트
  - intent.putExtra()를 통해 데이터를 주고 받을 수 있음.

## 3. ListView
- setOnItemClickListener()는 ListView만 가지고 있다.
- Adapter
   - 사용자가 정의한 데이터를 ListView에 출력하기 위해 사용하는 객체로, 사용자 데이터와 화면 출력 View로 이루어진 두 개의 부분을 이어주는 객체이다.
   - https://recipes4dev.tistory.com/42
   - Adapter는 BaseAdapter 클래스를 상속받아 새로 custom adapter를 구현할 수 있음
   - 스크롤 내리면서 데이터가 더 필요하면 getView() 호출하여 새로 생성.

## 4. 안드로이드 Life Cycle
![alt text](https://developer.android.com/guide/components/images/activity_lifecycle.png)

## 5. menu
- onCreateOptionsMenu(): activity에 메뉴를 생성할 수 있음.
- getMenuInflater()로 메뉴 inflater를 얻을 수 있다.
- onOptionsItemSelected(): 메뉴 선택 시 동작 정의

## 6. Thread
- 독립적으로 실행되는 실행 단위
- 네트워크 같은 경우 Thread로만 동작하게 만듦
- 단 직접 만든 Thread는 UI에 직접 접근할 수 없어서 'CalledFromWrongThreadException:Only the original thread that created a view hierarchy can touch its views.' 와 같은 예외가 발생한다.
- Thread에서 UI를 조작하기 위해서는 핸들러가 필요하다.

```
Handler handler = new Handler(){
     @Override
     public void handleMessage(Message msg) {
         super.handleMessage(msg);
     }
}
```

- Thread 예제로 progressBar 예제도 많이 사용 됨
- Logical한 코드만 Thread에 넣고, UI 조작 관련 코드는 Handler에 넣는 것을 권장함
- Thread & Handler의 기능을 같이 해주는 AsyncTask 클래스도 있다.

## 7. AsyncTask
- extends AsyncTask<Params, Progress, Result> 해서 사용한다

```
class MyTask extends AsyncTask<Void, Integer, Void>
```
#### 7-1. Thread 로직 관련
- Generic 타입이므로 기본형은 Wrapper 클래스로 넘겨야 한다.
- params: 진행 전에 넘기고 싶은 데이터
- progress : 진행 중간에 데이터를 넘기고 싶을 때
- result : 결과 리턴

#### 7-2. UI 조작
- onPreExecute() : doInBackground() 시작 전에
- onPostExecute() : doInBackground() 시작 후에
- onProgressUpdate(): doInBackground() 실행 중에 publishProgress()로 전달한 데이터를 받아 처리한다.

## 8. xmlpullparser
- xml 데이터를 읽고 파싱할 수 있다
- https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser
- 08_xml 프로젝트처럼 AndroidMenifest.xml에 다음 퍼미션을 추가해야 기상청의 기상정보 RSS를 받아올 수 있다.
- 자바 URL 객체에 url을 넣고 xmlpullparser에 전달하면 xmlpullparser가 알아서 읽어온다. (스트림을 열어서 읽고 쓰고 작업을 xmlpullparser가 함)

```
  <!-- 인터넷 사용 -->
  <uses-permission android:name="android.permission.INTERNET"/>
```

## 9. RecyclerView
- RecyclerView는 자동으로 추가되지 않으므로 Product Structure 메뉴를 이용한다.
- 새로 dependency를 추가한다.(implementation 'com.android.support:recyclerview-v7:28.0.0')
- custom ListView와 비슷하다고 보면 됨.
- 리스트뷰 + 그리드뷰 가 합쳐진 느낌

#### 1. ViewHolder
- ListView의 adapter가 했던 getView()의 역할을 대신함
- ViewHolder에 ClickListner를 붙이면 어떤 item이 클릭됐는지 알 수 있다 (ListView.OnItemClickListener() 와 같음)

## 10. WebViewClient
-

## 11. Style
- dp: 
- px:
- sp: dp와 동일한데, 안드로이드 시스템의 설정에서 '큰글씨'로 설정하면 글씨가 커짐. 하지만 sp를 쓰게 되면 레이아웃이 늘어날 수 있으므로 레이아웃 크기가 고정되어야 할 경우에는 dp를 쓴다.
- 참고할 만한 dpi 계산기 사이트: http://dpi.lv/
- https://rojhw.tistory.com/23
- https://m.blog.naver.com/PostView.nhn?blogId=qbxlvnf11&logNo=221080028435&proxyReferer=https%3A%2F%2Fwww.google.com%2F
- https://woovictory.github.io/2019/01/03/Android-Diff-of-DP-SP/
- https://blog.cracker9.io/2018/03/13/Android_DPI/

```
// style.xml
<!-- 상속 -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- override -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>

// AndroidMenifest.xml에서 style.xml을 사용함
 <application
     ...
     android:theme="@style/AppTheme">
    ...
 </application>
```

## 12. SharedPreferences
- SharedPreference를 이용하여 간단한 데이터들을 저장하고 불러올 수 있다.
- 보안에 취약한 데이터들은 저장하지 않는 것이 바람직하다.
- 보통 초기 설정값이나 자동로그인 여부 등 간단한 값을 저장하기 위해 사용한다.
- 어플리케이션에 파일 형태로 데이터를 저장한다.
- onPause() 시에 SharedPreferences에 데이터를 저장하거나, onResume() 시에 SharedPreferences에 있는 데이터를 불러올 수 있다.
- https://humble.tistory.com/9

## 13. SQLite
- https://www.sqlite.org/datatype3.html
- SQLite를 ORM으로도 사용할 수 있음

```
public class TestSQLiteOpenHelper extends SQLiteOpenHelper {

    public TestSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // SQLiteOpenHelper가 생성될 때 실행. 어플리케이션이 처음 생성될 때 실행된다.
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    
    // 버전업 될 때 db 내부적으로 필드를 추가하거나 수정사항이 있을 때 실행
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
```

## 14. MediaPlayer
#### 14-1. Permission
- permission : https://developer.android.com/training/permissions/requesting?hl=ko
- 마시멜로우 버전 이후부터 위험 권한의 퍼미션들은 menifest에 등록해도 바로 사용할 수는 없다. RuntimePermission을 이용해서 사용자에게 요청을 해서 권한을 얻어와야 한다. 아래의 링크는 위험한 권한 및 권한 그룹에 관한 표이다.
- https://developer.android.com/guide/topics/security/permissions.html?hl=ko#perm-groups
- https://developer.android.com/training/permissions/requesting?hl=ko#make-the-request

#### 14-2. READ_EXTERNAL_STORAGE 권한
- 안드로이드 내 저장 공간에 있는 미디어 파일을 얻어오기 위해서는 'READ_EXTERNAL_STORAGE' 권한 설정이 되어있어야 되는데, 'READ_EXTERNAL_STORAGE'은 위험 권한이다. 그러므로 runtime 때 권한을 사용자에게 요청하여 얻어와야 한다.
- https://webnautes.tistory.com/1225

#### 14-3. MediaPlayer
- https://developer.android.com/reference/android/media/MediaPlayer#state-diagram
- prepared() : 미리 buffer와 같은 느낌으로 준비함
- started() : 미디어 start
- seekTo() : 원하는 위치로 seek

## 15. Service
- Acitivity는 한 화면에 하나만 실행되지만, Service는 화면 밖에서도 동작 가능하다.
- 만약 미디어플레이어를 Service에 두면 Activity를 이동해도 영향을 받지 않고 정상적으로 재생 가능.
- onCreate() -> onStartCommand() -> onDestory()
- 한번 생성 되어있으면 다시 생성하지는 않음 (있는걸 재사용)

![alt text](https://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile4.uf.tistory.com%2Fimage%2F26FB553359803D1F09AF42)

- https://developer.android.com/guide/components/services?hl=ko
- https://programmingfbf7290.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%84%9C%EB%B9%84%EC%8A%A4-%EC%B4%9D%EC%A0%95%EB%A6%AC%EC%8A%A4%ED%83%80%ED%8B%B0%EB%93%9C%EB%B0%94%EC%9A%B4%EB%93%9C-%EC%84%9C%EB%B9%84%EC%8A%A4

- START_STICKY: 강제로 종료되었을 경우 재시작 시킴. 대신 Intent가 null로 들어옴
- START_NOT_STICKY: 종료되었을 때 재시작 시키지 않음.
- START_REDELIVER_INTENT: 종료되었을 때 재시작 시킴. Intent도 다시 전달.
- https://developer88.tistory.com/36
- https://apphappy.tistory.com/38

#### 15-1. StartService

#### 15-2. BindService 
 - Activity에서 멤버변수처럼 Service를 쓸 수 있어서, 액티비티에서 Service의 메소드를 호출할 수 있다.
 - 프로세스간 통신에도 된다..   
 - 서비스 바인딩은 연결된 액티비티가 사라지면 서비스도 소멸된다.
 - https://bitsoul.tistory.com/149?category=623707
 
## 16. BroadcastReceiver
 - AndoridMenifest.xml receiver내부에 적절한 intent-filter를 추가해줘야 함.

```
// AndroidMenifest.xml
<receiver
    android:name=".MyReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
    </intent-filter>
</receiver>
```

- 커스텀 액션을 추가하여 브로드캐스를 만들 수도 있음


## 17. Fragment
- https://developer.android.com/guide/components/fragments?hl=ko
- FrameLayout: 아무런 배치 속성이 없는 Layout.(무조건 좌측 상단에 붙음) 보통 fragment를 올리는 용도로 많이 씀

![alt text](https://developer.android.com/images/fragment_lifecycle.png?hl=ko)


#### 17-1. Fragment 추가 방법
- 레이아웃에 직접 추가

```
<fragment
    android:id="@+id/fragment1"
    tools:layout="@layout/fragment_blank"
    android:name="com.example.a17_fragment.BlankFragment"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

- 액티비티에서 직접 추가 : FragmentTransaction 이용

#### 17-2. BackStack
- 액티비티에서 뒤로가기 누르면 그 전 액티비티가 뜨는 것처럼, 같은 액티비티 위에 올라간 프래그먼트에서 back키를 눌렀을 때 이전 프래그먼트로 돌아가는 기능을 구현하고 싶다면 백스택을 활용.


## 18. ViewPager
- 스와이프 했을 때 Fragment를 교체해줌.
1) 디테일 화면을 보여줄 activity 생성
2) ViewPager가 가질 fragment 생성
3) adapter 설정

---
#### * 그외
- 디자인은 스케치 툴 사용
- cf.인플레이트: xml 코드에 있는 UI 객체를 메모리에 올려 사용할 수 있게 한다
- 뷰를 수정할 경우에는 Activity Context를, 나머지 경우에는 Application Context를 사용하는 것을 권장한다.
