package com.example.term_project;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//MainActivity 클래스에서  OnMapReadyCallback 인터페이스를 구현한다고 선언
public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    //이제 onCreate 메소드에서 getMapAsync() 메소드를 호출하여 GoogleMap 객체가 준비될 때 실행될 콜백을 등록.
    //그러기 위해서는 레이아웃에 추가했던 프래그먼트(com.google.android.gms.maps.SupportMapFragment)의 핸들을 가져와야 한다.
    //FragmentManager.findFragmentById 메소드를 사용하여 지정한 ID를 갖는 프래그먼트의 핸들을 가져온다.
    //getMapAsync() 메소드가 메인 쓰레드에서 호출되어야 메인스레드에서 onMapReady 콜백이 실행.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }

    // OnMapReadyCallback 인터페이스의 onMapReady 메소드를 구현
    // 맵이 사용할 준비가 되었을 때(NULL이 아닌 GoogleMap 객체를 파라미터로 제공해 줄 수 있을 때)  호출되어지는 메소드.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Mycountry= new LatLng(37.526639,126.728230);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Mycountry);
        markerOptions.title("인천광역시 계양구 작전동");
        markerOptions.snippet("병무가 살고있는 곳");
        mMap.addMarker(markerOptions);
        //moveCamera 메소드를 사용하여 카메라를 지정한 경도, 위도로 이동한다.
        //CameraUpdateFactory.zoomTo 메소드를 사용하여 지정한 단계로 카메라 줌을 조정한다.  1 단계로 지정하면 세계지도 수준으로 보이고 숫자가 커질수록 상세지도가 보인다.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Mycountry));
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
                mMap.addMarker(mOptions);
                mOptions.draggable(true);

                //moveCamera 메소드를 사용하여 카메라를 지정한 경도, 위도로 이동한다.
                //CameraUpdateFactory.zoomTo 메소드를 사용하여 지정한 단계로 카메라 줌을 조정합니다.  1 단계로 지정하면 세계지도 수준으로 보이고 숫자가 커질수록 상세지도가 보인다.
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }
        });
    }
}