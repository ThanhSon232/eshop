package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.activites.cartActivity;
import com.example.activites.detailActivity;
import com.example.activites.loginActivity;
import com.example.activites.profileActivity;
import com.example.adapter.category.category;
import com.example.adapter.category.categoryAdapter;
import com.example.adapter.product.categoryModel;
import com.example.adapter.product.category_list_adapter;
import com.example.adapter.product.product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends AppCompatActivity {
//    private Fragment homeFragment = new HomeFragment();
//    private Fragment accountFragment = new AccountFragment();
//    Fragment activeFragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView navView = findViewById(R.id.navigation);
//        navView.setOnItemSelectedListener(navListener);
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,homeFragment,homeFragment.getClass().getName()).show(homeFragment).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,accountFragment,accountFragment.getClass().getName()).hide(accountFragment).commit();
//        activeFragment = homeFragment;
//    }
//    private NavigationBarView.OnItemSelectedListener navListener =
//            new NavigationBarView.OnItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.homeFragment:
//                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(homeFragment).commit();
//                            activeFragment = homeFragment;
//                            return true;
//                        case R.id.settingsFragment:
//                            getSupportFragmentManager().beginTransaction().hide(activeFragment).show(accountFragment).commit();
//                            activeFragment = accountFragment;
//                            return true;
//                    }
//                    return false;
//                }
//            };
//    private void loadFragment(Fragment fragment) {
//        // load fragment
//        String name = fragment.getClass().getName();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.addToBackStack(null).setReorderingAllowed(true);
//        transaction.commit();
//    }
//}

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageButton imageButton;
    ImageButton imageButton1;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private com.example.adapter.category.categoryAdapter sectionAdapter;
    List<category> sectionList = new ArrayList<>();
    List<categoryModel> categoryModelList = new ArrayList<>();
    com.example.adapter.product.category_list_adapter category_list_adapter;
    ImageSlider imageSlider;
    List<SlideModel> slideModelList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        mAuth = FirebaseAuth.getInstance();

        imageButton = findViewById(R.id.accountButton);
        imageButton.setOnClickListener(this);

        imageButton1 = findViewById(R.id.cart);
        imageButton1.setOnClickListener(this);


        recyclerView = findViewById(R.id.rcv_product);
        recyclerView1 = findViewById(R.id.category_list);

        sectionAdapter = new categoryAdapter(this);
        category_list_adapter = new category_list_adapter(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        sectionAdapter.setCategoryList(sectionList);
        recyclerView.setAdapter(sectionAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        category_list_adapter.setItem(categoryModelList);
        recyclerView1.setAdapter(category_list_adapter);


        imageSlider = findViewById(R.id.slider);
        getData();

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cart:
                startActivity(new Intent(this, cartActivity.class));
                break;
            case R.id.accountButton:
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser == null) {
                    startActivity(new Intent(this, loginActivity.class));
                }
                else{
                    startActivity(new Intent(this, profileActivity.class));
                }
                break;
            default:
                startActivity(new Intent(this, detailActivity.class));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check if user is signed in (non-null) and update UI accordingly.
    }

    public void getData(){
        db = FirebaseFirestore.getInstance();

        categoryModelList.add(new categoryModel("Living room","https://product.hstatic.net/200000065946/product/pro_mau_tu_nhien_noi_that_moho_ke_trang_tri_oslo_201_d36b7ccedb8b4da2a7c59b54cc46962b_master.png"));
        categoryModelList.add(new categoryModel("Dining room","https://product.hstatic.net/200000065946/product/pro_mau_tu_nhien_noi_that_moho_bo_ban_an_milan_4_ghe_milan_901_6938169abce64b95a7e03f7e7fc2fa12_master.jpg"));
        categoryModelList.add(new categoryModel("Bedroom","https://product.hstatic.net/200000065946/product/pro_nau_noi_that_moho_giuong_ngu_malaga_302_1_45f76c26cf6e448799718c16245f2e7b_master.png"));
        categoryModelList.add(new categoryModel("Others","https://product.hstatic.net/200000065946/product/sach_mo_hinh-_3__d930560ae8a24ec0a408dfaf6a1ab83c_master.jpg"));
        category_list_adapter.notifyDataSetChanged();

        slideModelList.add(new SlideModel("https://theme.hstatic.net/200000065946/1000806110/14/slideshow_1.jpg?v=214"));
        slideModelList.add(new SlideModel("https://theme.hstatic.net/200000065946/1000806110/14/slideshow_5.jpg?v=214"));
        slideModelList.add(new SlideModel("https://theme.hstatic.net/200000065946/1000806110/14/slideshow_3.jpg?v=214"));

        imageSlider.setImageList(slideModelList,false);


        db.collection("/products/7qXTNSVyB3G4IazhJ0Hm/living-room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<product> productList = new ArrayList<product>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productList.add(new product(document.getId(),document.getData().get("name").toString(), document.getData().get("image").toString(), document.getData().get("description").toString(), document.getData().get("price").toString(), Float.valueOf(document.getData().get("star").toString()), null,null));
                            }
                            sectionList.add(new category(productList, "Living room"));
                            sectionAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(String.valueOf(this), "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("/products/7qXTNSVyB3G4IazhJ0Hm/dining-room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<product> productList = new ArrayList<product>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                productList.add(new product(document.getId(),document.getData().get("name").toString(), document.getData().get("image").toString(), document.getData().get("description").toString(), document.getData().get("price").toString(), Float.valueOf(document.getData().get("star").toString()), null,null));
                            }
                            sectionList.add(new category(productList, "Dining room"));
                            sectionAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(String.valueOf(this), "Error getting documents.", task.getException());
                        }
                    }
                });

    }


}