package com.stancorp.phonedirectory.datastructures;

import android.util.Log;
import android.util.Pair;

import com.stancorp.phonedirectory.Classes.ContactDetails;
import com.stancorp.phonedirectory.Classes.Gfunc;
import com.stancorp.phonedirectory.Classes.Name;
import com.stancorp.phonedirectory.Classes.Node;

import java.util.ArrayList;
import java.util.Stack;

public class BTree {
    Node root;
    int t;
    Gfunc gfunc;

    public BTree(int t) {
        this.root = null;
        this.t = t;
        gfunc = new Gfunc();
    }

    public ArrayList<Pair<Boolean, String>> fetchNames() {
        ArrayList<Pair<Boolean, String>> result = new ArrayList<>();
        if(root!=null){
            traverse(root,result);
        }
        return result;
    }

    private void traverse(Node ptr, ArrayList<Pair<Boolean, String>> result) {
        int i;
        for(i=0;i<ptr.keysize;i++){
            if(!ptr.is_leaf)
                traverse(ptr.children[i],result);
            String Name = gfunc.capitalize(ptr.keys[i].getName().getFirstName())+" "
                    +gfunc.capitalize(ptr.keys[i].getName().getLastName());
            if(result.size() == 0){
                Pair<Boolean,String> pair = new Pair<>(true,String.valueOf(Name.charAt(0)));
                result.add(pair);
            }else if(Name.charAt(0) != result.get(result.size()-1).second.charAt(0)){
                Pair<Boolean,String> pair = new Pair<>(true,String.valueOf(Name.charAt(0)));
                result.add(pair);
            }
            Pair<Boolean,String> pair = new Pair<>(false,Name);
            result.add(pair);
        }
        if(!ptr.is_leaf)
            traverse(ptr.children[i],result);
    }

    public void insert(ContactDetails key) {
        if (root == null) {
            root = new Node(t);
            root.is_leaf = true;
            root.keys[0] = key;
            root.keysize = 1;
        } else {
            if (root.keysize == 2 * t - 1) {
                Node s = new Node(t);
                s.children[0] = root;
                s.splitChild(0, root);
                int i = 0;
                if (Node.compareNames(s.keys[0].getName(), key.getName()) < 0)
                    i++;
                s.children[i].insertNotFull(key);
                root = s;
            } else root.insertNotFull(key);
        }
    }

    public ContactDetails retrieveItem(Name key){
        ContactDetails contactDetails = null;
        if(root!=null)
            contactDetails = root.search(key);
        String s = "false";
        if(contactDetails == null) s = "true";
        Log.i("ContactDetailsnull",s);
        return contactDetails;
    }

    public void updateContact(ContactDetails contactDetails){
        if(root!=null)
            root.update(contactDetails);
    }

    public void remove(Name key) {
        if (root == null) return;

        root.remove(key);

        if (root.keysize == 0) {
            if (root.is_leaf) root = null;
            else root = root.children[0];
        }
    }
}
