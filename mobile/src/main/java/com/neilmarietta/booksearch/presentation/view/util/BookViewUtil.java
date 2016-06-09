package com.neilmarietta.booksearch.presentation.view.util;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.entity.ImageLinks;
import com.neilmarietta.booksearch.entity.VolumeInfo;

public class BookViewUtil {

    public static void setVolumeCoverSimpleDraweeView(VolumeInfo volumeInfo, SimpleDraweeView view) {
        ImageLinks imageLinks = volumeInfo.getImageLinks();

        if (imageLinks != null) {
            Uri small = Uri.parse(imageLinks.getSmall());
            Uri high = Uri.parse(imageLinks.getHigh());

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(small))
                    .setImageRequest(ImageRequest.fromUri(high))
                    .setOldController(view.getController())
                    .build();
            view.setController(controller);
        } else {
            view.setImageResource(R.drawable.no_cover_thumb);
        }
    }
}
