package com.neilmarietta.booksearch.presentation.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.contract.BookContract;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.entity.VolumeInfo;
import com.neilmarietta.booksearch.presentation.presenter.BookPresenter;
import com.neilmarietta.booksearch.presentation.view.activity.BookActivity;
import com.neilmarietta.booksearch.presentation.view.util.BookViewUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookFragment extends Fragment implements BookContract.View {

    private BookPresenter mPresenter;

    @Bind(R.id.bt_preview) TextView mPreviewButton;
    @Bind(R.id.tv_title) TextView mTitleTextView;
    @Bind(R.id.tv_description) TextView mDescriptionTextView;
    @Bind(R.id.iv_cover) SimpleDraweeView mCoverView;

    public BookFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new BookPresenter(
                getActivity().getApplicationContext(),
                getActivity().getIntent().getExtras().<Book>getParcelable(BookActivity.EXTRA_BOOK));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save Presenter state (will be restored onViewCreated -> attachView)
        mPresenter.onSaveInstanceState(outState);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void renderBook(final Book book) {
        final VolumeInfo volumeInfo = book.getVolumeInfo();
        if (volumeInfo == null) return;

        mTitleTextView.setText(volumeInfo.getTitle());
        mDescriptionTextView.setText(volumeInfo.getDescription());
        BookViewUtil.setVolumeCoverSimpleDraweeView(volumeInfo, mCoverView);
        mPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onPreviewButtonClicked();
            }
        });
    }
}
