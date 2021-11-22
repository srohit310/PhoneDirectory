package com.stancorp.phonedirectory.datastructures;

import android.util.Pair;

import com.stancorp.phonedirectory.Classes.Gfunc;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class Trie {
    static final int ALPHABET_SIZE = 27;
    Gfunc gfunc;

    private class TrieNode {
        TrieNode[] children = new TrieNode[ALPHABET_SIZE];
        boolean isEndOfWord;
        TrieNode() {
            isEndOfWord = false;
            for (int i = 0; i < ALPHABET_SIZE; i++)
                children[i] = null;
        }
    }

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
        gfunc = new Gfunc();
    }

    public void insert(String key) {
        TrieNode pCrawl = root;

        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (key.charAt(i) == ' ') index = 26;
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();

            pCrawl = pCrawl.children[index];
        }

        pCrawl.isEndOfWord = true;
    }

    private boolean isEmpty(TrieNode root)
    {
        for (int i = 0; i < ALPHABET_SIZE; i++)
            if (root.children[i] != null)
                return false;
        return true;
    }

    private TrieNode remove(TrieNode root, String key, int depth)
    {
        if (root == null)
            return null;

        if (depth == key.length()) {
            if (root.isEndOfWord)
                root.isEndOfWord = false;
            if (isEmpty(root)) {
                root = null;
            }
            return root;
        }

        int index = key.charAt(depth) - 'a';
        if (key.charAt(depth) == ' ') index = 26;
        root.children[index] =
                remove(root.children[index], key, depth + 1);
        if (isEmpty(root) && root.isEndOfWord == false){
            root = null;
        }

        return root;
    }

    public void remove(String key){
        remove(root,key,0);
    }

    public void dfs(ArrayList<Pair<Boolean, String>> result, TrieNode Node, String s) {
        if (Node.isEndOfWord) result.add(new Pair<>(false,gfunc.capitalize(s)));
        for (int i = 0; i < 27; i++) {
            if (Node.children[i] != null) {
                char ch = (char) ('a' + i);
                if (i == 26) ch = ' ';
                dfs(result, Node.children[i], s + ch);
            }
        }
    }

    public ArrayList<Pair<Boolean, String>> autocomplete(String key) {
        TrieNode pCrawl = root;
        ArrayList<Pair<Boolean, String>> result = new ArrayList<>();
        for (int i = 0; i < key.length(); i++) {
            int index = key.charAt(i) - 'a';
            if (key.charAt(i) == ' ') index = 26;
            if (pCrawl.children[index] == null)
                return result;
            pCrawl = pCrawl.children[index];
        }
        dfs(result, pCrawl, key);
        return result;
    }
}
