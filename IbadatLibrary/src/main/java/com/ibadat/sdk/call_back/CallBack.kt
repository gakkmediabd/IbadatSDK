package com.ibadat.sdk.call_back

import com.ibadat.sdk.data.model.CommonDuaAndHadithModel

interface CallBack {
    fun onItemClick(
        mDuaList: MutableList<CommonDuaAndHadithModel>,
        selectedIndex: Int
    )
}