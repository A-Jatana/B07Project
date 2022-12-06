package com.example.b07projectapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
    //
    public void test1 (){
        when (view.getUsername()).thenReturn("");
        when (view.getPassword()).thenReturn("");

        Presenter p = new Presenter (model, view);
        p.checkLogin();
        verify(view).displayMessage("Fields cannot be empty!");
    }
}