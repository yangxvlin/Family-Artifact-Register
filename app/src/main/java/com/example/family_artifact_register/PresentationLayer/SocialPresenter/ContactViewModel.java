package com.example.family_artifact_register.PresentationLayer.SocialPresenter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.family_artifact_register.FoundationLayer.SocialModel.User;

import java.util.ArrayList;

public class ContactViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> contacts = new MutableLiveData<>();

    public ContactViewModel() {

    }

    public MutableLiveData<ArrayList<String>> getContact() { return contacts; }
}
