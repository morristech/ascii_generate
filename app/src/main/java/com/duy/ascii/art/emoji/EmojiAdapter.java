/*
 *     Copyright (C) 2018 Tran Le Duy
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.duy.ascii.art.emoji;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duy.ascii.art.R;
import com.duy.ascii.art.clipboard.ClipboardManagerCompat;
import com.duy.ascii.art.clipboard.ClipboardManagerCompatFactory;
import com.duy.ascii.art.emoji.model.EmojiCategory;
import com.duy.ascii.art.emoji.model.EmojiItem;


/**
 * Created by Duy on 06-May-17.
 */

class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder> {
    protected LayoutInflater mInflater;
    private Context mContext;
    private ClipboardManagerCompat mClipboardManagerCompat;
    private EmojiCategory mCategory;
    @Nullable
    private EmojiClickListener mListener;

    EmojiAdapter(@NonNull Context context, EmojiCategory category) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mClipboardManagerCompat = ClipboardManagerCompatFactory.getManager(context);
        this.mCategory = category;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_emoji, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final EmojiItem emojiItem = mCategory.get(position);
        holder.txtContent.setText(mCategory.get(position).getEmojiChar());
        holder.txtContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    mListener.onLongClick(holder.txtContent, emojiItem);
                }
                return true;
            }
        });
        holder.txtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(emojiItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public void setListener(@Nullable EmojiClickListener listener) {
        this.mListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.text);
        }

    }
}

