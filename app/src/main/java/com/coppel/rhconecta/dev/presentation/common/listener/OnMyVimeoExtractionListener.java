package com.coppel.rhconecta.dev.presentation.common.listener;

import uk.breedrapps.vimeoextractor.OnVimeoExtractionListener;
import uk.breedrapps.vimeoextractor.VimeoVideo;

public class OnMyVimeoExtractionListener implements OnVimeoExtractionListener {

    /* */
    private OnVimeoExtractionCompleted onVimeoExtractionCompleted;

    /**
     *
     *
     */
    public OnMyVimeoExtractionListener(OnVimeoExtractionCompleted onVimeoExtractionCompleted) {
        this.onVimeoExtractionCompleted = onVimeoExtractionCompleted;
    }

    @Override
    public final void onSuccess(VimeoVideo video) {
        onVimeoExtractionCompleted.onCompleted(null, video);
    }

    @Override
    public final void onFailure(Throwable throwable) {
        onVimeoExtractionCompleted.onCompleted(throwable, null);
    }

    /**
     *
     *
     */
    public interface OnVimeoExtractionCompleted {

        /**
         *
         *
         */
        void onCompleted(Throwable throwable, VimeoVideo video);

    }
}
