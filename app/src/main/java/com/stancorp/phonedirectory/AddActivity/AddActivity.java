package com.stancorp.phonedirectory.AddActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stancorp.phonedirectory.Adapters.PhonenoRecyclerAdapter;
import com.stancorp.phonedirectory.Classes.Address;
import com.stancorp.phonedirectory.Classes.ContactDetails;
import com.stancorp.phonedirectory.Classes.Gfunc;
import com.stancorp.phonedirectory.Classes.Name;
import com.stancorp.phonedirectory.Classes.PhoneNumber;
import com.stancorp.phonedirectory.MainActivity;
import com.stancorp.phonedirectory.R;

import java.util.ArrayList;

import static com.stancorp.phonedirectory.BaseActivity.contactTree;

public class AddActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView emailTextView;
    TextView addressTextView;
    TextView mainTitle;

    LinearLayout nameLayout;
    LinearLayout addressLayout;

    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText streetET;
    EditText cityET;
    EditText stateET;
    EditText countryET;
    EditText zipcodeET;
    EditText notesET;

    AlertDialog alertDialog;
    Button addPhoneNo;
    Button canceladdPhoneNo;
    EditText phoneNumberET;
    String phonenotype;

    Gfunc gfunc;
    ImageView upbutton;
    ImageButton addPhoneNumberButton;
    Button addUserButton;
    Button cancel;
    Spinner phonenoType;

    ArrayList<PhoneNumber> phoneNumbers;
    RecyclerView phoneRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    PhonenoRecyclerAdapter phonenoRecyclerAdapter;

    ContactDetails editContactDetails;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        gfunc = new Gfunc();
        phoneNumbers = new ArrayList<>();
        Intent intent = getIntent();
        editContactDetails = (ContactDetails) intent.getSerializableExtra("contactDetail");
        mode = (editContactDetails == null) ? 0 : 1;
        mainTitle = findViewById(R.id.MainTitle);
        if (mode == 1) {
            String firstName = gfunc.capitalize(editContactDetails.getName().getFirstName());
            String lastName = gfunc.capitalize(editContactDetails.getName().getLastName());
            mainTitle.setText(firstName + " " + lastName);
        }
        nameTextView = findViewById(R.id.fullNameTextView);
        emailTextView = findViewById(R.id.EmailTextView);
        addressTextView = findViewById(R.id.AddressTextView);

        nameLayout = findViewById(R.id.NameLayout);
        nameLayout.setVisibility(View.GONE);
        addressLayout = findViewById(R.id.AddressLayout);
        addressLayout.setVisibility(View.GONE);

        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        emailET = findViewById(R.id.EmailEditText);
        emailET.setVisibility(View.GONE);
        streetET = findViewById(R.id.street);
        cityET = findViewById(R.id.city);
        stateET = findViewById(R.id.state);
        countryET = findViewById(R.id.country);
        zipcodeET = findViewById(R.id.zipcode);
        notesET = findViewById(R.id.NotesEditText);

        addressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressLayout.getVisibility() == View.VISIBLE) {
                    String address = "";
                    String street = streetET.getText().toString().trim();
                    String city = cityET.getText().toString().trim();
                    String state = stateET.getText().toString().trim();
                    String country = countryET.getText().toString().trim();
                    String zipcode = zipcodeET.getText().toString().trim();
                    if (street.length() != 0)
                        address += gfunc.capitalize(street) + ", ";
                    if (city.length() != 0)
                        address += gfunc.capitalize(city) + ", ";
                    if (state.length() != 0)
                        address += gfunc.capitalize(state) + ", ";
                    if (country.length() != 0)
                        address += gfunc.capitalize(country) + ", ";
                    if (zipcode.length() != 0)
                        address += gfunc.capitalize(zipcode);
                    if (address.length() == 0) {
                        addressTextView.setText("Address");
                    } else {
                        addressTextView.setText(address);
                    }
                    addressLayout.setVisibility(View.GONE);
                } else {
                    addressLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emailET.getVisibility() == View.VISIBLE) {
                    String email = emailET.getText().toString().trim();
                    if (email.length() > 0) emailTextView.setText(email);
                    else emailTextView.setText("Email");
                    emailET.setVisibility(View.GONE);
                } else {
                    emailET.setVisibility(View.VISIBLE);
                }
            }
        });

        addPhoneNumberButton = findViewById(R.id.addphonenumberButton);
        addPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertaddphoneno(false);
            }
        });

        //Buttons
        upbutton = findViewById(R.id.upbutton);
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult(null);
            }
        });

        // Setting up recycler view
        phoneRecyclerView = findViewById(R.id.PhoneNumberList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        phoneRecyclerView.setLayoutManager(mLayoutManager);
        phonenoRecyclerAdapter = new PhonenoRecyclerAdapter(getApplicationContext(), phoneNumbers);
        phoneRecyclerView.setAdapter(phonenoRecyclerAdapter);

        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameLayout.getVisibility() == View.VISIBLE) {
                    String name = "";
                    name = gfunc.capitalize(firstNameET.getText().toString().trim()) + " " + gfunc.capitalize(lastNameET.getText().toString().trim());
                    if (name.length() <= 1) {
                        nameTextView.setText("Name");
                    } else {
                        nameTextView.setText(name);
                    }
                    nameLayout.setVisibility(View.GONE);
                } else {
                    nameLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        addUserButton = findViewById(R.id.AddUserButton);
        if (mode == 1) addUserButton.setText("Save");
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInputsandReturn();
            }
        });

        canceladdPhoneNo = findViewById(R.id.CancelAddUserButton);
        canceladdPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnResult(null);
            }
        });

        if (editContactDetails != null)
            fillDetails(editContactDetails);
    }

    private void fillDetails(ContactDetails contactDetails) {
        String firstName = gfunc.capitalize(contactDetails.getName().getFirstName());
        String lastName = gfunc.capitalize(contactDetails.getName().getLastName());
        nameTextView.setText(firstName + " " + lastName);
        firstNameET.setText(firstName);
        lastNameET.setText(lastName);
        Address contactAddress = contactDetails.getAddress();
        if (contactAddress != null) {
            String street = gfunc.capitalize(contactAddress.getStreet());
            String state = gfunc.capitalize(contactAddress.getState());
            String city = gfunc.capitalize(contactAddress.getCity());
            String country = gfunc.capitalize(contactAddress.getCountry());
            String zipcode = String.valueOf(contactAddress.getZipCode());
            String address = "";
            if (street.length() != 0) {
                streetET.setText(street);
                address += street + ", ";
            }
            if (city.length() != 0) {
                cityET.setText(city);
                address += city + ", ";
            }
            if (state.length() != 0) {
                stateET.setText(state);
                address += state + ", ";
            }
            if (country.length() != 0) {
                countryET.setText(country);
                address += country + ", ";
            }
            if (zipcode.length() != 0) {
                zipcodeET.setText(zipcode);
                address += zipcode;
            }
            if (address.length() == 0) {
                addressTextView.setText("Address");
            } else {
                addressTextView.setText(address);
            }
        }
        if (contactDetails.getEmail() != null && contactDetails.getEmail().length() > 0) {
            emailTextView.setText(contactDetails.getEmail());
            emailET.setText(contactDetails.getEmail());
        }
        phoneNumbers.addAll(contactDetails.getPhone());
        phonenoRecyclerAdapter.notifyDataSetChanged();
        if(contactDetails.getNotes() != null)
            notesET.setText(contactDetails.getNotes());
    }

    @Override
    public void onBackPressed() {
        returnResult(null);
    }

    private void alertaddphoneno(boolean error) {
        phonenotype = "Mobile";
        final AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        final View phoneaddView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.alertphoneadd, (RelativeLayout) findViewById(R.id.alertphoneaddcontainer)
        );
        builder.setView(phoneaddView);
        phonenoType = phoneaddView.findViewById(R.id.PhonenoType);
        addPhoneNo = phoneaddView.findViewById(R.id.ConfirmAddition);
        canceladdPhoneNo = phoneaddView.findViewById(R.id.CancelAddition);
        phoneNumberET = phoneaddView.findViewById(R.id.getPhoneNo);
        if (error) {
            phoneNumberET.setError("Contact must have atleast 1 phone number");
            phoneNumberET.requestFocus();
        }
        canceladdPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        addPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumberFetched = phoneNumberET.getText().toString();
                if (phoneNumberFetched.length() == 0) {
                    phoneNumberET.setError("Please Enter Phone number");
                    phoneNumberET.requestFocus();
                    return;
                }
                PhoneNumber phoneNumber = new PhoneNumber(Long.parseLong(phoneNumberFetched), phonenotype);
                phoneNumbers.add(phoneNumber);
                phonenoRecyclerAdapter.notifyDataSetChanged();
                alertDialog.cancel();
            }
        });

        setupSpinner(phonenoType);
        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

    private void checkInputsandReturn() {
        String name = firstNameET.getText().toString().trim().toLowerCase();
        if (name.length() == 0) {
            nameLayout.setVisibility(View.VISIBLE);
            firstNameET.setError("Please enter a first name to identify the person");
            firstNameET.requestFocus();
            return;
        }
        if (!name.matches("[a-zA-Z]+")) {
            nameLayout.setVisibility(View.VISIBLE);
            firstNameET.setError("Name should only contain letters a-z");
            firstNameET.requestFocus();
            return;
        }
        String lastName = lastNameET.getText().toString().trim().toLowerCase();
        if (lastName.length() != 0 && !lastName.matches("[a-zA-Z]+")) {
            nameLayout.setVisibility(View.VISIBLE);
            lastNameET.setError("Name should only contain letters a-z");
            lastNameET.requestFocus();
            return;
        }
        if (phoneNumbers.size() == 0) {
            alertaddphoneno(true);
            return;
        }
        ContactDetails contactDetails = new ContactDetails();
        Name nameNode = new Name(name, lastName);
        ContactDetails temp = contactTree.retrieveItem(nameNode);
        if (temp != null && (mode != 0 && !editContactDetails.getName().comp(nameNode))) {
            nameLayout.setVisibility(View.VISIBLE);
            firstNameET.setError("Name Already exists");
            firstNameET.requestFocus();
            return;
        }
        contactDetails.setName(nameNode);
        contactDetails.setPhoneNumbers(phoneNumbers);
        String street = streetET.getText().toString().trim().toLowerCase();
        String state = stateET.getText().toString().trim().toLowerCase();
        String city = cityET.getText().toString().trim().toLowerCase();
        String country = countryET.getText().toString().trim().toLowerCase();
        String zipcode = zipcodeET.getText().toString().trim().toLowerCase();

        if (street.length() > 0 || state.length() > 0 || city.length() > 0 || country.length() > 0 || zipcode.length() > 0) {
            Address address = new Address(street, city, state, country, Long.parseLong(zipcode));
            contactDetails.setAddress(address);
        } else {
            contactDetails.setAddress(null);
        }
        contactDetails.setEmail(emailET.getText().toString().trim());
        contactDetails.setNotes(notesET.getText().toString());
        if (mode == 1 && !editContactDetails.getName().comp(contactDetails.getName()))
            mode = 2;
        returnResult(contactDetails);
    }

    public void returnResult(ContactDetails contactDetails) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        if (contactDetails != null) {
                            data.putExtra("contactDetails", contactDetails);
                            data.putExtra("mode", mode);
                            if (mode == 2) data.putExtra("oldData", editContactDetails);
                            setResult(RESULT_OK, data);
                            finish();
                        } else {
                            setResult(RESULT_CANCELED, data);
                            finish();
                        }
                    }
                })
                .setNegativeButton("Decline", null);
        if (contactDetails == null) {
            alert.setTitle("Do you want to discard contact?");
            if (mode != 0) alert.setTitle("Changes made won't be saved!");
        } else {
            alert.setTitle("Confirm addition of contact?");
            if (mode == 1 || mode == 2) alert.setTitle("Save changes made?");
        }
        alert.show();
    }

    public void setupSpinner(Spinner phonenoType) {
        ArrayAdapter phoneNoTypeSelector = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.array_phoneno_type, R.layout.spinner_user_item_text);

        phoneNoTypeSelector.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        phonenoType.setAdapter(phoneNoTypeSelector);
        phonenoType.setSelection(0);
        phonenoType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                String[] arr = getResources().getStringArray(R.array.array_phoneno_type);
                if (!TextUtils.isEmpty(selection)) {
                    for (int j = 0; j < arr.length; j++) {
                        if (selection.equals(arr[j])) {
                            phonenotype = arr[j];
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                phonenotype = "Mobile";
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }
}