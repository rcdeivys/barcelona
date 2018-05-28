package com.losingtimeapps.whitebrand;

import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.ChooseProfile;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileContract;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void addition_isCorrect() throws Exception {
        ChooseProfile chooseProfile = new ChooseProfile();
        chooseProfile.initProfile(Mockito.mock(ChooseProfileContract.View.class));
        int value = chooseProfile.sumar();
        assertEquals(10, value);

    }
}