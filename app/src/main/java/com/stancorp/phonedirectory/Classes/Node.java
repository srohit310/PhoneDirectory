package com.stancorp.phonedirectory.Classes;

import java.util.ArrayList;

public class Node {

    public boolean is_leaf;
    public int keysize;
    public int childsize;
    public int t;
    public ContactDetails[] keys;
    public Node[] children;

    public Node(int t) {
        this.t = t;
        this.is_leaf = false;
        this.keysize = 0;
        this.childsize = 0;
        this.keys = new ContactDetails[2 * t - 1];
        this.children = new Node[2 * t];
    }

    public static int compareNames(Name details1, Name details2) {
        int diff = details1.getFirstName().compareTo(details2.getFirstName());
        if (diff == 0)
            diff = details1.getLastName().compareTo(details2.getLastName());
        return diff;
    }

    public ContactDetails search(Name key){
        int i = 0;
        while (i < keysize && compareNames(key,keys[i].getName())>0)
            i++;
        if (i != keysize && compareNames(keys[i].getName(),key)==0)
            return keys[i];
        if (is_leaf)
            return null;
        return children[i].search(key);
    }

    public void insertNotFull(ContactDetails key) {
        int i = keysize - 1;
        if (is_leaf) {
            while (i >= 0 && compareNames(keys[i].getName(), key.getName()) > 0) {
                keys[i + 1] = keys[i];
                i--;
            }
            keys[i + 1] = key;
            keysize += 1;
        } else {
            while (i >= 0 && compareNames(keys[i].getName(), key.getName()) > 0)
                i--;
            if (children[i + 1].keysize == 2 * t - 1) {
                splitChild(i + 1, children[i + 1]);
                if (compareNames(keys[i + 1].getName(), key.getName()) < 0) i++;
            }
            children[i + 1].insertNotFull(key);
        }
    }

    public void splitChild(int i, Node child) {
        Node z = new Node(child.t);
        z.is_leaf = child.is_leaf;
        z.keysize = t - 1;

        for (int j = 0; j < t - 1; j++)
            z.keys[j] = child.keys[j + t];
        if (!child.is_leaf) {
            for (int j = 0; j < t; j++)
                z.children[j] = child.children[j + t];
        }
        child.keysize = t - 1;

        for (int j = keysize; j >= i + 1; j--)
            children[j + 1] = children[j];
        children[i + 1] = z;

        for (int j = keysize - 1; j >= i; j--)
            keys[j + 1] = keys[j];
        keys[i] = child.keys[t - 1];

        keysize++;
    }

    public int findKey(Name key) {
        int idx = 0;
        while (idx < keysize && compareNames(keys[idx].getName(), key) < 0) ++idx;
        return idx;
    }

    public void remove(Name key) {

        int idx = findKey(key);
        if (idx < keysize && compareNames(keys[idx].getName(), key) == 0) {
            if (is_leaf) removeFromLeaf(idx);
            else removeFromNonLeaf(idx);
        } else {
            if (is_leaf) return;
            boolean flag = idx == keysize;

            if (children[idx].keysize < t)
                fill(idx);

            if (flag && idx > keysize)
                children[idx - 1].remove(key);
            else children[idx].remove(key);
        }
    }

    private void fill(int idx) {

        if (idx != 0 && children[idx - 1].keysize >= t)
            borrowFromPrev(idx);
        else if (idx != keysize && children[idx + 1].keysize >= t)
            borrowFromNext(idx);
        else {
            if (idx != keysize)
                merge(idx);
            else merge(idx - 1);
        }
    }

    private void borrowFromNext(int idx) {

        Node child = children[idx];
        Node sibling = children[idx + 1];

        child.keys[child.keysize] = keys[idx];

        if (!child.is_leaf)
            child.children[child.keysize + 1] = sibling.children[0];

        keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.keysize; i++)
            sibling.keys[i - 1] = sibling.keys[i];

        if (!sibling.is_leaf) {
            for (int i = 1; i <= sibling.keysize; i++)
                sibling.children[i - 1] = sibling.children[i];
        }
        child.keysize++;
        sibling.keysize--;
    }

    private void borrowFromPrev(int idx) {

        Node child = children[idx];
        Node sibling = children[idx - 1];

        for (int i = child.keysize - 1; i >= 0; i--)
            child.keys[i + 1] = child.keys[i];

        if (!child.is_leaf) {
            for (int i = child.keysize; i >= 0; i--)
                child.children[i + 1] = child.children[i];
        }

        child.keys[0] = keys[idx - 1];
        if (!child.is_leaf)
            child.children[0] = sibling.children[sibling.keysize];

        keys[idx - 1] = sibling.keys[sibling.keysize - 1];
        child.keysize++;
        sibling.keysize--;
    }

    private void removeFromNonLeaf(int idx) {
        ContactDetails key = keys[idx];

        if (children[idx].keysize >= t) {
            ContactDetails pred = getPred(idx);
            keys[idx] = pred;
            children[idx].remove(pred.getName());
        } else if (children[idx + 1].keysize >= t) {
            ContactDetails succ = getSucc(idx);
            keys[idx] = succ;
            children[idx + 1].remove(succ.getName());
        } else {
            merge(idx);
            children[idx].remove(key.getName());
        }
    }

    private void merge(int idx) {
        Node child = children[idx];
        Node sibling = children[idx + 1];

        child.keys[t - 1] = keys[idx];

        for (int i = 0; i < sibling.keysize; i++)
            child.keys[i + t] = sibling.keys[i];

        if (!child.is_leaf) {
            for (int i = 0; i <= sibling.keysize; i++)
                child.children[i + t] = sibling.children[i];
        }

        for (int i = idx + 1; i < keysize; i++)
            keys[i - 1] = keys[i];

        for (int i = idx + 2; i <= keysize; i++)
            children[i - 1] = children[i];

        child.keysize += sibling.keysize + 1;
        keysize--;
    }

    private ContactDetails getSucc(int idx) {
        Node cur = children[idx];
        while (!cur.is_leaf)
            cur = cur.children[0];
        return cur.keys[0];
    }

    private ContactDetails getPred(int idx) {
        Node cur = children[idx];
        while (!cur.is_leaf)
            cur = cur.children[cur.keysize];
        return cur.keys[cur.keysize - 1];
    }

    private void removeFromLeaf(int idx) {
        for (int i = idx + 1; i < keysize; i++)
            keys[i - 1] = keys[i];
        keysize--;
    }

    public void update(ContactDetails contactDetails) {
        int i = 0;
        while (i < keysize && compareNames(contactDetails.getName(),keys[i].getName())>0)
            i++;
        if (compareNames(keys[i].getName(),contactDetails.getName())==0) {
            keys[i] = contactDetails;
            return;
        }
        children[i].update(contactDetails);
    }
}