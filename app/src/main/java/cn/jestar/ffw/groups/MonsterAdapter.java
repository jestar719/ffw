package cn.jestar.ffw.groups;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cn.jestar.ffw.R;
import cn.jestar.ffw.monster.Monster;

/**
 * Created by 花京院 on 2019/3/29.
 */

class MonsterAdapter extends RecyclerView.Adapter<MonsterAdapter.MonsterHeaderHolder> {
    List<Monster> mList;

    View.OnClickListener mListener;

    public MonsterAdapter setListener(View.OnClickListener listener) {
        mListener = listener;
        return this;
    }

    public MonsterAdapter setList(List<Monster> list) {
        mList = list;
        notifyDataSetChanged();
        return this;
    }

    @NonNull
    @Override
    public MonsterHeaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_monster, parent, false);
        view.setOnClickListener(mListener);
        MonsterHeaderHolder viewHolder = new MonsterHeaderHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonsterHeaderHolder holder, int position) {
        Monster monster = mList.get(position);
        CardView view = (CardView) holder.itemView;
        view.setTag(position);
        Context context = view.getContext();
        Glide.with(context).load(monster.getUrl()).into(holder.mIv);
        holder.mTv.setText(monster.getSize());
        holder.mTv.setTextColor(context.getResources().getColor(monster.getTransType() % 2 == 0 ? R.color.primaryText : R.color.colorAccent));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MonsterHeaderHolder extends RecyclerView.ViewHolder {

        final ImageView mIv;
        final TextView mTv;

        public MonsterHeaderHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv_header);
            mTv = itemView.findViewById(R.id.tv_size);
        }
    }
}
