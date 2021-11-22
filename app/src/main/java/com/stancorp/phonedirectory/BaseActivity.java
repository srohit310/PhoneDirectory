package com.stancorp.phonedirectory;

import android.app.Activity;

import com.stancorp.phonedirectory.datastructures.BTree;

public class BaseActivity extends Activity {
    public static BTree contactTree = new BTree(2);
}
