package com.domin.demo01.drawerLayout;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.domin.demo01.BaseFragment;
import com.domin.demo01.R;
import com.domin.demo01.bottomsheet.BottomSheetActivity;
import com.domin.demo01.design.CoordinatorActivity;
import com.domin.demo01.recylerview.Status;
import com.domin.demo01.recylerview.simple.LoadMoreFooterView;
import com.domin.demo01.recylerview.simple.MyDecoration;
import com.domin.demo01.utils.ToastUtils;
import com.lvr.library.adapter.CommonAdapter;
import com.lvr.library.adapter.MultiItemCommonAdapter;
import com.lvr.library.anims.animators.LandingAnimator;
import com.lvr.library.holder.BaseViewHolder;
import com.lvr.library.recyclerview.HRecyclerView;
import com.lvr.library.recyclerview.OnLoadMoreListener;
import com.lvr.library.recyclerview.OnRefreshListener;
import com.lvr.library.support.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DummyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DummyFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private TextView dummy_tv;
    HRecyclerView recyclerView;
 //   private ArrayList<String> mData = new ArrayList<>();;
    private View view_root;

    private List<Status> mDatas;
    private final int NORMAL_TYPE = 1;
    private final int ANOTHER_TYPE = 2;
    private final int LOAD_COMPELERED=1001;
    private Random mRandom = new Random();
    private int[] type = new int[2];
    private MultiItemTypeSupport<Status> mItemTypeSupport;
    private MultiItemCommonAdapter<Status> mAdapter;
    private int n = 1;
    private LoadMoreFooterView mLoadMoreFooterView;
    private int count = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    refresh(1);
                    recyclerView.setRefreshing(false);
                    break;
                case 6:
                    //                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                    break;
                case 5:
                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR);
                    break;
                case LOAD_COMPELERED:
                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                    break;
                default:
                    if(loadMore(count++).size()==0)
                        mHandler.sendEmptyMessageDelayed(LOAD_COMPELERED,2000);
                    mDatas.addAll(loadMore(count++));
                    mAdapter.notifyItemInserted(mDatas.size()+1);
                    mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                    break;
            }
        }
    };
    public DummyFragment() {
        // Required empty public constructor
    }
    private void initData(int pager) {
/*        for (int i = 1; i < 50; i++) {
            mData.add("pager" + pager + " 第" + i + "个item");
        }*/
        type[0] = NORMAL_TYPE;
        type[1] = ANOTHER_TYPE;
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Status status = new Status();
            status.title = "data :" + pager;
            status.TYPE = type[getRandomInt()];
            mDatas.add(status);
        }

    }
    private void initView(View view)
    {
        recyclerView = (HRecyclerView)view. findViewById(R.id.recylerview);

        //设置RecycleView
/*        mAdapter = new RecylerViewAdapter(mData,getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new LandingAnimator());
        recyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.HORIZONTAL_LIST));
        mItemTypeSupport = new MultiItemTypeSupport<Status>() {
            @Override
            public int getLayoutId(int i) {
                if (i == NORMAL_TYPE) {
                    return R.layout.item_layout;
                } else {
                    return R.layout.quick_recylerview_item;
                }
            }

            @Override
            public int getItemViewType(int i, Status status) {
                int ty = mDatas.get(i).TYPE;
                if (ty == NORMAL_TYPE) {
                    return NORMAL_TYPE;
                }
                return ANOTHER_TYPE;
            }
        };
        mAdapter = new MultiItemCommonAdapter<Status>(getActivity(), mDatas, mItemTypeSupport) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, int i) {
                if (mDatas.get(i).TYPE == NORMAL_TYPE) {
                    baseViewHolder.setText(R.id.tv_info, mDatas.get(i).title + "NORMAL");
                } else {
                    baseViewHolder.setText(R.id.status_tv, mDatas.get(i).title + "ANOTHER");
                }
            }
        };
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                ToastUtils.showShortToast("点击");
            }
        });
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
        mLoadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();
        mLoadMoreFooterView.setOnRetryListener(new LoadMoreFooterView.OnRetryListener() {
            @Override
            public void onRetry(LoadMoreFooterView view) {
                ToastUtils.showShortToast("err");
                loadMore(count++);
                mDatas.addAll(loadMore(count++));
                mAdapter.notifyItemInserted(mDatas.size()+1);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DummyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DummyFragment newInstance(int param1, String param2) {
        DummyFragment fragment = new DummyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

/*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view_root =inflater.inflate(R.layout.fragment_dummy2, container, false);

     //
        return inflater.inflate(R.layout.fragment_dummy2, container, false);
    }*/

    private int getRandomInt() {
        return mRandom.nextInt(2);
    }
 /*   @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initView(view);
        //initData();
        initData(mParam1);
      //  initView(view);
    }*/

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dummy2;
    }

    @Override
    public void initViews(View view, Bundle saveInstanState) {
        recyclerView = (HRecyclerView)view. findViewById(R.id.recylerview);
    }

    @Override
    public void initListener() {
        recyclerView.setOnRefreshListener(this);
        recyclerView.setOnLoadMoreListener(this);
    }

    public void initData() {
        type[0] = NORMAL_TYPE;
        type[1] = ANOTHER_TYPE;
        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Status status = new Status();
            status.title = "data :" + mParam1;
            status.TYPE = type[getRandomInt()];
            mDatas.add(status);
        }
        //设置RecycleView
/*        mAdapter = new RecylerViewAdapter(mData,getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new LandingAnimator());
        recyclerView.addItemDecoration(new MyDecoration(getActivity(), MyDecoration.HORIZONTAL_LIST));
        mItemTypeSupport = new MultiItemTypeSupport<Status>() {
            @Override
            public int getLayoutId(int i) {
                if (i == NORMAL_TYPE) {
                    return R.layout.item_layout;
                } else {
                    return R.layout.quick_recylerview_item;
                }
            }

            @Override
            public int getItemViewType(int i, Status status) {
                int ty = mDatas.get(i).TYPE;
                if (ty == NORMAL_TYPE) {
                    return NORMAL_TYPE;
                }
                return ANOTHER_TYPE;
            }
        };
        mAdapter = new MultiItemCommonAdapter<Status>(getActivity(), mDatas, mItemTypeSupport) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, int i) {
                if (mDatas.get(i).TYPE == NORMAL_TYPE) {
                    baseViewHolder.setText(R.id.tv_info, mDatas.get(i).title + "NORMAL");
                } else {
                    baseViewHolder.setText(R.id.status_tv, mDatas.get(i).title + "ANOTHER");
                }
            }
        };
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if(viewHolder.getItemViewType()==NORMAL_TYPE){
                    //ToastUtils.showShortToast("点击");
                    Snackbar snackbar = Snackbar.make(view, "Click Here", Snackbar.LENGTH_SHORT).setAction("跳转", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), CoordinatorActivity.class));;
                        }
                    });
                    View mView = snackbar.getView();
                    mView.setBackgroundColor(Color.parseColor("#FA7199"));
                    TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                    text.setTextColor(Color.WHITE);
                    text.setTextSize(25);
                    snackbar.show();
                }else{
                    //ToastUtils.showShortToast("点击");
                    Snackbar snackbar = Snackbar.make(view, "Click Here", Snackbar.LENGTH_SHORT).setAction("跳转", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), BottomSheetActivity.class));;
                        }
                    });
                    View mView = snackbar.getView();
                    mView.setBackgroundColor(Color.parseColor("#FA7199"));
                    TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                    text.setTextColor(Color.WHITE);
                    text.setTextSize(25);
                    snackbar.show();
                }

            }
        });
        mLoadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();
        mLoadMoreFooterView.setOnRetryListener(new LoadMoreFooterView.OnRetryListener() {
            @Override
            public void onRetry(LoadMoreFooterView view) {
                ToastUtils.showShortToast("err");
                loadMore(count++);
                mDatas.addAll(loadMore(count++));
                mAdapter.notifyItemInserted(mDatas.size()+1);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
//        initData();
    }

    private List<Status> refresh(int i) {
        mDatas.clear();
        List<Status> more_list = new ArrayList<>();
        for (int m = 0; m < 20; m++) {
            Status status = new Status();
            status.TYPE = type[getRandomInt()];
            status.title = "refrsh" + i;
            more_list.add(status);
        }
        mDatas.addAll(more_list);
        mAdapter.notifyDataSetChanged();
        return more_list;
    }
    private List<Status> loadMore(int i) {
        List<Status> more_list = new ArrayList<>();
        for (int m = 0; m < 5; m++) {
            Status status = new Status();
            status.TYPE = type[getRandomInt()];
            status.title = "loadmore" + i;
            more_list.add(status);
        }
        return more_list;
    }
    @Override
    public void onLoadMore() {
        if (mLoadMoreFooterView.canLoadMore()) {
            mLoadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            Message message = Message.obtain();
            message.what = count++;
            mHandler.sendMessageDelayed(message, 2000);
        }
    }

    @Override
    public void onRefresh() {
        Message message = Message.obtain();
        message.what = 0;
        mHandler.sendMessageDelayed(message,2000);
    }
}
