package com.hqs.common.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hqs.R;
import com.hqs.common.utils.SDCardUtils;
import com.hqs.common.utils.ScreenUtils;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.compress.CompressImageUtil;
import com.jph.takephoto.model.TImage;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by apple on 16/10/9.
 */

public class SelectPhotoView extends HorizontalScrollView {

    private LinearLayout contentView;
    private ImageView addImgView;
    // 选择的图片
    private ArrayList<TImage> images = new ArrayList<>();
    private ArrayList<ImageView> imgViews;
    // 压缩后的小图片的路径
    private ArrayList<String> smallImgPaths;
    // 缓存
    private ArrayList<ImageView> imgViewsCache;
    private int lineSpacing = 10;
    private Context context;
    private int addButtonPadding = 10;
    private View rightView;

    // 网络图片部分的长度
    private int preNetImageCount = 0;

    // 小图片缓存路径
    public String cacheDir = null;
    private ImageView.ScaleType imageScaleType = ImageView.ScaleType.CENTER_CROP;

    public Handler handler = new Handler();
    private CompressImageUtil compressImage;

    private AdapterView.OnItemClickListener onItemClickListener;
    private OnClickListener onClickAddButtonListener;

    public SelectPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initView();
    }

    private void initView(){

        rightView = new View(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setScrollBarSize(lineSpacing);
        }

        imgViews = new ArrayList<>();
        imgViewsCache = new ArrayList<>();
        smallImgPaths = new ArrayList<>();

        contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.setLayoutParams(layoutParams);
        this.addView(contentView);

        addImgView = new ImageView(context);
        addImgView.setImageResource(R.mipmap.select_photos);
        addImgView.setScaleType(ImageView.ScaleType.FIT_XY);
        contentView.addView(addImgView);
        imgViews.add(addImgView);

        addImgView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickAddButtonListener != null){
                    onClickAddButtonListener.onClick(v);
                }
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        adjustViewsSize();
    }

    public OnClickListener getOnClickAddButtonListener() {
        return onClickAddButtonListener;
    }

    public void setOnClickAddButtonListener(OnClickListener onClickAddButtonListener) {
        this.onClickAddButtonListener = onClickAddButtonListener;
    }

    public ArrayList<TImage> getImages() {
        return images;
    }

    public ArrayList<String> getImagePaths() {

        ArrayList<String> filePaths = new ArrayList<>();
        if (images != null && images.size() > 0){
            for (TImage image : images){
                filePaths.add(image.getPath());
            }
        }
        return filePaths;
    }

    public ArrayList<ImageView> getImgViews() {
        return imgViews;
    }

    public void compressImage(String imgPath, String newImgPath, CompressImageUtil.CompressListener listener){

        if (compressImage == null){
            CompressConfig config = new CompressConfig();
            config.enableQualityCompress(true);
            compressImage = new CompressImageUtil(config);
        }
        compressImage.compress(imgPath, newImgPath, listener);
    }
 
    private void resetImages(){
        contentView.removeAllViews();
        imgViews.clear();

        if(images != null && images.size() + preNetImageCount > 0){
            for(int i = 0; i< images.size() + preNetImageCount; i++){
                final ImageView imgView = imageViewFromImgViewsCache(i);

                contentView.addView(imgView);
                imgViews.add(imgView);

                imgView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener != null){
                            int index = imgViews.indexOf(imgView);
                            onItemClickListener.onItemClick(null, imgView, index, index);
                        }
                    }
                });

                if (i < preNetImageCount){
                    continue;
                }

                if (cacheDir == null){
                    cacheDir = SDCardUtils.getSDCardPath() + "/SelectPhoto/";
                }
                String imgPath = images.get(i - preNetImageCount).getPath();
                String newPath = smallImgPath(i - preNetImageCount, cacheDir);

                compressImage(imgPath, newPath, new CompressImageUtil.CompressListener() {
                    @Override
                    public void onCompressSuccess(final String imgPath) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageDrawable(BitmapDrawable.createFromPath(imgPath));
                            }
                        });
                    }

                    @Override
                    public void onCompressFailed(String imgPath, String msg) {

                    }
                });
            }
        }
        imgViews.add(addImgView);
        contentView.addView(addImgView);
        contentView.addView(rightView);
        // 调整布局
        adjustViewsSize();
    }

    private ImageView imageViewFromImgViewsCache(int index){
        if(index >= imgViewsCache.size()){
            while(index >= imgViewsCache.size()){
                ImageView imgView = new ImageView(context);
                imgView.setScaleType(imageScaleType);
                imgViewsCache.add(imgView);
            }
        }
        return imgViewsCache.get(index);
    }

    private String smallImgPath(int index, String basePath){
        File file = new File(basePath);
        if (file.exists() == false){
            file.mkdir();
        }
        if(index >= smallImgPaths.size()){
            while(index >= smallImgPaths.size()){
                String path = basePath + "/" + UUID.randomUUID().toString() + ".png";
                smallImgPaths.add(path);
            }
        }
        return smallImgPaths.get(index);
    }

    private void adjustViewsSize(){

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
        params.width = getHeight() - this.getPaddingTop() - this.getPaddingBottom() - lineSpacing * 2;
        params.height = params.width;
        params.leftMargin = lineSpacing;
        params.topMargin = lineSpacing;
        params.bottomMargin = lineSpacing;
        if(imgViews != null && imgViews.size() > 0){
            for(int i = 0; i< imgViews.size(); i++){
                ImageView imgView = imgViews.get(i);
                imgView.setLayoutParams(params);
            }
        }

        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, 0);
        params1.width = lineSpacing;
        params1.height = params.width;
        rightView.setLayoutParams(params1);

        addImgView.setPadding(addButtonPadding, addButtonPadding, addButtonPadding, addButtonPadding);
    }

    public void removeImageAt(int index){
        if(index >= preNetImageCount && index < imgViews.size()){

            images.remove(index - preNetImageCount);
            smallImgPaths.remove(index - preNetImageCount);
            contentView.removeViewAt(index);
            imgViews.remove(index);
        }
    }

    public void removeAllLocalImage(){
        images.clear();
    }

    public void addImages(ArrayList<TImage> images){
        this.images.addAll(images);
        resetImages();
    }

    public void addImage(TImage image){
        int index = images == null ? 0 : images.size();
        addImageAt(image, index);
    }

    public void addImageAt(TImage image, int index){
        if(images == null){
            images = new ArrayList<>();
            images.add(image);
        }
        else{
            images.add(index, image);
        }
        resetImages();
    }

    public void clearImageCache(){
        imgViewsCache.clear();
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = (int) (lineSpacing * ScreenUtils.density(context));
    }

    public int getAddButtonPadding() {
        return addButtonPadding;
    }

    public void setAddButtonPadding(int addButtonPadding) {
        this.addButtonPadding = (int) (addButtonPadding * ScreenUtils.density(context));
    }

    public ImageView.ScaleType getImageScaleType() {
        return imageScaleType;
    }

    public int getPreNetImageCount() {
        return preNetImageCount;
    }

    public void setPreNetImageCount(int preNetImageCount) {
        this.preNetImageCount = preNetImageCount;

        resetImages();
    }

    public void setPreNetImageCountOnly(int preNetImageCount) {
        this.preNetImageCount = preNetImageCount;
    }

    public void setImageScaleType(ImageView.ScaleType imageScaleType) {
        this.imageScaleType = imageScaleType;

        for(int i = 0;i < imgViewsCache.size(); i++){
            ImageView imgView = imgViewsCache.get(i);
            imgView.setScaleType(imageScaleType);
        }
    }

    public ArrayList<String> getSmallImgPaths() {
        return smallImgPaths;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
