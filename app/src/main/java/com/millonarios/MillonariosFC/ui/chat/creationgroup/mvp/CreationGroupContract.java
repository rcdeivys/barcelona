package com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp;



import android.net.Uri;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;

import java.util.List;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */

public class CreationGroupContract {

    public interface ModelResultListener {

        void onCreationGroupSuccess(Grupo group);
        void onCreationGroupFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void createGroupChat(String groupName,Uri encodedImage);
        void onFutureMemberPress(Amigos friendData);
        void initGroupMembers(List<Amigos> groupMembers);
    }

    public interface View {

        void showProgress();
        void hideProgress();
        void updateSelectedMembers(List<Amigos> friends);
        void lunchChatActivity(Grupo newGroup);
        void refresh();

    }

}
