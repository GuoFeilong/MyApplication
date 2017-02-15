package com.xingyi.upscrolldemo;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RelativeLayout searchContainer;
    private RelativeLayout searchIconContainer;
    private LinearLayout searchParent;
    private ImageView ivSearch;
    private int originalWidth;
    private int originalHeight;
    private int originalParentWidth;
    private int originalParentHeight;
    private AppBarLayout appBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_test);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        searchContainer = (RelativeLayout) findViewById(R.id.rl_search_container);
        searchIconContainer = (RelativeLayout) findViewById(R.id.rl_iv_search_icon_container);
        searchParent = (LinearLayout) findViewById(R.id.ll_search_parent);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HomeReAdapter homeReAdapter = new HomeReAdapter();
        recyclerView.setAdapter(homeReAdapter);

    }

    /**
     * 根据appbar的偏移量,计算搜索框的layoutpaerm
     *
     * @param percent        偏移量
     * @param verticalOffset
     */
    private void changeSearchLayoutParm(float percent, int verticalOffset) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) searchContainer.getLayoutParams();
        layoutParams.width = (int) (originalWidth * percent);
        layoutParams.topMargin = Math.abs(verticalOffset);
        searchContainer.setLayoutParams(layoutParams);
        searchContainer.setAlpha(percent);
        ivSearch.setAlpha(1.0F - percent);
        Log.e(TAG, "--->>偏移量--->" + percent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        originalWidth = searchContainer.getWidth();
        originalHeight = searchContainer.getHeight();
        originalParentHeight = searchParent.getHeight();
        originalParentWidth = searchParent.getWidth();
        Log.e(TAG, "----原始宽度-->>" + searchContainer.getWidth() + "--原始parent高度-->" + originalParentHeight);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e(TAG, "---偏移量-->verticalOffset--->>>>>>  " + verticalOffset);
                changeSearchLayoutParm(1.0F - Math.abs(verticalOffset) * 1.0F / Math.abs(originalParentHeight), verticalOffset);

            }
        });

    }

    class HomeReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HomeVH(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_rv_data, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class HomeVH extends RecyclerView.ViewHolder {
            ImageView homeItem;

            public HomeVH(View itemView) {
                super(itemView);
                homeItem = (ImageView) itemView.findViewById(R.id.iv_data);
            }
        }
    }
}
