package com.neilmarietta.booksearch.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.neilmarietta.booksearch.R;
import com.neilmarietta.booksearch.entity.Book;
import com.neilmarietta.booksearch.presentation.view.util.BookViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, Book book);
    }

    private OnItemClickListener mListener;

    private final LayoutInflater mLayoutInflater;

    private List<Book> mBooks = new ArrayList<>();

    public BookAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear() {
        int size = mBooks.size();
        mBooks = new ArrayList<>();
        notifyItemRangeRemoved(0, size);
    }

    public void addBooks(List<Book> books) {
        for (Book book : books)
            addBook(book);
    }

    public void addBook(Book book) {
        if (mBooks == null)
            mBooks = new ArrayList<>();
        mBooks.add(book);
        // RecyclerView uses a DefaultItemAnimator by default.
        notifyItemInserted(mBooks.size() - 1);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ArrayList<Book> getBooksCopy() {
        return new ArrayList<>(mBooks);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mBooks != null) ? mBooks.size() : 0;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.cardview_book, parent, false);
        // SimpleDraweeView' fresco:roundedCornerRadius take care of that
        ((CardView) view).setPreventCornerOverlap(false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final Book book = mBooks.get(adapterPosition);
        holder.title.setText(book.volumeInfo().title());
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onItemClick(holder.cover, book);
            }
        });
        BookViewUtil.setVolumeCoverSimpleDraweeView(book.volumeInfo(), holder.cover);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title) TextView title;
        @Bind(R.id.iv_cover) SimpleDraweeView cover;

        public BookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}