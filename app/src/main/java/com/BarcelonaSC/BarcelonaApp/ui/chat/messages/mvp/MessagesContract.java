package com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;

import java.util.List;

/**
 * Created by Pedro Gomez on 25/01/2018.
 */

public class MessagesContract {

    public interface ModelResultListener {

        void onGetMessagesSuccess(List<MessageModelView> messagesData);
        void onGetMessagesFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickMessages(MessageModelView messageData);
        void loadMessages();
        void findByName(String name);
        boolean haveFriends();

    }

    public interface View {

        void updateMessages(List<MessageModelView> messagesData);
        void refresh();

    }

}
