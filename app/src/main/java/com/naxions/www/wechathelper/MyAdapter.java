package com.naxions.www.wechathelper;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
/**
 * @Author: zengke
 * @Date: 2018.12
 *
 */
public class MyAdapter extends BaseAdapter {

	private Context mContext;

	private List<DataBean> mDatas;

	private LayoutInflater mInflater;

//	public boolean flage = false;

	public MyAdapter(Context mContext, List<DataBean> mDatas) {
		this.mContext = mContext;
		this.mDatas = mDatas;

		mInflater = LayoutInflater.from(this.mContext);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// 条目布局
			convertView = mInflater.inflate(R.layout.layout_listadata, null);

			holder.checkboxOperateData = convertView
					.findViewById(R.id.checkbox_operate_data);
			holder.textDesc = convertView
					.findViewById(R.id.text_desc);
			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		final DataBean dataBean = mDatas.get(position);

		if (dataBean != null) {
			holder.textDesc.setText(dataBean.desc);
			holder.checkboxOperateData.setChecked(dataBean.isCheck);
			//注意这里设置的不是onCheckedChangListener，还是值得思考一下的
			holder.checkboxOperateData.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (dataBean.isCheck) {
						dataBean.isCheck = false;
					} else {
						dataBean.isCheck = true;
					}
				}
			});

		}

		return convertView;
	}

	class ViewHolder {
		public CheckBox checkboxOperateData;
		public TextView textDesc;

	}
}