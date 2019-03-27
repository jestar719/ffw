package cn.jestar.ffw.groups;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.jestar.ffw.R;
import cn.jestar.ffw.monster.Monster;
import cn.jestar.ffw.monster.MonsterGroup;

/**
 * Created by 花京院 on 2019/3/24.
 */

public class MonsterGroupAdapter extends RecyclerView.Adapter<MonsterGroupAdapter.MonsterGroupHolder> {
    private List<MonsterGroup> mGroups = new ArrayList<>();


    public MonsterGroupAdapter(List<MonsterGroup> groups) {
        mGroups.addAll(groups);
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
            inflater.inflate(R.layout.layout_monster_item, holder.getContainer());
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

    class MonsterGroupHolder extends RecyclerView.ViewHolder {
        private ViewGroup mContainer;

        public MonsterGroupHolder(View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.item_container);
        }

        public ViewGroup getContainer() {
            return mContainer;
        }

        public void setData(List<Monster> list) {
            int size = list.size();
            Context context = itemView.getContext();
            String packageName = context.getPackageName();
            Resources resources = context.getResources();
            String name = resources.getString(R.string.monster_name_temp);
            String location = resources.getString(R.string.monster_location_temp);
            for (int i = 0; i < size; i++) {
                View view = mContainer.getChildAt(i);
                Monster monster = list.get(i);
                boolean isWhite = monster.getTransType() % 2 == 0;
                view.setBackgroundColor(resources.getColor(isWhite ? R.color.white : R.color.colorPrimaryLight));
                ImageView iv = view.findViewById(R.id.iv_header);
                int id = monster.getId();
                int mipmap = resources.getIdentifier("icon_" + id, "mipmap", packageName);
                Log.e("id=", id + "");
                iv.setImageResource(mipmap);
                String catchCondition = monster.getCatchCondition();
                String shadowStone = monster.getShadowStone();
                String text = String.format(name, monster.getName(), getString(shadowStone));
                ((TextView) view.findViewById(R.id.tv_name)).setText(text);
                List<String> locationList = monster.getLocation();
                String locationText = locationList.isEmpty() ? "无" : locationList.toString();
                text = String.format(location, locationText, getString(catchCondition));
                ((TextView) view.findViewById(R.id.tv_location)).setText(text);
            }
        }

        private String getString(String text) {
            return text == null ? "无" : text;
        }
    }
}
