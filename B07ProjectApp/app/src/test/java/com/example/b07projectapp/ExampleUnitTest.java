package com.example.b07projectapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Mock
    Control.View view;

    @Mock
    Control.Model model;

    @Test
    //Both fields are empty
    public void test1 (){
        when (view.getUsername()).thenReturn("");
        when (view.getPassword()).thenReturn("");
        AdminPresenter p = new AdminPresenter(model, view);
        p.checkLogin();
        verify(view).displayMessage("Fields cannot be empty!");
    }

    @Test
    //One field is empty
    public void test2 (){

        when (view.getUsername()).thenReturn("");
        when (view.getPassword()).thenReturn("g");
        AdminPresenter p = new AdminPresenter(model, view);
        p.checkLogin();
        verify(view).displayMessage("Fields cannot be empty!");
    }

    @Test
    //Both username and password are incorrect
    public void test3 (){
        /*
        FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class)
  Mockito.when(mockFirestore.someMethodCallYouWantToMock()).thenReturn(something)

  DatabaseInteractor interactor = new DatabaseInteractor(mockFirestore)

         */
        //FirebaseDatabase fd= Mockito.mock(FirebaseDatabase.class);

        when (view.getUsername()).thenReturn("h");
        when (view.getPassword()).thenReturn("h");
        //when (model.isFound("h","h",view)).thenReturn();
        AdminPresenter p = new AdminPresenter(model, view);
        p.checkLogin();
        verify(view).displayMessage("Incorrect username or password");
    }

    @Test
    //Username is incorrect, password is correct
    public void test4 (){
        when (view.getUsername()).thenReturn("h");
        when (view.getPassword()).thenReturn("g");
        AdminPresenter p = new AdminPresenter(model, view);
        p.checkLogin();
        verify(view).displayMessage("Incorrect username or password");
    }
    /*
    //Password is incorrect, username is correct
    public void test5 (){
        when (view.getUsername()).thenReturn("g");
        when (view.getPassword()).thenReturn("h");
        AdminPresenter p = new AdminPresenter (model, view);
        p.checkLogin();
        verify(view).displayMessage("Incorrect username or password");
    }

     */
    @Test
    //Correct password and username
    public void test6 (){
        when (view.getUsername()).thenReturn("g");
        when (view.getPassword()).thenReturn("h");
        AdminPresenter p = new AdminPresenter(model, view);
        p.checkLogin();
        verify(view).displayMessage("Login successful!");
    }

}