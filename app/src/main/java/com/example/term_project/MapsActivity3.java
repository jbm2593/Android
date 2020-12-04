package com.example.term_project;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

//MainActivity 클래스에서  OnMapReadyCallback 인터페이스를 구현한다고 선언
public class MapsActivity3 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button btn_enter;
    private EditText editText;

    //이제 onCreate 메소드에서 getMapAsync() 메소드를 호출하여 GoogleMap 객체가 준비될 때 실행될 콜백을 등록.
    //그러기 위해서는 레이아웃에 추가했던 프래그먼트(com.google.android.gms.maps.SupportMapFragment)의 핸들을 가져와야 한다.
    //FragmentManager.findFragmentById 메소드를 사용하여 지정한 ID를 갖는 프래그먼트의 핸들을 가져온다.
    //getMapAsync() 메소드가 메인 쓰레드에서 호출되어야 메인스레드에서 onMapReady 콜백이 실행.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps3);
        editText = (EditText) findViewById(R.id.editText);
        btn_enter=(Button)findViewById(R.id.btn_enter);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
    }

    //OnMapReadyCallback 인터페이스의 onMapReady 메소드를 구현
    // 맵이 사용할 준비가 되었을 때(NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때)  호출되어지는 메소드.
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        LatLng Incheon = new LatLng(37.526639,126.728230);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Incheon);
        markerOptions.title("인천광역시 계양구 작전동");
        markerOptions.snippet("병무가 살고있는 곳");
        mMap.addMarker(markerOptions);

        //moveCamera 메소드를 사용하여 카메라를 지정한 경도, 위도로 이동한다.
        //CameraUpdateFactory.zoomTo 메소드를 사용하여 지정한 단계로 카메라 줌을 조정한다.  1 단계로 지정하면 세계지도 수준으로 보이고 숫자가 커질수록 상세지도가 보인다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Incheon));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        // 맵 터치 이벤트
        //MarkerOptions으로 마커가 표시될 위치(position), 마커에 표시될 타이틀(title)
        // 마커 클릭시 보여주는 간단한 설명(snippet)를 설정하고 addMarker 메소드로 GoogleMap 객체에 추가해주면 지도에 표시된다.
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });

        // 버튼 이벤트
        btn_enter.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String str=editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                System.out.println(latitude);
                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("열차 도착");
                mOptions2.snippet(address);
                mOptions2.position(point);
                // 마커(핀) 추가
                mMap.addMarker(mOptions2);

                //moveCamera 메소드를 사용하여 카메라를 지정한 경도, 위도로 이동한다.
                //CameraUpdateFactory.zoomTo 메소드를 사용하여 지정한 단계로 카메라 줌을 조정한다.  1 단계로 지정하면 세계지도 수준으로 보이고 숫자가 커질수록 상세지도가 보인다.
                mMap.moveCamera(CameraUpdateFactory.newLatLng(point));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                Toast.makeText(MapsActivity3.this, "목적지에 도착하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}