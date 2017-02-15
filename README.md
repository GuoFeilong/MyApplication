# MyApplication
APP滑动交互demo
#在群里偶然看到一个交互,觉得挺有意思的于是实现一下,效果图如下
![这里写图片描述](http://img.blog.csdn.net/20170215175231436?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ2l2ZW1lYWNvbmRvbQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#我靠预览图错了,效果图如下:
![这里写图片描述](http://img.blog.csdn.net/20170215174154577?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ2l2ZW1lYWNvbmRvbQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


###实现步骤,非常简单

 1. AndroidMD控件CoordinatorLayout的使用
 2. Applayout的监听,计算当前的偏移量
 
 就这两步即可实现这个滑动的交互,因为这个根据滑动进行的改变,所以用动画是不明智的选择,用偏移量动态的改变他的LayoutParams即可,然后根据偏移量设置view的透明度完事.下面看下实现过程;

```
  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    // 获取偏移量的监听
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
```

```
    private void changeSearchLayoutParm(float percent, int verticalOffset) {
    // 根据偏移量提供的百分比,改变layout的参数和透明度
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) searchContainer.getLayoutParams();
        layoutParams.width = (int) (originalWidth * percent);
        layoutParams.topMargin = Math.abs(verticalOffset);
        searchContainer.setLayoutParams(layoutParams);
        searchContainer.setAlpha(percent);
        ivSearch.setAlpha(1.0F - percent);
        Log.e(TAG, "--->>偏移量--->" + percent);
    }

```

#[下载地址:https://github.com/GuoFeilong/MyApplication虽然只有几行代码,还希望老铁给个star](https://github.com/GuoFeilong/MyApplication)
