package cn.jestar.ffw.groups;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.jestar.ffw.R;
import cn.jestar.ffw.StringUtils;
import cn.jestar.ffw.monster.Monster;
import cn.jestar.ffw.monster.MonsterGroup;

/**
 * Created by 花京院 on 2019/3/24.
 */

public class MonsterGroupAdapter extends RecyclerView.Adapter<MonsterGroupAdapter.MonsterGroupHolder> {
    private List<MonsterGroup> mGroups = new ArrayList<>();
    private OnItemChildClickListener mListener;

    public MonsterGroupAdapter(List<MonsterGroup> groups) {
        mGroups.addAll(groups);
    }

    public MonsterGroupAdapter setListener(OnItemChildClickListener listener) {
        mListener = listener;
        return this;
    }

    public void setList(Collection<MonsterGroup> list) {
        mGroups.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonsterGroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_monster_group, parent, false);
        MonsterGroupHolder holder = new MonsterGroupHolder(view);
        for (int i = 0; i < viewType; i++) {
            inflater.inflate(R.layout.item_group, holder.getContainer());
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonsterGroupHolder holder, int position) {
        List<Monster> list = mGroups.get(position).getList();
        holder.setData(list);
    }

    @Override
    public int getItemViewType(int position) {
        return mGroups.get(position).getList().size();
    }

    @Override
    public long getItemId(int position) {
        return mGroups.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mGroups.size();
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(int position, int index);
    }

    class MonsterGroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewGroup mContainer;
        private String nullText = "无";

        public MonsterGroupHolder(View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.item_container);
        }

        public ViewGroup getContainer() {
            return mContainer;
        }

        public MonsterGroupHolder setContainer(ViewGroup container) {
            mContainer = container;
            return this;
        }

        public void setData(List<Monster> list) {
            int size = list.size();
            Context context = itemView.getContext();
            Resources resources = context.getResources();
            String name = resources.getString(R.string.monster_name_temp);
            String locationTemp = resources.getString(R.string.monster_location_temp);
            for (int i = 0; i < size; i++) {
                View view = mContainer.getChildAt(i);
                view.setTag(i);
                view.setOnClickListener(this);
                Monster monster = list.get(i);
                boolean isWhite = monster.getTransType() % 2 == 0;
                view.setBackgroundColor(resources.getColor(isWhite ? R.color.white : R.color.colorPrimaryLight));
                ImageView iv = view.findViewById(R.id.iv_header);
                Glide.with(itemView.getContext()).load(monster.getUrl()).into(iv);
                String catchCondition = monster.getCatchCondition();
                String shadowStone = monster.getShadowStone();
                String text = String.format(name, monster.getName(), getString(shadowStone));
                ((TextView) view.findViewById(R.id.tv_name)).setText(text);
                List<String> locationList = monster.getLocation();
                text = String.format(locationTemp, getString(locationList), getString(catchCondition));
                ((TextView) view.findViewById(R.id.tv_location)).setText(text);
            }
        }

        private String getString(CharSequence text) {
            return (String) StringUtils.getString(text);
        }

        private String getString(List<? extends Object> list) {
            return StringUtils.getString(list);
        }

        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            int position = getLayoutPosition();
            if (mListener != null) {
                mListener.onItemChildClick(position, index);
            }
        }
    }
}
