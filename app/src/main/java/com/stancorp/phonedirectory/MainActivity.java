package com.stancorp.phonedirectory;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.stancorp.phonedirectory.Adapters.NamesRecyclerAdapter;
import com.stancorp.phonedirectory.AddActivity.AddActivity;
import com.stancorp.phonedirectory.Classes.ContactDetails;
import com.stancorp.phonedirectory.Classes.Gfunc;
import com.stancorp.phonedirectory.Classes.Name;
import com.stancorp.phonedirectory.Classes.PhoneNumber;
import com.stancorp.phonedirectory.datastructures.Trie;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import me.zhanghai.android.fastscroll.FastScrollerBuilder;

import static com.stancorp.phonedirectory.BaseActivity.contactTree;

public class MainActivity extends AppCompatActivity implements NamesRecyclerAdapter.OnNoteListner {

    EditText NamesSearch;
    Trie trie;
    TextInputLayout nameInput;
    FloatingActionButton addButton;
    ActivityResultLauncher<Intent> intentActivityResultLauncher;
    Gfunc gfunc;
    RelativeLayout emptyView;

    FastScrollRecyclerView recyclerView;
    NamesRecyclerAdapter namesRecyclerAdapter;
    ArrayList<Pair<Boolean, String>> stringArrayList;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gfunc = new Gfunc();

        NamesSearch = findViewById(R.id.NameSearch);
        NamesSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                if (query.length() != 0 && !query.toString().matches("[a-zA-Z]+")) {
                    NamesSearch.setError("Names only contain letters a-z");
                    return;
                }
                addNamesTosearchList(editable.toString().toLowerCase());
            }
        });
        trie = new Trie();

        emptyView = findViewById(R.id.EmptyView);

        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            updateContacts(result);
                        }
                    }
                });

        addButton = findViewById(R.id.fabAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                intentActivityResultLauncher.launch(intent);
            }
        });

        recyclerView = findViewById(R.id.ContactsList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        stringArrayList = new ArrayList<>();
        namesRecyclerAdapter = new NamesRecyclerAdapter(getApplicationContext(), stringArrayList, this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(namesRecyclerAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        addContact();
    }

    private void addContact() {
        PhoneNumber phoneNumber = new PhoneNumber(12313213, "mobile");
        ArrayList<PhoneNumber> phoneList = new ArrayList<>();
        ArrayList<Name> names = new ArrayList<>();
        names.add(new Name("abijith", "nair"));
        names.add(new Name("adams", "baker"));
        names.add(new Name("audrey", "deirdre"));
        names.add(new Name("dorothy", "ella"));
        names.add(new Name("bernadette", "claire"));
        names.add(new Name("emily", "felicity"));
        names.add(new Name("irene", "grace"));
        names.add(new Name("abigail", "jasmine"));
        names.add(new Name("jennifer", "karen"));
        names.add(new Name("kimberly", "katherine"));
        names.add(new Name("lauren", "lillian"));
        names.add(new Name("katherine", "madeleine"));
        names.add(new Name("michelle", "mary"));
        names.add(new Name("ruth", "sally"));
        names.add(new Name("samantha", "una"));
        names.add(new Name("sarah", "tracey"));
        names.add(new Name("wendy", "zoe"));
        names.add(new Name("yvonne", "pippa"));
        names.add(new Name("penelope", "natalie"));
        names.add(new Name("mary", "julia"));
        phoneList.add(phoneNumber);
        for (int i = 0; i < 20; i++) {
            ContactDetails contactDetails = new ContactDetails(names.get(i), phoneList);
            contactTree.insert(contactDetails);
            trie.insert(convertNameToStr(contactDetails.getName()));
        }
        stringArrayList.addAll(contactTree.fetchNames());
        namesRecyclerAdapter.updateDataSet(stringArrayList);
        emptyView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public static Name convertStrToName(String string) {
        string = string.toLowerCase();
        String[] strlist = string.split(" ");
        String firstName = strlist[0];
        String lastName = "";
        if (strlist.length == 2)
            lastName = strlist[1];
        return new Name(firstName, lastName);
    }

    public static String convertNameToStr(Name name) {
        String str = "";
        str += name.getFirstName();
        if (name.getLastName().length() > 0)
            str += ' ' + name.getLastName();
        return str;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            Pair<Boolean, String> pair = stringArrayList.get(position);
            if (pair.first)
                return 0;
            return super.getSwipeDirs(recyclerView, viewHolder);
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    String name = stringArrayList.get(position).second.toLowerCase();
                    contactTree.remove(convertStrToName(name));
                    stringArrayList.remove(position);
                    if (stringArrayList.get(position - 1).first
                            && (position == stringArrayList.size() || stringArrayList.get(position).first))
                        stringArrayList.remove(position - 1);
                    trie.remove(name);
                    namesRecyclerAdapter.notifyDataSetChanged();
                    if (stringArrayList.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.Red))
                    .addActionIcon(R.drawable.ic_baseline_delete_forever_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void updateContacts(ActivityResult result) {
        Intent data = result.getData();
        ContactDetails contactDetails = null;
        ContactDetails oldContactDetails = null;
        int mode = 0;
        if (data != null) {
            contactDetails = (ContactDetails) data.getSerializableExtra("contactDetails");
            mode = data.getIntExtra("mode", 0);
            oldContactDetails = (ContactDetails) data.getSerializableExtra("oldData");
        }
        if (mode == 0 || mode == 2) {
            if (mode == 2) {
                contactTree.remove(oldContactDetails.getName());
                trie.remove(convertNameToStr(oldContactDetails.getName()));
            }

            contactTree.insert(contactDetails);
            stringArrayList = contactTree.fetchNames();
            namesRecyclerAdapter.updateDataSet(stringArrayList);
            trie.insert(convertNameToStr(contactDetails.getName()));
            if (stringArrayList.size() == 2) {
                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        } else {
            contactTree.updateContact(contactDetails);
        }
    }

    private void addNamesTosearchList(String str) {
        if (str.length() == 0) {
            stringArrayList = contactTree.fetchNames();
        } else {
            stringArrayList = trie.autocomplete(str);
        }
        namesRecyclerAdapter.updateDataSet(stringArrayList);
    }

    @Override
    public void OnNoteClick(String string, int position) {
        ContactDetails contactDetails = contactTree.retrieveItem(convertStrToName(string));
        if (contactDetails == null)
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), AddActivity.class);
        intent.putExtra("contactDetail", contactDetails);
        intentActivityResultLauncher.launch(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}
