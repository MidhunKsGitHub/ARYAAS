package com.midhun.hawkssolutions.aryaas.Util;

public class RecyclerViewAutoScroll {

//    final int duration = 1000;
//    final int pixelsToMove = 100;
//    final Handler mHandler = new Handler(Looper.getMainLooper());
//    final Runnable SCROLLING_RUNNABLE = new Runnable() {
//
//        @Override
//        public void run() {
//            recyclerView1.smoothScrollBy(pixelsToMove, 0);
//            mHandler.postDelayed(this, duration);
//        }
//    };
//
//        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
//            if(lastItem == layoutManager.getItemCount()-1){
//                mHandler.removeCallbacks(SCROLLING_RUNNABLE);
//                Handler postHandler = new Handler();
//                postHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        recyclerView1.setAdapter(null);
//                        recyclerView1.setAdapter(homeBannerAdapter);
//                        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
//                    }
//                }, 2000);
//            }
//        }
//    });
//        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
}
